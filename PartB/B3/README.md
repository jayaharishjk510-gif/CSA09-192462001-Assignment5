# Task B3: Statement vs PreparedStatement vs Batch (8 Marks)

## Files in this Folder
- `PassLoader.java`: Completed implementation containing TODO 1 (parameter setting and batching every 1,000 rows), TODO 2 (commit and return count), and TODO 3 (rollback and rethrow).
- `SmallDataVerification.java`: Verifies insertion of 10 records and inspects table structure via `ResultSetMetaData`.
- `TransactionRollbackDemo.java`: Empirical demonstration of mid-batch failure: Auto-commit leaves 500 orphaned rows (data corruption), while Single Transaction with rollback cleans up completely to 0 rows.
- `StatementVsPreparedStatementBenchmark.java`: Measures performance across 1,000, 10,000, and 100,000 records.

## Compilation and Execution
```bash
# Compile B3 source files
javac PassLoader.java SmallDataVerification.java TransactionRollbackDemo.java StatementVsPreparedStatementBenchmark.java

# 1. Run Small Data & Metadata Verification
java SmallDataVerification

# 2. Run Transaction Rollback Demonstration (Atomicity Verification)
java TransactionRollbackDemo

# 3. Run JDBC Insertion Benchmark
java StatementVsPreparedStatementBenchmark
```

## Summary of Empirical Insertion Results
* **1,000 Records:**
  - Statement: 452.80 ms
  - PreparedStatement: 284.45 ms
  - Batch (1,000): 11.98 ms (**37.8x speedup**)
* **10,000 Records:**
  - Statement: 4,522.55 ms
  - PreparedStatement: 2,810.03 ms
  - Batch (1,000): 107.69 ms (**42.0x speedup**)
* **100,000 Records:**
  - Statement: 45,086.46 ms (~45 sec)
  - PreparedStatement: 28,023.08 ms (~28 sec)
  - Batch (1,000): 1,068.09 ms (~1.07 sec) (**42.2x speedup**)

## Engineering Recommendation (B3.4)
- **Nightly Import (100,000 passes):** Use PreparedStatement Batch (batches of 1,000) inside a single transaction. Reduces network roundtrips by 1,000x and avoids per-row log flushes.
- **Single Pass at Counter:** Use single PreparedStatement with Auto-Commit for immediate sub-millisecond persistence and injection safety.
