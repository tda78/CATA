package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.security.auth.login.Configuration;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sf;
    private Session session = null;

    public UserDaoHibernateImpl() {
        sf = Util.getSessionFactory();
    }

    public SessionFactory getSf() {
        return sf;
    }

    @Override
    public void createUsersTable() {
        try{
            session = sf.openSession();
            session.beginTransaction();
            SQLQuery query = session.createSQLQuery(
                    "CREATE table if not exists users("
                    + "id int auto_increment primary key,"
                    + "name varchar(45) null,"
                    + "lastName varchar(45) null,"
                    + "age int null,"
                    + "constraint user_id_uindex unique (id))");
            query.executeUpdate();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(session != null) {session.close();}
        }

    }

    @Override
    public void dropUsersTable() {
        try{
            session = sf.openSession();
            session.beginTransaction();
            SQLQuery query = session.createSQLQuery(
                    "drop table if exists users");
            query.executeUpdate();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(session != null) {session.close();}
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try{
            session = sf.openSession();
            session.beginTransaction();
            User user = new User();
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);
            session.save(user);
            session.getTransaction().commit();

        }catch (Exception e) {
            System.out.println("user not saved");
            e.printStackTrace();
        }finally {
            if(session!=null){session.close();}
        }
    }

    private User findUserById(long UserId){
        User user = null;
        try{
            session = sf.openSession();
            session.beginTransaction();
            user = (User) session.load(User.class, UserId);
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(session != null) {session.close();}
        }
        return user;
    }

    @Override
    public void removeUserById(long id) {
        try{
            User user = findUserById(id);
            session = sf.openSession();
            session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(session != null) {session.close();}
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        session = sf.openSession();
        session.beginTransaction();
        usersList = session.createQuery("from users").list();
        session.getTransaction().commit();
        return usersList;
    }

    @Override
    public void cleanUsersTable() {
        session = sf.openSession();
        session.beginTransaction();
        Query query = session.createQuery("delete from users");
        query.executeUpdate();
        session.getTransaction().commit();
    }

    public void close() {sf.close();}
}
