package com.ahellhound.bukkit.flypayment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Scheduler {

    // Utilites contructor
    private Utilities Utilities = new Utilities();
    // Messages constructor
    private Messages Messages = new Messages();
    // Hashmap of schedualr ID's
    private static Map<Player, Integer> schedulerID = new HashMap<Player, Integer>();
    // Hasmap of player's with protect flight
    private static Map<Player, Integer> protectFlightv = new HashMap<Player, Integer>();
    // Hasmap of players safe from fall damage
    private static HashSet<String> safePlayers = new HashSet<String>();
    // Configuration contructor
    private Configuration objConfig = new Configuration();
    // Hashmap of players start time
    private static Map<Player, Long> schedulerStartTime = new HashMap<Player, Long>();

    public void enableTimerTier(final Player p, int tier) {

        long timer = objConfig.getTimerAmount(tier);

        // Time left arithmetic
        String timeForm = Utilities.convertTicksToMinutes(timer);
        //Determines if minutes or seconds
        String minutesOrSeconds = Utilities.convertMinutesOrSeconds(timer);

        schedulerID.put(p,
                Main.getInstance().getServer().getScheduler().scheduleSyncDelayedTask((Plugin) Main.getInstance(), new Runnable() {
                    public void run() {
                        //Flight constructor
                        Flight Flight = new Flight();
                        Flight.disableFlight(p);
                        cancelFlightTimer(p);
                    }
                }, timer));

        p.sendMessage(ChatColor.GREEN + Messages.timeLeftMessage(p, timeForm, minutesOrSeconds));
        
        if (timer > 200) {
            protectFlightv.put(p,
                    Main.getInstance().getServer().getScheduler().scheduleSyncDelayedTask((Plugin) Main.getInstance(), new Runnable() {
                        public void run() {
                            final Player player = p;
                            player.sendMessage(ChatColor.RED + Messages.flyingOffMessage(p));
                            // Removes player from flight
                            // hashmap
                            protectFlightv.remove(p);
                            
                        }
                    }, timer - 200));
        }

    }

    public void removeFlightTimer(Player p) {

        if (schedulerID.containsKey(p)) {
            Main.getInstance().getServer().getScheduler().cancelTask(schedulerID.get(p));
            schedulerID.remove(p);
        }
        if (safePlayers.contains(p.getName())) {
            safePlayers.remove(p.getName());
        }
        if (protectFlightv.containsKey(p)) {
            Main.getInstance().getServer().getScheduler().cancelTask(protectFlightv.get(p));
            protectFlightv.remove(p);
        }
       /* if (schedulerStartTime.containsKey(p)) {
            
            schedulerStartTime.remove(p); 
        }*/
    }

    public void disableAllPlayersFlight() {
        Logger.getLogger("Minecraft").info("HashMap safePlayers = " + safePlayers);
        Logger.getLogger("Minecraft").info("HashMap protectFlightv = " + protectFlightv);
        Logger.getLogger("Minecraft").info("HashMap schedulerID = " + schedulerID);

        for (Map.Entry<Player, Integer> entry : schedulerID.entrySet()) {
            // Configuration class initialization
            Flight Flight = new Flight();
            Flight.disableFlight(entry.getKey());
            cancelFlightTimer(entry.getKey());
            entry.getKey()
                    .sendMessage(
                            ChatColor.RED
                                    + "The Server Administrator has disabled everyone's flight, most likely due to a pending server reload or restart.");
            Logger.getLogger("Minecraft").info(
                    "[FlyPayment] Please WAIT for a Confirmation Message from Fly Payment before reloading the server.");
            if (protectFlightv.isEmpty()) {

                Logger.getLogger("Minecraft").info("[FlyPayment] You may Reload/Restart your server!");
            }
        }
    }

    public void onServerReload() {
        Logger.getLogger("Minecraft")
                .info("[Fly Payment] Any players in flight may be stuck in blocks, or in lava, if /Fly DisableAll wasn't used before reloading the server.");
        for (Map.Entry<Player, Integer> entry : schedulerID.entrySet()) {

            Flight Flight = new Flight();
            Flight.disableFlight(entry.getKey());
            entry.getKey().sendMessage(
                    ChatColor.RED + "The Server Administrator has disabled everyone's flight, most likely due to a pending server reload.");

            int x = (int) entry.getKey().getLocation().getX();
            int y = (int) entry.getKey().getLocation().getY();
            int z = (int) entry.getKey().getLocation().getZ();
            if (y > 0) {
                for (int i = y; i >= 0; i--) {
                    if (!(entry.getKey().getWorld().getBlockAt(x, i, z).isEmpty())) {
                        entry.getKey().teleport(new Location(entry.getKey().getWorld(), x, (i + 1), z));
                        cancelFlightTimer(entry.getKey());
                        return;
                    }

                }
                cancelFlightTimer(entry.getKey());
                // Anything past here means there isn't a block under the
                // player.
            }
        }
    }

    public void addSafePlayer(Player p) {

        safePlayers.add(p.getName());

    }

    public void removeSafePlayer(String p) {
        safePlayers.remove(p);
    }

    public boolean getSafePlayer(String p) {
        if (safePlayers.contains(p)) {
            return true;
        } else {
            return false;
        }
    }

    public void cancelFlightTimer(Player p) {
        if (schedulerID.containsKey(p)) {
            Main.getInstance().getServer().getScheduler().cancelTask(schedulerID.get(p));
            schedulerID.remove(p);
        }
        if (protectFlightv.containsKey(p)) {
            Main.getInstance().getServer().getScheduler().cancelTask(protectFlightv.get(p));
            protectFlightv.remove(p);
        }
        /*if (schedulerStartTime.containsKey(p)) {
            
            schedulerStartTime.remove(p); 
        }*/
    }
    //clears safe player hashmap
    public void clearSafePlayers(Player p) {

        safePlayers.clear();
    }
    //clears protectlfight hashmap
    public void clearProtectFlight(Player p) {

        protectFlightv.clear();
    }
    //removes player from protect flight hashmap
    public void removeProtectFlight(Player p) {
        protectFlightv.remove(p.getName());

    }
    //gets player time left
    public String getPlayerTimeLeft(Player p, int tier){
        long timerAmount = objConfig.getTimerAmount(tier);
        long timerAmountCalculated = ((timerAmount/20)*1000);
        //retrieves time player started flying
        long playerStartTime = schedulerStartTime.get(p);
        //Gets time left in mili seconds
        long playerTimeLeftMili = ((playerStartTime + timerAmountCalculated) - System.currentTimeMillis());
        //formats string
        String playerTimeString = String.format("%d min, %d sec", 
                TimeUnit.MILLISECONDS.toMinutes(playerTimeLeftMili),
                TimeUnit.MILLISECONDS.toSeconds(playerTimeLeftMili) - 
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(playerTimeLeftMili)));
        //returns player time left
        return playerTimeString;
    }
    
    //Scheduler get player time
    public long getSchedulerStartTime (Player p){
        //Checks if player is in hashmap
        if (schedulerStartTime.containsKey(p)){
          //Gets player from hashmap
        long startTime = schedulerStartTime.get(p);
        //returns start time
        return startTime;
        }
        //error time of player isn't in hashmap
        long errorTime = 0L;
        //returns errorTime
        return errorTime;
    }

    // Adds Player time left 
    public void addPlayerTimeLeft(Player p, long currentTime) {
            schedulerStartTime.put(p, currentTime);
    }
    // removes Player time left
    public void removePlayerTimeLeft(Player p) {

        if (schedulerStartTime.containsKey(p)) {
 //removes player from hashmap
            schedulerStartTime.remove(p);
        }
    }
    

}
