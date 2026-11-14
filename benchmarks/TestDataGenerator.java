package benchmarks;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

/**
 * Utility to generate reproducible test datasets for Assignment 5 benchmarks:
 * - 1 MB, 10 MB, and 50 MB log files for LineCounterBenchmark.
 * - 1,000, 10,000, 50,000, and 100,000 record files for PassSearchBenchmark & PassLoader.
 * - Deliberately damaged CSV files for adversarial testing.
 */
public class TestDataGenerator {

    private static final String[] FIRST_NAMES = {"Arun", "Divya", "Rahul", "Sneha", "Karthik", "Pooja", "Vikram", "Ananya", "Sanjay", "Deepa"};
    private static final String[] LAST_NAMES = {"Kumar", "Rajan", "Sharma", "Nair", "Iyer", "Reddy", "Patel", "Menon", "Verma", "Pillai"};
    private static final String[] STOPS = {"Central Station", "Tambaram", "Guindy", "Koyambedu", "Velachery", "T Nagar", "Adyar", "Porur"};

    public static void main(String[] args) throws IOException {
        Path testDataDir = Paths.get("CSA09-RegisterNumber-Assignment5", "test-data");
        Files.createDirectories(testDataDir);

        System.out.println("=== Generating Test Data for Benchmarks ===");

        // 1. Generate Log Files for Buffering Benchmark (Task A2 / B1.3)
        generateLogFile(testDataDir.resolve("log_1mb.txt"), 1 * 1024 * 1024);
        generateLogFile(testDataDir.resolve("log_10mb.txt"), 10 * 1024 * 1024);
        generateLogFile(testDataDir.resolve("log_50mb.txt"), 50 * 1024 * 1024);

        // 2. Generate CSV Pass Records for Search Growth Benchmark (Task B1.4)
        generatePassCsv(testDataDir.resolve("passes_1k.csv"), 1000);
        generatePassCsv(testDataDir.resolve("passes_10k.csv"), 10000);
        generatePassCsv(testDataDir.resolve("passes_50k.csv"), 50000);
        generatePassCsv(testDataDir.resolve("passes_100k.csv"), 100000);

        // 3. Generate Small Clean Sample Dataset
        generatePassCsv(testDataDir.resolve("sample_passes.csv"), 10);

        // 4. Generate Damaged Pass Dataset (Task B1.1, B2)
        generateDamagedCsv(testDataDir.resolve("damaged_passes.csv"));

        System.out.println("=== All Test Datasets Generated Successfully ===");
    }

    public static void generateLogFile(Path targetPath, long targetSizeBytes) throws IOException {
        System.out.printf("Generating %s (~%.2f MB)...%n", targetPath.getFileName(), targetSizeBytes / (1024.0 * 1024.0));
        Random rand = new Random(42); // Deterministic seed
        try (BufferedWriter writer = Files.newBufferedWriter(targetPath)) {
            long written = 0;
            int lineNum = 1;
            while (written < targetSizeBytes) {
                String logLine = String.format("2026-11-15 08:%02d:%02d.123 [INFO] BusGate-%d: Student %d checked-in at Stop %d%n",
                        rand.nextInt(60), rand.nextInt(60), rand.nextInt(10) + 1, 192311000 + lineNum, rand.nextInt(8) + 1);
                writer.write(logLine);
                written += logLine.getBytes().length;
                lineNum++;
            }
        }
        System.out.printf("Done: %s (Actual size: %d bytes)%n", targetPath.getFileName(), Files.size(targetPath));
    }

    public static void generatePassCsv(Path targetPath, int count) throws IOException {
        System.out.printf("Generating %s (%d records)...%n", targetPath.getFileName(), count);
        Random rand = new Random(count);
        try (BufferedWriter writer = Files.newBufferedWriter(targetPath)) {
            for (int i = 1; i <= count; i++) {
                String passId = String.format("P%06d", i);
                String regNo = String.format("19231%04d", i);
                String name = FIRST_NAMES[rand.nextInt(FIRST_NAMES.length)] + " " + LAST_NAMES[rand.nextInt(LAST_NAMES.length)];
                int route = rand.nextInt(25) + 1;
                double amount = 3000.0 + (rand.nextInt(30) * 100.0);
                writer.write(String.join(",", passId, regNo, name, String.valueOf(route), String.format("%.2f", amount)));
                writer.newLine();
            }
        }
        System.out.printf("Done: %s%n", targetPath.getFileName());
    }

    public static void generateDamagedCsv(Path targetPath) throws IOException {
        System.out.printf("Generating %s with deliberate structural anomalies...%n", targetPath.getFileName());
        try (BufferedWriter writer = Files.newBufferedWriter(targetPath)) {
            // Valid record 1
            writer.write("P1001,192311001,Arun Kumar,12,4500.00\n");
            // Damaged record 1: Incorrect field count (only 2 fields)
            writer.write("P1002,DAMAGED LINE\n");
            // Valid record 2
            writer.write("P1003,192311003,Divya Rajan,7,3800.00\n");
            // Damaged record 2: Empty line
            writer.write("\n");
            // Damaged record 3: Non-numeric amount ("FREE_PASS")
            writer.write("P1004,192311004,Rahul Sharma,5,FREE_PASS\n");
            // Damaged record 4: Non-numeric route ("ROUTE_A")
            writer.write("P1005,192311005,Sneha Nair,ROUTE_A,4200.00\n");
            // Valid record 3
            writer.write("P1006,192311006,Karthik Iyer,12,4500.00\n");
            // Damaged record 5: Extra trailing fields (6 fields instead of 5)
            writer.write("P1007,192311007,Pooja Reddy,8,3500.00,EXTRA_COLUMN_DATA\n");
        }
        System.out.printf("Done: %s%n", targetPath.getFileName());
    }
}
