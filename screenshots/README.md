# Evidence and Output Screenshots Guide

This directory holds the execution output screenshots required for the mandatory assignment submission.

## Screenshot Capture Checklist

| Screenshot ID | Task Reference | Command to Execute | Expected Output / What to Capture |
| :--- | :--- | :--- | :--- |
| **SS-B1.1** | Task B1 Complete Code | `cd PartB/B1; java PassStore` | Console output showing `[P1003, 192311003, Divya, 7, 3800.0]` and `null`. |
| **SS-B1.2** | Task B1 Validation | `cd PartB/B1; java PassStoreValidationTest` | Console output showing all 4 tests PASSED (Normal, Not Found, Damaged, Route listing). |
| **SS-B1.3** | Task B1 Buffering Benchmark | `cd PartB/B1; java LineCounterBenchmark` | Table comparing unbuffered vs buffered execution time on 1MB, 10MB, and 50MB files. |
| **SS-B1.4** | Task B1 Search Growth | `cd PartB/B1; java PassSearchBenchmark` | Table showing search times across 1,000 to 100,000 records (Best, Avg, Worst, Miss). |
| **SS-B2.1** | Task B2 SQL Injection | `cd PartB/B2; java AdversarialLoginDemo` | Demonstration showing UnsafeLogin granting access to `admin' -- ` while SecureLogin blocks it. |
| **SS-B2.2** | Task B2 Damaged Files | `cd PartB/B2; java -cp ".;../B1" DamagedFileInputDemo` | PassStore skipping truncated lines, non-numeric amount, and blank lines without crashing. |
| **SS-B3.1** | Task B3 Small Data & Meta | `cd PartB/B3; java SmallDataVerification` | Column types and nullability verified via `ResultSetMetaData`. |
| **SS-B3.2** | Task B3 Transaction Rollback | `cd PartB/B3; java TransactionRollbackDemo` | Case A (Auto-commit = 500 dirty rows) vs Case B (Transaction rollback = 0 rows). |
| **SS-B3.3** | Task B3 JDBC Benchmark | `cd PartB/B3; java StatementVsPreparedStatementBenchmark` | Statement vs PreparedStatement vs Batch performance table across 1k, 10k, and 100k rows. |
| **SS-B4.1** | Task B4 Migration Report | `cd PartB/B4; java FileToDatabaseMigrator` | Migration summary showing valid records migrated and 5 rejected lines logged to CSV. |
| **SS-B4.2** | Task B4 Database Metadata | `cd PartB/B4; java DatabaseInspector` | Output showing Database Product (MySQL), Driver Version, and tables in `bus_pass_db`. |

## Recommended File Naming Convention
Save each captured screenshot in this folder using the following names:
* `screenshots/SS-B1.1-PassStore-Output.png`
* `screenshots/SS-B1.2-PassStoreValidation.png`
* `screenshots/SS-B1.3-LineCounter-Benchmark.png`
* `screenshots/SS-B1.4-Search-Growth.png`
* `screenshots/SS-B2.1-Adversarial-SQL-Injection.png`
* `screenshots/SS-B2.2-Damaged-File-Resilience.png`
* `screenshots/SS-B3.1-SmallData-Metadata.png`
* `screenshots/SS-B3.2-Transaction-Rollback.png`
* `screenshots/SS-B3.3-JDBC-Benchmark.png`
* `screenshots/SS-B4.1-Data-Migration-Report.png`
* `screenshots/SS-B4.2-Database-Metadata.png`
