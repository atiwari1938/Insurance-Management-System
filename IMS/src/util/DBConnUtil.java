package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnUtil {

    private static Connection connection;

    private DBConnUtil() {}

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                String connectionString = DBPropertyUtil.getPropertyFilePath();
                connection = DriverManager.getConnection(connectionString);
                System.out.println("Database connection established.");
            } catch (SQLException e) {
                e.printStackTrace();
                throw new SQLException("Failed to establish Connection.", e);
            }
        }
        return connection;
    }
}
