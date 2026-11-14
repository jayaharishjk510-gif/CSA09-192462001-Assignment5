# Task A4: What Should Be Serialized? (4 Marks)

## Files in this Folder
- `HostelModel.java`: Java domain classes (`Hostel`, `Room`, `Resident`) implementing `Serializable`, demonstrating `transient` attributes and explicit `serialVersionUID`.
- `SerializationDesign.md`: Detailed analysis of object graph traversal, transient field rationale (`Connection` and `password`), `serialVersionUID` schema evolution, and comparison with CSV/JSON.

## Compilation
```bash
javac HostelModel.java
```
