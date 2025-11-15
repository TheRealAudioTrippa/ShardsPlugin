package com.example.shards;

import org.bukkit.entity.Player;
import java.util.UUID;

public class ShardAPI {
    public static long get(Player p){return ShardsPlugin.getInstance().getShardManager().get(p);}
    public static long get(UUID id){return ShardsPlugin.getInstance().getShardManager().get(id);}
    public static void add(Player p,long amt){ShardsPlugin.getInstance().getShardManager().add(p,amt);}
    public static boolean take(Player p,long amt){return ShardsPlugin.getInstance().getShardManager().take(p,amt);}
}
