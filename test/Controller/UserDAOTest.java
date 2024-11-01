package Controller;

import Controller.UserDAO;
import Database.DBConnection;
import Model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.junit.Ignore;

public class UserDAOTest {

    private Connection conn;
    private PreparedStatement ps;

    @Before
    public void setUp() {
        try {
            conn = new DBConnection().setConnection();
            ps = conn.prepareStatement("DELETE FROM restoku_db.user");
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error in setUp: " + e.getMessage());
        }    
    }

    @After
    public void tearDown() {
        try {
            ps = conn.prepareStatement("DELETE FROM restoku_db.user");
            ps.executeUpdate();

            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Error in tearDown: " + e.getMessage());
        }
    }
    
    
    ///test Save User

    @Test
    public void testSave() {
        User testUser = new User();
        testUser.setUsername("adminrestoku");
        testUser.setPassword("admintest12");

        int status = UserDAO.save(testUser);
        assertEquals(1, status);
        
        List<User> users = UserDAO.getAllUsers();
        User user = users.get(0);
        assertTrue(user.getUsername().equals("adminrestoku"));
        assertTrue(user.getPassword().equals("admintest12"));
    }
    
    //test save user invalid username 
    
    @Test
    public void testSaveWithInvalidUsername() {
        User testUser = new User();
        testUser.setUsername("");
        testUser.setPassword("admintest12");

        int status = UserDAO.save(testUser);
        assertEquals("Save should fail with invalid phone number", 0, status);
        
        List<User> users = UserDAO.getAllUsers();
        assertTrue(users.isEmpty());
    }
    
    //test user invalid password
    
    @Test
    public void testSaveWithInvalidPassword() {
        User testUser = new User();
        testUser.setUsername("adminrestoku");
        testUser.setPassword("");

        int status = UserDAO.save(testUser);
        assertEquals("Save should fail with invalid phone number", 0, status);
        
        List<User> users = UserDAO.getAllUsers();
        assertTrue(users.isEmpty());
    }
    
    //SQL Exception Handling
    
    @Test(expected = SQLException.class)
    public void testSQLExceptionHandlingInSave() throws SQLException {
        conn = new DBConnection().setConnection();
            ps = conn.prepareStatement("INSERT INTO TestTable (username, password) VALUES (?, ?)");
            ps.setString(1, "adminrestoku");
            ps.setString(2, "admintest12");
            ps.executeUpdate();
    }
    
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    //test get all users / records
    
    @Test
    public void testGetAllUsers() {
        
        try {
            conn = new DBConnection().setConnection();
            ps = conn.prepareStatement("INSERT INTO restoku_db.user (username, password) VALUES (?, ?)");
            ps.setString(1, "adminrestoku");
            ps.setString(2, "admintest12");
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error  " + e.getMessage());
        }
       
        
        List<User> users = UserDAO.getAllUsers();
        assertNotNull(users);
        assertTrue(!users.isEmpty());
        
        User user = users.get(0);
        assertTrue(user.getUsername().equals("adminrestoku"));
        assertTrue(user.getPassword().equals("admintest12"));
    }
    
    //test get all records when databse is empty
    @Test
    public void testGetAllUsersWhenDatabaseIsEmpty() {
        // Precondition: Ensure the reservations table is empty
//        List<User> users = UserDAO.getAllUsers();
//        for (User user : users) {
//            UserDAO.delete(reservation); // Clear all reservations
//        }

        // Fetch all records when the database is empty
        List<User> emptyUsers = UserDAO.getAllUsers();
        assertTrue(emptyUsers.isEmpty()); // Expected: The list should be empty
    }
    
    //SQL Exception Handling
    
    @Test(expected = SQLException.class)
    public void testSQLExceptionHandlingInGetAllUsers() throws SQLException {
        conn = new DBConnection().setConnection();
        ps = conn.prepareStatement("SELECT * FROM TestTable");
        ps.executeUpdate();
    }
    
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    //test get user by id
    
    @Test
    public void testGetUserById() {
        User testUser = new User();
        
        try {
            conn = new DBConnection().setConnection();
            ps = conn.prepareStatement("INSERT INTO restoku_db.user (username, password) VALUES (?, ?)");
            ps.setString(1, "adminrestoku");
            ps.setString(2, "admintest12");
            ps.executeUpdate();

        
        
        List<User> users = UserDAO.getAllUsers();
        User lastInsertedUser = users.get(users.size() - 1);
        int testId = lastInsertedUser.getId();
        
        User user = UserDAO.getUserById(testId);
        
        assertNotNull(user);
        assertEquals("adminrestoku", user.getUsername());
        assertEquals("admintest12", user.getPassword());
        
        } catch (SQLException e) {
            System.out.println("Error  " + e.getMessage());
        }
    }
    
    
    // test get id but invalid id
    @Test
    public void testGetRecordByInvalidId() {
        // Attempt to retrieve a reservation with an invalid ID
        User user = UserDAO.getUserById(-1); // Assuming -1 is an invalid ID
        assertNull(user); // Expected: Should return null, as ID is invalid
    }
    
    //SQL Exception Handling
    @Test(expected = SQLException.class)
    public void testSQLExceptionHandlingGetRecordByID() throws SQLException {
            conn = new DBConnection().setConnection();
            ps = conn.prepareStatement("select * from tableTest where id_reservation=?");
            ps.executeUpdate();

    }
    
    
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    //test delete
    
    @Test
    public void testDelete() {
        try {
            conn = new DBConnection().setConnection();
            ps = conn.prepareStatement("INSERT INTO restoku_db.user (username, password) VALUES (?, ?)");
            ps.setString(1, "adminrestoku");
            ps.setString(2, "admintest12");
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error  " + e.getMessage());
        }
        
        List<User> users = UserDAO.getAllUsers();
        User lastUser = users.get(users.size() - 1);
        int status = UserDAO.delete(lastUser);
        assertEquals("data user barhasil di hapus", 1, status);

        User deletedUser = UserDAO.getUserById(lastUser.getId());
        assertNull(deletedUser);
    }
    
    //test delete when no user exsist
    
    @Test
    public void testDeleteNonExistentUser() {
        // Test deleting a user that does not exist in the database
        User nonExistentUser = new User();
        nonExistentUser.setId(-1); // Use an invalid ID

        int status = UserDAO.delete(nonExistentUser);
        assertEquals(0, status); // Expected: Deletion fails and returns status 0
    }
    
    //SQL Exception Handling
    
    @Test(expected = SQLException.class)
    public void testSQLExceptionHandlingInDelete() throws SQLException {
        conn = new DBConnection().setConnection();
        ps = conn.prepareStatement("DELETE FROM TestTable");
        ps.executeUpdate();
    }
    
    
}

