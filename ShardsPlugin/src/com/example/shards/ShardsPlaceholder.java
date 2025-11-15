package com.example.shards;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class ShardsPlaceholder extends PlaceholderExpansion {
    private final ShardsPlugin plugin;
    public ShardsPlaceholder(ShardsPlugin plugin){this.plugin=plugin;}

    @Override public String getIdentifier(){return "shards";}
    @Override public String getAuthor(){return "ShardsPlugin";}
    @Override public String getVersion(){return "1.0";}
    @Override public boolean persist(){return true;}

    @Override
    public String onPlaceholderRequest(Player p,String id){
        if(id.equalsIgnoreCase("balance")) return ""+ShardAPI.get(p);
        return null;
    }
}
