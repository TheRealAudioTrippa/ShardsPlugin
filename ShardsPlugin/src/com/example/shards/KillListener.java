package com.example.shards;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class KillListener implements Listener {
    private final ShardsPlugin plugin;

    public KillListener(ShardsPlugin plugin){this.plugin=plugin;}

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e){
        if(e.getEntity().getKiller()==null)return;
        Player k=e.getEntity().getKiller();
        long r=plugin.getConfig().getLong("kills.player",5);
        ShardAPI.add(k,r);
        k.sendActionBar("§a+ "+r+" shards");
    }
}
