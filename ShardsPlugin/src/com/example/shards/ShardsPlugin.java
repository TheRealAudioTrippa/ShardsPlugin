package com.example.shards;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class ShardsPlugin extends JavaPlugin {
    private static ShardsPlugin INSTANCE;
    private ShardManager manager;
    private MySQLManager mysqlManager;
    private BukkitTask afkTask;
    private MovementListener movementListener;

    @Override
    public void onEnable() {
        INSTANCE = this;
        saveDefaultConfig();

        if (getConfig().getString("storage.type","yaml").equalsIgnoreCase("mysql")
            && getConfig().getBoolean("storage.mysql.enabled", false)) {
            mysqlManager = new MySQLManager(this);
            mysqlManager.connect();
            manager = new ShardManager(this, mysqlManager);
        } else {
            manager = new ShardManager(this, null);
        }

        getCommand("shards").setExecutor(new ShardsCommand(this));

        Bukkit.getPluginManager().registerEvents(new KillListener(this), this);
        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(this), this);

        movementListener = new MovementListener(this);
        Bukkit.getPluginManager().registerEvents(movementListener, this);

        if (getConfig().getBoolean("afk.enabled", true)) {
            long interval = getConfig().getLong("afk.check-interval-seconds", 60);
            AFKService afk = new AFKService(this, movementListener);
            afkTask = afk.runTaskTimer(this, 20L*interval, 20L*interval);
        }

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new ShardsPlaceholder(this).register();
        }
    }

    @Override
    public void onDisable() {
        if (afkTask != null) afkTask.cancel();
        if (manager != null) manager.saveAll();
        if (mysqlManager != null) mysqlManager.disconnect();
    }

    public static ShardsPlugin getInstance() { return INSTANCE; }
    public ShardManager getShardManager() { return manager; }
    public MySQLManager getMySQLManager() { return mysqlManager; }
}
