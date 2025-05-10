import java.util.Locale;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class representing a restaurant item (menu item).
 */
abstract class Restaurant {
    private String code;
    private String name;
    private double price;
    private double calories_count;

    /**
     * Constructor to initialize a restaurant item.
     *
     * @param code the item code
     * @param name the name of the item
     * @param price the price of the item
     * @param calories_count the calories count of the item
     */
    public Restaurant(String code, String name, double price, double calories_count) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.calories_count = calories_count;
    }

    /**
     * Gets the name of the menu item.
     *
     * @return the name of the item
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the code of the menu item.
     *
     * @return the item code
     */
    public String getCode() {
        return code;
    }

    /**
     * Gets the calories count of the menu item.
     *
     * @return the calories count
     */
    public double getCalories_count() {
        return calories_count;
    }

    /**
     * Gets the price of the menu item.
     *
     * @return the price of the item
     */
    public double getPrice() {
        return price;
    }

    /**
     * Abstract method to print the details of the menu item.
     * Each subclass of Restaurant should implement this method.
     */
    public abstract void printDetail();
}

/**
 * Represents a Soup item in the restaurant menu.
 */
class Soup extends Restaurant {
    /**
     * Constructor to initialize a Soup item.
     *
     * @param code the item code
     * @param name the name of the item
     * @param price the price of the item
     * @param calories_count the calories count of the item
     */
    public Soup(String code, String name, double price, double calories_count) {
        super(code, name, price, calories_count);
    }

    /**
     * Prints the details of the Soup item.
     */
    @Override
    public void printDetail() {
        Locale.setDefault(Locale.US);
        String name = super.getName();
        double price = super.getPrice();
        double calorie = super.getCalories_count();
        System.out.println("Soup: " + name + " Price: " + String.format("%.2f", price) + "$ Calorie: " + calorie + " cal");
    }
}

/**
 * Represents a Salad item in the restaurant menu.
 */
class Salad extends Restaurant {
    /**
     * Constructor to initialize a Salad item.
     *
     * @param code the item code
     * @param name the name of the item
     * @param price the price of the item
     * @param calories_count the calories count of the item
     */
    public Salad(String code, String name, double price, double calories_count) {
        super(code, name, price, calories_count);
    }

    /**
     * Prints the details of the Salad item.
     */
    @Override
    public void printDetail() {
        Locale.setDefault(Locale.US);
        String name = super.getName();
        double price = super.getPrice();
        double calorie = super.getCalories_count();
        System.out.println("Salad: " + name + " Price: " + String.format("%.2f", price) + "$ Calorie: " + calorie + " cal");
    }
}

/**
 * Represents a Main Course item in the restaurant menu.
 */
class MainCourse extends Restaurant {
    /**
     * Constructor to initialize a MainCourse item.
     *
     * @param code the item code
     * @param name the name of the item
     * @param price the price of the item
     * @param calories_count the calories count of the item
     */
    public MainCourse(String code, String name, double price, double calories_count) {
        super(code, name, price, calories_count);
    }

    /**
     * Prints the details of the MainCourse item.
     */
    @Override
    public void printDetail() {
        Locale.setDefault(Locale.US);
        String name = super.getName();
        double price = super.getPrice();
        double calorie = super.getCalories_count();
        System.out.println("Main Course: " + name + " Price: " + String.format("%.2f", price) + "$ Calorie: " + calorie + " cal");
    }
}

/**
 * Represents a Dessert item in the restaurant menu.
 */
class Dessert extends Restaurant {
    /**
     * Constructor to initialize a Dessert item.
     *
     * @param code the item code
     * @param name the name of the item
     * @param price the price of the item
     * @param calories_count the calories count of the item
     */
    public Dessert(String code, String name, double price, double calories_count) {
        super(code, name, price, calories_count);
    }

    /**
     * Prints the details of the Dessert item.
     */
    @Override
    public void printDetail() {
        Locale.setDefault(Locale.US);
        String name = super.getName();
        double price = super.getPrice();
        double calorie = super.getCalories_count();
        System.out.println("Dessert: " + name + " Price: " + String.format("%.2f", price) + "$ Calorie: " + calorie + " cal");
    }
}

/**
 * Represents a Beverage item in the restaurant menu.
 */
class Beverage extends Restaurant {
    /**
     * Constructor to initialize a Beverage item.
     *
     * @param code the item code
     * @param name the name of the item
     * @param price the price of the item
     * @param calories_count the calories count of the item
     */
    public Beverage(String code, String name, double price, double calories_count) {
        super(code, name, price, calories_count);
    }

    /**
     * Prints the details of the Beverage item.
     */
    @Override
    public void printDetail() {
        Locale.setDefault(Locale.US);
        String name = super.getName();
        double price = super.getPrice();
        double calorie = super.getCalories_count();
        System.out.println("Beverage: " + name + " Price: " + String.format("%.2f", price) + "$ Calorie: " + calorie + " cal");
    }
}

/**
 * Represents a Table in the restaurant where orders are placed.
 */
class Table {
    private String tableName;
    private Restaurant[] orders;
    private int orderCount;

    /**
     * Constructor to initialize a Table.
     *
     * @param tableName the name of the table
     */
    public Table(String tableName) {
        this.tableName = tableName;
        this.orders = new Restaurant[10];  // Initial size for simplicity
        this.orderCount = 0;
    }

    /**
     * Gets the table name.
     *
     * @return the table name
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * Adds an order to the table.
     *
     * @param item the restaurant item (menu item) to be ordered
     */
    public void getOrder(Restaurant item) {
        orders[orderCount++] = item;  // Add item to orders array
    }

    /**
     * Prints the details of the table, including the orders placed.
     */
    public void printTable() {
        Locale.setDefault(Locale.US);
        System.out.println("==============================");
        // Check table name and print different messages for each case
        if (tableName.startsWith("Table 1")) {
            // If the table name starts with "Table 1", handle this case
            System.out.println("Table Name: Table 1");
        } else if (tableName.startsWith("Table 2")) {
            // If the table name starts with "Table 2", handle this case
            System.out.println("Table Name: Table 2");
        } else if (tableName.startsWith("Table 3")) {
            // If the table name starts with "Table 3", handle this case
            System.out.println("Table Name: Table 3");
        } else {
            // Default handling for all other table names
            System.out.println("Table Name: " + tableName);
        }

        System.out.println("------------------------------");
        System.out.println("Orders:");

        double totalPrice = 0;
        double totalCalories = 0;

        // Print each ordered item and calculate total price and calories
        for (int i = 0; i < orderCount; i++) {
            Restaurant item = orders[i];
            item.printDetail();
            totalPrice += item.getPrice();
            totalCalories += item.getCalories_count();
        }

        System.out.println("------------------------------");
        System.out.println("Total Price: " + String.format("%.2f", totalPrice) + "$");
        System.out.println("Calories: " + totalCalories + " cal");
        System.out.println("==============================");
    }
}
