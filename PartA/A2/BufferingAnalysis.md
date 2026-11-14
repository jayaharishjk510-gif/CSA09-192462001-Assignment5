# TASK A2: DON’T READ WHAT YOU DON’T NEED (5 MARKS)

## Context
A program reads a 50 MB attendance log file one byte at a time using `FileInputStream.read()` to count the total number of lines (`\n` characters).

---

## A2.1 – Predict the Cost (2 Marks)

### Quantitative Prediction
* **File Size in Binary Units:**
  $$50\text{ MB} = 50 \times 1024 \times 1024\text{ bytes} = 52,428,800\text{ bytes}$$
* **Number of `read()` Invocations:**
  `FileInputStream.read()` returns an `int` containing the next single byte of data (0–255) or -1 on EOF.
  Therefore, to process the entire 50 MB file, the program executes **exactly 52,428,800 method calls** plus one final call returning -1.
  $$\text{Total Calls} = 52,428,801\text{ read calls}$$

### Architectural Explanation of Extreme Slowness
Reading byte-by-byte unbuffered is drastically slow due to three architectural bottlenecks:
1. **User-Space to Kernel-Space Context Switching:**
   Each invocation of unbuffered `FileInputStream.read()` triggers a Java Native Interface (JNI) call to the underlying C library, which issues an operating system kernel system call (such as `read()` in POSIX or `ReadFile()` in Win32). The CPU must save registers, transition processor privilege levels from Ring 3 (user mode) to Ring 0 (kernel mode), execute trap handlers, and restore state 52.4 million times.
2. **Sub-optimal Storage Block Utilization:**
   Modern storage devices (Solid State Drives and Magnetic Hard Drives) and OS file systems (NTFS, ext4) are organized into 4 KB (4,096 byte) sectors or clusters. Fetching 1 byte forces the operating system cache to repeatedly service microscopic requests rather than transferring full block allocations.
3. **Instruction Pipeline & Call Overhead:**
   The JVM execution engine cannot effectively inline or optimize native JNI transitions. The CPU spends over 95% of its execution cycles in operating system context switches and scheduler bookkeeping rather than useful line counting.

---

## A2.2 – Redesign Using Buffering (2 Marks)

### Redesign with Buffered Stream / Byte Array Buffer
Instead of reading 1 byte per system call, we wrap the stream in a `BufferedInputStream` (or allocate an 8,192-byte `byte[]` array buffer).
An 8 KB (8,192 byte) buffer fetches chunks of data matching the operating system page size.

```java
// Redesigned Line Counter using Buffered Block Transfers
public static long countLinesBuffered(Path path) throws IOException {
    long lineCount = 0;
    try (InputStream in = new BufferedInputStream(Files.newInputStream(path), 8192)) {
        byte[] buffer = new byte[8192];
        int bytesRead;
        while ((bytesRead = in.read(buffer)) != -1) {
            for (int i = 0; i < bytesRead; i++) {
                if (buffer[i] == '\n') {
                    lineCount++;
                }
            }
        }
    }
    return lineCount;
}
```

### Mathematical Reduction in Operating System System Calls
* **Number of OS System Calls with 8 KB Buffer:**
  $$\text{OS Calls} = \left\lceil \frac{52,428,800\text{ bytes}}{8,192\text{ bytes/call}} \right\rceil = 6,400\text{ system calls}$$
* **System Call Reduction Factor:**
  $$\text{Reduction} = \frac{52,428,800 - 6,400}{52,428,800} \times 100\% = 99.9878\%$$
* **System calls are reduced by 99.988%!**
  The CPU executes 6,400 bulk disk-page transfers, and all subsequent single-byte iterations happen inside ultra-fast L1/L2 CPU cache and user-space memory without crossing the kernel boundary.

---

## A2.3 – When Buffering Does Not Help (1 Mark)

Adding a buffer provides **little or no benefit** in the following four scenarios:
1. **Bulk Block Transfers:** When the application already reads or writes in large memory chunks that equal or exceed the operating system page/block size (e.g., using `channel.read(ByteBuffer.allocateDirect(1024 * 1024))` or streaming 64 KB blocks). Adding another intermediate layer of buffering merely introduces redundant memory-to-memory `memcpy()` copies.
2. **Tiny Files (< 4 KB):** Files whose total size is smaller than a single disk sector (e.g., small configuration files of a few hundred bytes). The OS reads the entire file in one disk operation regardless.
3. **Non-Sequential Random Access:** Workloads characterized by scattered, random seeks (e.g., B-tree index traversal in `RandomAccessFile`). The buffer prefetches sequential bytes that will never be read, causing cache pollution and wasted I/O.
4. **Memory-Mapped I/O (`FileChannel.map`):** Direct virtual memory address mapping bypasses application buffers entirely, delegating paging directly to the CPU's Memory Management Unit (MMU).

---

## Alternative Concept and Complexity Analysis: Files.readAllLines() vs Buffered Stream

### Solution with `Files.readAllLines()`
```java
List<String> allLines = Files.readAllLines(Paths.get("attendance.log"), StandardCharsets.UTF_8);
long count = allLines.size();
```

### Big-O Time and Memory Complexity Analysis
| Dimension | `Files.readAllLines()` | Buffered Stream (`BufferedReader` / `BufferedInputStream`) |
| :--- | :--- | :--- |
| **Time Complexity** | $O(N)$ where $N$ is file size | $O(N)$ where $N$ is file size |
| **Space / Memory Complexity** | **$O(N)$ Heap Space (Dangerous for large files)** | **$O(1)$ Auxiliary Heap Space (Constant 8 KB buffer)** |
| **Garbage Collector Impact** | Severe: Instantiates hundreds of thousands of `String` and `ArrayList` nodes | Negligible: Reuses a single static 8 KB byte/char array |
| **50 MB File Memory Footprint** | Consumes **~150 MB to 200 MB** of JVM heap (due to UTF-16 String overhead, object headers, and array resizing) | Consumes **exactly 8 KB** of RAM regardless of whether the file is 50 MB, 50 GB, or 500 GB |
| **Failure Mode** | Risk of `java.lang.OutOfMemoryError: Java heap space` | Stream-processing safely processes arbitrarily large terabyte files |

### Appropriate Usage Contexts
* **`Files.readAllLines()` is appropriate** ONLY for small configuration files, metadata manifests, or small dictionary files (< 5 MB) where the entire dataset comfortably fits in memory and random indexing by line number is required.
* **Buffered Streams (`BufferedReader` / `InputStream`) are appropriate** for logs, large datasets, CSV imports, network sockets, and production data pipelines where memory footprint must remain strictly bounded $O(1)$.
