package com.ahellhound.bukkit.flypayment;

import java.util.logging.Logger;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
    Logger log = Logger.getLogger("Minecraft");

    // eliminate Variables
    public static Economy econ = null;
    // Config instance variable
    private static Main instance;
    // Reload config instance variable
    private static Main reloadInstance;
    //Configuration constructor
    private Utilities Utilities = new Utilities();
    //banlist constructor
    FileConfiguration FileConfiguration = new FileConfiguration();
    //Ban list constructor
    private BanList BanList = new BanList();

    @Override
    public void onEnable() {
        // Config instance
        instance = this;
        // reloads config
        reloadConfiguration();
        //reloads ban list
        FileConfiguration.reloadBanListConfig();
        // checks if file exists

        if (!setupEconomy()) {
            log.severe(String.format("[%s] - Disabled! You NEED Vault, with an Economy plugin and a Permission plugin!", getDescription()
                    .getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        //saves default config
        saveDefaultConfig();
        //saves banlist
        FileConfiguration.saveDefaultBanList();
        //gets plugin manager
        getServer().getPluginManager().registerEvents(this, this);
        log.info("[" + getDescription().getName() + "] " + getDescription().getName() + " version " + getDescription().getVersion()
                + " is now enabled.");
        log.info("[" + getDescription().getName() + "]" + " Made By AhellHound");
    }

    // Main instance
    public static Main getInstance() {
        return instance;

    }

    // Reload instance
    public static Main getReloadInstance() {
        return reloadInstance;

    }

    // Reloads configuration, creates tiers
    public void reloadConfiguration() {
        reloadConfig();
        reloadInstance = this;

    }

    // Hooks into Vault
    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(
                net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            econ = economyProvider.getProvider();
        }

        return (econ != null);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        // Variables
        Flight Flight = new Flight();
        Scheduler Scheduler = new Scheduler();
        // Player check
        Messages Messages = new Messages();
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + Messages.playerErrorMessage());
            return true;
        }

        // Variables after Player Check
        Player p = (Player) sender;
        PlayerInventory inventory = p.getInventory();

        // Arg's check
        if (args.length != 1) {
            p.sendMessage(ChatColor.RED + Messages.commandUsageErrorMessage(p));
            return true;
        }
        // TierArea permission-creator
        Configuration configuration = new Configuration();
        int tier = configuration.getTier(p);

        // If permission node isn't within the 1-5 range
        if (tier == 0) {
            p.sendMessage(ChatColor.RED + Messages.permissionErrorMessage(p) /* "You lack the permission to do this!" */);
            return true;
        }

        // Checks to see if 'Flying' is enabled
        if (args[0].equalsIgnoreCase("on")) {
            if (p.getAllowFlight()) {
                p.sendMessage(ChatColor.GREEN + Messages.flyingAlreadyEnabledMessage(p));
                // returns true
                return true;
            }
            // If player has flyp.bypass, gives them fly without checks TEST
            if (p.hasPermission("flyp.bypass")) {
                p.sendMessage(ChatColor.GREEN + Messages.flyingEnabledMessage(p));
                // Enables flight
                Flight.enableFlight(p);
                // Adds to safe player scheduler
                Scheduler.addSafePlayer(p);
                return true;
            }

            // Checks if amnt is greater than 0 in config, then checks if
            // inventory contains required items, respectively
            Configuration config = new Configuration();
            int itemChargeAmount = config.getItemChargeAmount(tier);
            String itemChargeEnum = config.getItemChargeEnum(tier).toString();
            long timerAmount = config.getTimerAmount(tier);
            int expChargeAmount = config.getExpChargeAmount(tier);
            int moneyChargeAmount = config.getMoneyChargeAmount(tier);

            // Item charge amount check
            if (itemChargeAmount > 0 && !inventory.contains(Material.valueOf(itemChargeEnum), itemChargeAmount)) {

                p.sendMessage(ChatColor.RED
                        + Messages.itemRequirementMessage(p, itemChargeAmount, Material.getMaterial(itemChargeEnum).name()));
                return true;

            }

            // Checks if ChargeEXP is greater than 0
            if (expChargeAmount > 0) {

                int remainingEXPRequired = (expChargeAmount - p.getTotalExperience());

                // Checks if player has enough EXP
                if (p.getTotalExperience() < expChargeAmount) {
                    p.sendMessage(ChatColor.RED + Messages.EXPRequirementMessage(p, remainingEXPRequired));
                    return true;
                }

            }

            // Checks if ChargeMoney is greater than 0
            if (moneyChargeAmount > 0) {
                double moneyRequired = (moneyChargeAmount - econ.getBalance(p.getName()));
                // Checks if player has enough Money
                if (econ.getBalance(p.getName()) < moneyChargeAmount) {
                    p.sendMessage(ChatColor.RED
                            + Messages.moneyRequirementMessage(p, moneyRequired, econ.currencyNamePlural(), econ.currencyNameSingular()));
                    return true;
                }
            }

            // Put Hunger Drain Check Here

            // Checks if they're allowed to fly and removes items
            if (!p.getAllowFlight()) {
                PlayerPayments PlayerPayments = new PlayerPayments();
                // Checks if config wants to take items
                if (itemChargeAmount > 0) {
                    // Takes items
                    PlayerPayments.removePlayerItem(p, tier);
                }
                // Checks if config wants to take exp
                if (expChargeAmount > 0) {
                    PlayerPayments.removePlayerExp(p, tier);
                }
                // Checks if config wants to take money
                if (moneyChargeAmount > 0) {
                    PlayerPayments.removePlayerMoney(p, tier);
                }
                // Checks permission for timer and permission 'flyp.notimeout'
            }
            if (timerAmount != 0 && !p.hasPermission("flyp.notimeout")) {
                p.sendMessage(ChatColor.GREEN + Messages.flyingEnabledMessage(p));
                // Starts timer
                Scheduler.enableTimerTier(p, tier);
                // enables flight
                Flight.enableFlight(p);
                // Adds to safe fall damage list
                Scheduler.addSafePlayer(p);
                // If time limits enabled, sends player message, enables
                // flight,and adds player to timer
            } else {
                // sends message to player
                p.sendMessage(ChatColor.GREEN + Messages.flyingEnabledNoTimelimitMessage(p));
                // enables dlight
                Flight.enableFlight(p);
                // Adds to safe player scheduler
                Scheduler.addSafePlayer(p);
            }
        }

        // The 'off' argument
        else if (args[0].equalsIgnoreCase("off")) {
            if (!p.getAllowFlight()) {
                p.sendMessage(ChatColor.GREEN + Messages.flyingAlreadyDisabledMessage(p));
                return true;
            }
            p.sendMessage(ChatColor.GREEN + Messages.flyingDisabledMessage(p));
            Flight.disableFlight(p);
            // Removes timer
            Scheduler.cancelFlightTimer(p);
            // Reload argument
        } else if (args[0].equalsIgnoreCase("reload")) {
            // permission check
            if (!p.hasPermission("flyp.reload")) {
                p.sendMessage(ChatColor.RED + Messages.permissionErrorMessage(p));
                return true;
            }

            reloadConfiguration();
            saveDefaultConfig();
            // clears player from safeplayer hashmap
            Scheduler.disableAllPlayersFlight();
            getServer().getPluginManager().registerEvents(this, this);
            log.info("[" + getDescription().getName() + "] " + getDescription().getName() + " version " + getDescription().getVersion()
                    + " is now enabled.");
            log.info("[" + getDescription().getName() + "]" + " Made By AhellHound");
            p.sendMessage(ChatColor.GREEN + "FlyPayment successfully reloaded!");
        } else if (args[0].equalsIgnoreCase("disableAll")) {
            // permission check
            if (!p.hasPermission("flyp.disableall")) {
                p.sendMessage(ChatColor.RED + Messages.permissionErrorMessage(p));
                return true;
            }
            // Disables everyone's flight
            Scheduler.disableAllPlayersFlight();

        } else if (args[0].equalsIgnoreCase("timeLeft")) {
            // permission check
            if (!p.hasPermission("flyp.timeleft")) {
                p.sendMessage(ChatColor.RED + Messages.permissionErrorMessage(p));
                // Returns true
                return true;
            }
            // checks if player is allowed to fly
                if((Utilities.getTimerIsZero(p)) || (p.hasPermission("flyp.notimeout"))){
                    
                    p.sendMessage(ChatColor.RED + Messages.flyingHasNoTimeLimitMessage(p));
                    return true;
            }else if (Flight.checkPlayerFlight(p)) {

                // gets player's time left
                p.sendMessage(ChatColor.GREEN + Messages.timeLeftCommandMessage(p, Scheduler.getPlayerTimeLeft(p, tier)));
                return true;
            
            }else{
            //if player is not flying, they can't get time left message
            p.sendMessage(ChatColor.RED + Messages.timeLeftNotFlyingMessage(p));
            return true;
            
            }
        //fly ban
        } else if (args[0].equalsIgnoreCase("ban")) {
            //permission check
            if (!(p.hasPermission("FlyP.ban.add"))){
                //Sends player no permission message
                p.sendMessage(Messages.permissionErrorMessage(p));
                //returns true
                return true;
            }
            //if no argument is made for ban time
            if (args[2].equalsIgnoreCase(null)){
                //Checks if config allows permanent bans
                if (configuration.getFlyBanRequireTime()){
                  //Sends player message requiring them to enter a ban time
                    p.sendMessage(Messages.requiredBanTimeCommandMessage(p));
                    return true;
                } else {
                    //TODO Change to format
                    String timeRaw = args[2].toString();
                    //TODO Change to format
                    String timeBannedFormat = ("Utilites time changed to nice format");
                    //banned player's name
                    String bannedP = args[1].toString();
                    //adds player to ban list
                    BanList.addPlayerBanList(bannedP, timeRaw);
                    //Sends message to command sender that ban was made
                    p.sendMessage(Messages.banAddedMessage(p, bannedP, timeBannedFormat));
                    //returns true
                   return true; 
                }
                
            }
            
            
        }else {
            p.sendMessage(ChatColor.RED + Messages.invalidArgumentMessage(p));
            return true;
        }

        // Method return
        return true;
    }

    // player fall damage event
    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerFallDamageEvent(EntityDamageEvent event) {
        EventHandlers EventHandlers = new EventHandlers();
        EventHandlers.onPlayerFallDamage(event);

    }

    // player quit event
    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerQuitEvent(PlayerQuitEvent event) {
        EventHandlers EventHandlers = new EventHandlers();
        EventHandlers.onPlayerQuit(event);

    }

    // player kick event
    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerKickEvent(PlayerKickEvent event) {
        EventHandlers EventHandlers = new EventHandlers();
        EventHandlers.onPlayerKick(event);

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void serverReloadEvent(ServerCommandEvent event) {
        EventHandlers EventHandlers = new EventHandlers();
        EventHandlers.onServerReload(event);

    }

}
