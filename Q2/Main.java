import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Main class that drives the program, loads menu, processes orders, and prints the orders for each table.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        // Ensure two file arguments are passed (menu.txt and orders.txt)
        if (args.length < 2) {
            System.out.println("Usage: java Main menu.txt orders.txt");
            return;
        }

        String menuFile = args[0];  // Menu file
        String ordersFile = args[1];  // Orders file

        // Create a MenuManager instance to load and manage the menu items
        MenuManager menuManager = new MenuManager();
        menuManager.loadMenu(menuFile);

        // Create an OrderManager instance to process the orders
        OrderManager orderManager = new OrderManager(menuManager);
        orderManager.processOrders(ordersFile);

        // Print the orders for each table
        orderManager.printOrders();
    }
}

/**
 * Manages the restaurant menu and allows fetching items by their code.
 */
class MenuManager {
    private Restaurant[] menu;  // Array of restaurant menu items
    private int menuSize;  // Size of the menu

    /**
     * Constructor to initialize the menu with a size of 100.
     */
    public MenuManager() {
        this.menu = new Restaurant[100];  // Initial size of 100 for simplicity
        this.menuSize = 0;
    }

    /**
     * Loads menu items from a file and populates the menu array.
     * @param menuFile the file path of the menu to load
     * @throws IOException if an I/O error occurs while reading the file
     */
    public void loadMenu(String menuFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(menuFile));
        String line;
        while ((line = reader.readLine()) != null) {
            // Split the line by commas
            String[] parts = line.split(",");
            String code = parts[1];
            String name = parts[2];
            double price = Double.parseDouble(parts[3]);
            double calories = Double.parseDouble(parts[4]);

            // Create a specific restaurant item based on the type of item (e.g., Soup, Salad)
            Restaurant item;
            switch (parts[0].trim()) {
                case "Soup":
                    item = new Soup(code, name, price, calories);
                    break;
                case "Salad":
                    item = new Salad(code, name, price, calories);
                    break;
                case "MainCourse":
                    item = new MainCourse(code, name, price, calories);
                    break;
                case "Dessert":
                    item = new Dessert(code, name, price, calories);
                    break;
                case "Beverage":
                    item = new Beverage(code, name, price, calories);
                    break;
                default:
                    continue;  // Skip unknown item types
            }
            menu[menuSize++] = item;  // Add the item to the menu array
        }
        reader.close();
    }

    /**
     * Retrieves a menu item based on its code.
     * @param code the item code
     * @return the menu item corresponding to the code, or null if not found
     */
    public Restaurant getMenuItem(String code) {
        for (int i = 0; i < menuSize; i++) {
            if (menu[i].getCode().equals(code)) {
                return menu[i];
            }
        }
        return null;
    }
}

/**
 * Manages the orders from different tables and processes orders based on menu codes.
 */
class OrderManager {
    private Table[] tables;  // Array of tables
    private int tableSize;  // Number of tables
    private MenuManager menuManager;  // The menu manager to fetch menu items

    /**
     * Constructor to initialize the order manager.
     * @param menuManager the MenuManager instance
     */
    public OrderManager(MenuManager menuManager) {
        this.menuManager = menuManager;
        this.tables = new Table[10];  // Initial size for simplicity
        this.tableSize = 0;
    }

    /**
     * Processes orders from a file and adds items to the corresponding table orders.
     * @param ordersFile the file containing the orders to process
     * @throws IOException if an I/O error occurs while reading the file
     */
    public void processOrders(String ordersFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(ordersFile));
        String line;

        // Process each order line
        while ((line = reader.readLine()) != null) {
            // Split the line into table name and item codes
            String[] parts = line.split(" ", 2);  // Split at the first space
            String tableName = parts[0] + " " + parts[1]; // Keep "Table x"
            String[] itemCodes = parts[1].substring(parts[1].indexOf(" ") + 1).split(",");  // Split at commas

            // Retrieve or create the table for the given table name
            Table table = getTableByName(tableName);
            if (table == null) {
                table = new Table(tableName);
                tables[tableSize++] = table;  // Add to tables array
            }

            // Process each item code
            for (String code : itemCodes) {
                code = code.trim();  // Remove leading/trailing whitespace

                // Retrieve the menu item using the code
                Restaurant item = menuManager.getMenuItem(code);

                if (item != null) {
                    table.getOrder(item);  // Add the item to the table's order
                } else {
                    System.out.println("Unknown item code: " + code);  // Handle unknown item codes
                }
            }
        }
        reader.close();
    }

    /**
     * Retrieves a table based on its name.
     * @param tableName the name of the table
     * @return the Table instance corresponding to the table name, or null if not found
     */
    private Table getTableByName(String tableName) {
        for (int i = 0; i < tableSize; i++) {
            if (tables[i].getTableName().equals(tableName)) {
                return tables[i];
            }
        }
        return null;
    }

    /**
     * Prints the orders for each table.
     */
    public void printOrders() {
        for (int i = 0; i < tableSize; i++) {
            tables[i].printTable();
        }
    }
}
