import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Task B2 / A5.2: Secure login verification using PreparedStatement parameter binding.
 * Completely immune to SQL Injection attacks.
 */
public class SecureLogin {

    /**
     * Secure login method using parameterized placeholders (?).
     * The database driver treats parameters strictly as literal data values,
     * preventing any alteration of the SQL query structure or execution plan.
     *
     * @param con Active database connection
     * @param id User identifier input
     * @param pwd Password input
     * @return true if and only if a matching user record exists
     * @throws SQLException on database error
     */
    public static boolean authenticate(Connection con, String id, String pwd) throws SQLException {
        String sql = "SELECT * FROM users WHERE id=? AND pwd=?";
        System.out.println("  [DEBUG SQL] Executing prepared query: " + sql + " [params: id='" + id + "', pwd='***']");

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, pwd);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
}
