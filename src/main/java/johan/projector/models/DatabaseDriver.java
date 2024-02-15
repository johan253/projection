package johan.projector.models;

import java.sql.*;

public class DatabaseDriver {
    private Connection myConnection;

    public DatabaseDriver () throws SQLException {
        try {
            myConnection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/database.db");
            System.out.println("database accessed successfully");
        } catch (SQLException e) {
            System.out.println(e);
            System.exit(-1);
        }
        testSQL();
    }
    private void testSQL() throws SQLException {
        Statement s;
        ResultSet r = null;
        try {
            s = myConnection.createStatement();
            r = s.executeQuery("SELECT * FROM Projects");
            String st = r.getString("name");
            System.out.println("SUCCESS: " + st);
        } catch(SQLException e) {
            System.out.println(e + "failed");
        }
    }

    public static void main(String[] args) throws SQLException {
        new DatabaseDriver();
    }
}
