/**
 * Abstract class representing a generic spacecraft system.
 */
abstract class SpacecraftSystem {

    /**
     * Prints the status message for the system.
     */
    public abstract void print();
}

/**
 * Represents the Propulsion System of a spacecraft.
 */
class PropulsionSystem extends SpacecraftSystem {

    /** Total fuel capacity of the system. */
    private double totalFuel;

    /** Fuel consumption rate per kilometer. */
    private double fuelRatePerKm;

    /** Current amount of fuel available. */
    public double currentFuel;

    /** Expected fuel required for the mission. */
    public double expectedFuel;

    /**
     * Constructs a PropulsionSystem with specified total fuel and fuel rate per kilometer.
     *
     * @param totalFuel    Total fuel capacity.
     * @param fuelRatePerKm Fuel consumption rate per kilometer.
     */
    public PropulsionSystem(double totalFuel, double fuelRatePerKm) {
        this.totalFuel = totalFuel;
        this.fuelRatePerKm = fuelRatePerKm;
        this.currentFuel = totalFuel;
    }

    /**
     * Calculates the expected fuel consumption for a given distance.
     *
     * @param distance    Distance to travel in kilometers.
     * @param fuelRatePerKm Fuel consumption rate per kilometer.
     * @return The expected fuel consumption.
     */
    public double calculateExpectedFuel(double distance, double fuelRatePerKm) {
        expectedFuel = (distance * this.fuelRatePerKm);
        return expectedFuel;
    }

    /**
     * Displays the propulsion system status report.
     *
     * @param expectedFuel Expected fuel consumption.
     * @param totalFuel    Total fuel capacity.
     */
    public void statusReport(double expectedFuel, double totalFuel) {
        double fuelPercentage = (this.totalFuel - expectedFuel);
        System.out.println("Propulsion System Status: Operating at " + String.format("%.1f", fuelPercentage) + "%" + " fuel" + "\n" +
                "Fuel Level: " + fuelPercentage);
    }

    /**
     * Prints a message indicating that the propulsion system is active and ready.
     */
    public void print() {
        System.out.println("Propulsion System is now active.\n" +
                "Propulsion system is ready for launch.");
    }

    /**
     * Checks if the available fuel is sufficient for the mission.
     *
     * @param expectedFuel The expected fuel consumption.
     * @return True if there is enough fuel; false otherwise.
     */
    public boolean compareFuel(double expectedFuel) {
        return (expectedFuel <= totalFuel);
    }
}

/**
 * Represents the Navigation System of a spacecraft.
 */
class NavigationSystem extends SpacecraftSystem {

    /** Current latitude of the spacecraft. */
    private double currentLatitude;

    /** Current longitude of the spacecraft. */
    private double currentLongitude;

    /** Distance to the target destination. */
    public double distance;

    /**
     * Constructs a NavigationSystem with specified current latitude and longitude.
     *
     * @param currentLatitude  Current latitude coordinate.
     * @param currentLongitude Current longitude coordinate.
     */
    public NavigationSystem(double currentLatitude, double currentLongitude) {
        this.currentLatitude = currentLatitude;
        this.currentLongitude = currentLongitude;
    }

    /**
     * Calculates the great-circle distance between two points using the Haversine formula.
     *
     * @param currentLatitude  Current latitude coordinate.
     * @param currentLongitude Current longitude coordinate.
     * @param targetLatitude   Target latitude coordinate.
     * @param targetLongitude  Target longitude coordinate.
     * @return The distance between the two points in kilometers.
     */
    double Haversine(double currentLatitude, double currentLongitude, double targetLatitude, double targetLongitude) {
        final int R = 6371; // Radius of the Earth in kilometers
        double latDistance = Math.toRadians(targetLatitude - this.currentLatitude);
        double lonDistance = Math.toRadians(targetLongitude - this.currentLongitude);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(Math.toRadians(this.currentLatitude)) * Math.cos(Math.toRadians(targetLatitude)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // Distance in kilometers
    }

    /**
     * Displays the navigation system status report.
     *
     * @param currentLatitude  Current latitude coordinate.
     * @param currentLongitude Current longitude coordinate.
     */
    public void statusReport(double currentLatitude, double currentLongitude) {
        System.out.println("Navigation System Status: Active\n" +
                "Current Latitude: " + currentLatitude + " Current Longitude: " + currentLongitude);
    }

    /**
     * Prints a message indicating that the navigation system is active and stabilizing.
     */
    public void print() {
        System.out.println("Navigation System is now active.\n" +
                "Navigation system activated and stabilizing environment.");
    }
}

/**
 * Represents the Life Support System of a spacecraft.
 */
class LifeSupportSystem extends SpacecraftSystem {

    /** Current temperature in degrees Celsius. */
    public double temperature;

    /** Current oxygen level percentage. */
    private double oxygenLevel;

    /** Coefficient affecting oxygen consumption. */
    private double oxygenCoeff;

    /** Expected oxygen consumption for the mission. */
    public double expectedOxy;

    /**
     * Constructs a LifeSupportSystem with specified temperature, oxygen level, and oxygen coefficient.
     *
     * @param temperature Current temperature in degrees Celsius.
     * @param oxygenLevel Current oxygen level percentage.
     * @param oxygenCoeff Oxygen consumption coefficient.
     */
    public LifeSupportSystem(double temperature, double oxygenLevel, double oxygenCoeff) {
        this.temperature = temperature;
        this.oxygenLevel = oxygenLevel;
        this.oxygenCoeff = oxygenCoeff;
    }

    /**
     * Calculates the expected oxygen consumption based on mission conditions.
     *
     * @param temperature   Current temperature in degrees Celsius.
     * @param missionTemp   Target mission temperature in degrees Celsius.
     * @param duration      Duration of the mission in hours.
     * @return The expected oxygen consumption.
     */
    public double calculateOxy(double temperature, double missionTemp, double duration) {
        double tempDiff = Math.abs(this.temperature - missionTemp);
        expectedOxy = tempDiff * duration * this.oxygenCoeff;
        return expectedOxy;
    }

    /**
     * Checks if the available oxygen is sufficient for the mission.
     *
     * @param temperature Current temperature in degrees Celsius.
     * @param missionTemp Target mission temperature in degrees Celsius.
     * @param duration    Duration of the mission in hours.
     * @return True if there is enough oxygen; false otherwise.
     */
    public boolean calculateOxygenConsumption(double temperature, double missionTemp, double duration) {
        double tempDiff = Math.abs(temperature - missionTemp);
        expectedOxy = tempDiff * duration * oxygenCoeff;
        return (expectedOxy <= oxygenLevel);
    }

    /**
     * Displays the life support system status report.
     *
     * @param oxygenLevel Current oxygen level percentage.
     * @param expectedOxy Expected oxygen consumption.
     * @param temperature Current temperature in degrees Celsius.
     */
    public void statusReport(double oxygenLevel, double expectedOxy, double temperature) {
        System.out.printf("Life Support System Status: Oxygen Level: %.1f%%, Temperature: %.1f\u00B0C%n",
                (oxygenLevel - expectedOxy), temperature);
    }

    /**
     * Prints a message indicating that the life support system is active and stabilizing.
     */
    public void print() {
        System.out.println("Life Support System is now active.\n" +
                "Life support system activated and stabilizing environment.");
    }
}
