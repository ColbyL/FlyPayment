package com.ahellhound.bukkit.flypayment;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerCommandEvent;

public class EventHandlers {
    // Scheduler constructor
    private Scheduler Scheduler = new Scheduler();
    // Configuration constructor
    private Configuration Configuration = new Configuration();

    // Quit event
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        Scheduler.removeFlightTimer(p);
    }

    // Kick event
    public void onPlayerKick(PlayerKickEvent event) {
        Player p = event.getPlayer();
        Scheduler.removeFlightTimer(p);
    }

    // Player fall damage event
    public void onPlayerFallDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {

            Player p = ((Player) event.getEntity());
            if (event.getCause() == DamageCause.FALL && Scheduler.getSafePlayer((((Player) event.getEntity()).getName()))
                    && (!p.hasPermission("flyp.takedamage"))) {

                event.setCancelled(true);
                Scheduler.removeSafePlayer(p.getName());

            }
        }

    }

    // Server reload event
    public void onServerReload(ServerCommandEvent event) {
        if (event.getCommand().equalsIgnoreCase("reload")) {
            if (Configuration.getReloadSafetyTeleport()) {
                Scheduler.onServerReload();
            }
        }
    }
}
