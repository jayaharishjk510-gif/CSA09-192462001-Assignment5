import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Task B2 / A5.2: Vulnerable login verification using raw SQL String concatenation.
 * Susceptible to SQL Injection attacks.
 */
public class UnsafeLogin {

    /**
     * Vulnerable login method that concatenates untrusted input directly into SQL statement.
     *
     * @param con Active database connection
     * @param id User identifier input
     * @param pwd Password input
     * @return true if credentials match or if SQL injection payload evaluates to TRUE
     * @throws SQLException on database error
     */
    public static boolean authenticate(Connection con, String id, String pwd) throws SQLException {
        // Vulnerable SQL query construction
        String sql = "SELECT * FROM users WHERE id='" + id + "' AND pwd='" + pwd + "'";
        System.out.println("  [DEBUG SQL] Executing raw query: " + sql);

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            // If any record is returned, authentication succeeds
            return rs.next();
        }
    }
}
