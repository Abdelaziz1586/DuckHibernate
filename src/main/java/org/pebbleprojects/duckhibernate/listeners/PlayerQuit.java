package org.pebbleprojects.duckhibernate.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.pebbleprojects.duckhibernate.DuckHibernate;

public class PlayerQuit implements Listener {

    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent event) {
        if (Bukkit.getOnlinePlayers().size() == 0) DuckHibernate.INSTANCE.startTimer();

    }

}
