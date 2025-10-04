package hotelbookingmanagement;

public class SuiteRoom extends Room {

    private double livingAreaSize;
    private int numberOfBathrooms;
    private boolean hasKitchenette;

    // Constructor
    public SuiteRoom(int roomNumber, int floor, String occupancy, double price, double livingArea, int numberOfBathrooms, boolean hasKitchenette) {
    super(roomNumber, floor, "Suite", occupancy, price); // Use "Suite" as a valid type
    setLivingAreaSize(livingArea);
    setNumberOfBathrooms(numberOfBathrooms);
    setHasKitchenette(hasKitchenette);
}


    public double getLivingAreaSize() {
        return livingAreaSize;
    }

    public void setLivingAreaSize(double livingAreaSize) {
        if (livingAreaSize <= 0) {
            throw new IllegalArgumentException("Living area size must be positive.");
        }
        this.livingAreaSize = livingAreaSize;
    }

    public int getNumberOfBathrooms() {
        return numberOfBathrooms;
    }

    public void setNumberOfBathrooms(int numberOfBathrooms) {
        if (numberOfBathrooms <= 0) {
            throw new IllegalArgumentException("Number of bathrooms must be at least 1.");
        }
        this.numberOfBathrooms = numberOfBathrooms;
    }

    public boolean isHasKitchenette() {
        return hasKitchenette;
    }

    public void setHasKitchenette(boolean hasKitchenette) {
        this.hasKitchenette = hasKitchenette;
    }

    @Override
    public String getRoomDetails() {
        return super.getRoomDetails()
                + "\n  Living Area Size: " + livingAreaSize + " sq.m."
                + "\n  Number of Bathrooms: " + numberOfBathrooms
                + "\n  Has Kitchenette: " + (hasKitchenette ? "Yes" : "No");
    }


}
