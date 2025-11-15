package com.example.shards;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import java.util.*;

public class ShardsCommand implements CommandExecutor, TabCompleter {
    private final ShardsPlugin plugin;

    public ShardsCommand(ShardsPlugin plugin){this.plugin=plugin;}

    @Override
    public boolean onCommand(CommandSender s, Command c,String l,String[] a){
        if(a.length==0){
            if(s instanceof Player) s.sendMessage("Shards: "+ShardAPI.get((Player)s));
            return true;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender s,Command c,String a,String[] b){
        return Arrays.asList("balance","give","take");
    }
}
