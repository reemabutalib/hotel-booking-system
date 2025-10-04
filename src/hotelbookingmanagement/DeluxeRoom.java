package hotelbookingmanagement;

public class DeluxeRoom extends Room {
    private double balconySize;
    private String view;

    // Constructor
    public DeluxeRoom(int roomNumber, int floor, String occupancy, double price, double balconySize, String view) {
    super(roomNumber, floor, "Double", occupancy, price); // Use "Double" as a valid type
    setBalconySize(balconySize);
    setView(view);
}

    @Override
    public String getRoomDetails() {
        return super.getRoomDetails()
                + "\n  Balcony Size: " + balconySize + " sq.m."
                + "\n  View: " + view;
    }

    public double getBalconySize() {
        return balconySize;
    }

    public void setBalconySize(double balconySize) {
        if (balconySize < 0) {
            throw new IllegalArgumentException("Balcony size cannot be negative.");
        }
        this.balconySize = balconySize;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        if (view == null || view.trim().isEmpty()) {
            throw new IllegalArgumentException("View cannot be null or empty.");
        }
        this.view = view.trim();
    }
}
