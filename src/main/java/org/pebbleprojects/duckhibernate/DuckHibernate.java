package org.pebbleprojects.duckhibernate;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Iterator;

public final class DuckHibernate extends JavaPlugin {

    private int i = -1;

    @Override
    public void onEnable() {

        getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            if (getServer().getOnlinePlayers().size() == 0) {
                if (i == -1) {
                    final Iterator<World> w = getServer().getWorlds().iterator();

                    while (w.hasNext()) w.next().save();

                    i = 0;
                    return;
                }

                try {
                    Thread.sleep(1000L);

                    int u = 0;

                    final Iterator<World> w = getServer().getWorlds().iterator();

                    while (w.hasNext()) {
                        final Chunk[] c;
                        int l = (c = w.next().getLoadedChunks()).length;

                        for(int i = 0; i < l; ++i) {
                            if (c[i].unload(true)) u++;
                        }
                    }

                    if (u > 0) System.gc();

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, 0L, 1L);
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
        getServer().shutdown();
    }
}
