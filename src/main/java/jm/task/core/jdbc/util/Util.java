package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String url = "jdbc:mysql://localhost:3306/preproect";
    private static final String user = "Tatarinov";
    private static final String psv = "root";

    private Util() {
    }

    public static Connection getConnection() throws SQLException {

            return DriverManager.getConnection(url, user, psv);

    }
}
