# Task B1: Implement and Instrument the File Store (7 Marks)

## Files in this Folder
- `PassStore.java`: Complete implementation of the bus-pass file store, including TODO 1 implementation, `add`, `find`, and `list` operations.
- `PassStoreValidationTest.java`: Comprehensive validation test suite covering normal cases, non-existent pass IDs, damaged records, and route filtering.
- `LineCounterBenchmark.java`: High-precision micro-benchmark measuring unbuffered vs buffered line counting across 1 MB, 10 MB, and 50 MB files.
- `PassSearchBenchmark.java`: Search performance measurement as dataset scales from 1,000 to 100,000 records.

## Compilation and Execution Commands
```bash
# Compile all B1 source files
javac PassStore.java PassStoreValidationTest.java LineCounterBenchmark.java PassSearchBenchmark.java

# 1. Run Complete the Code PassStore main()
java PassStore
# Expected Output:
# [P1003, 192311003, Divya, 7, 3800.0]
# null

# 2. Run Comprehensive Validation Tests
java PassStoreValidationTest

# 3. Run Line Counter Buffering Benchmark (1MB, 10MB, 50MB)
java LineCounterBenchmark

# 4. Run Sequential Pass Search Growth Benchmark (1k to 100k records)
java PassSearchBenchmark
```

## Summary of Empirical Results
* **Buffering Speedup:**
  - 1 MB File: 1,535.39 ms (Unbuffered) vs 4.01 ms (Buffered) -> **383.2x Speedup**
  - 10 MB File: 14,656.23 ms (Unbuffered) vs 7.61 ms (Buffered) -> **1,926.7x Speedup**
  - 50 MB File: 69,158.36 ms (Unbuffered) vs 68.16 ms (Buffered) -> **1,014.7x Speedup**
* **Search Growth:**
  - Best-case search remains constant $O(1)$ (< 1 ms).
  - Worst-case and Miss-case scale linearly $O(n)$ with file size.
