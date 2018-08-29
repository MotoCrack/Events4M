package me.devnatan.events4m.bolao;

import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class Storage {

    final String TABLE = "events4m_bolao";
    private Connection connection;

    public void connect(Plugin plugin, String host, String user, String pass, String database, boolean sqlite) {
        try {
            if(connection != null && !connection.isClosed())
                return;

            if(!sqlite) {
                synchronized (this) {
                    Class.forName("com.mysql.jdbc.Driver");
                    connection = DriverManager.getConnection("jdbc:mysql://" + host + ":3306/" + database, user, pass);
                    table();
                }
            } else {
                File f = new File(plugin.getDataFolder(), database + ".db");
                if (!f.exists())
                    f.createNewFile();

                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:" + f);
                table();
            }
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() throws SQLException {
        if(connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    private void table() throws SQLException {
        connection.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS " + TABLE + " (" +
                "uuid CHAR(36) NOT NULL, " +
                "name VARCHAR(255) NOT NULL, " +
                "time BIGINT NOT NULL, " +
                "amount DOUBLE NOT NULL DEFAULT 0, " +
                "totalAmount DOUBLE NOT NULL DEFAULT 0" +
                ")");
    }

    public void insert(UUID uuid, String name, long time, double amount, double totalAmount) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO " + TABLE + "(uuid, name, time, amount, totalAmount) VALUES (?, ?, ?, ?, ?)");
        ps.setString(1, uuid.toString());
        ps.setString(2, name);
        ps.setLong(3, time);
        ps.setDouble(4, amount);
        ps.setDouble(5, totalAmount);
        ps.execute();
        ps.close();
    }

}
