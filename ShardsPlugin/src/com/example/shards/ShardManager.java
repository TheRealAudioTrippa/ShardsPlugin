package com.example.shards;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ShardManager {
    private final JavaPlugin plugin;
    private final File file;
    private final YamlConfiguration cfg;
    private final Map<UUID,Long> balances = new ConcurrentHashMap<>();
    private final MySQLManager mysql;

    public ShardManager(JavaPlugin plugin, MySQLManager mysql) {
        this.plugin = plugin;
        this.mysql = mysql;
        this.file = new File(plugin.getDataFolder(), "balances.yml");
        cfg = YamlConfiguration.loadConfiguration(file);
        load();
    }

    public void load() {
        balances.clear();
        for (String key : cfg.getKeys(false)) {
            try { balances.put(UUID.fromString(key), cfg.getLong(key)); } catch (Exception ignored) {}
        }
    }

    public void saveAll() {
        for (UUID u : balances.keySet()) cfg.set(u.toString(), balances.get(u));
        try { cfg.save(file); } catch (IOException ignored) {}
        if (mysql != null && mysql.isConnected()) {
            try {
                mysql.createTableIfNotExists();
                for (UUID id : balances.keySet()) mysql.setBalance(id, balances.get(id));
            } catch (SQLException ignored) {}
        }
    }

    public long get(UUID id) { return balances.getOrDefault(id,0L); }
    public long get(Player p) { return get(p.getUniqueId()); }

    public void set(UUID id, long amount) {
        balances.put(id, Math.max(0,amount));
        try { if (mysql!=null && mysql.isConnected()) mysql.setBalance(id, amount);} catch(Exception ignored){}
    }

    public void add(Player p,long amt){ set(p.getUniqueId(), get(p)+amt); }
    public boolean take(Player p,long amt){
        long c=get(p);
        if(c<amt)return false;
        set(p.getUniqueId(),c-amt);
        return true;
    }
}
