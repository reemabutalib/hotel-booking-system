package hotelbookingmanagement;

/**
 * Custom exception class for handling booking-related errors.
 */
public class BookingException extends Exception {

    /**
     * Default constructor for BookingException.
     */
    public BookingException() {
        super("A booking error occurred.");
    }

    /**
     * Constructor for BookingException with a custom message.
     * @param message The detailed error message explaining the booking failure.
     */
    public BookingException(String message) {
        super(message);
    }
}
