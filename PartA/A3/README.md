# Task A3: Discover the Data-Access Design Pattern (5 Marks)

## Files in this Folder
- `Dao.java`: Generic `Dao<T, K>` CRUD interface.
- `Student.java`: Domain entity class representing student attributes.
- `StudentDao.java`: Concrete implementation of `Dao<Student, String>` using `PreparedStatement`.
- `DataAccessPatternAnalysis.md`: Analysis of repeated vs differing code in DB access, object-relational mapping table, and maintainability comparison against SQL in GUI/Service layers.

## Compilation and Execution
```bash
javac Dao.java Student.java StudentDao.java
```
All files compile cleanly with standard JDK 21 `java.sql` module.
