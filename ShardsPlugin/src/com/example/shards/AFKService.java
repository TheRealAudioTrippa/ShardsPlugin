package com.example.shards;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class AFKService extends BukkitRunnable {
    private final ShardsPlugin plugin;
    private final MovementListener movement;

    public AFKService(ShardsPlugin plugin, MovementListener movement) {
        this.plugin = plugin;
        this.movement = movement;
    }

    @Override
    public void run() {
        long idle = plugin.getConfig().getLong("afk.idle-seconds", 300);
        long reward = plugin.getConfig().getLong("afk.reward-per-interval", 5);
        long now = System.currentTimeMillis()/1000;

        for (Player p : Bukkit.getOnlinePlayers()) {
            Long last = movement.getLastMoveMap().get(p.getUniqueId());
            if (last != null && now - last >= idle) {
                ShardAPI.add(p, reward);
                p.sendActionBar("§a+ " + reward + " shards (AFK)");
            }
        }
    }
}
