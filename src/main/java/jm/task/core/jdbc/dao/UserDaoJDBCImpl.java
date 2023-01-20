package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    Connection connection;

    public UserDaoJDBCImpl() {
        try {
            this.connection = Util.getConnection();
        } catch (SQLException e) {
            System.out.println("Не удалось подкючиться в БД");;
        }
    }

    public void createUsersTable() {

        String query = "CREATE TABLE IF NOT EXISTS USER(" +
                "ID int PRIMARY KEY AUTO_INCREMENT," +
                "NAME varchar(50)," +
                "LASTNAME varchar(10)," +
                "AGE int)";


        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void dropUsersTable() {
        String query = "DROP TABLE IF EXISTS USER";


        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        String query = "INSERT INTO USER (name, lastName, age) VALUES ('" + name + "'" + ", '" + lastName + "'" + ", " + age + ")";


        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            System.out.println("User с именем – "+name+" добавлен в базу данных");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void removeUserById(long id) {
        String query = "DELETE FROM USER WHERE id = " + id;


        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> user = new ArrayList<>();


        try (Statement statement = connection.createStatement()) {
            ResultSet results = statement.executeQuery("SELECT * FROM user");
            while (results.next()) {
                user.add(new User(results.getString("NAME"), results.getString("LASTNAME"), results.getByte("AGE")));
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (User y : user) {
            System.out.println(y);
        }

        return user;
    }

    public void cleanUsersTable() {
        String query = "TRUNCATE TABLE USER";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
