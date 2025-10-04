package hotelbookingmanagement;

import java.util.Comparator;

public class RoomComparators {

    public static Comparator<Room> byNumber() {
        return Comparator.comparingInt(Room::getRoomNumber);
    }

    public static Comparator<Room> byFloor() {
        return Comparator.comparingInt(Room::getFloor).reversed(); // Descending order
    }

    public static Comparator<Room> byPrice() {
        return Comparator.comparingDouble(Room::getPrice);
    }
}
