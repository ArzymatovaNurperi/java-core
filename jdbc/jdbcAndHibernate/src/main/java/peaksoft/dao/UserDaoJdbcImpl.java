package peaksoft.dao;

import peaksoft.model.User;
import peaksoft.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJdbcImpl implements UserDao {

    public UserDaoJdbcImpl() {

    }


    public void createUsersTable() {
        String SQL = "create table if not exists  users(" +
                "id serial primary key," +
                "name varchar(50) not null," +
                "last_name varchar(50) not null," +
                "age int2 not null);";
        try (Connection conn = Util.connect();
             Statement statement = conn.createStatement()) {
            statement.executeQuery(SQL);
            System.out.println("User sucessfully created");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void dropUsersTable() {
        String SQL = "drop table if exists users";
        try (Connection connection = Util.connect();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(SQL);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String SQL = "insert into users(name,last_name,age) values (?,?,?)";
        try (Connection conn = Util.connect();
             PreparedStatement statement = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            System.out.println("Created column" + name);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void removeUserById(long id) {
        String SQL = "delete from users where id=?";
        try (Connection connection = Util.connect();
             PreparedStatement prpstm = connection.prepareStatement(SQL)) {
            prpstm.executeUpdate();
            System.out.println("User with id" + id + "was delete");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        User user;
        String SQL = "select * from users";
        try (Connection connection = Util.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL);
             ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("last_name"));
                user.setAge(rs.getByte("age"));
                list.add(user);

            }
        } catch (SQLException e) {
            e.getMessage();
        }
         return list;
    }

    public void cleanUsersTable() {
        String SQL = "truncate users";
        try (Connection connection = Util.connect();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(SQL);


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}