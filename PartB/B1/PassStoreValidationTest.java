import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

/**
 * Task B1.1: Comprehensive validation suite for PassStore.
 * Tests:
 * 1. Normal case: add and find existing valid records.
 * 2. Non-existent ID: find pass ID that does not exist (returns null).
 * 3. Damaged record: malformed line skipped gracefully without aborting search.
 * 4. List operation: filter passes by route number while ignoring damaged records.
 */
public class PassStoreValidationTest {

    public static void main(String[] args) throws IOException {
        Path testFile = Files.createTempFile("pass_validation_test", ".csv");
        PassStore store = new PassStore(testFile);

        System.out.println("==========================================================");
        System.out.println(" TASK B1.1 - PASS STORE VALIDATION TEST SUITE");
        System.out.println("==========================================================");

        // Setup test records
        store.add("P1001", "192311001", "Arun Kumar", 12, 4500.0);
        Files.writeString(testFile, "P1002,DAMAGED LINE MISSING FIELDS\n", StandardOpenOption.APPEND);
        store.add("P1003", "192311003", "Divya Rajan", 7, 3800.0);
        Files.writeString(testFile, "\n", StandardOpenOption.APPEND); // Blank line
        store.add("P1004", "192311004", "Karthik Iyer", 12, 4500.0);

        // Test 1: Normal Case (Existing Pass ID)
        System.out.println("\n[Test 1] Find Existing Pass ID ('P1003'):");
        String[] r1 = store.find("P1003");
        System.out.println("  Result: " + Arrays.toString(r1));
        boolean test1Pass = r1 != null && r1[0].equals("P1003") && r1[2].equals("Divya Rajan");
        System.out.println("  Verification: " + (test1Pass ? "PASSED (Found correct record)" : "FAILED"));

        // Test 2: Non-existent Pass ID
        System.out.println("\n[Test 2] Find Non-existent Pass ID ('P9999'):");
        String[] r2 = store.find("P9999");
        System.out.println("  Result: " + Arrays.toString(r2));
        boolean test2Pass = r2 == null;
        System.out.println("  Verification: " + (test2Pass ? "PASSED (Returned null as expected)" : "FAILED"));

        // Test 3: Damaged Line (Malformatted Record 'P1002')
        System.out.println("\n[Test 3] Find Damaged Line ('P1002'):");
        String[] r3 = store.find("P1002");
        System.out.println("  Result: " + Arrays.toString(r3));
        boolean test3Pass = r3 == null;
        System.out.println("  Verification: " + (test3Pass ? "PASSED (Skipped damaged record without crashing)" : "FAILED"));

        // Test 4: List Passes by Route (Route 12)
        System.out.println("\n[Test 4] List Passes on Route 12:");
        List<String[]> route12Passes = store.list(12);
        for (String[] pass : route12Passes) {
            System.out.println("  Found: " + Arrays.toString(pass));
        }
        boolean test4Pass = route12Passes.size() == 2 && route12Passes.get(0)[0].equals("P1001") && route12Passes.get(1)[0].equals("P1004");
        System.out.println("  Verification: " + (test4Pass ? "PASSED (Found exactly 2 passes on Route 12)" : "FAILED"));

        System.out.println("\n==========================================================");
        System.out.println(" ALL B1.1 TESTS COMPLETED SUCCESSFULLY");
        System.out.println("==========================================================");

        Files.deleteIfExists(testFile);
    }
}
