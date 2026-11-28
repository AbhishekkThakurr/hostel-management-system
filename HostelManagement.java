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
                    viewAllocations();
                    break;
                case "7":
                    searchStudent();
                    break;
                case "8":
                    searchRoom();
                    break;
                case "9":
                    vacancyCheck();
                    break;
                case "10":
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please choose 1-10.");
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
        System.out.println("6. View allocations");
        System.out.println("7. Search student");
        System.out.println("8. Search room");
        System.out.println("9. Vacancy check");
        System.out.println("10. Exit");
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

    private void viewAllocations() {
        if (students.isEmpty()) {
            System.out.println("No students added yet.");
            return;
        }
        int allocated = 0;
        System.out.println("Allocated students:");
        for (Student student : students) {
            if (student.isAllocated()) {
                System.out.println("  " + student.getName() + " (" + student.getRollNo() + ") -> Room " + student.getRoomNo());
                allocated++;
            }
        }
        if (allocated == 0) {
            System.out.println("  (none allocated yet)");
        }
        System.out.println("Unallocated students: " + (students.size() - allocated) + " of " + students.size());
        for (Student student : students) {
            if (!student.isAllocated()) {
                System.out.println("  " + student.getName() + " (" + student.getRollNo() + ") - waiting for a room");
            }
        }
    }

    private void searchStudent() {
        if (students.isEmpty()) {
            System.out.println("No students added yet.");
            return;
        }
        System.out.print("Enter roll number or part of name to search: ");
        String query = scanner.nextLine().trim();
        if (query.isEmpty()) {
            System.out.println("Search text cannot be blank.");
            return;
        }
        int found = 0;
        for (Student student : students) {
            if (student.getRollNo().equalsIgnoreCase(query)
                    || student.getName().toLowerCase().contains(query.toLowerCase())) {
                System.out.println("  " + student);
                found++;
            }
        }
        if (found == 0) {
            System.out.println("No student matched \"" + query + "\".");
        } else {
            System.out.println(found + " match(es) found.");
        }
    }

    private void searchRoom() {
        if (rooms.isEmpty()) {
            System.out.println("No rooms added yet.");
            return;
        }
        System.out.print("Enter room number to search: ");
        String roomNo = scanner.nextLine().trim();
        if (roomNo.isEmpty()) {
            System.out.println("Room number cannot be blank.");
            return;
        }
        Room room = findRoom(roomNo);
        if (room == null) {
            System.out.println("Room " + roomNo + " does not exist.");
            return;
        }
        System.out.println("  " + room);
        if (room.getOccupantRollNos().isEmpty()) {
            System.out.println("  No students in this room yet.");
        } else {
            System.out.println("  Students in Room " + room.getRoomNo() + ":");
            for (String rollNo : room.getOccupantRollNos()) {
                Student student = findStudent(rollNo);
                if (student != null) {
                    System.out.println("    - " + student.getName() + " (" + rollNo + ", " + student.getCourse() + ")");
                } else {
                    System.out.println("    - " + rollNo);
                }
            }
        }
    }

    private void vacancyCheck() {
        if (rooms.isEmpty()) {
            System.out.println("No rooms added yet.");
            return;
        }
        int totalFree = 0;
        int fullRooms = 0;
        System.out.println("Vacancy check:");
        for (Room room : rooms) {
            if (room.hasFreeBed()) {
                System.out.println("  Room " + room.getRoomNo() + ": " + room.getFreeBeds() + " free bed(s) of " + room.getCapacity());
                totalFree += room.getFreeBeds();
            } else {
                System.out.println("  Room " + room.getRoomNo() + ": FULL (" + room.getCapacity() + "/" + room.getCapacity() + ")");
                fullRooms++;
            }
        }
        System.out.println("Total free beds: " + totalFree + "  |  Full rooms: " + fullRooms + " of " + rooms.size());
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
