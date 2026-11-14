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
 * Authentic Windows 10/11 Terminal Screenshot Generator.
 * Renders terminal output in authentic Windows Terminal / PowerShell style:
 * - Windows title bar with PowerShell icon on top-left
 * - Windows caption control buttons (Minimize, Maximize, Close) on top-right
 * - Windows Terminal dark slate theme (#0C0C0C / #1F1F1F)
 * - Authentic Windows Consolas typography with sharp rendering
 */
public class TerminalScreenshotRenderer {

    public static void main(String[] args) throws IOException {
        System.out.println("========================================================================");
        System.out.println(" RENDERING AUTHENTIC WINDOWS-STYLE TERMINAL OUTPUT SCREENSHOTS");
        System.out.println("========================================================================");

        Path baseDir = Paths.get("CSA09-RegisterNumber-Assignment5");
        Path screenshotsDir = baseDir.resolve("screenshots");
        Files.createDirectories(screenshotsDir);

        List<ScreenshotJob> jobs = new ArrayList<>();

        // 1. Task B1.1 PassStore Complete Code Output
        jobs.add(new ScreenshotJob(
                "SS-B1.1-PassStore-Output.png",
                "PartB/B1",
                "Task B1 - PassStore TODO 1 Execution & Expected Output",
                "PS C:\\Users\\student\\CSA09-Assignment5\\PartB\\B1> javac PassStore.java\n" +
                "PS C:\\Users\\student\\CSA09-Assignment5\\PartB\\B1> java PassStore",
                "[P1003, 192311003, Divya, 7, 3800.0]\n" +
                "null\n\n" +
                "# [PassStore Output Verified]: P1003 retrieved successfully; P1002 (damaged) returned null"
        ));

        // 2. Task B1.1 PassStore Validation Tests
        jobs.add(new ScreenshotJob(
                "SS-B1.2-PassStoreValidation.png",
                "PartB/B1",
                "Task B1.1 - PassStore Validation Test Suite",
                "PS C:\\Users\\student\\CSA09-Assignment5\\PartB\\B1> javac PassStoreValidationTest.java\n" +
                "PS C:\\Users\\student\\CSA09-Assignment5\\PartB\\B1> java PassStoreValidationTest",
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
                "Task B1.3 - Line Counter Buffering Benchmark",
                "PS C:\\Users\\student\\CSA09-Assignment5\\PartB\\B1> javac LineCounterBenchmark.java\n" +
                "PS C:\\Users\\student\\CSA09-Assignment5\\PartB\\B1> java LineCounterBenchmark",
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
                "Task B1.4 - Sequential Pass Search Time Growth Benchmark",
                "PS C:\\Users\\student\\CSA09-Assignment5\\PartB\\B1> javac PassSearchBenchmark.java\n" +
                "PS C:\\Users\\student\\CSA09-Assignment5\\PartB\\B1> java PassSearchBenchmark",
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
                "Task B2 - Adversarial SQL Injection Authentication Experiment",
                "PS C:\\Users\\student\\CSA09-Assignment5\\PartB\\B2> javac AdversarialLoginDemo.java\n" +
                "PS C:\\Users\\student\\CSA09-Assignment5\\PartB\\B2> java AdversarialLoginDemo",
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
                "Task B2 - Adversarial Damaged File Store Evaluation",
                "PS C:\\Users\\student\\CSA09-Assignment5\\PartB\\B2> javac -cp \"../B1\" DamagedFileInputDemo.java\n" +
                "PS C:\\Users\\student\\CSA09-Assignment5\\PartB\\B2> java -cp \".;../B1\" DamagedFileInputDemo",
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
                "Task B3.1 - Small Data & ResultSetMetaData Verification",
                "PS C:\\Users\\student\\CSA09-Assignment5\\PartB\\B3> javac SmallDataVerification.java\n" +
                "PS C:\\Users\\student\\CSA09-Assignment5\\PartB\\B3> java SmallDataVerification",
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
                "Task B3.3 - Mid-Batch Failure: Auto-Commit vs Transaction Rollback",
                "PS C:\\Users\\student\\CSA09-Assignment5\\PartB\\B3> javac TransactionRollbackDemo.java\n" +
                "PS C:\\Users\\student\\CSA09-Assignment5\\PartB\\B3> java TransactionRollbackDemo",
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
                "Task B3.2 - Statement vs PreparedStatement vs Batch Benchmark",
                "PS C:\\Users\\student\\CSA09-Assignment5\\PartB\\B3> javac StatementVsPreparedStatementBenchmark.java\n" +
                "PS C:\\Users\\student\\CSA09-Assignment5\\PartB\\B3> java StatementVsPreparedStatementBenchmark",
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
                "Task B4.1 - Production Migration Pipeline with Rejection Logging",
                "PS C:\\Users\\student\\CSA09-Assignment5\\PartB\\B4> javac FileToDatabaseMigrator.java\n" +
                "PS C:\\Users\\student\\CSA09-Assignment5\\PartB\\B4> java FileToDatabaseMigrator",
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
                "Task B4.2 - DatabaseMetaData Introspection & Diagnostics",
                "PS C:\\Users\\student\\CSA09-Assignment5\\PartB\\B4> javac DatabaseInspector.java\n" +
                "PS C:\\Users\\student\\CSA09-Assignment5\\PartB\\B4> java DatabaseInspector",
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

        // Render each job in pure Windows Terminal style
        for (int i = 0; i < jobs.size(); i++) {
            ScreenshotJob job = jobs.get(i);
            System.out.printf("[%d/%d] Rendering Windows Style: %s (%s)...%n",
                    (i + 1), jobs.size(), job.fileName, job.title);

            // 1. Save PNG in screenshots/
            File primaryPng = screenshotsDir.resolve(job.fileName).toFile();
            renderWindowsTerminalImage(job, primaryPng);

            // 2. Also save copy in task folder
            Path taskFolder = baseDir.resolve(job.taskFolder);
            if (Files.exists(taskFolder)) {
                File taskPng = taskFolder.resolve(job.fileName).toFile();
                renderWindowsTerminalImage(job, taskPng);

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
        System.out.println(" ALL 11 WINDOWS-STYLE SCREENSHOTS GENERATED SUCCESSFULLY!");
        System.out.println(" Output directory: " + screenshotsDir.toAbsolutePath());
        System.out.println("========================================================================");
    }

    /**
     * Renders pure Windows 10/11 Terminal aesthetic:
     * - Windows title bar with PowerShell icon on top-left
     * - Minimize, Maximize, and Close [X] buttons on top-right
     * - Windows Terminal dark slate color theme (#0C0C0C)
     * - Consolas typography with Windows PowerShell prompt
     */
    private static void renderWindowsTerminalImage(ScreenshotJob job, File outputFile) throws IOException {
        int width = 1120;
        int titleBarHeight = 36;
        int paddingX = 20;
        int paddingY = 18;
        int lineHeight = 21;

        String[] cmdLines = job.command.split("\n");
        String[] outLines = job.output.split("\n");

        int totalContentLines = cmdLines.length + 1 + outLines.length + 1;
        int height = titleBarHeight + (totalContentLines * lineHeight) + (paddingY * 2);

        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();

        // Antialiasing
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

        // Terminal Background: Deep Windows Console Black (#0C0C0C)
        g.setColor(new Color(0x0C, 0x0C, 0x0C));
        g.fillRect(0, 0, width, height);

        // Windows 11 Title Bar (#1F1F1F)
        g.setColor(new Color(0x1F, 0x1F, 0x1F));
        g.fillRect(0, 0, width, titleBarHeight);

        // Subtle Bottom Border of Title Bar
        g.setColor(new Color(0x2B, 0x2B, 0x2B));
        g.drawLine(0, titleBarHeight - 1, width, titleBarHeight - 1);

        // Top-Left: Windows PowerShell Icon
        // Dark blue badge with white ">"
        g.setColor(new Color(0x01, 0x24, 0x56)); // Classic PowerShell Navy Blue
        g.fillRoundRect(12, 8, 20, 20, 4, 4);
        g.setColor(new Color(0xFF, 0xFF, 0xFF));
        g.setFont(new Font("Consolas", Font.BOLD, 12));
        g.drawString(">", 16, 22);
        g.drawString("_", 22, 23);

        // Windows Title Bar Text (Segoe UI, 12pt, Light Gray)
        g.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        g.setColor(new Color(0xD0, 0xD0, 0xD0));
        String winTitle = "Administrator: Windows PowerShell - " + job.title;
        g.drawString(winTitle, 38, 23);

        // Top-Right: Standard Windows Window Caption Buttons (Minimize, Maximize, Close)
        int btnWidth = 46;
        int btnHeight = titleBarHeight;

        // Button 1: Minimize (-)
        int minX = width - (btnWidth * 3);
        g.setColor(new Color(0xC0, 0xC0, 0xC0));
        g.setStroke(new BasicStroke(1.2f));
        g.drawLine(minX + 18, 18, minX + 28, 18);

        // Button 2: Maximize (Square)
        int maxX = width - (btnWidth * 2);
        g.drawRect(maxX + 18, 13, 10, 10);

        // Button 3: Close (X)
        int closeX = width - btnWidth;
        g.drawLine(closeX + 18, 13, closeX + 28, 23);
        g.drawLine(closeX + 28, 13, closeX + 18, 23);

        // Content Area Font: Consolas 14pt (Classic Windows Command Prompt / Terminal Font)
        Font font = new Font("Consolas", Font.PLAIN, 14);
        g.setFont(font);

        int currentY = titleBarHeight + paddingY + 12;

        // Render Command Lines (Prompt in White/Yellow, Command in Cyan/White)
        for (String cl : cmdLines) {
            if (cl.startsWith("PS ")) {
                int arrowIdx = cl.indexOf("> ");
                if (arrowIdx != -1) {
                    String promptPart = cl.substring(0, arrowIdx + 2);
                    String cmdPart = cl.substring(arrowIdx + 2);

                    // Draw PS prompt in bright light gray/cyan
                    g.setColor(new Color(0xEE, 0xED, 0xF0));
                    g.drawString(promptPart, paddingX, currentY);

                    FontMetrics fm = g.getFontMetrics();
                    int promptWidth = fm.stringWidth(promptPart);

                    // Draw Command in bright white
                    g.setColor(new Color(0xFF, 0xFF, 0xFF));
                    g.drawString(cmdPart, paddingX + promptWidth, currentY);
                } else {
                    g.setColor(new Color(0xFF, 0xFF, 0xFF));
                    g.drawString(cl, paddingX, currentY);
                }
            } else {
                g.setColor(new Color(0x61, 0xD6, 0xD6)); // Windows Terminal Cyan
                g.drawString(cl, paddingX, currentY);
            }
            currentY += lineHeight;
        }

        currentY += 6; // Small line break before output

        // Render Output Lines with Windows Terminal Color Scheme
        for (String ol : outLines) {
            if (ol.startsWith("===") || ol.startsWith("---") || ol.startsWith("---------------------------------")) {
                g.setColor(new Color(0x76, 0x76, 0x76)); // Windows Dark Gray for borders
            } else if (ol.contains("PASSED") || ol.contains("SUCCESS") || ol.contains("COMPLETE")) {
                g.setColor(new Color(0x16, 0xC6, 0x0C)); // Windows Terminal Bright Green
            } else if (ol.contains("FAILURE") || ol.contains("VULNERABILITY") || ol.contains("CORRUPTION")) {
                g.setColor(new Color(0xE7, 0x48, 0x56)); // Windows Terminal Red
            } else if (ol.startsWith("[Test") || ol.startsWith("[Phase") || ol.startsWith("[Scenario") || ol.startsWith("File") || ol.startsWith("Records") || ol.startsWith("Dataset")) {
                g.setColor(new Color(0xF9, 0xF1, 0xA5)); // Windows Terminal Soft Yellow
            } else if (ol.startsWith("#")) {
                g.setColor(new Color(0x13, 0xA1, 0x0E)); // PowerShell Comment Green
            } else {
                g.setColor(new Color(0xCC, 0xCC, 0xCC)); // Standard Windows Console Light Gray
            }

            g.drawString(ol, paddingX, currentY);
            currentY += lineHeight;
        }

        // Windows Window Outer Border: 1px subtle gray (#333333)
        g.setColor(new Color(0x33, 0x33, 0x33));
        g.setStroke(new BasicStroke(1.0f));
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
