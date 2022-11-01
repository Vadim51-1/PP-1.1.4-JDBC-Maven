package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    @Override
    public void createUsersTable() {
        try (PreparedStatement pst = Util.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS users (id BIGINT PRIMARY KEY AUTO_INCREMENT, Name VARCHAR(20)," +
                " lastName VARCHAR(20), age TINYINT)")) {
            pst.executeUpdate("CREATE TABLE IF NOT EXISTS users (id BIGINT PRIMARY KEY AUTO_INCREMENT, Name VARCHAR(20)," +
                    " lastName VARCHAR(20), age TINYINT)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement("DROP TABLE users ")) {
            preparedStatement.executeUpdate("DROP TABLE users ");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement prStatement = Util.getConnection().
                prepareStatement("INSERT INTO users (name , lastName, age) VALUES (?,?,?)")) {

            prStatement.setString(1, name);
            prStatement.setString(2, lastName);
            prStatement.setByte(3, age);
            prStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (PreparedStatement prs = Util.getConnection().prepareStatement("DELETE FROM users " + "WHERE id =" + id)) {
            prs.executeUpdate("DELETE FROM users " + "WHERE id =" + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (PreparedStatement prs = Util.getConnection().prepareStatement("SELECT * FROM users");
             ResultSet resultSet = prs.executeQuery("SELECT * FROM users")) {
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                Byte age = resultSet.getByte("age");
                User user = new User(name, lastName, age);
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(userList);
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try (PreparedStatement prs = Util.getConnection().prepareStatement("Truncate table users")) {
            prs.executeUpdate("Truncate table users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
