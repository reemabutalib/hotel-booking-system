package hotelbookingmanagement;

import java.time.LocalDate;
import java.util.List;

public interface HotelManager {
    /**
     * Adds a new room to the system.
     * 
     * @param room The room to be added.
     */
    void addRoom(Room room);

    /**
     * Removes an existing room from the system using its room number.
     * 
     * @param roomNumber The number of the room to be removed.
     * @return true if the room was successfully removed, false otherwise.
     */
    boolean removeRoom(int roomNumber);

    /**
     * Lists all registered rooms, sorted by room number in ascending order.
     * 
     * @return A list of rooms sorted by room number.
     */
    List<Room> listRoomsByNumber();

    /**
     * Lists all registered rooms, sorted by floor number with the top floor displayed first.
     * 
     * @return A list of rooms sorted by floor (descending order).
     */
    List<Room> listRoomsByFloor();

    /**
     * Generates a report of all room bookings within a given timeframe.
     * The report includes room information and details of the customers who booked the rooms.
     * 
     * @param startDate The start date of the timeframe.
     * @param endDate   The end date of the timeframe.
     * @return A string containing the booking report.
     */
    String generateBookingReport(LocalDate startDate, LocalDate endDate);
}
