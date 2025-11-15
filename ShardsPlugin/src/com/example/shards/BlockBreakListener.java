package com.example.shards;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.entity.Player;
import org.bukkit.Material;

public class BlockBreakListener implements Listener {
    private final ShardsPlugin plugin;

    public BlockBreakListener(ShardsPlugin plugin){this.plugin=plugin;}

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        Player p=e.getPlayer();
        Material m=e.getBlock().getType();
        long r=plugin.getConfig().getLong("rewards.blocks."+m.name(),0);
        if(r>0){
            ShardAPI.add(p,r);
            p.sendActionBar("§a+ "+r+" shards");
        }
    }
}
