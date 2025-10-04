package hotelbookingmanagement;

import java.time.LocalDate;

public class Booking implements Overlappable {
    private Customer customer;
    private Room room;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String bookingId;

    // Constructor
    public Booking(Customer customer, Room room, LocalDate checkInDate, LocalDate checkOutDate) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null.");
        }
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null.");
        }
        if (checkInDate == null || checkOutDate == null) {
            throw new IllegalArgumentException("Check-in and check-out dates cannot be null.");
        }
        if (checkOutDate.isBefore(checkInDate)) {
            throw new IllegalArgumentException("Check-out date cannot be before check-in date.");
        }

        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bookingId = generateBookingId(); // Automatically generate a unique booking ID
    }

    // Generate a unique booking ID (simple implementation)
    private String generateBookingId() {
    return "BKG-" + room.getRoomNumber();
}


    // Getters and Setters
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null.");
        }
        this.customer = customer;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null.");
        }
        this.room = room;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        if (checkInDate == null) {
            throw new IllegalArgumentException("Check-in date cannot be null.");
        }
        if (this.checkOutDate != null && checkInDate.isAfter(this.checkOutDate)) {
            throw new IllegalArgumentException("Check-in date cannot be after the check-out date.");
        }
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        if (checkOutDate == null) {
            throw new IllegalArgumentException("Check-out date cannot be null.");
        }
        if (this.checkInDate != null && checkOutDate.isBefore(this.checkInDate)) {
            throw new IllegalArgumentException("Check-out date cannot be before the check-in date.");
        }
        this.checkOutDate = checkOutDate;
    }

    public String getBookingId() {
        return bookingId;
    }

    // Method to check if this booking overlaps with another booking
    @Override
    public boolean overlaps(Booking other) {
        if (other == null) {
            return false;
        }
        return !this.checkOutDate.isBefore(other.checkInDate) && !this.checkInDate.isAfter(other.checkOutDate);
    }


    // Overlaps with a date range
    public boolean overlaps(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start and end dates cannot be null.");
        }
        return !this.checkOutDate.isBefore(startDate) && !this.checkInDate.isAfter(endDate);
    }

    // Get details of the booking
    public String getBookingDetails() {
        return "Booking ID: " + bookingId +
               "\nRoom Number: " + room.getRoomNumber() +
               "\nCustomer: " + customer.getName() + 
               "\nContact Details: " + customer.getContactDetails() +
               "\nCheck-In: " + checkInDate +
               "\nCheck-Out: " + checkOutDate;
    }

    @Override
    public String toString() {
        return getBookingDetails();
    }
}
