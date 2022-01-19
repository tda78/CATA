package jm.task.core.jdbc.util;

import com.mysql.jdbc.Connection;
import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

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

    public static SessionFactory getSessionFactory(){
        Properties props = new Properties();
        try(InputStream in = Files.newInputStream(Paths.get("hibernate.properties"))){
            props.load(in);
        } catch (IOException e) {
            System.out.println("file not found");
            e.printStackTrace();
        }
        Configuration config = new Configuration();
        config.addAnnotatedClass(User.class);
        config.setProperties(props);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
        SessionFactory sessionFactory = config.buildSessionFactory(serviceRegistry);
        return sessionFactory;
    }

}
