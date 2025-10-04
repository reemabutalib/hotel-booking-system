package hotelbookingmanagement;

import java.util.Set;

public class Room {

    // Valid room types and occupancies
    public static final Set<String> VALID_TYPES = Set.of("Single", "Double", "Suite");
    public static final Set<String> VALID_OCCUPANCIES = Set.of("Single", "Double", "Triple");

    private final int roomNumber;
    private final int floor;
    private String type;       // e.g., "Single", "Double", "Suite"
    private String occupancy;  // e.g., "Single", "Double", "Triple"
    private double price;      // Price per night
    private boolean isEmpty;   // Room availability

    // Constructor
    public Room(int roomNumber, int floor, String type, String occupancy, double price) {
        if (roomNumber <= 0) {
            throw new IllegalArgumentException("Room number must be a positive integer.");
        }
        if (floor < 0) {
            throw new IllegalArgumentException("Floor number cannot be negative.");
        }
        if (!VALID_TYPES.contains(type)) {
            throw new IllegalArgumentException("Invalid room type. Valid types: " + VALID_TYPES);
        }
        if (!VALID_OCCUPANCIES.contains(occupancy)) {
            throw new IllegalArgumentException("Invalid occupancy. Valid options: " + VALID_OCCUPANCIES);
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }

        this.roomNumber = roomNumber;
        this.floor = floor;
        this.type = type.trim();
        this.occupancy = occupancy.trim();
        this.price = price;
        this.isEmpty = true; // Default to available when created
    }

    // Getters and setters with validation
    public int getRoomNumber() {
        return roomNumber;
    }

    public int getFloor() {
        return floor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if (!VALID_TYPES.contains(type)) {
            throw new IllegalArgumentException("Invalid room type. Valid types: " + VALID_TYPES);
        }
        this.type = type.trim();
    }

    public String getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(String occupancy) {
        if (!VALID_OCCUPANCIES.contains(occupancy)) {
            throw new IllegalArgumentException("Invalid occupancy. Valid options: " + VALID_OCCUPANCIES);
        }
        this.occupancy = occupancy.trim();
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        this.price = price;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }

    // Method to retrieve room details
    public String getRoomDetails() {
        return String.format(
            "Room Number: %d, Floor: %d, Type: %s, Occupancy: %s, Price: $%.2f, Availability: %s",
            roomNumber, floor, type, occupancy, price, isEmpty ? "Available" : "Occupied"
        );
    }

    @Override
    public String toString() {
        return getRoomDetails();
    }

    // Utility method for room comparison
    public boolean matchesCriteria(String type, String occupancy) {
        return this.type.equalsIgnoreCase(type.trim()) && this.occupancy.equalsIgnoreCase(occupancy.trim());
    }
}
