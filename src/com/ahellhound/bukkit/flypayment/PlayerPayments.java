package com.ahellhound.bukkit.flypayment;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class PlayerPayments {
    // Config constructor
    private Configuration config = new Configuration();
    private Messages Messages = new Messages();

    public void removePlayerItem(Player p, int tier) {
        // Config variables
        int itemChargeAmount = config.getItemChargeAmount(tier);
        String itemChargeEnum = config.getItemChargeEnum(tier);

        PlayerInventory inventory = p.getInventory();
        // removes item(s)
        //makes the itemName usable in the Messages constructor
        String itemName = Material.getMaterial(itemChargeEnum).name();
        //test

        //Removes item from inventory
        inventory.removeItem(new ItemStack(Material.getMaterial(itemChargeEnum), itemChargeAmount));
        //Sends player message about what, and how much, of the item was removed
        p.sendMessage(ChatColor.GREEN + Messages.itemRemovedMessage(p, itemName, itemChargeAmount));
    }

    public void removePlayerExp(Player p, int tier) {
        //Charged amount of EXP
        int EXPChargeAmount = config.getExpChargeAmount(tier);
        // removes exp
        p.giveExp(EXPChargeAmount * -1);
        //sends player message
        p.sendMessage(ChatColor.GREEN + Messages.EXPRemovedMessage(p, EXPChargeAmount));
    }

    public void removePlayerMoney(Player p, int tier) {
        // Config variables
        int moneyChargeAmount = config.getMoneyChargeAmount(tier);
        //gets player economy account name
        String economyAccountName = config.getEconomyAccountName(tier);
        //gets player economy account true or false
        Boolean economyAccount = config.getEconomyAccount(tier);
        //Gets main class instance
        Main.getInstance();
        // Economy instance from Main
        Economy econ = Main.econ;
        // Withdraws money
        econ.withdrawPlayer(p.getName(), moneyChargeAmount);
        //sends player money
        p.sendMessage(ChatColor.GREEN + Messages.moneyRemovedMessage(p, moneyChargeAmount, econ.currencyNamePlural(), econ.currencyNameSingular()));
        // Puts money into account
        if (economyAccount) {
            econ.bankDeposit(economyAccountName, moneyChargeAmount);
        }
    }

}
