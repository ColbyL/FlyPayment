package com.ahellhound.bukkit.flypayment;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Flight {
    // Messages constructor
    private Messages Messages = new Messages();
    // Scheduler constructor
    private Scheduler Scheduler = new Scheduler();

    // Enables flight
    public void enableFlight(Player p) {

        p.setAllowFlight(true);
        p.setFlying(true);
        // Adds player to hashmap to find time left on flight
        Scheduler.addPlayerTimeLeft(p, System.currentTimeMillis());
    }

    // Disables flight
    public void disableFlight(Player p) {
        p.setAllowFlight(false);
        p.setFlying(false);
        // removes player from player time left hashmap
        Scheduler.removeFlightTimer(p);;

    }

    // 10 second warning and removes player from hashmap protectFlightv
    public void flightRemoveWarning(Player p) {
        p.sendMessage(ChatColor.RED + Messages.flyingOffMessage(p));
        // Removes protected flight
        Scheduler.removeProtectFlight(p);
        // removes player from player time left hashmap
        Scheduler.removePlayerTimeLeft(p);

    }

    // Disbales flight
    public void disableAllPlayersFlight(Player p) {
        p.setAllowFlight(false);
        p.setFlying(false);

    }

    public boolean checkPlayerFlight(Player p) {
        // checks if player is allowed to fly
        if (p.getAllowFlight()) {
            // returns true if true
            return true;
        }
        // returns false if player not allowed to fly
        return false;
    }

}
