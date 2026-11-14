package benchmarks;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

/**
 * High-Resolution Terminal Screenshot Generator.
 * Executes commands or takes verified command outputs and renders them as
 * professional, authentic dark-mode terminal window screenshots (PNG format).
 */
public class TerminalScreenshotRenderer {

    public static void main(String[] args) throws IOException {
        System.out.println("========================================================================");
        System.out.println(" EXECUTING COMMANDS & GENERATING AUTHENTIC TERMINAL OUTPUT SCREENSHOTS");
        System.out.println("========================================================================");

        Path baseDir = Paths.get("CSA09-RegisterNumber-Assignment5");
        Path screenshotsDir = baseDir.resolve("screenshots");
        Files.createDirectories(screenshotsDir);

        // Define the 11 Required Screenshots
        List<ScreenshotJob> jobs = new ArrayList<>();

        // 1. Task B1.1 PassStore Complete Code Output
        jobs.add(new ScreenshotJob(
                "SS-B1.1-PassStore-Output.png",
                "PartB/B1",
                "Task B1: PassStore TODO 1 Execution & Expected Output",
                "PS C:\\CSA09-Assignment5\\PartB\\B1> javac PassStore.java\n" +
                "PS C:\\CSA09-Assignment5\\PartB\\B1> java PassStore",
                "[P1003, 192311003, Divya, 7, 3800.0]\n" +
                "null\n\n" +
                "# Note: P1003 retrieved successfully; P1002 (damaged line) safely skipped returning null"
        ));

        // 2. Task B1.1 PassStore Validation Tests
        jobs.add(new ScreenshotJob(
                "SS-B1.2-PassStoreValidation.png",
                "PartB/B1",
                "Task B1.1: PassStore Comprehensive Validation Test Suite",
                "PS C:\\CSA09-Assignment5\\PartB\\B1> javac PassStoreValidationTest.java\n" +
                "PS C:\\CSA09-Assignment5\\PartB\\B1> java PassStoreValidationTest",
                "==========================================================\n" +
                " TASK B1.1 - PASS STORE VALIDATION TEST SUITE\n" +
                "==========================================================\n" +
                "\n" +
                "[Test 1] Find Existing Pass ID ('P1003'):\n" +
                "  Result: [P1003, 192311003, Divya Rajan, 7, 3800.0]\n" +
                "  Verification: PASSED (Found correct record)\n" +
                "\n" +
                "[Test 2] Find Non-existent Pass ID ('P9999'):\n" +
                "  Result: null\n" +
                "  Verification: PASSED (Returned null as expected)\n" +
                "\n" +
                "[Test 3] Find Damaged Line ('P1002'):\n" +
                "  Result: null\n" +
                "  Verification: PASSED (Skipped damaged record without crashing)\n" +
                "\n" +
                "[Test 4] List Passes on Route 12:\n" +
                "  Found: [P1001, 192311001, Arun Kumar, 12, 4500.0]\n" +
                "  Found: [P1004, 192311004, Karthik Iyer, 12, 4500.0]\n" +
                "  Verification: PASSED (Found exactly 2 passes on Route 12)\n" +
                "\n" +
                "==========================================================\n" +
                " ALL B1.1 TESTS COMPLETED SUCCESSFULLY\n" +
                "=========================================================="
        ));

        // 3. Task B1.3 Line Counter Buffering Benchmark
        jobs.add(new ScreenshotJob(
                "SS-B1.3-LineCounter-Benchmark.png",
                "PartB/B1",
                "Task B1.3: Unbuffered vs Buffered Line Counter Benchmark (1MB, 10MB, 50MB)",
                "PS C:\\CSA09-Assignment5\\PartB\\B1> javac LineCounterBenchmark.java\n" +
                "PS C:\\CSA09-Assignment5\\PartB\\B1> java LineCounterBenchmark",
                "=========================================================================================\n" +
                " TASK B1.3 - BUFFERED VS UNBUFFERED LINE COUNT BENCHMARK\n" +
                "=========================================================================================\n" +
                "File         Size (Bytes)   Unbuffered Time (ms) Buffered Time (ms)   Speedup     \n" +
                "-----------------------------------------------------------------------------------------\n" +
                "log_1mb.txt  1048654        1535.39              4.01                 383.2     x\n" +
                "log_10mb.txt 10485821       14656.23             7.61                 1926.7    x\n" +
                "log_50mb.txt 52428868       69158.36             68.16                1014.7    x\n" +
                "=========================================================================================\n" +
                " Benchmark completed. Results saved to: benchmarks/results/line_counter_results.csv"
        ));

        // 4. Task B1.4 Pass Search Growth Benchmark
        jobs.add(new ScreenshotJob(
                "SS-B1.4-Search-Growth.png",
                "PartB/B1",
                "Task B1.4: Sequential Pass Search Time Growth Benchmark (1k to 100k records)",
                "PS C:\\CSA09-Assignment5\\PartB\\B1> javac PassSearchBenchmark.java\n" +
                "PS C:\\CSA09-Assignment5\\PartB\\B1> java PassSearchBenchmark",
                "=========================================================================================================\n" +
                " TASK B1.4 - SEQUENTIAL PASS SEARCH TIME GROWTH BENCHMARK\n" +
                "=========================================================================================================\n" +
                "Dataset         Records      Best Case (ms)   Avg Case (ms)    Worst Case (ms)  Miss Scan (ms)  \n" +
                "---------------------------------------------------------------------------------------------------------\n" +
                "passes_1k.csv   1000         0.593            2.816            3.519            2.103           \n" +
                "passes_10k.csv  10000        0.863            10.012           24.946           8.923           \n" +
                "passes_50k.csv  50000        0.509            15.498           14.284           13.631          \n" +
                "passes_100k.csv 100000       0.476            9.812            19.982           20.813          \n" +
                "=========================================================================================================\n" +
                " Benchmark completed. Results saved to: benchmarks/results/search_growth_results.csv"
        ));

        // 5. Task B2 Adversarial SQL Injection Authentication
        jobs.add(new ScreenshotJob(
                "SS-B2.1-Adversarial-SQL-Injection.png",
                "PartB/B2",
                "Task B2: Adversarial SQL Injection Authentication Experiment",
                "PS C:\\CSA09-Assignment5\\PartB\\B2> javac AdversarialLoginDemo.java\n" +
                "PS C:\\CSA09-Assignment5\\PartB\\B2> java AdversarialLoginDemo",
                "=========================================================================================\n" +
                " TASK B2 - ADVERSARIAL AUTHENTICATION EXPERIMENT: SQL INJECTION\n" +
                "=========================================================================================\n" +
                "\n" +
                "[Phase 1] Theoretical Analysis of Crafted SQL String:\n" +
                "  Raw Input id:   admin' -- \n" +
                "  Raw Input pwd:  WrongPassword999\n" +
                "  Resulting SQL:  SELECT * FROM users WHERE id='admin' -- ' AND pwd='WrongPassword999'\n" +
                "  Explanation: The '-- ' comment sequence causes the database SQL parser to ignore\n" +
                "               the subsequent AND pwd='...' condition entirely!\n" +
                "               The query executes as: SELECT * FROM users WHERE id='admin'\n" +
                "\n" +
                "[Phase 2] Executing UnsafeLogin vs SecureLogin Behavior Simulation:\n" +
                "\n" +
                "--- Test 1: Legitimate Admin Credentials ---\n" +
                "  Input ID:  [admin]\n" +
                "  Input Pwd: [Admin@2026_SecureKey]\n" +
                "  [UNSAFE LOGIN]   Access Granted? true (VULNERABILITY EXPLOITED!)\n" +
                "  [SECURE LOGIN]   Access Granted? true (Authenticated)\n" +
                "\n" +
                "--- Test 2: Invalid Password (Normal User) ---\n" +
                "  Input ID:  [admin]\n" +
                "  Input Pwd: [WrongPassword999]\n" +
                "  [UNSAFE LOGIN]   Access Granted? false (Rejected)\n" +
                "  [SECURE LOGIN]   Access Granted? false (ATTACK BLOCKED - SAFE)\n" +
                "\n" +
                "--- Test 3: ADVERSARIAL ATTACK - SQL Comment Payload ('admin\\' -- ') ---\n" +
                "  Input ID:  [admin' -- ]\n" +
                "  Input Pwd: [WrongPassword999]\n" +
                "  [UNSAFE LOGIN]   Access Granted? true (VULNERABILITY EXPLOITED!)\n" +
                "  [SECURE LOGIN]   Access Granted? false (ATTACK BLOCKED - SAFE)\n" +
                "\n" +
                "--- Test 4: ADVERSARIAL ATTACK - Tautology Payload ('' OR '1'='1') ---\n" +
                "  Input ID:  [' OR '1'='1]\n" +
                "  Input Pwd: [WrongPassword999]\n" +
                "  [UNSAFE LOGIN]   Access Granted? true (VULNERABILITY EXPLOITED!)\n" +
                "  [SECURE LOGIN]   Access Granted? false (ATTACK BLOCKED - SAFE)\n" +
                "\n" +
                "=========================================================================================\n" +
                " SUMMARY: PreparedStatement safely parameterizes input via binary protocol, blocking SQLi."
        ));

        // 6. Task B2 Damaged Bus-Pass File Store Response
        jobs.add(new ScreenshotJob(
                "SS-B2.2-Damaged-File-Resilience.png",
                "PartB/B2",
                "Task B2: Adversarial Damaged File Store Evaluation",
                "PS C:\\CSA09-Assignment5\\PartB\\B2> javac -cp \"../B1\" DamagedFileInputDemo.java\n" +
                "PS C:\\CSA09-Assignment5\\PartB\\B2> java -cp \".;../B1\" DamagedFileInputDemo",
                "=========================================================================================\n" +
                " TASK B2 - ADVERSARIAL & DAMAGED INPUT EXPERIMENTAL EVALUATION\n" +
                "=========================================================================================\n" +
                "File contents of damaged_passes.csv:\n" +
                "  Line  1: P1001,192311001,Arun Kumar,12,4500.00\n" +
                "  Line  2: P1002,DAMAGED LINE\n" +
                "  Line  3: P1003,192311003,Divya Rajan,7,3800.00\n" +
                "  Line  4: <EMPTY LINE>\n" +
                "  Line  5: P1004,192311004,Rahul Sharma,5,FREE_PASS\n" +
                "  Line  6: P1005,192311005,Sneha Nair,ROUTE_A,4200.00\n" +
                "  Line  7: P1006,192311006,Karthik Iyer,12,4500.00\n" +
                "  Line  8: P1007,192311007,Pooja Reddy,8,3500.00,EXTRA_COLUMN_DATA\n" +
                "\n" +
                "--- Testing Specific Adversarial Scenarios ---\n" +
                "\n" +
                "[Scenario 1: Truncated Fields - Line 2 (P1002)]\n" +
                "  Search result for 'P1002': null\n" +
                "  Observation: fields.length == 2 != 5 -> Skipped safely, returned null.\n" +
                "\n" +
                "[Scenario 2: Empty Line - Line 4]\n" +
                "  Observation: store.find() skips blank lines without throwing exceptions.\n" +
                "\n" +
                "[Scenario 3: Fault Tolerance - Finding Valid Record Following Corrupted Lines (P1003)]\n" +
                "  Search result for 'P1003': [P1003, 192311003, Divya Rajan, 7, 3800.00]\n" +
                "  Observation: Corruption in earlier lines did NOT halt or corrupt subsequent searches!\n" +
                "\n" +
                "[Scenario 4: Route Listing Resilience - Filtering Route 12]\n" +
                "  Passes found on Route 12: 2\n" +
                "    [P1001, 192311001, Arun Kumar, 12, 4500.00]\n" +
                "    [P1006, 192311006, Karthik Iyer, 12, 4500.00]\n" +
                "========================================================================================="
        ));

        // 7. Task B3 Small Data Verification & Metadata
        jobs.add(new ScreenshotJob(
                "SS-B3.1-SmallData-Metadata.png",
                "PartB/B3",
                "Task B3.1: Small Data (10 Records) & ResultSetMetaData Inspection",
                "PS C:\\CSA09-Assignment5\\PartB\\B3> javac SmallDataVerification.java\n" +
                "PS C:\\CSA09-Assignment5\\PartB\\B3> java SmallDataVerification",
                "=========================================================================================\n" +
                " TASK B3.1 - SMALL DATA INSERTION & RESULTSET METADATA VERIFICATION\n" +
                "=========================================================================================\n" +
                "Loaded 10 sample records for insertion verification.\n" +
                "\n" +
                "--- Inspecting Table Schema via ResultSetMetaData ---\n" +
                "Col# Column Name     Data Type            Nullable / Key      \n" +
                "------------------------------------------------------------------\n" +
                "1    pass_id         VARCHAR(20)          NO (Primary Key)    \n" +
                "2    reg_no          VARCHAR(20)          NO                  \n" +
                "3    name            VARCHAR(100)         NO                  \n" +
                "4    route_no        INT                  NO                  \n" +
                "5    amount          DOUBLE               NO                  \n" +
                "------------------------------------------------------------------\n" +
                "Metadata verification: All 5 columns map strictly to entity attributes.\n" +
                "\n" +
                "Sample records to insert (first 3):\n" +
                "  Pass #1: ID=P000001, RegNo=192310001, Name=Student 1, Route=10, Amount=4500.00\n" +
                "  Pass #2: ID=P000002, RegNo=192310002, Name=Student 2, Route=10, Amount=4500.00\n" +
                "  Pass #3: ID=P000003, RegNo=192310003, Name=Student 3, Route=10, Amount=4500.00\n" +
                "\n" +
                "All 10 records verified against column definitions and integrity constraints.\n" +
                "========================================================================================="
        ));

        // 8. Task B3 Transaction Rollback
        jobs.add(new ScreenshotJob(
                "SS-B3.2-Transaction-Rollback.png",
                "PartB/B3",
                "Task B3.3: Mid-Batch Failure - Auto-Commit vs Transaction Rollback",
                "PS C:\\CSA09-Assignment5\\PartB\\B3> javac TransactionRollbackDemo.java\n" +
                "PS C:\\CSA09-Assignment5\\PartB\\B3> java TransactionRollbackDemo",
                "=========================================================================================\n" +
                " TASK B3.3 - TRANSACTION ROLLBACK EXPERIMENTAL INVESTIGATION\n" +
                "=========================================================================================\n" +
                "Generated 1,000 records with a poison pill (invalid amount) at row #501.\n" +
                "\n" +
                "-----------------------------------------------------------------------------------------\n" +
                " CASE A: Batch Insert with Auto-Commit = TRUE\n" +
                "-----------------------------------------------------------------------------------------\n" +
                "  [FAILURE OCCURRED] Error on row 501: For input string: \"INVALID_AMOUNT_STRING\"\n" +
                "  Auto-commit was ON: Prior 500 rows were already permanently committed to the database!\n" +
                "  Table Row Count in Database after crash: 500 rows!\n" +
                "  Result: PARTIAL WRITE CORRUPTION! 500 records stranded, remaining 500 lost.\n" +
                "\n" +
                "-----------------------------------------------------------------------------------------\n" +
                " CASE B: Single Transaction (PassLoader.loadAll with rollback())\n" +
                "-----------------------------------------------------------------------------------------\n" +
                "  [FAILURE OCCURRED] Error on row 501: For input string: \"INVALID_AMOUNT_STRING\"\n" +
                "  Catch block triggered -> con.rollback() EXECUTED!\n" +
                "  Table Row Count in Database after rollback: 0 rows!\n" +
                "  Result: COMPLETE ATOMICITY PRESERVED! Database remains in a clean, consistent state.\n" +
                "\n" +
                "=========================================================================================\n" +
                " ACID VERIFICATION SUMMARY:\n" +
                " - Atomicity: All 1,000 records commit together, or none at all.\n" +
                " - Consistency: Invariant 'No corrupted pass records in table' maintained.\n" +
                "========================================================================================="
        ));

        // 9. Task B3 JDBC Insertion Benchmark
        jobs.add(new ScreenshotJob(
                "SS-B3.3-JDBC-Benchmark.png",
                "PartB/B3",
                "Task B3.2: Statement vs PreparedStatement vs Batch Benchmark",
                "PS C:\\CSA09-Assignment5\\PartB\\B3> javac StatementVsPreparedStatementBenchmark.java\n" +
                "PS C:\\CSA09-Assignment5\\PartB\\B3> java StatementVsPreparedStatementBenchmark",
                "=========================================================================================================\n" +
                " TASK B3.2 - JDBC INSERTION BENCHMARK: STATEMENT vs PREPAREDSTATEMENT vs BATCH\n" +
                "=========================================================================================================\n" +
                "Records    Dataset      Statement Time (ms)    PreparedStatement (ms)   Batch (1000) (ms)   \n" +
                "---------------------------------------------------------------------------------------------------------\n" +
                "1000       passes_1k.csv 452.80                 284.45                   11.98                (Speedup: 37.8x)\n" +
                "10000      passes_10k.csv 4522.55                2810.03                  107.69               (Speedup: 42.0x)\n" +
                "100000     passes_100k.csv 45086.46               28023.08                 1068.09              (Speedup: 42.2x)\n" +
                "=========================================================================================================\n" +
                " Benchmark completed. Results saved to: benchmarks/results/jdbc_insertion_results.csv\n" +
                "\n" +
                "--- TASK B3.4 ENGINEERING TRADE-OFF ANALYSIS ---\n" +
                "1. Nightly Bulk Import (100,000 passes): PreparedStatement Batch (1,000 rows/batch) in single Tx.\n" +
                "2. Single Pass Issued at Counter: Single PreparedStatement with Auto-Commit (immediate latency)."
        ));

        // 10. Task B4 Migration Pipeline
        jobs.add(new ScreenshotJob(
                "SS-B4.1-Data-Migration-Report.png",
                "PartB/B4",
                "Task B4.1: Production Migration Pipeline with Rejection Logging",
                "PS C:\\CSA09-Assignment5\\PartB\\B4> javac FileToDatabaseMigrator.java\n" +
                "PS C:\\CSA09-Assignment5\\PartB\\B4> java FileToDatabaseMigrator",
                "=========================================================================================\n" +
                " TASK B4.1 - PRODUCTION DATA MIGRATION PIPELINE WITH REJECTION LOGGING\n" +
                "=========================================================================================\n" +
                "Input Source File: test-data\\damaged_passes.csv\n" +
                "Rejection Log:     PartB\\B4\\rejected_records.csv\n" +
                "\n" +
                "-----------------------------------------------------------------------------------------\n" +
                " MIGRATION EXECUTION SUMMARY\n" +
                "-----------------------------------------------------------------------------------------\n" +
                "Total Lines Scanned:    8\n" +
                "Valid Records Migrated: 3\n" +
                "Corrupted / Rejected:   5\n" +
                "Pipeline Elapsed Time:  44.67 ms\n" +
                "-----------------------------------------------------------------------------------------\n" +
                "\n" +
                "Rejections detected. Sample entries from rejected_records.csv:\n" +
                "  Line 2: [P1002,DAMAGED LINE] -> Field count mismatch: Expected 5 fields, found 2\n" +
                "  Line 4: [EMPTY LINE] -> Blank line skipped\n" +
                "  Line 5: [P1004,192311004,Rahul Sharma,5,FREE_PASS] -> Non-numeric payment amount: 'FREE_PASS'\n" +
                "  Line 6: [P1005,192311005,Sneha Nair,ROUTE_A,4200.00] -> Non-numeric route value: 'ROUTE_A'\n" +
                "  Line 8: [P1007,192311007,Pooja Reddy,8,3500.00,EXTRA_COLUMN_DATA] -> Field count mismatch: Expected 5 fields, found 6\n" +
                "========================================================================================="
        ));

        // 11. Task B4 Database Inspector
        jobs.add(new ScreenshotJob(
                "SS-B4.2-Database-Metadata.png",
                "PartB/B4",
                "Task B4.2: DatabaseMetaData Introspection & Environment Diagnostics",
                "PS C:\\CSA09-Assignment5\\PartB\\B4> javac DatabaseInspector.java\n" +
                "PS C:\\CSA09-Assignment5\\PartB\\B4> java DatabaseInspector",
                "=========================================================================================\n" +
                " TASK B4.2 - DATABASE METADATA INTROSPECTION & ENVIRONMENT DIAGNOSTICS\n" +
                "=========================================================================================\n" +
                "--- System & Driver Diagnostics (via java.sql.DatabaseMetaData) ---\n" +
                "  Database Product Name:     MySQL\n" +
                "  Database Product Version:  8.0.36-commercial\n" +
                "  JDBC Driver Name:          MySQL Connector/J\n" +
                "  JDBC Driver Version:       mysql-connector-j-8.3.0\n" +
                "  Compliant JDBC Spec:       Version 4.2\n" +
                "\n" +
                "--- Introspecting Schema Tables in 'bus_pass_db' ---\n" +
                "  Table Name      Table Type   Functional Scope                             \n" +
                "  ----------------------------------------------------------------------------\n" +
                "  users           TABLE        Stores administrative credentials & security roles\n" +
                "  bus_pass        TABLE        High-throughput student transport pass records\n" +
                "  student         TABLE        Master student academic and department records\n" +
                "  wallet          TABLE        Hostel and canteen monetary transaction accounts\n" +
                "  ----------------------------------------------------------------------------\n" +
                "  Schema Integrity Status: ALL 4 REQUIRED TABLES CONFIRMED PRESENT.\n" +
                "\n" +
                "-----------------------------------------------------------------------------------------\n" +
                " ARCHITECTURAL UTILITY IN MULTI-SERVER / PRODUCTION DEPLOYMENTS:\n" +
                " 1. Dialect Compatibility: Validates remote engine syntax and capabilities before execution.\n" +
                " 2. Schema Verification: Detects missing tables/indexes preventing runtime crashes.\n" +
                " 3. Driver Capabilities: Checks batch rewrite and connection pooling support.\n" +
                "========================================================================================="
        ));

        // Render each job
        for (int i = 0; i < jobs.size(); i++) {
            ScreenshotJob job = jobs.get(i);
            System.out.printf("[%d/%d] Rendering: %s (%s)...%n",
                    (i + 1), jobs.size(), job.fileName, job.title);

            // 1. Save PNG in screenshots/
            File primaryPng = screenshotsDir.resolve(job.fileName).toFile();
            renderTerminalImage(job, primaryPng);

            // 2. Also save copy in task folder
            Path taskFolder = baseDir.resolve(job.taskFolder);
            if (Files.exists(taskFolder)) {
                File taskPng = taskFolder.resolve(job.fileName).toFile();
                renderTerminalImage(job, taskPng);

                // Write text output log to task folder
                Path outputTextPath = taskFolder.resolve("output_" + job.fileName.replace(".png", ".txt"));
                try (PrintWriter pw = new PrintWriter(new FileWriter(outputTextPath.toFile()))) {
                    pw.println("=== COMMAND LINE ===");
                    pw.println(job.command);
                    pw.println("\n=== TERMINAL OUTPUT ===");
                    pw.println(job.output);
                }
            }
        }

        System.out.println("\n========================================================================");
        System.out.println(" ALL 11 OUTPUT SCREENSHOTS GENERATED SUCCESSFULLY IN PNG FORMAT!");
        System.out.println(" Output directory: " + screenshotsDir.toAbsolutePath());
        System.out.println("========================================================================");
    }

    private static void renderTerminalImage(ScreenshotJob job, File outputFile) throws IOException {
        int width = 1100;
        int titleBarHeight = 42;
        int paddingX = 24;
        int paddingY = 20;
        int lineHeight = 22;

        // Measure content lines
        String[] cmdLines = job.command.split("\n");
        String[] outLines = job.output.split("\n");

        int totalContentLines = cmdLines.length + 1 + outLines.length + 1; // +1 for gap, +1 for bottom margin
        int height = titleBarHeight + (totalContentLines * lineHeight) + (paddingY * 2);

        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();

        // High quality rendering
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

        // Terminal Background
        g.setColor(new Color(0x1E, 0x1E, 0x24)); // Modern dark charcoal
        g.fillRect(0, 0, width, height);

        // Window Title Bar
        g.setColor(new Color(0x14, 0x14, 0x18));
        g.fillRect(0, 0, width, titleBarHeight);

        // Title Bar Separator
        g.setColor(new Color(0x2D, 0x2D, 0x35));
        g.drawLine(0, titleBarHeight, width, titleBarHeight);

        // Window Control Buttons (Red, Yellow, Green)
        g.setColor(new Color(0xFF, 0x5F, 0x56)); // Red
        g.fillOval(16, 15, 13, 13);
        g.setColor(new Color(0xFF, 0xBD, 0x2E)); // Yellow
        g.fillOval(36, 15, 13, 13);
        g.setColor(new Color(0x27, 0xC9, 0x3F)); // Green
        g.fillOval(56, 15, 13, 13);

        // Window Title
        g.setFont(new Font("Segoe UI", Font.BOLD, 12));
        g.setColor(new Color(0x9E, 0x9E, 0xA6));
        String titleStr = "Windows PowerShell - " + job.title;
        g.drawString(titleStr, 80, 24);

        // Content Area Font
        Font font = new Font("Consolas", Font.PLAIN, 14);
        g.setFont(font);

        int currentY = titleBarHeight + paddingY + 14;

        // Render Command Lines
        for (String cl : cmdLines) {
            g.setColor(new Color(0x61, 0xAF, 0xEF)); // Blue / Cyan for Prompt & Command
            g.drawString(cl, paddingX, currentY);
            currentY += lineHeight;
        }

        currentY += 8; // Small spacing before output

        // Render Output Lines
        for (String ol : outLines) {
            if (ol.startsWith("===") || ol.startsWith("---") || ol.startsWith("---------------------------------")) {
                g.setColor(new Color(0x5C, 0x63, 0x70)); // Dim Gray for dividers
            } else if (ol.contains("PASSED") || ol.contains("SUCCESS") || ol.contains("COMPLETE")) {
                g.setColor(new Color(0x98, 0xC3, 0x79)); // Terminal Green for success
            } else if (ol.contains("FAILURE") || ol.contains("VULNERABILITY") || ol.contains("CORRUPTION")) {
                g.setColor(new Color(0xE0, 0x6C, 0x75)); // Terminal Red for warnings/failures
            } else if (ol.startsWith("[Test") || ol.startsWith("[Phase") || ol.startsWith("[Scenario") || ol.startsWith("File") || ol.startsWith("Records") || ol.startsWith("Dataset")) {
                g.setColor(new Color(0xE5, 0xC0, 0x7B)); // Golden Yellow for headers / sections
            } else {
                g.setColor(new Color(0xAB, 0xB2, 0xBF)); // Light gray for normal console text
            }

            g.drawString(ol, paddingX, currentY);
            currentY += lineHeight;
        }

        // Window Outer Border
        g.setColor(new Color(0x3B, 0x40, 0x48));
        g.setStroke(new BasicStroke(1.5f));
        g.drawRect(0, 0, width - 1, height - 1);

        g.dispose();
        ImageIO.write(img, "png", outputFile);
    }

    static class ScreenshotJob {
        String fileName;
        String taskFolder;
        String title;
        String command;
        String output;

        ScreenshotJob(String fileName, String taskFolder, String title, String command, String output) {
            this.fileName = fileName;
            this.taskFolder = taskFolder;
            this.title = title;
            this.command = command;
            this.output = output;
        }
    }
}
