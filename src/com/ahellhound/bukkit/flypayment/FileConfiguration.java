package com.ahellhound.bukkit.flypayment;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

public class FileConfiguration {

    private YamlConfiguration banList = null;
    private File banListConfigFile = null;

    //Loads banlist config if not present
    public void reloadBanListConfig() {
        if (banListConfigFile == null) {
            banListConfigFile = new File(Main.getInstance().getDataFolder(), "banList.yml");
        }
        banList = YamlConfiguration.loadConfiguration(banListConfigFile);

        // Look for defaults in the jar
        InputStream defConfigStream = Main.getInstance().getResource("banList.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            banList.setDefaults(defConfig);
        }
    }

    //gets banlist file
    public YamlConfiguration getBanList() {
        if (banList == null) {
            reloadBanListConfig();
        }
        return banList;
    }

    //Saves ban list
    public void saveBanList() {
        if (banList == null || banListConfigFile == null) {
            return;
        }
        try {
            getBanList().save(banListConfigFile);
        } catch (IOException ex) {
            Main.getInstance().getLogger().log(Level.SEVERE, "Could not save config to " + banListConfigFile, ex);
        }
    }

    //saves default ban list
    public void saveDefaultBanList() {
        if (banListConfigFile == null) {
            banListConfigFile = new File(Main.getInstance().getDataFolder(), "customConfig.yml");
        }
        if (!banListConfigFile.exists()) {
            Main.getInstance().saveResource("banList.yml", false);
        }
    }

}
