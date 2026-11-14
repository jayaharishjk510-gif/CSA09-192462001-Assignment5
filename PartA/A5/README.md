# Task A5: Design for Correctness and Safety (4 Marks)

## Files in this Folder
- `TransactionAndInjectionAnalysis.md`: SIMATS Engineering Standard pseudocode for atomic wallet transfer using JDBC `commit` and `rollback`, SQL injection Abstract Syntax Tree (AST) exploitation analysis, `PreparedStatement` parameterized redesign, and comparison with two independent auto-commit statements.

## Key Insights
1. **A5.1 Transaction Design:**
   - Invariant: `HostelBalance + CanteenBalance = Constant`.
   - `setAutoCommit(false)`, debit, credit, `commit()`, and `rollback()` inside catch block ensure full ACID atomicity.
2. **A5.2 Injection Analysis:**
   - Raw string concatenation allows `' -- ` to truncate the SQL grammar, bypassing authentication.
   - `PreparedStatement` pre-compiles the SQL AST on the database server, transmitting inputs as literal values via binary protocol.
3. **Alternative Concept Analysis:**
   - Two auto-commits incur 2 separate WAL disk `fsync()` operations and risk leaving half-transferred money stranded on mid-operation crashes.
