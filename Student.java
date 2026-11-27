public class Student {
    private String name;
    private String rollNo;
    private String course;
    private String roomNo; // empty string means not allocated yet

    public Student(String name, String rollNo, String course) {
        this.name = name;
        this.rollNo = rollNo;
        this.course = course;
        this.roomNo = "";
    }

    public String getName() {
        return name;
    }

    public String getRollNo() {
        return rollNo;
    }

    public String getCourse() {
        return course;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public boolean isAllocated() {
        return roomNo != null && !roomNo.isEmpty();
    }

    @Override
    public String toString() {
        String room = isAllocated() ? roomNo : "Not allocated";
        return name + " (" + rollNo + ", " + course + ") - Room: " + room;
    }
}
