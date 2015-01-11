package com.ahellhound.bukkit.flypayment;

import org.bukkit.entity.Player;

public class BanList {
    //FileConfiguration constructor
    private FileConfiguration FileConfiguration = new FileConfiguration();

    public void addPlayerBanList(String p, String timeFormatted) {
        //Converts from raw time to nano time
        long timeNano = Utilities.convertTimeFormatToNano(timeFormatted);
        //creates player name in file
        FileConfiguration.getBanList().set(p, timeNano);
        //Reloads ban list
        FileConfiguration.saveBanList();

    }

    public boolean checkBanList(Player p) {
        //Checks file for player name
        if (FileConfiguration.getBanList().getString(p.getName()) != null) {

            return true;
        }

        return false;
    }

}
