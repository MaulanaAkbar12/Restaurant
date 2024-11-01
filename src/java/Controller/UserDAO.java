package Controller;

import Model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Database.DBConnection;

public class UserDAO {
    private static Connection conn;
    private static PreparedStatement ps;

    // Method to save a new user
    public static int save(User user) {
        int status = 0;
          
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            return status;
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            return status;
        }
        try {
            conn = new DBConnection().setConnection();
            ps = conn.prepareStatement("INSERT INTO restoku_db.user (username, password) VALUES(?, ?)");
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            status = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return status;
    }

    // Method to get all user records
    public static List<User> getAllUsers() {
        List<User> list = new ArrayList<>();

        try {
            conn = new DBConnection().setConnection();
            ps = conn.prepareStatement("SELECT * FROM restoku_db.user");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                list.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } 
        return list;
    }

    // Method to get a single user record by ID
    public static User getUserById(int id) {
        User user = null;
        try {
            conn = new DBConnection().setConnection();
            ps = conn.prepareStatement("SELECT * FROM restoku_db.user WHERE id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        } 
        return user;
    }

    // Method to delete a user record
    public static int delete(User user) {
        int status = 0;
        try {
            conn = new DBConnection().setConnection();
            ps = conn.prepareStatement("DELETE FROM restoku_db.user WHERE id=?");
            ps.setInt(1, user.getId());
            status = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } 
        return status;
    }
}
