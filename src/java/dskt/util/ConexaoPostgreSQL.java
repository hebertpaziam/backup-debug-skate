package dskt.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoPostgreSQL {

    public static Connection getConnection() throws ClassNotFoundException {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/DSKT_DB", "postgres", "postgres");
        } catch (SQLException e) {
            try {
                Class.forName("org.postgresql.Driver");
                return DriverManager.getConnection("jdbc:postgresql://localhost:5433/DSKT_DB", "postgres", "postgres");
            } catch (SQLException e2) {
                throw new RuntimeException(e2);
            }
        }
    }
}
