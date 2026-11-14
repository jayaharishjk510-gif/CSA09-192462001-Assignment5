# Task B2: Engineer an Adversarial Input (5 Marks)

## Files in this Folder
- `UnsafeLogin.java`: Vulnerable authentication implementation using raw SQL string concatenation.
- `SecureLogin.java`: Secure authentication implementation using `PreparedStatement` parameter placeholders.
- `AdversarialLoginDemo.java`: Test runner demonstrating SQL injection exploit (`admin' -- ` and `' OR '1'='1`) bypassing authentication on the unsafe version while being rejected by `PreparedStatement`.
- `DamagedFileInputDemo.java`: Adversarial test suite evaluating how `PassStore` responds to damaged files (wrong field counts, non-numeric amounts, blank lines).

## Compilation and Execution
```bash
# Compile B2 source files
javac -cp "../B1" UnsafeLogin.java SecureLogin.java AdversarialLoginDemo.java DamagedFileInputDemo.java

# 1. Run Adversarial SQL Injection Demonstration
java AdversarialLoginDemo

# 2. Run Damaged File Store Evaluation
java -cp ".;../B1" DamagedFileInputDemo
```

## Summary of Findings
- **SQL Injection:** Payload `admin' -- ` comments out the password predicate in `UnsafeLogin`, granting admin access without password. `SecureLogin` binds the payload as a literal string value, safely denying access.
- **File Input Resilience:** `PassStore` safely skips malformed records (fields != 5), blank lines, and corrupted types without crashing or breaking searches for subsequent valid records.
- **Alternative Concept Analysis:** Input sanitization (regex / quote escaping) is fragile against character encoding tricks and incurs $O(L)$ CPU string parsing costs, whereas `PreparedStatement` solves the problem at the protocol layer.
