# Task B4: Final Engineering Decision (5 Marks)

## Files in this Folder
- `FileToDatabaseMigrator.java`: Production-grade migration program that validates text records, executes batch loading inside transactions, and logs rejected lines to `rejected_records.csv` with specific failure causes.
- `DatabaseInspector.java`: System and driver introspection utility using `DatabaseMetaData`.
- `ProductionArchitectureDecision.md`: Detailed engineering evaluation of CSV vs Serialization vs RDBMS for the 12-campus scale-up scenario, concurrent counter analysis, and distributed serialized file trade-offs.

## Compilation and Execution
```bash
# Compile B4 source files
javac FileToDatabaseMigrator.java DatabaseInspector.java

# 1. Run Migration Pipeline with Rejection Logging
java FileToDatabaseMigrator

# 2. Run Database Environment Inspector
java DatabaseInspector
```

## Summary of Findings
- **Migration Pipeline:** Successfully scanned damaged input file, correctly migrated valid records, and isolated all corrupted records into `rejected_records.csv` with clear error reasons (field count mismatch, empty lines, non-numeric values).
- **Metadata Introspection:** Reports database product, version, and driver information, ensuring schema integrity across development, staging, and production environments.
- **Production Decision:** Unanimously recommends MySQL 8.x with InnoDB and connection pooling. Text files and serialization are single-threaded local formats that fail under concurrent multi-counter writes, high data volume, and cross-campus searching.
