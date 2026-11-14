# Task A1: Design a File Format (7 Marks)

## Files in this Folder
- `RecordLayouts_and_Pseudocode.md`: Complete record layouts (delimited text and binary `DataOutputStream`), stream selection rationale, SIMATS Engineering standard pseudocodes for Add, Find, and List by Route, damaged data handling, and RandomAccessFile alternative complexity analysis.

## Overview of Solutions
1. **A1.1 Represent the Record:**
   - Delimited text layout with RFC 4180 quoting for names containing commas.
   - Binary layout with `DataOutputStream` (`writeUTF`, `writeInt`, `writeLong`, `writeDouble`).
2. **A1.2 Design the Operations:**
   - Pseudocode for Add (append mode), Find by Pass ID, and List by Route using `BufferedReader` and `BufferedWriter`.
3. **A1.3 Design for Damaged Data:**
   - Robust detection and recovery for damaged lines, missing files, and power interruptions (atomic rename).
4. **Alternative Concept Analysis:**
   - Fixed-length `RandomAccessFile`: $O(1)$ direct seek vs $O(n)$ text scan, trade-off with wasted padding memory.
