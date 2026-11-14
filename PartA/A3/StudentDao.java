import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Task A3.2: Concrete DAO implementation for Student entities.
 * Implements Dao<Student, String> using PreparedStatement and JDBC Connection.
 */
public class StudentDao implements Dao<Student, String> {

    private final Connection connection;

    public StudentDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean insert(Student s) {
        String sql = "INSERT INTO student(reg_no, name, dept, year, cgpa) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, s.getRegNo());
            ps.setString(2, s.getName());
            ps.setString(3, s.getDept());
            ps.setInt(4, s.getYear());
            ps.setDouble(5, s.getCgpa());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error inserting student: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Optional<Student> findById(String regNo) {
        String sql = "SELECT reg_no, name, dept, year, cgpa FROM student WHERE reg_no = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, regNo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRowToStudent(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding student by ID: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT reg_no, name, dept, year, cgpa FROM student ORDER BY reg_no ASC";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                students.add(mapRowToStudent(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all students: " + e.getMessage());
        }
        return students;
    }

    @Override
    public boolean update(Student s) {
        String sql = "UPDATE student SET name = ?, dept = ?, year = ?, cgpa = ? WHERE reg_no = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, s.getName());
            ps.setString(2, s.getDept());
            ps.setInt(3, s.getYear());
            ps.setDouble(4, s.getCgpa());
            ps.setString(5, s.getRegNo());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating student: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(String regNo) {
        String sql = "DELETE FROM student WHERE reg_no = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, regNo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting student: " + e.getMessage());
            return false;
        }
    }

    /** Helper method isolating Object-Relational column mapping logic. */
    private Student mapRowToStudent(ResultSet rs) throws SQLException {
        return new Student(
            rs.getString("reg_no"),
            rs.getString("name"),
            rs.getString("dept"),
            rs.getInt("year"),
            rs.getDouble("cgpa")
        );
    }
}
