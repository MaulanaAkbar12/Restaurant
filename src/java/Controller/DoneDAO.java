package Controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Database.DBConnection;
import Model.Done;

public class DoneDAO {

    static Connection conn;
    static PreparedStatement ps;
    static ResultSet rs;
    static String sql;

    public static List<Done> getAllRecordsdone() {
        List<Done> list = new ArrayList<>();

        try {
            conn = new DBConnection().setConnection();
            ps = conn.prepareStatement("SELECT * FROM restoku_db.done");
            rs = ps.executeQuery();
            while (rs.next()) {
                Done d = new Done();
                d.setId_reservation(rs.getInt("id_reservation"));
                d.setFirst_name(rs.getString("first_name"));
                d.setLast_name(rs.getString("last_name"));
                d.setPhone(rs.getString("phone"));
                d.setEmail(rs.getString("email"));
                d.setPeople(rs.getInt("people"));
                d.setReservation_date(rs.getString("reservation_date"));
                d.setReservation_time(rs.getString("reservation_time"));
                list.add(d);
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error while retrieving all done records: " + e.getMessage());
        } 
        return list;
    }
}
