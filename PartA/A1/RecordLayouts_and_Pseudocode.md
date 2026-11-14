# TASK A1: DESIGN A FILE FORMAT (7 MARKS)

## Context
The university transport office issues approximately 8,000 bus passes each year. Each record consists of:
1. Pass ID (Unique string identifier, e.g., `P10001`)
2. Student Register Number (Institutional ID, e.g., `192311001`)
3. Student Name (Variable length string, e.g., `Arun Kumar` or `Rajan, Divya`)
4. Route Number (Integer identifier, e.g., `12`)
5. Boarding Stop (String location name, e.g., `Central Station`)
6. Validity Date (ISO-8601 date format `YYYY-MM-DD`, e.g., `2027-05-31`)
7. Amount Paid (Double precision floating point currency, e.g., `4500.00`)

---

## A1.1 – Represent the Record (2 Marks)

### (a) Delimited Text File Layout
* **Field Order:**
  `PassID,RegisterNumber,StudentName,RouteNumber,BoardingStop,ValidityDate,AmountPaid`
* **Delimiter:** Comma (`,`) as field delimiter, Unix newline (`\n`) as record terminator.
* **Handling Delimiter within Data (RFC 4180 Standard):**
  If a student name or boarding stop contains a comma (e.g., `"Rajan, Divya"` or `"Stop 4, Tambaram"`), the field is enclosed in double quotation marks (`"`). If the field itself contains literal double quotes, they are escaped by doubling them (`""`).
* **Sample Delimited Text Records:**
  ```text
  P10001,192311001,"Kumar, Arun",12,Central Station,2027-05-31,4500.00
  P10002,192311002,Divya Rajan,7,"Guindy, Ring Road",2027-05-31,3800.00
  P10003,192311003,Rahul Sharma,12,Tambaram,2027-05-31,4500.00
  ```

### (b) Binary File Layout (`DataOutputStream`)
* **Encoding Strategy:** Native binary encoding via Java `java.io.DataOutputStream` / `DataInputStream`.
* **Field Specifications & Byte Sizes:**
  1. `passId`: Modified UTF-8 (`writeUTF()`) -> 2-byte unsigned short length prefix + $L_1$ bytes string data.
  2. `regNo`: Modified UTF-8 (`writeUTF()`) -> 2-byte unsigned short length prefix + $L_2$ bytes string data.
  3. `name`: Modified UTF-8 (`writeUTF()`) -> 2-byte unsigned short length prefix + $L_3$ bytes string data.
  4. `routeNo`: 32-bit signed two's complement integer (`writeInt()`) -> Exactly 4 bytes (Big-Endian).
  5. `boardingStop`: Modified UTF-8 (`writeUTF()`) -> 2-byte unsigned short length prefix + $L_4$ bytes string data.
  6. `validityDate`: Epoch timestamp in milliseconds (`writeLong()`) or 10-char ASCII UTF string -> Exactly 8 bytes (`writeLong()`).
  7. `amountPaid`: 64-bit IEEE 754 double precision floating point (`writeDouble()`) -> Exactly 8 bytes.
* **Delimiter Handling in Binary Mode:**
  Binary formats do NOT use delimiter characters. Instead, `writeUTF()` writes a 2-byte length header preceding the byte array. Therefore, names containing commas, semicolons, null bytes, or quotation marks require **zero escaping** and cannot corrupt field boundaries.

---

## A1.2 – Design the Operations (3 Marks)

### Stream Selection and Technical Justification
* **Chosen Stream Classes:** `BufferedWriter` and `BufferedReader` wrapping `OutputStreamWriter` and `InputStreamReader` (or `java.nio.file.Files.newBufferedWriter` / `newBufferedReader`) using explicit `StandardCharsets.UTF_8`.
* **Technical Justification:**
  1. *Character vs Byte Streams:* Bus-pass records contain textual information (student names, stops, dates) that must be decoded correctly according to character encoding standards (UTF-8). `FileInputStream`/`FileOutputStream` operate on raw 8-bit bytes and lack character set decoding, causing corruption with multi-byte Unicode characters (e.g., regional accents or Indian names).
  2. *Buffering:* `BufferedReader` buffers an 8,192-character block in user-space memory, reducing expensive OS kernel system calls by over 99%. `readLine()` provides an efficient abstraction for reading newline-delimited records.
  3. *Appending:* `BufferedWriter` opened with `StandardOpenOption.APPEND` guarantees that additions are written to the end of the file without re-writing or truncating existing records.

---

### Algorithmic Pseudocode (SIMATS Engineering Standard)

#### Algorithm 1: Add a New Bus Pass Record
```text
ALGORITHM AddBusPassRecord(filePath, passId, regNo, name, routeNo, boardingStop, validityDate, amountPaid)
    INPUT:  filePath (Path to pass database file),
            passId, regNo, name, boardingStop, validityDate (Strings),
            routeNo (Integer), amountPaid (Real Number)
    OUTPUT: Boolean (True if successfully appended, False otherwise)

    VARIABLES:
        formattedName, escapedRecord, writer : Object

    BEGIN
        // Step 1: Escape field if it contains the delimiter comma or quotes
        IF Contains(name, ",") OR Contains(name, "\"") THEN
            formattedName <- "\"" + ReplaceAll(name, "\"", "\"\"") + "\""
        ELSE
            formattedName <- name
        END IF

        // Step 2: Construct comma-delimited record line
        escapedRecord <- passId + "," + regNo + "," + formattedName + "," + 
                         ToString(routeNo) + "," + boardingStop + "," + 
                         validityDate + "," + ToString(amountPaid)

        // Step 3: Open file in append mode and write line
        TRY
            OPEN writer AS BufferedWriter(filePath, StandardCharsets.UTF_8, APPEND_MODE)
            WRITE line escapedRecord TO writer
            WRITE newline TO writer
            FLUSH writer
            CLOSE writer
            RETURN TRUE
        CATCH IOException ex
            LOG "I/O Error while appending bus pass record: " + ex.Message
            RETURN FALSE
        END TRY
    END
```

#### Algorithm 2: Find a Pass Record by Pass ID
```text
ALGORITHM FindPassById(filePath, targetPassId)
    INPUT:  filePath (Path to pass database file), targetPassId (String)
    OUTPUT: Array of Strings containing the 7 fields if found, or NULL if not found

    VARIABLES:
        reader : BufferedReader
        currentLine : String
        tokens : Array of Strings

    BEGIN
        IF NOT FileExists(filePath) THEN
            LOG "File not found: " + filePath
            RETURN NULL
        END IF

        TRY
            OPEN reader AS BufferedReader(filePath, StandardCharsets.UTF_8)
            WHILE (currentLine <- ReadLine(reader)) != NULL DO
                currentLine <- Trim(currentLine)
                
                // Skip empty lines or comments
                IF Length(currentLine) = 0 THEN
                    CONTINUE
                END IF

                tokens <- ParseCsvLine(currentLine)

                // Validate field count integrity
                IF Length(tokens) != 7 THEN
                    LOG "Damaged line encountered. Skipping line: " + currentLine
                    CONTINUE
                END IF

                // Check primary key match
                IF tokens[0] = targetPassId THEN
                    CLOSE reader
                    RETURN tokens
                END IF
            END WHILE
            CLOSE reader
            RETURN NULL
        CATCH IOException ex
            LOG "Error reading pass file: " + ex.Message
            RETURN NULL
        END TRY
    END
```

#### Algorithm 3: List All Passes on a Given Route
```text
ALGORITHM ListPassesByRoute(filePath, targetRouteNo)
    INPUT:  filePath (Path to pass database file), targetRouteNo (Integer)
    OUTPUT: List of Pass Record Arrays matching the given route

    VARIABLES:
        reader : BufferedReader
        currentLine : String
        tokens : Array of Strings
        routeParsed : Integer
        matchedPasses : List of Array of Strings

    BEGIN
        matchedPasses <- EmptyList()

        IF NOT FileExists(filePath) THEN
            RETURN matchedPasses
        END IF

        TRY
            OPEN reader AS BufferedReader(filePath, StandardCharsets.UTF_8)
            WHILE (currentLine <- ReadLine(reader)) != NULL DO
                currentLine <- Trim(currentLine)
                IF Length(currentLine) = 0 THEN
                    CONTINUE
                END IF

                tokens <- ParseCsvLine(currentLine)
                IF Length(tokens) != 7 THEN
                    CONTINUE // Skip corrupted lines gracefully
                END IF

                TRY
                    routeParsed <- ParseInteger(tokens[3])
                    IF routeParsed = targetRouteNo THEN
                        Append(matchedPasses, tokens)
                    END IF
                CATCH NumberFormatException nfe
                    LOG "Corrupted route number on line: " + currentLine
                    CONTINUE
                END TRY
            END WHILE
            CLOSE reader
            RETURN matchedPasses
        CATCH IOException ex
            LOG "Error reading file during route search: " + ex.Message
            RETURN matchedPasses
        END TRY
    END
```

---

## A1.3 – Design for Damaged Data (2 Marks)

| Failure Scenario | Detection Mechanism | Mitigation & Recovery Handling |
| :--- | :--- | :--- |
| **Damaged Line** (e.g. truncated line, missing delimiter, invalid token) | Token count check (`tokens.length != 7`) and parsing validation (`NumberFormatException`). | The parser skips the corrupt line without crashing, logs a warning with line number, and continues searching remaining lines. |
| **Missing File** | `Files.exists(path)` check and `NoSuchFileException` catching. | For read operations: returns an empty list or `null` cleanly without crashing. For write operations: creates the file and parent directories automatically (`StandardOpenOption.CREATE`). |
| **Incomplete Write from Power Failure** | Partial record at the end of file (lacking newline terminator or fewer than 7 tokens). | **Atomic Write Protocol:** Writes are performed to a temporary file (`passes.tmp`). On successful completion and flush, an atomic file rename (`Files.move` with `ATOMIC_MOVE`) replaces the original file. Alternatively, each record includes an end-of-record marker / checksum. |

---

## Alternative Concept and Complexity Analysis: RandomAccessFile Fixed-Length Records

### Solution Architecture
In a `RandomAccessFile` architecture, each record is assigned an invariant fixed byte length $S$ (e.g., $S = 200$ bytes). Variable length strings are padded with spaces or null bytes:
- `passId`: 10 bytes ASCII
- `regNo`: 12 bytes ASCII
- `name`: 40 bytes ASCII (padded)
- `routeNo`: 4 bytes binary integer
- `boardingStop`: 30 bytes ASCII (padded)
- `validityDate`: 8 bytes long (epoch timestamp)
- `amountPaid`: 8 bytes double
- `recordStatus`: 1 byte (Active=1, Deleted=0)
- **Total Fixed Record Size:** Exactly 113 bytes (padded to 128 bytes power-of-two alignment).

To find record $i$:
$$\text{byteOffset} = i \times 128$$
The disk head seeks directly: `file.seek(byteOffset)`.

### Big-O Complexity Comparison
| Operation | Delimited Text File | Fixed-Length `RandomAccessFile` |
| :--- | :--- | :--- |
| **Find by Record Index / Numeric ID** | $O(n)$ sequential scan (must parse all preceding newlines) | **$O(1)$ direct seek** (`seek(id * recordSize)`) |
| **Find by Arbitrary String ID** | $O(n)$ linear scan | $O(n)$ linear scan (or $O(\log n)$ via binary search if sorted) |
| **Add New Record** | $O(1)$ append to tail | $O(1)$ append or overwrite deleted record slot |
| **Space Utilization** | **High efficiency** (compact, variable length, no padding) | **Wasteful:** Up to 40%–60% wasted space due to fixed-width string padding. |
| **Schema Evolution** | Difficult (requires rewriting entire file) | Impossible without rewriting or complex offset migrations |
