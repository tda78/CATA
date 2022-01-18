package jm.task.core.jdbc.dao;
import com.mysql.jdbc.Connection;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {}

    public void createUsersTable() {
        try (Connection connection = Util.getConnection();
            Statement statement = connection.createStatement();
        ) {
            statement.execute("create table if not exists users("
                    + "id int auto_increment primary key,"
                    + "name     varchar(45) null,"
                    + "lastName varchar(45) null,"
                    + "age int null,"
                    + "constraint user_id_uindex unique (id))");
        } catch (SQLException e) {
            System.out.println("statement not ready");
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try(Connection connection = Util.getConnection();
            Statement statement = connection.createStatement()){
            statement.execute("drop table if exists users");
        } catch (SQLException e) {
            System.out.println("table not deleted");
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try(Connection connection = (Connection) Util.getConnection()){
            String sql = "insert into users (name, lastName, age) values (?, ?, ?)";
            PreparedStatement prStmnt = connection.prepareStatement(sql);
            prStmnt.setString(1, name);
            prStmnt.setString(2, lastName);
            prStmnt.setByte(3, age);
            prStmnt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try(Connection connection = Util.getConnection()){
            String sql = "delete from users where id=?";
            PreparedStatement prStmnt = connection.prepareStatement(sql);
            prStmnt.setLong(1,id);
            prStmnt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        try
            (Connection connection = Util.getConnection();
            Statement statement = connection.createStatement()){
            ResultSet rs = statement.executeQuery("select * from users");
            while (rs.next()){
                result.add(new User( rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getByte(4)));
            }
            return result;
        }
        catch (Exception e){
            System.out.println("users not read");
            e.printStackTrace();
        }
        return result;
    }

    public void cleanUsersTable() {
        try(Connection connection = Util.getConnection();
            Statement statement = connection.createStatement();){
            statement.execute("truncate table users");
        }catch (Exception e){
            System.out.println("table not cleared");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new UserDaoJDBCImpl();
    }
}
