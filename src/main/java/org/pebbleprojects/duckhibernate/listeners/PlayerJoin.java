package org.pebbleprojects.duckhibernate.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.pebbleprojects.duckhibernate.DuckHibernate;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        DuckHibernate.INSTANCE.stopTimer();
    }

}
