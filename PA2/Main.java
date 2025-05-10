import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Main class responsible for running the spacecraft mission simulation.
 */
public class Main {

    /**
     * Reads a text file and parses its contents into a list of string arrays.
     *
     * @param input Path to the input text file.
     * @return A list of string arrays where each array represents a row of the file.
     */
    public static List<String[]> readTxt(String input) {
        List<String[]> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(input))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                data.add(parts); // Store each row's values in the list
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    /**
     * Main method that initializes and runs the spacecraft systems and mission.
     *
     * @param args Command-line arguments:
     *             1. Path to the input file.
     *             2. Spacecraft name.
     *             3. Total available energy.
     */
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        if (args.length == 3) {
            String inputFile = args[0];
            String spacecraftName = args[1];
            int totalEnergy = Integer.parseInt(args[2]);

            List<String[]> data = readTxt(inputFile);
            double energyCons = 0, fuelLevel = 0, consPerKm = 0;
            double energyCons2 = 0, temperature = 0, oxygenLevel = 0, oxygenCoeff = 0;
            double energyCons3 = 0, startLatitude = 0, startLongitude = 0;
            double latitude = 0, longitude = 0, duration = 0, temp = 0;

            // Parsing data from the input file
            for (String[] row : data) {
                if (row.length < 2) continue; // Skip incomplete rows

                String identifier = row[0];
                switch (identifier) {
                    case "propulSys":
                        energyCons = Double.parseDouble(row[1]);
                        fuelLevel = Double.parseDouble(row[2]);
                        consPerKm = Double.parseDouble(row[3]);
                        break;

                    case "supportSys":
                        energyCons2 = Double.parseDouble(row[1]);
                        temperature = Double.parseDouble(row[2]);
                        oxygenLevel = Double.parseDouble(row[3]);
                        oxygenCoeff = Double.parseDouble(row[4]);
                        break;

                    case "navSys":
                        energyCons3 = Double.parseDouble(row[1]);
                        startLatitude = Double.parseDouble(row[2]);
                        startLongitude = Double.parseDouble(row[3]);
                        break;

                    case "exploreMission":
                        latitude = Double.parseDouble(row[1]);
                        longitude = Double.parseDouble(row[2]);
                        duration = Double.parseDouble(row[3]);
                        temp = Double.parseDouble(row[4]);
                        break;
                }
            }

            System.out.println("Propulsion System is now active.");
            System.out.println("Propulsion system is ready for launch.");
            System.out.println("Life Support System is now active.");
            System.out.println("Life support system activated and stabilizing environment.");
            System.out.println("Navigation System is now active.");
            System.out.println("Navigation activated and stabilizing environment.");
            System.out.println("All systems active");

            if (totalEnergy >= energyCons + energyCons2 + energyCons3) {
                LifeSupportSystem lifeSup = new LifeSupportSystem(temperature, oxygenLevel, oxygenCoeff);
                double expectedOxy = lifeSup.calculateOxy(temperature, temp, duration);
                boolean isEnoughOxy = lifeSup.calculateOxygenConsumption(temperature, temp, duration);

                NavigationSystem navigate = new NavigationSystem(startLatitude, startLongitude);
                double distance = navigate.Haversine(startLatitude, startLongitude, latitude, longitude);

                PropulsionSystem propulsion = new PropulsionSystem(fuelLevel, consPerKm);
                double newexfuel = propulsion.calculateExpectedFuel(distance, consPerKm);
                boolean isEnoughFuel = propulsion.compareFuel(newexfuel);

                if (!isEnoughFuel) {
                    System.out.println("Cannot perform spaceflight, fuel level is not enough!");
                    standbyMessage();
                    System.out.println("Explore mission start failed!");
                } else if (!isEnoughOxy) {
                    System.out.println("Cannot perform spaceflight, oxygen level is not enough!");
                    standbyMessage();
                    System.out.println("Explore mission start failed!");
                } else {
                    System.out.println("Explore mission is started");
                    System.out.println("Spacecraft Status: " + spacecraftName);
                    propulsion.statusReport(newexfuel, totalEnergy);
                    lifeSup.statusReport(oxygenLevel, expectedOxy, temp);
                    navigate.statusReport(startLatitude, startLongitude);
                    System.out.println("Explore rescue mission is ended");
                }
            } else {
                System.out.println("System cannot be initialized.");
            }
        } else {
            System.out.println("Please enter 3 arguments in the correct order.");
        }
    }

    /**
     * Displays the system standby message.
     */
    private static void standbyMessage() {
        System.out.println("Propulsion System is now standby.");
        System.out.println("Life Support System is now standby.");
        System.out.println("Navigation System is now standby.");
    }
}
