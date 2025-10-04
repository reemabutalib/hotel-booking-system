
package hotelbookingmanagement;

public class StandardRoom extends Room {
    private int numberOfWindows;

     // Constructor
    public StandardRoom(int roomNumber, int floor, String occupancy, double price) {
    super(roomNumber, floor, "Single", occupancy, price); // Use "Single" as a valid type
}

    @Override
    public String getRoomDetails() {
        return "Standard Room -> " + super.getRoomDetails() +
               ", Number of Windows: " + numberOfWindows;
    }
}


