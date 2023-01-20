package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {


    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

        String query = "CREATE TABLE IF NOT EXISTS USER(" +
                "ID int PRIMARY KEY AUTO_INCREMENT," +
                "NAME varchar(50)," +
                "LASTNAME varchar(10)," +
                "AGE int)";


        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate(query);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void dropUsersTable() {
        String query = "DROP TABLE IF EXISTS USER";


        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate(query);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        String query = "INSERT INTO USER (name, lastName, age) VALUES (?, ?, ?)";

        try (PreparedStatement statement = Util.getConnection().prepareStatement(query)) {

            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setInt(3, age);
            statement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public void removeUserById(long id) {
        String query = "DELETE FROM USER WHERE id = ?";


        try (PreparedStatement statement = Util.getConnection().prepareStatement(query)) {
            statement.setInt(1, (int) id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> user = new ArrayList<>();


        try (Statement statement = Util.getConnection().createStatement()) {
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
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
