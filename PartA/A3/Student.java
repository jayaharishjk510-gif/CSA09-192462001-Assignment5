import java.util.Objects;

/**
 * Task A3.3: Domain entity representing a Student.
 * Maps directly to the 'student' relational table.
 */
public class Student {
    private String regNo;    // Primary Key -> student.reg_no
    private String name;     // Attribute   -> student.name
    private String dept;     // Attribute   -> student.dept
    private int year;        // Attribute   -> student.year
    private double cgpa;     // Attribute   -> student.cgpa

    public Student() {}

    public Student(String regNo, String name, String dept, int year, double cgpa) {
        this.regNo = regNo;
        this.name = name;
        this.dept = dept;
        this.year = year;
        this.cgpa = cgpa;
    }

    public String getRegNo() { return regNo; }
    public void setRegNo(String regNo) { this.regNo = regNo; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDept() { return dept; }
    public void setDept(String dept) { this.dept = dept; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public double getCgpa() { return cgpa; }
    public void setCgpa(double cgpa) { this.cgpa = cgpa; }

    @Override
    public String toString() {
        return String.format("Student[regNo='%s', name='%s', dept='%s', year=%d, cgpa=%.2f]",
                regNo, name, dept, year, cgpa);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student student)) return false;
        return Objects.equals(regNo, student.regNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regNo);
    }
}
