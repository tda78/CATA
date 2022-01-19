package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService service = new UserServiceImpl();
        service.createUsersTable();
        service.saveUser("Vasya","Petrov", (byte) 32);
        service.saveUser("Masha","Petrova", (byte) 67);
        service.saveUser("R2","D2", (byte) 12);
        service.saveUser("Max","Mad", (byte) 40);
        service.removeUserById(1);
        service.getAllUsers().stream().forEach(System.out::println);
        service.cleanUsersTable();
        service.dropUsersTable();
        service.close();
    }
}
