package hotelbookingmanagement;

/**
 * Interface for entities that can determine if they overlap with other entities.
 * Typically used for checking conflicts, such as overlapping bookings.
 */
public interface Overlappable {

    /**
     * Checks whether the current entity overlaps with another booking.
     * @param other The booking to check for overlap with the current entity.
     * @return true if the bookings overlap; false otherwise.
     */
    boolean overlaps(Booking other);
}
