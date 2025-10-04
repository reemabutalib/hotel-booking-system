package hotelbookingmanagement;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HotelSystem implements HotelManager, HotelCustomer {

    private List<Room> rooms;       // List of all rooms in the system
    private List<Booking> bookings; // List of all bookings in the system

    // Constructor to initialize lists
    public HotelSystem() {
        this.rooms = new ArrayList<>();
        this.bookings = new ArrayList<>();
    }

    // Add a room to the system
    @Override
    public void addRoom(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null.");
        }
        if (rooms.stream().anyMatch(r -> r.getRoomNumber() == room.getRoomNumber())) {
            throw new IllegalArgumentException("A room with number " + room.getRoomNumber() + " already exists.");
        }
        rooms.add(room);
        System.out.println("Room added successfully!");
    }

    // Remove a room by its number
    @Override
    public boolean removeRoom(int roomNumber) {
        boolean removed = rooms.removeIf(room -> room.getRoomNumber() == roomNumber);
        System.out.println(removed ? "Room removed successfully!" : "Room with number " + roomNumber + " not found.");
        return removed;
    }

    // List rooms sorted by number
    @Override
    public List<Room> listRoomsByNumber() {
        System.out.println("Rooms in the system: " + rooms.size());
        rooms.forEach(room -> System.out.println("Room: " + room.getRoomDetails()));
        return rooms.stream()
                .sorted(RoomComparators.byNumber())
                .collect(Collectors.toList());
    }


    // List rooms sorted by floor
    @Override
    public List<Room> listRoomsByFloor() {
        return rooms.stream()
                .sorted(RoomComparators.byFloor())
                .collect(Collectors.toList());
    }

    // Generate a booking report as a string
    @Override
    public String generateBookingReport(LocalDate startDate, LocalDate endDate) {
        validateDates(startDate, endDate);

        return bookings.stream()
                .filter(booking -> isBookingInRange(booking, startDate, endDate))
                .map(booking -> "Room: " + booking.getRoom().getRoomDetails() +
                                "\nCustomer: " + booking.getCustomer().toString() +
                                "\nBooking Dates: " + booking.getCheckInDate() + " to " + booking.getCheckOutDate())
                .collect(Collectors.joining("\n\n"));
    }

    // Generate a booking report and save it to a file
    public void generateBookingReportToFile(LocalDate startDate, LocalDate endDate, String filePath) {
        try {
            String report = generateBookingReport(startDate, endDate);
            Files.writeString(Paths.get(filePath), report);
            System.out.println("Report saved to " + filePath);
        } catch (IOException e) {
            System.err.println("Failed to save booking report: " + e.getMessage());
        }
    }

    // List available rooms for a given date range
    @Override
    public List<Room> listAvailableRooms(LocalDate start, LocalDate end) {
        System.out.println("Checking availability from " + start + " to " + end);
        return rooms.stream()
                .filter(room -> bookings.stream()
                .noneMatch(booking -> {
                    boolean overlaps = booking.getRoom().equals(room) && booking.overlaps(start, end);
                    if (overlaps) {
                        System.out.println("Room " + room.getRoomNumber() + " is booked.");
                    }
                    return overlaps;
                }))
                .sorted(RoomComparators.byPrice())
                .collect(Collectors.toList());
    }


    // Book a room
    @Override
    public Booking bookRoom(Room room, Customer customer, LocalDate start, LocalDate end) throws BookingException {
        if (room == null || customer == null || start == null || end == null) {
            throw new IllegalArgumentException("Room, customer, start date, and end date cannot be null.");
        }
        if (!listAvailableRooms(start, end).contains(room)) {
            throw new BookingException("Room " + room.getRoomNumber() + " is not available for the given dates.");
        }

        Booking booking = new Booking(customer, room, start, end);
        bookings.add(booking);
        System.out.println("Booking successful! Booking ID: " + booking.getBookingId());
        return booking;
    }

    // Overloaded bookRoom method with simpler parameters
    public boolean bookRoom(int roomNumber, String name, String contact, LocalDate start, LocalDate end) throws BookingException {
        Room room = rooms.stream()
                .filter(r -> r.getRoomNumber() == roomNumber)
                .findFirst()
                .orElseThrow(() -> new BookingException("Room number " + roomNumber + " does not exist."));

        Customer customer = new Customer(name, "N/A", LocalDate.now(), contact);

        return bookRoom(room, customer, start, end) != null;
    }

    // Delete a booking by its ID
    @Override
    public boolean deleteBooking(String bookingID) {
        if (bookingID == null || bookingID.trim().isEmpty()) {
            throw new IllegalArgumentException("Booking ID cannot be null or empty.");
        }

        boolean removed = bookings.removeIf(booking -> booking.getBookingId().equals(bookingID));
        System.out.println(removed ? "Booking canceled successfully!" : "Booking with ID " + bookingID + " not found.");
        return removed;
    }

    // List rooms by criteria
    @Override
    public List<Room> listRoomsByCriteria(String type, String occupancy, LocalDate start, LocalDate end) {
        validateDates(start, end);
        if (type == null || occupancy == null) {
            throw new IllegalArgumentException("Type and occupancy cannot be null.");
        }

        return listAvailableRooms(start, end).stream()
                .filter(room -> room.getClass().getSimpleName().equalsIgnoreCase(type))
                .filter(room -> room.getOccupancy().equalsIgnoreCase(occupancy))
                .collect(Collectors.toList());
    }

    // Helper method: Validate dates
    private void validateDates(LocalDate start, LocalDate end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start and end dates cannot be null.");
        }
        if (end.isBefore(start)) {
            throw new IllegalArgumentException("End date cannot be before start date.");
        }
    }

    // Helper method: Check if a booking is within a given date range
    private boolean isBookingInRange(Booking booking, LocalDate startDate, LocalDate endDate) {
        return !booking.getCheckOutDate().isBefore(startDate) && !booking.getCheckInDate().isAfter(endDate);
    }
    
    // utility method to fetch a room by its number
    public Room getRoomByNumber(int roomNumber) throws IllegalArgumentException {
        return rooms.stream()
                .filter(room -> room.getRoomNumber() == roomNumber)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Room number " + roomNumber + " does not exist."));
    }


    // Add a room using its parameters
    public boolean addRoom(String roomType, int roomNumber, int floor, double price) {
        Room room;
        switch (roomType.toLowerCase()) {
            case "standard":
                room = new StandardRoom(roomNumber, floor, "single", price);
                break;
            case "deluxe":
                room = new DeluxeRoom(roomNumber, floor, "double", price, 10.0, "Sea View");
                break;
            case "suite":
                room = new SuiteRoom(roomNumber, floor, "triple", price, 20.0, 2, true);
                break;
            default:
                throw new IllegalArgumentException("Invalid room type: " + roomType);
        }

        addRoom(room);
        return true;
    }
}
