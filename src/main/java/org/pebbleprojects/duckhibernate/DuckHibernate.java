package org.pebbleprojects.duckhibernate;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.pebbleprojects.duckhibernate.listeners.PlayerJoin;
import org.pebbleprojects.duckhibernate.listeners.PlayerQuit;

import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

public final class DuckHibernate extends JavaPlugin {

    private Timer task;
    private int i = -1;
    public static DuckHibernate INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;

        new Thread(this::startTimer).start();

        final PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new PlayerJoin(), this);
        pm.registerEvents(new PlayerQuit(), this);

        getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            if (getServer().getOnlinePlayers().size() == 0) {
                if (i == -1) {
                    final Iterator<World> w = getServer().getWorlds().iterator();

                    while (w.hasNext()) w.next().save();

                    i = 0;
                    return;
                }

                try {
                    Thread.sleep(5000L);

                    int u = 0;

                    final Iterator<World> w = getServer().getWorlds().iterator();

                    while(w.hasNext()) {
                        final Chunk[] c;
                        int l = (c = w.next().getLoadedChunks()).length;

                        for(int i = 0; i < l; ++i) {
                            try {
                               if (c[l].unload(true)) u++;
                            } catch (final IndexOutOfBoundsException ignored) {}
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

    public void startTimer() {
        task = new Timer();

        task.schedule(new TimerTask() {
            @Override
            public void run() {
                if (getServer().getOnlinePlayers().size() == 0) getServer().shutdown();

            }
        }, 120000);
    }

    public void stopTimer() {
        task.cancel();
    }

}
