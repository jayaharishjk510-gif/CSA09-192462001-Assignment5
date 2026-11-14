package database;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Manages JDBC Database Connections using externalized configuration.
 * Avoids hardcoded credentials by reading from db.properties or environment variables.
 */
public class DBConnection {

    private static final Properties props = new Properties();
    private static boolean initialized = false;

    private static synchronized void init() {
        if (initialized) return;

        Path propsFile = Paths.get("database", "db.properties");
        if (!Files.exists(propsFile)) {
            // Check relative to subfolders
            propsFile = Paths.get("..", "database", "db.properties");
        }
        if (!Files.exists(propsFile)) {
            propsFile = Paths.get("..", "..", "database", "db.properties");
        }

        if (Files.exists(propsFile)) {
            try (InputStream in = Files.newInputStream(propsFile)) {
                props.load(in);
            } catch (IOException e) {
                System.err.println("[WARN] Could not load db.properties: " + e.getMessage());
            }
        }

        // Allow environment variable overrides
        if (System.getenv("DB_URL") != null) props.setProperty("db.url", System.getenv("DB_URL"));
        if (System.getenv("DB_USER") != null) props.setProperty("db.username", System.getenv("DB_USER"));
        if (System.getenv("DB_PASSWORD") != null) props.setProperty("db.password", System.getenv("DB_PASSWORD"));

        initialized = true;
    }

    /**
     * Obtains a standard live MySQL JDBC Connection.
     */
    public static Connection getConnection() throws SQLException {
        init();
        String url = props.getProperty("db.url", "jdbc:mysql://localhost:3306/bus_pass_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC");
        String user = props.getProperty("db.username", "root");
        String pass = props.getProperty("db.password", "");

        try {
            Class.forName(props.getProperty("db.driver", "com.mysql.cj.jdbc.Driver"));
        } catch (ClassNotFoundException e) {
            // Driver may be auto-loaded via SPI in modern JDBC 4.x
        }

        return DriverManager.getConnection(url, user, pass);
    }

    public static Properties getProperties() {
        init();
        return props;
    }
}
