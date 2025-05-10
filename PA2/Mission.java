/**
 * Abstract class representing a generic mission.
 */
abstract class Mission {

   /** Duration of the mission in hours. */
   protected double duration;

   /** Latitude coordinate for the mission. */
   protected double latitude;

   /** Longitude coordinate for the mission. */
   protected double longitude;

   /** Expected temperature during the mission in degrees Celsius. */
   protected double missionTemperature;

   /**
    * Starts the mission. Must be implemented by subclasses.
    */
   public abstract void startMission();

   /**
    * Retrieves the mission report. Must be implemented by subclasses.
    *
    * @return A string containing the mission report.
    */
   public abstract String getMissionReport();

   // Getter and setter methods for encapsulation

   /**
    * Gets the duration of the mission.
    *
    * @return The mission duration in hours.
    */
   public double getDuration() {
      return duration;
   }

   /**
    * Sets the duration of the mission.
    *
    * @param duration The mission duration in hours.
    */
   public void setDuration(double duration) {
      this.duration = duration;
   }

   /**
    * Gets the latitude coordinate of the mission.
    *
    * @return The latitude coordinate.
    */
   public double getLatitude() {
      return latitude;
   }

   /**
    * Sets the latitude coordinate of the mission.
    *
    * @param latitude The latitude coordinate.
    */
   public void setLatitude(double latitude) {
      this.latitude = latitude;
   }

   /**
    * Gets the longitude coordinate of the mission.
    *
    * @return The longitude coordinate.
    */
   public double getLongitude() {
      return longitude;
   }

   /**
    * Sets the longitude coordinate of the mission.
    *
    * @param longitude The longitude coordinate.
    */
   public void setLongitude(double longitude) {
      this.longitude = longitude;
   }

   /**
    * Gets the mission temperature.
    *
    * @return The expected mission temperature in degrees Celsius.
    */
   public double getMissionTemperature() {
      return missionTemperature;
   }

   /**
    * Sets the mission temperature.
    *
    * @param missionTemperature The expected mission temperature in degrees Celsius.
    */
   public void setMissionTemperature(double missionTemperature) {
      this.missionTemperature = missionTemperature;
   }
}

/**
 * Class representing an Explore Mission.
 */
class ExploreMission extends Mission {

   /**
    * Constructs an ExploreMission with the specified parameters.
    *
    * @param latitude          The latitude coordinate for the mission.
    * @param longitude         The longitude coordinate for the mission.
    * @param duration          The duration of the mission in hours.
    * @param missionTemperature The expected mission temperature in degrees Celsius.
    */
   public ExploreMission(double latitude, double longitude, double duration, double missionTemperature) {
      this.latitude = latitude;
      this.longitude = longitude;
      this.duration = duration;
      this.missionTemperature = missionTemperature;
   }

   /**
    * Starts the Explore mission.
    */
   @Override
   public void startMission() {
      System.out.println("Explore mission is started");
   }

   /**
    * Retrieves the Explore mission report.
    *
    * @return A string containing the mission report.
    */
   @Override
   public String getMissionReport() {
      return "Explore rescue mission is ended";
   }
}

/**
 * Class representing a Satellite Mission.
 */
class SatelliteMission extends Mission {

   /**
    * Constructs a SatelliteMission with the specified parameters.
    *
    * @param latitude          The latitude coordinate for the mission.
    * @param longitude         The longitude coordinate for the mission.
    * @param duration          The duration of the mission in hours.
    * @param missionTemperature The expected mission temperature in degrees Celsius.
    */
   public SatelliteMission(double latitude, double longitude, double duration, double missionTemperature) {
      this.latitude = latitude;
      this.longitude = longitude;
      this.duration = duration;
      this.missionTemperature = missionTemperature;
   }

   /**
    * Starts the Satellite mission.
    */
   @Override
   public void startMission() {
      System.out.println("Satellite mission is started");
   }

   /**
    * Retrieves the Satellite mission report.
    *
    * @return A string containing the mission report.
    */
   @Override
   public String getMissionReport() {
      return "Explore rescue mission is ended";
   }
}

/**
 * Class representing a Supply Mission.
 */
class SupplyMission extends Mission {

   /**
    * Constructs a SupplyMission with the specified parameters.
    *
    * @param latitude          The latitude coordinate for the mission.
    * @param longitude         The longitude coordinate for the mission.
    * @param duration          The duration of the mission in hours.
    * @param missionTemperature The expected mission temperature in degrees Celsius.
    */
   public SupplyMission(double latitude, double longitude, double duration, double missionTemperature) {
      this.latitude = latitude;
      this.longitude = longitude;
      this.duration = duration;
      this.missionTemperature = missionTemperature;
   }

   /**
    * Starts the Supply mission.
    */
   @Override
   public void startMission() {
      System.out.println("Supply mission is started");
   }

   /**
    * Retrieves the Supply mission report.
    *
    * @return A string containing the mission report.
    */
   @Override
   public String getMissionReport() {
      return "Explore rescue mission is ended";
   }
}
