import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HostelManagement {
    private List<Student> students = new ArrayList<>();
    private List<Room> rooms = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        HostelManagement app = new HostelManagement();
        app.run();
    }

    public void run() {
        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    addRoom();
                    break;
                case "2":
                    addStudent();
                    break;
                case "3":
                    allocateRoom();
                    break;
                case "4":
                    listRooms();
                    break;
                case "5":
                    listStudents();
                    break;
                case "6":
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please choose 1-6.");
                    break;
            }
            System.out.println();
        }
    }

    private void printMenu() {
        System.out.println("===== Hostel Management System =====");
        System.out.println("Rooms: " + rooms.size() + "  |  Students: " + students.size());
        System.out.println("1. Add a room");
        System.out.println("2. Add a student");
        System.out.println("3. Allocate a room to a student");
        System.out.println("4. List all rooms");
        System.out.println("5. List all students");
        System.out.println("6. Exit");
        System.out.print("Choose an option: ");
    }

    private void addRoom() {
        System.out.print("Enter room number: ");
        String roomNo = scanner.nextLine().trim();
        if (roomNo.isEmpty()) {
            System.out.println("Room number cannot be blank.");
            return;
        }
        if (findRoom(roomNo) != null) {
            System.out.println("Room " + roomNo + " already exists.");
            return;
        }
        System.out.print("Enter room capacity (number of beds): ");
        String capacityText = scanner.nextLine().trim();
        int capacity;
        try {
            capacity = Integer.parseInt(capacityText);
        } catch (NumberFormatException e) {
            System.out.println("Capacity must be a number.");
            return;
        }
        if (capacity <= 0) {
            System.out.println("Capacity must be more than 0.");
            return;
        }
        rooms.add(new Room(roomNo, capacity));
        System.out.println("Room " + roomNo + " added with capacity " + capacity + ".");
    }

    private void addStudent() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be blank.");
            return;
        }
        System.out.print("Enter roll number: ");
        String rollNo = scanner.nextLine().trim();
        if (rollNo.isEmpty()) {
            System.out.println("Roll number cannot be blank.");
            return;
        }
        if (findStudent(rollNo) != null) {
            System.out.println("A student with roll number " + rollNo + " already exists.");
            return;
        }
        System.out.print("Enter course (e.g. BTech CSE): ");
        String course = scanner.nextLine().trim();
        if (course.isEmpty()) {
            course = "Not specified";
        }
        students.add(new Student(name, rollNo, course));
        System.out.println("Student added: " + name + " (" + rollNo + ").");
    }

    private void allocateRoom() {
        if (students.isEmpty()) {
            System.out.println("No students yet. Add a student first (option 2).");
            return;
        }
        if (rooms.isEmpty()) {
            System.out.println("No rooms yet. Add a room first (option 1).");
            return;
        }
        System.out.print("Enter student roll number: ");
        String rollNo = scanner.nextLine().trim();
        Student student = findStudent(rollNo);
        if (student == null) {
            System.out.println("No student found with roll number " + rollNo + ".");
            return;
        }
        if (student.isAllocated()) {
            System.out.println(student.getName() + " is already in Room " + student.getRoomNo() + ".");
            return;
        }
        System.out.print("Enter room number to allocate: ");
        String roomNo = scanner.nextLine().trim();
        Room room = findRoom(roomNo);
        if (room == null) {
            System.out.println("Room " + roomNo + " does not exist.");
            return;
        }
        if (!room.hasFreeBed()) {
            System.out.println("Room " + roomNo + " is full.");
            return;
        }
        room.addOccupant(rollNo);
        student.setRoomNo(roomNo);
        System.out.println("Allocated: " + student.getName() + " (" + rollNo + ") -> Room " + roomNo + ".");
    }

    private void listRooms() {
        if (rooms.isEmpty()) {
            System.out.println("No rooms added yet.");
            return;
        }
        System.out.println("All rooms (" + rooms.size() + "):");
        for (Room room : rooms) {
            System.out.println("  " + room);
        }
    }

    private void listStudents() {
        if (students.isEmpty()) {
            System.out.println("No students added yet.");
            return;
        }
        System.out.println("All students (" + students.size() + "):");
        for (Student student : students) {
            System.out.println("  " + student);
        }
    }

    private Student findStudent(String rollNo) {
        for (Student student : students) {
            if (student.getRollNo().equalsIgnoreCase(rollNo)) {
                return student;
            }
        }
        return null;
    }

    private Room findRoom(String roomNo) {
        for (Room room : rooms) {
            if (room.getRoomNo().equalsIgnoreCase(roomNo)) {
                return room;
            }
        }
        return null;
    }
}
