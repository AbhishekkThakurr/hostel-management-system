import java.util.ArrayList;
import java.util.List;

public class Room {
    private String roomNo;
    private int capacity;
    private List<String> occupantRollNos;

    public Room(String roomNo, int capacity) {
        this.roomNo = roomNo;
        this.capacity = capacity;
        this.occupantRollNos = new ArrayList<>();
    }

    public String getRoomNo() {
        return roomNo;
    }

    public int getCapacity() {
        return capacity;
    }

    public List<String> getOccupantRollNos() {
        return occupantRollNos;
    }

    public int getOccupiedCount() {
        return occupantRollNos.size();
    }

    public int getFreeBeds() {
        return capacity - occupantRollNos.size();
    }

    public boolean hasFreeBed() {
        return getFreeBeds() > 0;
    }

    public boolean addOccupant(String rollNo) {
        if (!hasFreeBed()) {
            return false;
        }
        if (occupantRollNos.contains(rollNo)) {
            return false;
        }
        occupantRollNos.add(rollNo);
        return true;
    }

    @Override
    public String toString() {
        return "Room " + roomNo + " - Capacity: " + capacity
                + ", Occupied: " + getOccupiedCount()
                + ", Free beds: " + getFreeBeds()
                + (occupantRollNos.isEmpty() ? "" : ", Students: " + occupantRollNos);
    }
}
