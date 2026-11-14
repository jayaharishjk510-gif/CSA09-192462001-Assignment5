import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Task B1.3 / A2: Benchmark comparing unbuffered vs buffered I/O performance.
 * Measures time to count lines across 1 MB, 10 MB, and 50 MB files.
 * Tracks elapsed time using System.nanoTime() and outputs results to console and CSV.
 */
public class LineCounterBenchmark {

    public static void main(String[] args) throws IOException {
        Path testDataDir = Paths.get("..", "..", "test-data");
        Path resultsFile = Paths.get("..", "..", "benchmarks", "results", "line_counter_results.csv");
        Files.createDirectories(resultsFile.getParent());

        Path[] testFiles = {
            testDataDir.resolve("log_1mb.txt"),
            testDataDir.resolve("log_10mb.txt"),
            testDataDir.resolve("log_50mb.txt")
        };

        System.out.println("=========================================================================================");
        System.out.println(" TASK B1.3 - BUFFERED VS UNBUFFERED LINE COUNT BENCHMARK");
        System.out.println("=========================================================================================");
        System.out.printf("%-12s %-14s %-20s %-20s %-12s%n", "File", "Size (Bytes)", "Unbuffered Time (ms)", "Buffered Time (ms)", "Speedup");
        System.out.println("-----------------------------------------------------------------------------------------");

        try (PrintWriter csvOut = new PrintWriter(new FileWriter(resultsFile.toFile()))) {
            csvOut.println("File,SizeBytes,UnbufferedLines,UnbufferedTimeMs,BufferedLines,BufferedTimeMs,SpeedupFactor");

            for (Path filePath : testFiles) {
                File file = filePath.toFile();
                if (!file.exists()) {
                    System.err.println("File not found: " + file.getAbsolutePath());
                    continue;
                }
                long sizeBytes = file.length();
                String fileName = file.getName();

                // 1. Measure Unbuffered Reading (1 byte at a time)
                long startUnbuf = System.nanoTime();
                long unbufLines = countLinesUnbuffered(file);
                long elapsedUnbufNs = System.nanoTime() - startUnbuf;
                double unbufMs = elapsedUnbufNs / 1_000_000.0;

                // 2. Measure Buffered Reading (8KB buffer stream)
                long startBuf = System.nanoTime();
                long bufLines = countLinesBuffered(file);
                long elapsedBufNs = System.nanoTime() - startBuf;
                double bufMs = elapsedBufNs / 1_000_000.0;

                double speedup = unbufMs / (bufMs > 0 ? bufMs : 0.001);

                System.out.printf("%-12s %-14d %-20.2f %-20.2f %-10.1fx%n",
                        fileName, sizeBytes, unbufMs, bufMs, speedup);

                csvOut.printf("%s,%d,%d,%.2f,%d,%.2f,%.2f%n",
                        fileName, sizeBytes, unbufLines, unbufMs, bufLines, bufMs, speedup);
            }
        }

        System.out.println("=========================================================================================");
        System.out.println(" Benchmark completed. Results saved to: " + resultsFile.toAbsolutePath());
    }

    /**
     * Unbuffered read: invokes FileInputStream.read() one byte at a time.
     * Generates a separate system call / JVM native call transition for every single byte.
     */
    public static long countLinesUnbuffered(File file) throws IOException {
        long lines = 0;
        try (FileInputStream fis = new FileInputStream(file)) {
            int b;
            while ((b = fis.read()) != -1) {
                if (b == '\n') {
                    lines++;
                }
            }
        }
        return lines;
    }

    /**
     * Buffered read: uses BufferedInputStream with an 8 KB buffer.
     * Reads entire 8,192 byte blocks into user-space RAM, minimizing OS context switches.
     */
    public static long countLinesBuffered(File file) throws IOException {
        long lines = 0;
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file), 8192)) {
            byte[] buf = new byte[8192];
            int bytesRead;
            while ((bytesRead = bis.read(buf)) != -1) {
                for (int i = 0; i < bytesRead; i++) {
                    if (buf[i] == '\n') {
                        lines++;
                    }
                }
            }
        }
        return lines;
    }
}
