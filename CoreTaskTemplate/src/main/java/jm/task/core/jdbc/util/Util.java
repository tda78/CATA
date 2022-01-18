package jm.task.core.jdbc.util;

import com.mysql.jdbc.Connection;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {

    public static Connection getConnection(){

        Properties props = new Properties();
        try(InputStream in = Files.newInputStream(Paths.get("jdbc.properties"))){
            props.load(in);
        } catch (IOException e) {
            System.out.println("file not found");
            e.printStackTrace();
        }
        String url = props.getProperty("url");
        String username = props.getProperty("username");
        String password = props.getProperty("password");

        try {
            return (Connection) DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            System.out.println("connection not connected");
            e.printStackTrace();
            return null;
        }
    }

}
