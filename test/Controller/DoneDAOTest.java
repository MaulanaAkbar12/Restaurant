package Controller;

import Database.DBConnection;
import Model.Done;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class DoneDAOTest {

    private Connection conn;
    private PreparedStatement ps;

    @Before
    public void setUp() {
        try {
            conn = new DBConnection().setConnection();
            ps = conn.prepareStatement("DELETE FROM restoku_db.done");
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error in setUp: " + e.getMessage());
        }    
    }

    @After
    public void tearDown() {
        try {
            ps = conn.prepareStatement("DELETE FROM restoku_db.done");
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
    
    @Test
    public void testGetAllRecords() {
        
        try {
            conn = new DBConnection().setConnection();
            ps = conn.prepareStatement("INSERT INTO restoku_db.done (id_reservation, first_name, last_name, phone, email, people, reservation_date, reservation_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, 1);
            ps.setString(2, "John");
            ps.setString(3, "Doe");
            ps.setString(4, "1234567891011");
            ps.setString(5, "john.doe@example.com");
            ps.setInt(6, 4);
            ps.setString(7, "2024-11-01");
            ps.setString(8, "18:00");
            ps.executeUpdate();
        
        List<Done> doneList = DoneDAO.getAllRecordsdone();
        assertNotNull(doneList);
        assertTrue(!doneList.isEmpty());

        Done done = doneList.get(0);
        assertTrue(done.getFirst_name().equals("John"));
        assertTrue(done.getLast_name().equals("Doe"));
        assertTrue(done.getPhone().equals("1234567891011"));
        assertTrue(done.getEmail().equals("john.doe@example.com"));
        assertTrue(done.getPeople() == 4);
        assertTrue(done.getReservation_date().equals("2024-11-01"));
        assertTrue(done.getReservation_time().equals("18:00"));        
        
        } catch (SQLException e) {
            System.out.println("Error  " + e.getMessage());
        }
    }
    
    @Test
    public void testGetAllUsersWhenDatabaseIsEmpty() {
        // Precondition: Ensure the reservations table is empty
//        List<User> users = UserDAO.getAllUsers();
//        for (User user : users) {
//            UserDAO.delete(reservation); // Clear all reservations
//        }

        // Fetch all records when the database is empty
        List<Done> emptyDone = DoneDAO.getAllRecordsdone();
        assertTrue(emptyDone.isEmpty()); // Expected: The list should be empty
    }
    
    
    @Test(expected = SQLException.class)
    public void testSQLExceptionHandlingInGetAllRecordsdone() throws SQLException {
        conn = new DBConnection().setConnection();
        ps = conn.prepareStatement("SELECT * FROM TableTest");
        ps.executeQuery();
    }

}
