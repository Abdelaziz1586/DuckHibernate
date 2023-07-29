package org.pebbleprojects.duckhibernate;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class DuckHibernate extends JavaPlugin {

    @Override
    public void onEnable() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (getServer().getOnlinePlayers().size() > 0) return;

                for (final World world : getServer().getWorlds()) {
                    for (final Chunk chunk : world.getLoadedChunks()) {
                        chunk.unload(true);
                        Runtime.getRuntime().freeMemory();
                    }
                }
            }
        }.runTaskTimer(this, 0, 20);
    }

    @Override
    public void onDisable() {
        getServer().shutdown();
    }
}
