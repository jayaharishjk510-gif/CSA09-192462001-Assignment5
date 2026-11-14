import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Task A4: Object Model Demonstrating Java Serialization Architecture.
 * Illustrates object graph hierarchy: Hostel -> Room -> Resident.
 */
public class HostelModel {

    public static class Hostel implements Serializable {
        private static final long serialVersionUID = 1L;

        private String hostelName;
        private List<Room> rooms = new ArrayList<>();

        public Hostel(String hostelName) {
            this.hostelName = hostelName;
        }

        public void addRoom(Room r) { rooms.add(r); }
        public List<Room> getRooms() { return rooms; }
        public String getHostelName() { return hostelName; }
    }

    public static class Room implements Serializable {
        private static final long serialVersionUID = 1L;

        private int roomNumber;
        private int capacity;
        private List<Resident> residents = new ArrayList<>();

        public Room(int roomNumber, int capacity) {
            this.roomNumber = roomNumber;
            this.capacity = capacity;
        }

        public void addResident(Resident res) { residents.add(res); }
        public List<Resident> getResidents() { return residents; }
        public int getRoomNumber() { return roomNumber; }
    }

    public static class Resident implements Serializable {
        // Explicit serialVersionUID ensures forward/backward version compatibility
        private static final long serialVersionUID = 2L;

        private String name;
        private String regNo;

        // FIELD 1 TO EXCLUDE: Sensitive credential must NOT be stored in plain serialized streams
        private transient String password;

        // FIELD 2 TO EXCLUDE: Live OS/database socket resource is NOT serializable
        private transient Connection connection;

        // NEW FIELD ADDED IN VERSION 2: Evolution requirement
        private String phoneNumber;

        public Resident(String name, String regNo, String password, Connection connection, String phoneNumber) {
            this.name = name;
            this.regNo = regNo;
            this.password = password;
            this.connection = connection;
            this.phoneNumber = phoneNumber;
        }

        public String getName() { return name; }
        public String getRegNo() { return regNo; }
        public String getPhoneNumber() { return phoneNumber; }
        public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    }
}
