package be.ehb.multec.data.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public final class MySqlConnection {

    private static final String URL = "jdbc:mysql://localhost/library?serverTimezone=UTC";
    private static final String USER = "library_db_user";
    private static final String PWD = "pass";
    private static final String SQL_FILE = "library.sql";

    private MySqlConnection() {
    }

    //wrapper method to establish connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PWD);
    }

    // executes sql scipt (with ddl so db is rewritten)
    // just like the thunderbolt icon in gui mySql workbench
    public static void resetDatabase() {
        // moet in try catch want jdbc code throws errors
        try (Scanner s = new Scanner(new File(SQL_FILE));// maak scanner-obj van sql-script
             Connection con = getConnection();
             Statement stmt = con.createStatement();) { // maak een statement voor de db
            StringBuffer content = new StringBuffer();
            while (s.hasNext())
                content.append(s.nextLine()); // scan sql-script en steek in stringbuffer
            String[] sqlStatements = content.toString().split(";"); // splits stringbuffer per "statement" en vang op in array

            for (String sqlStatement : sqlStatements){
                stmt.addBatch(sqlStatement); // voeg alle string-statements toe aan statement batch
            }
            stmt.executeBatch();

        } catch (FileNotFoundException | SQLException e) {
            System.err.println(e);
        }
    }
}