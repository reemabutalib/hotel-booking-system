package hotelbookingmanagement;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Hotelbookingmanagement {
    private static final Scanner scanner = new Scanner(System.in);
    private static final HotelSystem hotelsystem = new HotelSystem(); // Main system object

    public static void main(String[] args) {
        populateRooms(); // Ensure rooms are added to the system
        // Pre-populate rooms for testing
        
        while (true) {
            try {
                System.out.println("----- Hotel Booking Management System -----");
                System.out.println("1. Customer Menu");
                System.out.println("2. Admin Menu");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        customerMenu();
                        break;
                    case 2:
                        adminMenu();
                        break;
                    case 3:
                        if (confirmExit()) {
                            System.out.println("Exiting system. Goodbye!");
                            return;
                        }
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred. Please try again.");
                scanner.nextLine(); // Clear buffer
            }
        }
    }
    
    private static void populateRooms() {
        System.out.println("Populating rooms...");
        try {
            hotelsystem.addRoom(new StandardRoom(101, 1, "Single", 100.0));
            hotelsystem.addRoom(new DeluxeRoom(201, 2, "Double", 200.0, 10.0, "Sea View"));
            hotelsystem.addRoom(new SuiteRoom(301, 3, "Triple", 300.0, 25.0, 2, true));
        } catch (IllegalArgumentException e) {
            System.err.println("Error populating rooms: " + e.getMessage());
        }
        System.out.println("Rooms pre-populated. Total rooms: " + hotelsystem.listRoomsByNumber().size());
        hotelsystem.listRoomsByNumber().forEach(room -> System.out.println(room.getRoomDetails()));
    }



    private static void customerMenu() {
        while (true) {
            try {
                System.out.println("---- Customer Menu ----");
                System.out.println("1. View available rooms");
                System.out.println("2. View rooms by type and occupancy");
                System.out.println("3. Book a room");
                System.out.println("4. Cancel a booking");
                System.out.println("5. Return to main menu");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        viewAvailableRooms();
                        break;
                    case 2:
                        viewRoomsByCriteria();
                        break;
                    case 3:
                        bookRoom();
                        break;
                    case 4:
                        cancelBooking();
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred. Please try again.");
                scanner.nextLine(); // Clear buffer
            }
        }
    }

    private static void adminMenu() {
        while (true) {
            try {
                System.out.println("---- Admin Menu ----");
                System.out.println("1. Add a room");
                System.out.println("2. Remove a room");
                System.out.println("3. List rooms by number");
                System.out.println("4. List rooms by floor");
                System.out.println("5. Generate booking report");
                System.out.println("6. Return to main menu");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        addRoom();
                        break;
                    case 2:
                        removeRoom();
                        break;
                    case 3:
                        listRoomsByNumber();
                        break;
                    case 4:
                        listRoomsByFloor();
                        break;
                    case 5:
                        generateBookingReport();
                        break;
                    case 6:
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred. Please try again.");
                scanner.nextLine(); // Clear buffer
            }
        }
    }
    
    private static boolean confirmExit() {
        System.out.print("Are you sure you want to exit? (yes/no): ");
        String response = scanner.nextLine().trim().toLowerCase();
        return response.equals("yes") || response.equals("y");
    }

    private static void viewAvailableRooms() {
        try {
            System.out.print("Enter start date (yyyy-mm-dd): ");
            LocalDate start = LocalDate.parse(scanner.nextLine());
            System.out.print("Enter end date (yyyy-mm-dd): ");
            LocalDate end = LocalDate.parse(scanner.nextLine());

            List<Room> rooms = hotelsystem.listAvailableRooms(start, end);
            if (rooms.isEmpty()) {
                System.out.println("No rooms available for the given dates.");
            } else {
                System.out.println("Available Rooms:");
                rooms.forEach(room -> System.out.println(room.getRoomDetails()));
            }
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use yyyy-mm-dd.");
        }
    }

    private static void viewRoomsByCriteria() {
        try {
            System.out.print("Enter room type (StandardRoom, DeluxeRoom, SuiteRoom): ");
            String type = scanner.nextLine();
            System.out.print("Enter occupancy (single, double, triple): ");
            String occupancy = scanner.nextLine();
            System.out.print("Enter start date (yyyy-mm-dd): ");
            LocalDate start = LocalDate.parse(scanner.nextLine());
            System.out.print("Enter end date (yyyy-mm-dd): ");
            LocalDate end = LocalDate.parse(scanner.nextLine());

            List<Room> rooms = hotelsystem.listRoomsByCriteria(type, occupancy, start, end);
            if (rooms.isEmpty()) {
                System.out.println("No rooms match the criteria.");
            } else {
                System.out.println("Matching Rooms:");
                rooms.forEach(room -> System.out.println(room.getRoomDetails()));
            }
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use yyyy-mm-dd.");
        }
    }

    private static void bookRoom() {
    try {
        System.out.print("Enter room number: ");
        int roomNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter your full name: ");
        String fullName = scanner.nextLine().trim();
        String[] nameParts = fullName.split("\\s+", 2); // Split into first name and surname

        if (nameParts.length < 2) {
            System.out.println("Invalid name. Please enter both first name and surname.");
            return;
        }

        String firstName = nameParts[0];
        String surname = nameParts[1];

        System.out.print("Enter your contact number (11 digits): ");
        String contact;
        while (true) {
            contact = scanner.nextLine().trim();
            if (contact.matches("\\d{11}")) {
                break;
            } else {
                System.out.println("Invalid contact number. Please enter exactly 11 digits.");
            }
        }

        System.out.print("Enter check-in date (yyyy-mm-dd): ");
        LocalDate start = LocalDate.parse(scanner.nextLine());
        System.out.print("Enter check-out date (yyyy-mm-dd): ");
        LocalDate end = LocalDate.parse(scanner.nextLine());

        // Attempt to book the room
        try {
            Customer customer = new Customer(firstName, surname, LocalDate.now(), contact);
            Room room = hotelsystem.getRoomByNumber(roomNumber); 
            hotelsystem.bookRoom(room, customer, start, end);
            System.out.println("Room booked successfully!");
        } catch (Exception e) {
            System.out.println("An error occurred while booking the room. Details: " + e.getMessage());
        }
    } catch (DateTimeParseException e) {
        System.out.println("Invalid date format. Please use yyyy-mm-dd.");
    } catch (Exception e) {
        System.out.println("An error occurred. Please try again.");
        scanner.nextLine(); // Clear buffer
    }
}



    private static void cancelBooking() {
        System.out.print("Enter booking ID: ");
        String bookingID = scanner.nextLine();
        if (hotelsystem.deleteBooking(bookingID)) {
            System.out.println("Booking canceled successfully.");
        } else {
            System.out.println("Failed to cancel booking. Booking ID not found.");
        }
    }

    private static void addRoom() {
        try {
            System.out.print("Enter room type (StandardRoom, DeluxeRoom, SuiteRoom): ");
            String roomType = scanner.nextLine().trim();

            System.out.print("Enter room number: ");
            int roomNumber = scanner.nextInt();

            System.out.print("Enter floor: ");
            int floor = scanner.nextInt();

            System.out.print("Enter price: ");
            double price = scanner.nextDouble();
            scanner.nextLine(); // Consume newline

            Room room = null; // Declare the room object

            switch (roomType) {
                case "StandardRoom":
                    room = new StandardRoom(roomNumber, floor, "Single", price);
                    break;
                case "DeluxeRoom":
                    System.out.print("Enter balcony size (in sq.m): ");
                    double balconySize = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline

                    System.out.print("Enter view (e.g., Sea View, Mountain View): ");
                    String view = scanner.nextLine();

                    room = new DeluxeRoom(roomNumber, floor, "Double", price, balconySize, view);
                    break;
                case "SuiteRoom":
                    System.out.print("Enter living area size (in sq.m): ");
                    double livingArea = scanner.nextDouble();

                    System.out.print("Enter number of bathrooms: ");
                    int bathrooms = scanner.nextInt();

                    System.out.print("Does it have a kitchenette? (true/false): ");
                    boolean hasKitchenette = scanner.nextBoolean();
                    scanner.nextLine(); // Consume newline

                    room = new SuiteRoom(roomNumber, floor, "Triple", price, livingArea, bathrooms, hasKitchenette);
                    break;
                default:
                    System.out.println("Invalid room type. Please enter one of: StandardRoom, DeluxeRoom, SuiteRoom.");
                    return; // Exit the method if the room type is invalid
            }

            // Add the room to the system
            hotelsystem.addRoom(room);
            System.out.println("Room added successfully!");

        } catch (Exception e) {
            System.out.println("An error occurred while adding the room. Please try again.");
            scanner.nextLine(); // Clear buffer
        }
    }


    private static void removeRoom() {
        try {
            System.out.print("Enter room number to remove: ");
            int roomNumber = scanner.nextInt();
            if (hotelsystem.removeRoom(roomNumber)) {
                System.out.println("Room removed successfully.");
            } else {
                System.out.println("Room not found.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred. Please try again.");
            scanner.nextLine(); // Clear buffer
        }
    }

    private static void listRoomsByNumber() {
        List<Room> rooms = hotelsystem.listRoomsByNumber();
        System.out.println("Rooms sorted by number:");
        rooms.forEach(room -> System.out.println(room.getRoomDetails()));
    }

    private static void listRoomsByFloor() {
        List<Room> rooms = hotelsystem.listRoomsByFloor();
        System.out.println("Rooms sorted by floor:");
        rooms.forEach(room -> System.out.println(room.getRoomDetails()));
    }

    private static void generateBookingReport() {
        try {
            System.out.print("Enter start date (yyyy-mm-dd): ");
            LocalDate start = LocalDate.parse(scanner.nextLine());
            System.out.print("Enter end date (yyyy-mm-dd): ");
            LocalDate end = LocalDate.parse(scanner.nextLine());
            String report = hotelsystem.generateBookingReport(start, end);
            System.out.println("Booking Report:");
            System.out.println(report);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use yyyy-mm-dd.");
        }
    }
}
