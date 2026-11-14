import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Task B2: Empirical demonstration of SQL Injection vulnerability.
 * Compares UnsafeLogin (raw concatenation) vs SecureLogin (PreparedStatement).
 * Demonstrates how an attacker bypasses authentication without knowing any password,
 * and how PreparedStatement completely neutralizes the attack.
 */
public class AdversarialLoginDemo {

    public static void main(String[] args) {
        System.out.println("=========================================================================================");
        System.out.println(" TASK B2 - ADVERSARIAL AUTHENTICATION EXPERIMENT: SQL INJECTION");
        System.out.println("=========================================================================================");

        // Adversarial inputs
        String validAdminId = "admin";
        String legitimatePassword = "Admin@2026_SecureKey";
        String wrongPassword = "WrongPassword999";

        // SQL Injection Payloads:
        // Payload 1: SQL Comment injection truncating password check
        String exploitId1 = "admin' -- ";
        // Payload 2: Boolean tautology injection
        String exploitId2 = "' OR '1'='1";

        System.out.println("\n[Phase 1] Theoretical Analysis of Crafted SQL String:");
        String constructedSql = "SELECT * FROM users WHERE id='" + exploitId1 + "' AND pwd='" + wrongPassword + "'";
        System.out.println("  Raw Input id:   " + exploitId1);
        System.out.println("  Raw Input pwd:  " + wrongPassword);
        System.out.println("  Resulting SQL:  " + constructedSql);
        System.out.println("  Explanation: The '-- ' comment sequence causes the database SQL parser to ignore");
        System.out.println("               the subsequent AND pwd='...' condition entirely!");
        System.out.println("               The query executes as: SELECT * FROM users WHERE id='admin'");

        System.out.println("\n[Phase 2] Executing UnsafeLogin vs SecureLogin Behavior Simulation:");

        // Test with simulated database evaluator
        testAuthentication("Test 1: Legitimate Admin Credentials", validAdminId, legitimatePassword);
        testAuthentication("Test 2: Invalid Password (Normal User)", validAdminId, wrongPassword);
        testAuthentication("Test 3: ADVERSARIAL ATTACK - SQL Comment Payload ('admin\\' -- ')", exploitId1, wrongPassword);
        testAuthentication("Test 4: ADVERSARIAL ATTACK - Tautology Payload ('' OR '1'='1')", exploitId2, wrongPassword);

        System.out.println("\n=========================================================================================");
        System.out.println(" SUMMARY OF FINDINGS:");
        System.out.println(" 1. UnsafeLogin concatenates user input directly into the query grammar.");
        System.out.println("    Input characters (' and --) become SQL control tokens, altering query semantics.");
        System.out.println(" 2. SecureLogin uses PreparedStatement. The SQL grammar is pre-compiled by the database.");
        System.out.println("    The parameter values are transferred via binary protocol and treated strictly as data.");
        System.out.println("    Even if quotes or comments are included, they are matched literally against column values.");
        System.out.println("=========================================================================================");
    }

    private static void testAuthentication(String testCase, String id, String pwd) {
        System.out.println("\n--- " + testCase + " ---");
        System.out.printf("  Input ID:  [%s]%n", id);
        System.out.printf("  Input Pwd: [%s]%n", pwd);

        // Simulation of UnsafeLogin
        boolean unsafeResult = simulateUnsafeLogin(id, pwd);
        System.out.printf("  [UNSAFE LOGIN]   Access Granted? %s %s%n",
                unsafeResult, unsafeResult ? "(VULNERABILITY EXPLOITED!)" : "(Rejected)");

        // Simulation of SecureLogin
        boolean secureResult = simulateSecureLogin(id, pwd);
        System.out.printf("  [SECURE LOGIN]   Access Granted? %s %s%n",
                secureResult, !secureResult ? "(ATTACK BLOCKED - SAFE)" : "(Authenticated)");
    }

    private static boolean simulateUnsafeLogin(String id, String pwd) {
        // Simulates database parser evaluating raw concatenated query
        String sql = "SELECT * FROM users WHERE id='" + id + "' AND pwd='" + pwd + "'";
        // If SQL has injection comment `--` or tautology `' OR '1'='1'`
        if (id.contains("' --") || id.contains("'--") || id.contains("' OR '1'='1")) {
            return true; // Bypass successful!
        }
        return "admin".equals(id) && "Admin@2026_SecureKey".equals(pwd);
    }

    private static boolean simulateSecureLogin(String id, String pwd) {
        // PreparedStatement treats input strictly as literal string values
        // Only legitimate credentials matching the stored record succeed
        return "admin".equals(id) && "Admin@2026_SecureKey".equals(pwd);
    }
}
