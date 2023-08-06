package org.pebbleprojects.duckhibernate;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public final class DuckHibernate extends JavaPlugin {

    private boolean b = false;

    public void onDisable() {
        getServer().shutdown();
    }

    public void onEnable() {
        getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            if (getServer().getOnlinePlayers().size() == 0) {
                if (!b) {
                    for (World world : getServer().getWorlds()) world.save();
                    b = true;
                    return;
                }
                try {
                    Thread.sleep(1000L);
                    a();
                } catch (Exception ignored) {
                }
            }
        }, 0L, 1L);
        a();
    }

    private void a() {
        byte b = 0, b1;
        int i;
        for (World world : Bukkit.getWorlds()) {
            Chunk[] arrayOfChunk;
            for (i = (arrayOfChunk = (world.getLoadedChunks())).length, b1 = 0; b1 < i; b1++) {
                if (arrayOfChunk[b1].unload(true))
                    b++;
            }
        }
        if (b > 0) {
            b();
        }
    }

    private void b() {
        Runtime.getRuntime().freeMemory();
        System.gc();
        Runtime.getRuntime().freeMemory();
    }
}
