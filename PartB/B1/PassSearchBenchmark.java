import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Task B1.4: Measure search time growth as file grows from 1,000 to 100,000 records.
 * Tests:
 * - Best-case search (first record)
 * - Average-case search (middle record)
 * - Worst-case search (last record)
 * - Non-existent record (complete scan)
 * Saves empirical measurements to benchmarks/results/search_growth_results.csv.
 */
public class PassSearchBenchmark {

    public static void main(String[] args) throws IOException {
        Path testDataDir = Paths.get("..", "..", "test-data");
        Path resultsFile = Paths.get("..", "..", "benchmarks", "results", "search_growth_results.csv");
        Files.createDirectories(resultsFile.getParent());

        int[] recordCounts = {1000, 10000, 50000, 100000};
        String[] fileNames = {
            "passes_1k.csv",
            "passes_10k.csv",
            "passes_50k.csv",
            "passes_100k.csv"
        };

        System.out.println("=========================================================================================================");
        System.out.println(" TASK B1.4 - SEQUENTIAL PASS SEARCH TIME GROWTH BENCHMARK");
        System.out.println("=========================================================================================================");
        System.out.printf("%-15s %-12s %-16s %-16s %-16s %-16s%n",
                "Dataset", "Records", "Best Case (ms)", "Avg Case (ms)", "Worst Case (ms)", "Miss Scan (ms)");
        System.out.println("---------------------------------------------------------------------------------------------------------");

        try (PrintWriter csvOut = new PrintWriter(new FileWriter(resultsFile.toFile()))) {
            csvOut.println("Dataset,RecordCount,FileSizeBytes,BestCaseMs,AvgCaseMs,WorstCaseMs,MissScanMs");

            for (int i = 0; i < recordCounts.length; i++) {
                int count = recordCounts[i];
                Path filePath = testDataDir.resolve(fileNames[i]);
                File file = filePath.toFile();

                if (!file.exists()) {
                    System.err.println("Dataset missing: " + file.getAbsolutePath());
                    continue;
                }

                PassStore store = new PassStore(filePath);
                long fileSizeBytes = file.length();

                String firstId = String.format("P%06d", 1);
                String middleId = String.format("P%06d", count / 2);
                String lastId = String.format("P%06d", count);
                String nonExistentId = "P999999";

                // Warm-up JIT
                store.find(firstId);

                // 1. Best Case (First Record)
                long t0 = System.nanoTime();
                String[] rFirst = store.find(firstId);
                double bestMs = (System.nanoTime() - t0) / 1_000_000.0;

                // 2. Average Case (Middle Record)
                long t1 = System.nanoTime();
                String[] rMid = store.find(middleId);
                double avgMs = (System.nanoTime() - t1) / 1_000_000.0;

                // 3. Worst Case (Last Record)
                long t2 = System.nanoTime();
                String[] rLast = store.find(lastId);
                double worstMs = (System.nanoTime() - t2) / 1_000_000.0;

                // 4. Miss Case (Non-existent Record - full scan)
                long t3 = System.nanoTime();
                String[] rMiss = store.find(nonExistentId);
                double missMs = (System.nanoTime() - t3) / 1_000_000.0;

                System.out.printf("%-15s %-12d %-16.3f %-16.3f %-16.3f %-16.3f%n",
                        fileNames[i], count, bestMs, avgMs, worstMs, missMs);

                csvOut.printf("%s,%d,%d,%.4f,%.4f,%.4f,%.4f%n",
                        fileNames[i], count, fileSizeBytes, bestMs, avgMs, worstMs, missMs);
            }
        }

        System.out.println("=========================================================================================================");
        System.out.println(" Benchmark completed. Results saved to: " + resultsFile.toAbsolutePath());
    }
}
