package com.example.shards;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import java.util.*;

public class MovementListener implements Listener {
    private final Map<UUID, Long> lastMove = new HashMap<>();
    public MovementListener(ShardsPlugin plugin) {}

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        lastMove.put(e.getPlayer().getUniqueId(), System.currentTimeMillis()/1000);
    }

    public Map<UUID,Long> getLastMoveMap() { return lastMove; }
}
