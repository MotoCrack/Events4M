package me.devnatan.events4m.fight;

import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Storage {

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

    private void table() {

    }

}
