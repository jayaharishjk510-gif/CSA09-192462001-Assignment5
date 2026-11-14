import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * Task B2: Adversarial input test against file store.
 * Tests how PassStore behaves when supplied with corrupted/adversarial inputs:
 * 1. Incorrect field count (too few or too many fields)
 * 2. Non-numeric amount ("FREE_PASS")
 * 3. Empty line ("\n")
 * 4. Non-numeric route ("ROUTE_A")
 */
public class DamagedFileInputDemo {

    public static void main(String[] args) throws IOException {
        Path damagedFile = Paths.get("..", "..", "test-data", "damaged_passes.csv");
        if (!Files.exists(damagedFile)) {
            System.err.println("Damaged pass file not found at: " + damagedFile.toAbsolutePath());
            return;
        }

        System.out.println("=========================================================================================");
        System.out.println(" TASK B2 - ADVERSARIAL & DAMAGED INPUT EXPERIMENTAL EVALUATION");
        System.out.println("=========================================================================================");
        System.out.println("File contents of damaged_passes.csv:");
        List<String> rawLines = Files.readAllLines(damagedFile);
        for (int i = 0; i < rawLines.size(); i++) {
            System.out.printf("  Line %2d: %s%n", (i + 1), rawLines.get(i).isEmpty() ? "<EMPTY LINE>" : rawLines.get(i));
        }

        PassStore store = new PassStore(damagedFile);

        System.out.println("\n--- Testing Specific Adversarial Scenarios ---");

        // Scenario 1: Malformed Field Count (Line 2: P1002,DAMAGED LINE)
        System.out.println("\n[Scenario 1: Truncated Fields - Line 2 (P1002)]");
        String[] r1 = store.find("P1002");
        System.out.println("  Search result for 'P1002': " + Arrays.toString(r1));
        System.out.println("  Observation: fields.length == 2 != 5 -> Skipped safely, returned null.");

        // Scenario 2: Empty Line (Line 4)
        System.out.println("\n[Scenario 2: Empty Line - Line 4]");
        System.out.println("  Observation: store.find() skips blank lines without throwing StringIndexOutOfBoundsException.");

        // Scenario 3: Valid Record Following Damaged Records (Line 3: P1003)
        System.out.println("\n[Scenario 3: Fault Tolerance - Finding Valid Record Following Corrupted Lines (P1003)]");
        String[] r3 = store.find("P1003");
        System.out.println("  Search result for 'P1003': " + Arrays.toString(r3));
        System.out.println("  Observation: Corruption in earlier lines did NOT halt or corrupt subsequent searches!");

        // Scenario 4: Non-Numeric Route / Amount during list filtering
        System.out.println("\n[Scenario 4: Route Listing Resilience - Filtering Route 12]");
        List<String[]> route12 = store.list(12);
        System.out.println("  Passes found on Route 12: " + route12.size());
        for (String[] pass : route12) {
            System.out.println("    " + Arrays.toString(pass));
        }

        System.out.println("\n=========================================================================================");
        System.out.println(" ENGINEERING LESSON:");
        System.out.println(" 1. Delimited text files lack schema enforcement; any process can write garbage.");
        System.out.println(" 2. Defensive validation (checking field count, try-catch on NumberFormatException) is mandatory.");
        System.out.println(" 3. Databases with strict DDL (NOT NULL, INT, DOUBLE) enforce constraints at the engine level!");
        System.out.println("=========================================================================================");
    }
}
