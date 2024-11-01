package Controller;

import Database.DBConnection;
import Model.Reservation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;
import org.junit.Ignore;

public class ReservationDAOTest {
    private Connection conn;
    private PreparedStatement ps;

    @Before
    public void setUp() {
        try (Connection conn = new DBConnection().setConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM restoku_db.reservations")) {
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        // Hapus data reservasi yang digunakan untuk pengujian
        try (Connection conn = new DBConnection().setConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM restoku_db.reservations")) {
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    
    //test Save

    @Test
    public void testSave() {
        Reservation newReservation = new Reservation();
        newReservation.setFirst_name("Jane");
        newReservation.setLast_name("Doe");
        newReservation.setPhone("0987654321321");
        newReservation.setEmail("jane.doe@gmail.com");
        newReservation.setPeople(2);
        newReservation.setDate("2024-12-26");
        newReservation.setTime("19:00");

        int status = ReservationDAO.save(newReservation);
        assertEquals(1, status);
        
        
        List<Reservation> reservations = ReservationDAO.getAllRecords();
        assertTrue(!reservations.isEmpty());
        
        Reservation reservation = reservations.get(0);
        assertTrue(reservation.getFirst_name().equals("Jane"));
        assertTrue(reservation.getLast_name().equals("Doe"));
        assertTrue(reservation.getPhone().equals("0987654321321"));
        assertTrue(reservation.getEmail().equals("jane.doe@gmail.com"));
        assertTrue(reservation.getPeople() == 2);
        assertTrue(reservation.getDate().equals("2024-12-26"));
        assertTrue(reservation.getTime().equals("19:00"));
        
    }
    
    
    @Test
    public void testSaveWithNullReservation() {
        Reservation newReservation = new Reservation();
        newReservation.setFirst_name("");
        newReservation.setLast_name("");
        newReservation.setPhone("");
        newReservation.setEmail("");
        newReservation.setPeople(0);
        newReservation.setDate("");
        newReservation.setTime("");
        
        int status = ReservationDAO.save(newReservation);
        assertEquals(0, status); //data kosong
        
        List<Reservation> reservations = ReservationDAO.getAllRecords();
        assertTrue(reservations.isEmpty());
    }
    
    //test save invaild input
    @Test
    public void testSaveWithEmptyFirstName() {
        Reservation reservation = new Reservation();
        reservation.setFirst_name(""); // First name kosong
        reservation.setLast_name("Doe");
        reservation.setPhone("0987654321");
        reservation.setEmail("jane.doe@gmail.com");
        reservation.setPeople(2);
        reservation.setDate("2024-12-26");
        reservation.setTime("19:00");

        int status = ReservationDAO.save(reservation);
        assertEquals("Save should fail with empty first name", 0, status);
        
        List<Reservation> reservations = ReservationDAO.getAllRecords();
        assertTrue(reservations.isEmpty());
    }
    
    @Test
    public void testSaveWithInvalidPhoneNumber() {
        Reservation reservation = new Reservation();
        reservation.setFirst_name("Jane");
        reservation.setLast_name("Doe");
        reservation.setPhone("ABCDE"); // Nomor telepon tidak valid
        reservation.setEmail("jane.doe@gmail.com");
        reservation.setPeople(2);
        reservation.setDate("2024-12-26");
        reservation.setTime("19:00");

        int status = ReservationDAO.save(reservation);
        assertEquals("Save should fail with invalid phone number", 0, status);
        
        List<Reservation> reservations = ReservationDAO.getAllRecords();
        assertTrue(reservations.isEmpty());
    }
    
    
    //SQL Exception Handling Save
    
    @Test(expected = SQLException.class)
    public void testSQLExceptionHandlingInSave() throws SQLException {
        conn = new DBConnection().setConnection();
            ps = conn.prepareStatement("INSERT INTO TestTable (first_name, last_name, phone, email, people, reservation_date, reservation_time) VALUES (?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, "John");
            ps.setString(2, "Doe");
            ps.setString(3, "1234567891011");
            ps.setString(4, "john.doe@example.com");
            ps.setInt(5, 4);
            ps.setString(6, "2024-11-01");
            ps.setString(7, "18:00");
            ps.executeUpdate();
    }
    
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    //test Get All Records
    
    @Test
    public void testGetAllRecords() {
        try {
            conn = new DBConnection().setConnection();
            ps = conn.prepareStatement("INSERT INTO restoku_db.reservations (first_name, last_name, phone, email, people, reservation_date, reservation_time) VALUES (?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, "John");
            ps.setString(2, "Doe");
            ps.setString(3, "1234567891011");
            ps.setString(4, "john.doe@gmail.com");
            ps.setInt(5, 4);
            ps.setString(6, "2024-11-01");
            ps.setString(7, "18:00");
            ps.executeUpdate();

        
        List<Reservation> reservations = ReservationDAO.getAllRecords();
        assertNotNull(reservations);
        assertTrue(!reservations.isEmpty());
        
        Reservation reservation = reservations.get(0);
        assertTrue(reservation.getFirst_name().equals("John"));
        assertTrue(reservation.getLast_name().equals("Doe"));
        assertTrue(reservation.getPhone().equals("1234567891011"));
        assertTrue(reservation.getEmail().equals("john.doe@gmail.com"));
        assertTrue(reservation.getPeople() == 4);
        assertTrue(reservation.getDate().equals("2024-11-01"));
        assertTrue(reservation.getTime().equals("18:00"));
        
        } catch (SQLException e) {
            System.out.println("Error  " + e.getMessage());
        }
    }
    
    //test get all records when databse is empty
    @Test
    public void testGetAllRecordsWhenDatabaseIsEmpty() {
        // Precondition: Ensure the reservations table is empty
//        List<Reservation> reservations = ReservationDAO.getAllRecords();
//        for (Reservation reservation : reservations) {
//            ReservationDAO.delete(reservation); // Clear all reservations
//        }

        // Fetch all records when the database is empty
        List<Reservation> emptyReservations = ReservationDAO.getAllRecords();
        assertTrue(emptyReservations.isEmpty()); // Expected: The list should be empty
    }
    
    
    //SQL Exception Handling Get All Records
    
    @Test(expected = SQLException.class)
    public void testSQLExceptionHandlingInGetAllRecords() throws SQLException {
        conn = new DBConnection().setConnection();
        ps = conn.prepareStatement("SELECT * FROM TestTable");
        ps.executeQuery();
    }
    
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    //test Update
    
    @Test
    public void testUpdate() {
        try {
            conn = new DBConnection().setConnection();
            ps = conn.prepareStatement("INSERT INTO restoku_db.reservations (first_name, last_name, phone, email, people, reservation_date, reservation_time) VALUES (?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, "John");
            ps.setString(2, "Doe");
            ps.setString(3, "1234567891011");
            ps.setString(4, "john.doe@gmail.com");
            ps.setInt(5, 4);
            ps.setString(6, "2024-11-01");
            ps.setString(7, "18:00");
            ps.executeUpdate();

            List<Reservation> reservations = ReservationDAO.getAllRecords();
            Reservation lastInsertedReservation = reservations.get(reservations.size() - 1);

            lastInsertedReservation.setFirst_name("Jamal");
            int status = ReservationDAO.update(lastInsertedReservation);
            assertEquals(1, status);

            reservations = ReservationDAO.getAllRecords();
            Reservation updatedReservation = reservations.get(reservations.size() - 1);

            assertNotNull(updatedReservation);
            assertEquals("Jamal", updatedReservation.getFirst_name());

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    //test update no user exsist
    
    @Test
    public void testUpdateNonExistentReservation() {
        // Attempt to update a reservation that doesn't exist in the database
        Reservation nonExistentReservation = new Reservation();
        nonExistentReservation.setId_reservation(-1); // Assuming -1 is an invalid ID
        nonExistentReservation.setFirst_name("Ghost");

        int status = ReservationDAO.update(nonExistentReservation);
        assertEquals(0, status); // Expected: Update should fail, return status 0
    }
    
    
    @Test(expected = SQLException.class)
    public void testSQLExceptionHandlingUpdate() throws SQLException {
            conn = new DBConnection().setConnection();
            ps = conn.prepareStatement("update TableTest set first_name=?, last_name=?, phone=?, email=?, people=?, reservation_date=?, reservation_time=? where id_reservation=?");
            ps.setString(1, "Betul");
            ps.setString(2, "Sekali");
            ps.setString(3, "081233444567");
            ps.setString(4, "Helloword@gmail.com");
            ps.setInt(5, 1);
            ps.setString(6, "2024-11-02");
            ps.setString(7, "20:00");
            ps.setInt(8, 2);
            ps.executeUpdate();

    }
    
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    //test getrecordbyid
    
    @Test
    public void testGetRecordById() {
        try {
            conn = new DBConnection().setConnection();
            ps = conn.prepareStatement("INSERT INTO restoku_db.reservations (first_name, last_name, phone, email, people, reservation_date, reservation_time) VALUES (?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, "John");
            ps.setString(2, "Doe");
            ps.setString(3, "1234567891011");
            ps.setString(4, "john.doe@gmail.com");
            ps.setInt(5, 4);
            ps.setString(6, "2024-11-01");
            ps.setString(7, "18:00");
            ps.executeUpdate();

            List<Reservation> reservations = ReservationDAO.getAllRecords();
            Reservation lastInsertedReservation = reservations.get(reservations.size() - 1);
            int testId = lastInsertedReservation.getId_reservation();

            Reservation retrievedReservation = ReservationDAO.getRecordById(testId);
            assertNotNull(retrievedReservation);
            assertEquals("John", retrievedReservation.getFirst_name());
            assertEquals("Doe", retrievedReservation.getLast_name());
            assertEquals("1234567891011", retrievedReservation.getPhone());
            assertEquals("john.doe@gmail.com", retrievedReservation.getEmail());
            assertEquals(4, retrievedReservation.getPeople());
            assertEquals("2024-11-01", retrievedReservation.getDate());
            assertEquals("18:00", retrievedReservation.getTime());

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } 
    }
    
    // test get id but invalid id
    
    @Test
    public void testGetRecordByInvalidId() {
        // Attempt to retrieve a reservation with an invalid ID
        Reservation reservation = ReservationDAO.getRecordById(-1); // Assuming -1 is an invalid ID
        assertNull(reservation); // Expected: Should return null, as ID is invalid
    }
    
    
    //SQL Exception Handling
    @Test(expected = SQLException.class)
    public void testSQLExceptionHandlingGetRecordByID() throws SQLException {
            conn = new DBConnection().setConnection();
            ps = conn.prepareStatement("select * from test where id_reservation=?");
            ps.executeUpdate();

    }

    
    
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    //test Move to Done
    
    @Test
    public void testMoveToDone() {
        
        try {
            conn = new DBConnection().setConnection();
            ps = conn.prepareStatement("INSERT INTO restoku_db.reservations (first_name, last_name, phone, email, people, reservation_date, reservation_time) VALUES (?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, "John");
            ps.setString(2, "Doe");
            ps.setString(3, "1234567891011");
            ps.setString(4, "john.doe@gmail.com");
            ps.setInt(5, 4);
            ps.setString(6, "2024-11-01");
            ps.setString(7, "18:00");
            ps.executeUpdate();
            
        List<Reservation> reservations = ReservationDAO.getAllRecords();
        Reservation lastInsertedReservation = reservations.get(reservations.size() - 1);

        int status = ReservationDAO.moveToDone(lastInsertedReservation.getId_reservation());
        assertEquals(1, status);


        Reservation movedReservation = ReservationDAO.getRecordById(lastInsertedReservation.getId_reservation());
        assertNull(movedReservation);
        
        } catch (SQLException e) {
            System.out.println("Error  " + e.getMessage());
        }
        
        try {
            conn = new DBConnection().setConnection();
            ps = conn.prepareStatement("DELETE FROM restoku_db.done");
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error  " + e.getMessage());
        }
    }
    
    //test Invalid ID / non exsist data move to done
    @Test
    public void testMoveToDoneNonExistentReservation() {
        // Attempt to move a non-existent reservation to "done"
        int status = ReservationDAO.moveToDone(-1); // Assuming -1 is an invalid ID
        assertEquals(0, status); // Expected: Move should fail, return status 0
    }
    
    
    //SQL Exception Handling Move to Done
    
    @Test(expected = SQLException.class)
    public void testSQLExceptionHandlingMoveToDone() throws SQLException {
        conn = new DBConnection().setConnection();
            ps = conn.prepareStatement("INSERT INTO TestTable (first_name, last_name, phone, email, people, reservation_date, reservation_time) VALUES (?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, "John");
            ps.setString(2, "Doe");
            ps.setString(3, "1234567891011");
            ps.setString(4, "john.doe@gmail.com");
            ps.setInt(5, 4);
            ps.setString(6, "2024-11-01");
            ps.setString(7, "18:00");
            ps.executeUpdate();
    }
    
    
   /////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
    
    
    //Test Search
    
    @Test
    public void testSearchRecords() {
        
        try {
            conn = new DBConnection().setConnection();
            ps = conn.prepareStatement("INSERT INTO restoku_db.reservations (first_name, last_name, phone, email, people, reservation_date, reservation_time) VALUES (?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, "John");
            ps.setString(2, "Doe");
            ps.setString(3, "1234567891011");
            ps.setString(4, "john.doe@gmail.com");
            ps.setInt(5, 4);
            ps.setString(6, "2024-11-01");
            ps.setString(7, "18:00");
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error  " + e.getMessage());
        }
        
        List<Reservation> searchResults = ReservationDAO.searchRecords("John");

        assertNotNull("Search results should not be null", searchResults);
        assertTrue("Search results should contain at least one reservation", !searchResults.isEmpty());

    }
    
    // test search no keyword
    
    @Test
    public void testSearchRecordsWithNullKeyword() {
        // Negative test: Keyword is null
        List<Reservation> result = ReservationDAO.searchRecords(null);
        assertTrue(result.isEmpty());
    }
    
    //test search null keyword

    @Test
    public void testSearchRecordsWithEmptyStringKeyword() {
        // Negative test: Keyword is an empty string
        List<Reservation> result = ReservationDAO.searchRecords("");
        assertTrue(result.isEmpty());
    }
    
    //test search no data 

    @Test
    public void testSearchRecordsWithNonExistentKeyword() {
        // Negative test: Keyword does not match any records
        String nonExistentKeyword = "xyzNonExistent";
        List<Reservation> result = ReservationDAO.searchRecords(nonExistentKeyword);
        assertTrue(result.isEmpty());
    }
    
    //test search special character

    @Test
    public void testSearchRecordsWithSpecialCharactersKeyword() {
        // Negative test: Keyword contains special characters
        String specialCharKeyword = "#$%&*!";
        List<Reservation> result = ReservationDAO.searchRecords(specialCharKeyword);
        assertTrue(result.isEmpty());
    }
    
    //SQL Exception Handling Search
    
    @Test(expected = SQLException.class)
    public void testSQLExceptionHandlingSearch() throws SQLException {
        List<Reservation> searchResults = ReservationDAO.searchRecords("John");
        conn = new DBConnection().setConnection();
        ps = conn.prepareStatement("SELECT * FROM TestTable where first_name LIKE " + searchResults );
        ps.executeQuery();
    }

}
