package com.example.shards;

import java.sql.*;
import java.util.UUID;

public class MySQLManager {
    private final ShardsPlugin plugin;
    private Connection conn;

    public MySQLManager(ShardsPlugin plugin){this.plugin=plugin;}

    public void connect(){
        try{
            String host=plugin.getConfig().getString("storage.mysql.host");
            int port=plugin.getConfig().getInt("storage.mysql.port");
            String db=plugin.getConfig().getString("storage.mysql.database");
            String user=plugin.getConfig().getString("storage.mysql.username");
            String pass=plugin.getConfig().getString("storage.mysql.password");
            String url="jdbc:mysql://"+host+":"+port+"/"+db+"?useSSL=false";
            conn=DriverManager.getConnection(url,user,pass);
        }catch(Exception e){
            plugin.getLogger().severe("MySQL connect failed: "+e.getMessage());
        }
    }

    public boolean isConnected(){
        try{return conn!=null && !conn.isClosed();}catch(Exception e){return false;}
    }

    public void disconnect(){
        try{if(conn!=null)conn.close();}catch(Exception ignored){}
    }

    public void createTableIfNotExists() throws SQLException {
        String sql="CREATE TABLE IF NOT EXISTS shards_balances (uuid VARCHAR(36) PRIMARY KEY, balance BIGINT NOT NULL)";
        conn.createStatement().execute(sql);
    }

    public void setBalance(UUID id,long value) throws SQLException {
        PreparedStatement ps=conn.prepareStatement("REPLACE INTO shards_balances VALUES(?,?)");
        ps.setString(1,id.toString());
        ps.setLong(2,value);
        ps.executeUpdate();
    }
}
