package hotelbookingmanagement;

import java.time.LocalDate;
import java.util.List;

public interface HotelCustomer {
   
    /**
     * Lists all available rooms in a specified timeframe, sorted by price
     * (lowest to highest).
     *
     * @param start The start date of the timeframe.
     * @param end The end date of the timeframe.
     * @return A list of available rooms.
     */
    List<Room> listAvailableRooms(LocalDate start, LocalDate end);


    /**
     * Lists all available rooms matching a specific type and occupancy in a given timeframe.
     * 
     * @param type      The type of room (e.g., "SuiteRoom").
     * @param occupancy The room occupancy (e.g., "double").
     * @param start     The start date of the timeframe.
     * @param end       The end date of the timeframe.
     * @return A list of rooms that match the criteria.
     */
    List<Room> listRoomsByCriteria(String type, String occupancy, LocalDate start, LocalDate end);

    /**
     * Books a room for a customer in a specified timeframe.
     * 
     * @param room       The room to be booked.
     * @param customer   The customer making the booking.
     * @param start      The check-in date.
     * @param end        The check-out date.
     * @return The booking object if successful.
     * @throws Exception If the room is unavailable or booking fails.
     */
    Booking bookRoom(Room room, Customer customer, LocalDate start, LocalDate end) throws Exception;

    /**
     * Deletes an existing booking using the booking ID.
     * 
     * @param bookingID The unique identifier for the booking.
     * @return true if the booking was successfully deleted; false otherwise.
     */
    boolean deleteBooking(String bookingID);
}

    

