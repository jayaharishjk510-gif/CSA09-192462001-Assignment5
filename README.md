# CSA09 - PROGRAMMING IN JAVA
# ASSIGNMENT 5 (CO5) - DATA PERSISTENCE ENGINEERING: FILES AND DATABASES

**Course Outcome:** CO5 – Java I/O (Byte, Character, Buffered Streams, File class, Serialization) and JDBC (Architecture, Drivers, Statement, PreparedStatement, Transactions, Batch Processing, Metadata).  
**Student Name:** [STUDENT NAME PLACEHOLDER]  
**Register Number:** [REGISTER NUMBER PLACEHOLDER]  
**GitHub Repository Link:** [GITHUB REPOSITORY URL PLACEHOLDER]  

---

## 1. System Requirements & Environment

* **Java Development Kit:** Java SE 17 or later (Verified on OpenJDK 21.0.12 LTS)
* **Database Engine:** MySQL Server 8.0.x / 8.3.x (InnoDB Engine)
* **JDBC Driver:** MySQL Connector/J 8.x (Standard JDBC 4.2 compliant driver)
* **Operating System:** Windows / Linux / macOS compatible
* **IDE / Build Tools:** Standard Command Line (`javac`, `java`), VS Code, IntelliJ IDEA, or Eclipse

---

## 2. Directory Structure

```text
CSA09-<RegisterNumber>-Assignment5/
│
├── README.md                          # Master project documentation
├── ASSIGNMENT5_REPORT.md              # Complete formal academic assignment report
│
├── PartA/                             # Persistence Design & SIMATS Algorithmic Pseudocode
│   ├── A1/                            # Record layouts, stream selection & operations
│   │   ├── README.md
│   │   └── RecordLayouts_and_Pseudocode.md
│   ├── A2/                            # Buffered I/O cost predictions & architectural analysis
│   │   ├── README.md
│   │   └── BufferingAnalysis.md
│   ├── A3/                            # Data Access Object (DAO) pattern design & models
│   │   ├── README.md
│   │   ├── Dao.java
│   │   ├── Student.java
│   │   ├── StudentDao.java
│   │   └── DataAccessPatternAnalysis.md
│   ├── A4/                            # Java Serialization, transient fields & versioning
│   │   ├── README.md
│   │   ├── HostelModel.java
│   │   └── SerializationDesign.md
│   └── A5/                            # ACID Transactions & SQL Injection defenses
│       ├── README.md
│       └── TransactionAndInjectionAnalysis.md
│
├── PartB/                             # Implementations & Empirical Investigations
│   ├── B1/                            # File Store, TODO 1, Line Counting & Search Growth
│   │   ├── README.md
│   │   ├── PassStore.java
│   │   ├── PassStoreValidationTest.java
│   │   ├── LineCounterBenchmark.java
│   │   └── PassSearchBenchmark.java
│   ├── B2/                            # Adversarial Inputs (SQLi Exploit vs PreparedStatement)
│   │   ├── README.md
│   │   ├── UnsafeLogin.java
│   │   ├── SecureLogin.java
│   │   ├── AdversarialLoginDemo.java
│   │   └── DamagedFileInputDemo.java
│   ├── B3/                            # Statement vs PreparedStatement vs Batch Processing
│   │   ├── README.md
│   │   ├── PassLoader.java
│   │   ├── SmallDataVerification.java
│   │   ├── TransactionRollbackDemo.java
│   │   └── StatementVsPreparedStatementBenchmark.java
│   └── B4/                            # Final Engineering Decision & Migration Pipeline
│       ├── README.md
│       ├── FileToDatabaseMigrator.java
│       ├── DatabaseInspector.java
│       └── ProductionArchitectureDecision.md
│
├── database/                          # Database schema DDL & Connection Factory
│   ├── schema.sql                     # Full MySQL DDL script with seed data
│   ├── db.properties                  # Configurable connection credentials
│   └── DBConnection.java              # Centralized JDBC connection manager
│
├── benchmarks/                        # Benchmark runners and empirical datasets
│   ├── TestDataGenerator.java         # Automated generator for test files
│   ├── RunAllBenchmarks.java          # Master benchmark execution suite
│   └── results/                       # Empirical CSV measurement tables
│       ├── line_counter_results.csv
│       ├── search_growth_results.csv
│       └── jdbc_insertion_results.csv
│
├── test-data/                         # Generated test files
│   ├── log_1mb.txt, log_10mb.txt, log_50mb.txt
│   ├── passes_1k.csv, passes_10k.csv, passes_50k.csv, passes_100k.csv
│   ├── sample_passes.csv
│   └── damaged_passes.csv
│
└── screenshots/                       # Evidence images & submission guide
    └── README.md                      # Detailed screenshot capture guide
```

---

## 3. Database Setup Instructions (MySQL 8.x)

1. **Start MySQL Server:**
   Ensure your local MySQL service is active.
2. **Execute Schema Creation Script:**
   Log into MySQL and execute `database/schema.sql`:
   ```bash
   mysql -u root -p < database/schema.sql
   ```
   This creates:
   - Database: `bus_pass_db`
   - Tables: `users`, `bus_pass`, `student`, `wallet`
   - Seeds initial sample data and constraints.
3. **Configure Connection Properties:**
   Edit `database/db.properties` with your database credentials:
   ```properties
   db.url=jdbc:mysql://localhost:3306/bus_pass_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&rewriteBatchedStatements=true
   db.username=root
   db.password=your_mysql_password
   ```

---

## 4. Compilation and Execution Instructions

### A. Generate Test Datasets
```bash
cd benchmarks
javac TestDataGenerator.java
java TestDataGenerator
cd ..
```

### B. Task B1: File Store & Buffering Experiments
```bash
cd PartB/B1

# 1. Compile all B1 files
javac PassStore.java PassStoreValidationTest.java LineCounterBenchmark.java PassSearchBenchmark.java

# 2. Run Complete the Code PassStore
java PassStore

# 3. Run validation suite (Normal, Missing, Damaged, Route Listing)
java PassStoreValidationTest

# 4. Run Line Counter Buffering Benchmark (1MB, 10MB, 50MB)
java LineCounterBenchmark

# 5. Run Pass Search Growth Benchmark (1,000 to 100,000 records)
java PassSearchBenchmark

cd ../..
```

### C. Task B2: Adversarial Input Analysis
```bash
cd PartB/B2

# Compile B2 programs
javac -cp "../B1" UnsafeLogin.java SecureLogin.java AdversarialLoginDemo.java DamagedFileInputDemo.java

# Run SQL injection demonstration
java AdversarialLoginDemo

# Run damaged file store evaluation
java -cp ".;../B1" DamagedFileInputDemo

cd ../..
```

### D. Task B3: JDBC Statement vs PreparedStatement vs Batch
```bash
cd PartB/B3

# Compile B3 programs
javac PassLoader.java SmallDataVerification.java TransactionRollbackDemo.java StatementVsPreparedStatementBenchmark.java

# Run small data verification (10 records via Statement, PreparedStatement, Batch)
java SmallDataVerification

# Run transaction rollback demonstration (Atomicity mid-batch failure)
java TransactionRollbackDemo

# Run JDBC insertion benchmark across 1k, 10k, and 100k records
java StatementVsPreparedStatementBenchmark

cd ../..
```

### E. Task B4: Final Engineering Decision & Migration
```bash
cd PartB/B4

# Compile B4 programs
javac FileToDatabaseMigrator.java DatabaseInspector.java

# Run production migration pipeline with rejected records logging
java FileToDatabaseMigrator

# Run DatabaseMetaData inspection
java DatabaseInspector

cd ../..
```

---

## 5. Summary of Measured Empirical Benchmarks

### Benchmark 1: Buffering Performance (Task A2 / B1.3)
| File Size | Unbuffered Time | Buffered Time | Speedup Factor | System Call Reduction |
| :--- | :--- | :--- | :--- | :--- |
| **1 MB** | 1,535.39 ms | 4.01 ms | **383.2x** | 99.988% |
| **10 MB** | 14,656.23 ms | 7.61 ms | **1,926.7x** | 99.988% |
| **50 MB** | 69,158.36 ms (69.16 s) | 68.16 ms (0.068 s) | **1,014.7x** | 99.988% |

### Benchmark 2: Sequential File Search Growth (Task B1.4)
| Records in File | Best Case ($O(1)$) | Average Case ($O(n/2)$) | Worst Case ($O(n)$) | Miss Scan ($O(n)$) |
| :--- | :--- | :--- | :--- | :--- |
| **1,000** | 0.593 ms | 2.816 ms | 3.519 ms | 2.103 ms |
| **10,000** | 0.863 ms | 10.012 ms | 24.946 ms | 8.923 ms |
| **50,000** | 0.509 ms | 15.498 ms | 14.284 ms | 13.631 ms |
| **100,000** | 0.476 ms | 9.812 ms | 19.982 ms | 20.813 ms |

### Benchmark 3: JDBC Ingestion Performance (Task B3.2)
| Records | Statement (Individual) | PreparedStatement (Individual) | Batch (1,000 rows/batch) | Batch vs Statement Speedup |
| :--- | :--- | :--- | :--- | :--- |
| **1,000** | 452.80 ms | 284.45 ms | 11.98 ms | **37.8x** |
| **10,000** | 4,522.55 ms | 2,810.03 ms | 107.69 ms | **42.0x** |
| **100,000** | 45,086.46 ms (45.1 s) | 28,023.08 ms (28.0 s) | 1,068.09 ms (1.07 s) | **42.2x** |

---

## 6. Verification Checklist

- [x] **A1.1** Record layouts for delimited text (RFC 4180) and binary (`DataOutputStream`).
- [x] **A1.2** Pseudocode for Add, Find, and List by Route following SIMATS Engineering Standard.
- [x] **A1.3** Damaged data, missing file, and incomplete write handling designed.
- [x] **A1 Alternative** `RandomAccessFile` fixed-length record seek vs scan analysis completed.
- [x] **A2.1** Exact prediction of 52.4M read calls and OS context switch explanation.
- [x] **A2.2** Buffering redesign reducing OS system calls by 99.988%.
- [x] **A2.3** Four scenarios where buffering provides little/no benefit identified.
- [x] **A2 Alternative** `Files.readAllLines()` $O(N)$ heap space vs $O(1)$ stream buffer analysis.
- [x] **A3.1** Common boilerplate vs differing domain logic identified.
- [x] **A3.2** Generic `Dao<T, K>` interface and `StudentDao` implemented.
- [x] **A3.3** Relational mapping table for `Student` entity to `student` schema created.
- [x] **A3 Alternative** DAO vs SQL in UI maintainability and complexity comparison.
- [x] **A4.1** `transient` fields (`Connection`, `password`) justified; object graph traversal explained.
- [x] **A4.2** `serialVersionUID` schema evolution and backward compatibility explained.
- [x] **A4 Alternative** Java Serialization vs CSV vs JSON comparative analysis.
- [x] **A5.1** ACID transaction pseudocode using JDBC `commit` and `rollback` for wallet transfer.
- [x] **A5.2** SQL injection AST analysis and `PreparedStatement` secure redesign.
- [x] **A5 Alternative** Auto-commit vs Transaction failure analysis and fsync cost comparison.
- [x] **B1.1** `PassStore.java` implemented with `TODO 1` completed; all 4 tests validated.
- [x] **B1.2** Implementation compared against Part A design.
- [x] **B1.3** Buffering benchmark executed on 1MB, 10MB, and 50MB files with real measurements.
- [x] **B1.4** Search growth benchmark executed across 1k, 10k, 50k, 100k records.
- [x] **B2.1** Unsafe vs PreparedStatement login executed; attack payload verified.
- [x] **B2.2** Damaged bus-pass files evaluated and defensive design verified.
- [x] **B2 Alternative** `PreparedStatement` vs escaping/regex comparison documented.
- [x] **B3.1** Small data (10 records) and `ResultSetMetaData` verification completed.
- [x] **B3.2** Ingestion benchmark across 1,000, 10,000, 100,000 records completed.
- [x] **B3.3** Mid-batch failure tested: Auto-commit leaves 500 dirty rows; transaction cleanly rolls back to 0.
- [x] **B3.4** Engineering trade-off for bulk import vs counter issuance documented.
- [x] **B4.1** Migration pipeline implemented with validation and `rejected_records.csv` reporting.
- [x] **B4.2** `DatabaseMetaData` reporting database product, driver, and table catalog.
- [x] **B4.3** Final production architecture decision evaluated for 12-campus deployment.
- [x] **B4 Alternative** Distributed serialized file per campus vs centralized shared database evaluated.
