# Task A2: Don't Read What You Don't Need (5 Marks)

## Files in this Folder
- `BufferingAnalysis.md`: Quantitative prediction of 52.4 million read calls for a 50 MB file, architectural analysis of JNI context switches, buffering redesign reducing calls by 99.988%, identification of non-beneficial buffering scenarios, and `Files.readAllLines()` alternative analysis.

## Summary of Findings
1. **A2.1 Predict the Cost:**
   - Exact prediction: 52,428,801 calls to `FileInputStream.read()`.
   - Primary bottleneck: 52.4 million transitions between user-space (Ring 3) and kernel-space (Ring 0).
2. **A2.2 Redesign Using Buffering:**
   - 8 KB buffer reduces OS system calls from 52,428,800 to 6,400 (a 99.988% reduction).
3. **A2.3 When Buffering Does Not Help:**
   - Direct large-block transfers, tiny files (< 4 KB), random non-contiguous seeks, and memory-mapped files (`FileChannel.map`).
4. **Alternative Concept Analysis:**
   - `Files.readAllLines()` loads entire file into heap ($O(N)$ space, ~150-200 MB RAM for 50 MB file) vs buffered streams which require strictly $O(1)$ auxiliary memory (8 KB).
