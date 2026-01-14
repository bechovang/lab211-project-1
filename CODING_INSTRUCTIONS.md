# Step-by-Step Coding Instructions

## Order of Coding (Follow this sequence for fastest results)

### Step 1: Create Folder Structure (2 minutes)
```bash
# In NetBeans: File → New Project → Java Application
# Project name: TraditionalFeastOrderManagement

# Create packages:
src/com/feast/model
src/com/feast/business
src/com/feast/tools
src/com/feast/dispatcher

# Create data folder:
data/
```

### Step 2: Create Acceptable Interface (5 minutes)
**File:** `tools/Acceptable.java`

**Key Patterns to Remember:**
```java
// Customer ID: C/c/G/g/K/k + 4 digits
String CUS_ID_VALID = "^[CcGgKk]\\d{4}$";

// Name: 2-25 characters
String NAME_VALID = "^.{2,25}$";

// Phone: 0 + 9 digits
String PHONE_VALID = "^0\\d{9}$";

// Email: standard format
String EMAIL_VALID = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";

// Menu ID: PW + 3 digits
String MENU_ID_VALID = "^PW\\d{3}$";

// Positive integer (no leading zero)
String INTEGER_VALID = "^[1-9]\\d*$";

// Positive double
String DOUBLE_VALID = "^[1-9]\\d*(\\.\\d+)?$";

// Static validation method
static boolean isValid(String data, String pattern) {
    return data.matches(pattern);
}
```

### Step 3: Create Inputter Class (10 minutes)
**File:** `tools/Inputter.java`

**Template for all input methods:**
```java
// Pattern: Loop → Print → Read → Validate → Return/Repeat

public String getString(String mess) {
    while (true) {
        System.out.print(mess);
        String result = scanner.nextLine().trim();
        if (!result.isEmpty()) return result;
        System.out.println("Cannot be empty!");
    }
}

public int getInt(String mess) {
    while (true) {
        String input = getString(mess);
        if (Acceptable.isValid(input, Acceptable.INTEGER_VALID))
            return Integer.parseInt(input);
        System.out.println("Invalid!");
    }
}

public String getValidString(String mess, String pattern) {
    while (true) {
        String input = getString(mess);
        if (Acceptable.isValid(input, pattern)) return input;
        System.out.println("Invalid format!");
    }
}
```

### Step 4: Create Model Classes (15 minutes each)

#### Customer.java - Template:
```java
// 1. Implement Serializable
public class Customer implements java.io.Serializable {

    // 2. Private fields
    private String id;
    private String name;
    private String phone;
    private String email;

    // 3. Default constructor
    public Customer() {}

    // 4. Full constructor (use Alt+Insert → Constructor)
    public Customer(String id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    // 5. Getters and Setters (use Alt+Insert → Getter and Setter)
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    // ... repeat for all fields

    // 6. toString() for table display
    public String toString() {
        return String.format("| %-10s | %-25s | %-15s | %-30s |",
                id, name, phone, email);
    }

    // 7. Static header/footer methods
    public static String getHeader() { return "+----+---+..."; }
    public static String getFooter() { return "+----+---+..."; }
}
```

#### SetMenu.java - Same pattern as Customer

#### Order.java - Special additions:
```java
// Auto-generate order code (timestamp-based)
private String generateOrderCode() {
    Date now = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
    return sdf.format(now);
}

public void setOrderCodeWithGeneration() {
    this.orderCode = generateOrderCode();
}

// Override equals() for duplicate detection (Alt+Insert → equals() and hashCode())
// Select: customerId, menuId, eventDate
```

### Step 5: Create FileUtils Class (10 minutes)
**File:** `tools/FileUtils.java`

**Generic Read Template:**
```java
public static <T> List<T> readFromFile(String filePath) {
    List<T> list = new ArrayList<>();
    File file = new File(filePath);
    if (!file.exists()) return list;

    try (FileInputStream fis = new FileInputStream(file);
         ObjectInputStream ois = new ObjectInputStream(fis)) {
        while (fis.available() > 0) {
            @SuppressWarnings("unchecked")
            T obj = (T) ois.readObject();
            list.add(obj);
        }
    } catch (IOException | ClassNotFoundException e) {
        System.err.println("Error: " + e.getMessage());
    }
    return list;
}
```

**Generic Write Template:**
```java
public static <T> void saveToFile(List<T> list, String filePath) {
    try (FileOutputStream fos = new FileOutputStream(filePath);
         ObjectOutputStream oos = new ObjectOutputStream(fos)) {
        for (T item : list) {
            oos.writeObject(item);
        }
    } catch (IOException e) {
        System.err.println("Error: " + e.getMessage());
    }
}
```

### Step 6: Create Business Classes (20 minutes each)

#### Customers.java - Template:
```java
public class Customers extends ArrayList<Customer> {

    private String pathFile;
    private boolean isSaved;

    public Customers() {
        this.pathFile = "data/customers.dat";
        this.isSaved = true;
    }

    // Add with duplicate check
    public void addNew(Customer customer) {
        for (Customer c : this) {
            if (c.getId().equals(customer.getId())) {
                System.out.println("Duplicate ID!");
                return;
            }
        }
        this.add(customer);
        isSaved = false;
        System.out.println("Added!");
    }

    // Update by ID
    public void update(Customer customer) {
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getId().equals(customer.getId())) {
                this.set(i, customer);
                isSaved = false;
                System.out.println("Updated!");
                return;
            }
        }
        System.out.println("Not found!");
    }

    // Search by ID
    public Customer searchById(String id) {
        for (Customer c : this) {
            if (c.getId().equals(id)) return c;
        }
        return null;
    }

    // Filter by name (partial match)
    public List<Customer> filterByName(String name) {
        List<Customer> result = new ArrayList<>();
        for (Customer c : this) {
            if (c.getName().toLowerCase().contains(name.toLowerCase()))
                result.add(c);
        }
        return result;
    }

    // Overloaded showAll - two versions
    public void showAll() {
        if (this.isEmpty()) { System.out.println("Empty!"); return; }
        System.out.println(Customer.getHeader());
        for (Customer c : this) System.out.println(c);
        System.out.println(Customer.getFooter());
    }

    public void showAll(List<Customer> list) {
        if (list.isEmpty()) { System.out.println("Empty!"); return; }
        System.out.println(Customer.getHeader());
        for (Customer c : list) System.out.println(c);
        System.out.println(Customer.getFooter());
    }

    // File operations using FileUtils
    public void readFromFile() {
        List<Customer> list = FileUtils.readFromFile(pathFile);
        this.clear();
        this.addAll(list);
        isSaved = true;
        System.out.println("Loaded " + this.size() + " items.");
    }

    public void saveToFile() {
        FileUtils.saveToFile(new ArrayList<>(this), pathFile);
        isSaved = true;
        System.out.println("Saved!");
    }
}
```

#### Orders.java - Difference from Customers:
```java
// Use HashSet instead of ArrayList
public class Orders extends HashSet<Order> {

    // Duplicate check using contains() (uses equals())
    public void addNew(Order order) {
        if (this.contains(order)) {
            System.out.println("Duplicate order!");
            return;
        }
        this.add(order);
        isSaved = false;
        System.out.println("Added!");
    }

    // Update: remove then add
    public void update(Order order) {
        for (Order o : this) {
            if (o.getOrderCode().equals(order.getOrderCode())) {
                this.remove(o);
                this.add(order);
                isSaved = false;
                System.out.println("Updated!");
                return;
            }
        }
        System.out.println("Not found!");
    }
}
```

#### SetMenus.java - Reads from CSV:
```java
public class SetMenus extends ArrayList<SetMenu> {

    // Convert CSV line to object
    public SetMenu dataToObject(String text) {
        String[] parts = text.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        if (parts.length < 4) return null;

        String menuId = parts[0].trim();
        String menuName = parts[1].trim();
        double price = Double.parseDouble(parts[2].trim());
        String ingredients = parts[3].trim();

        // Remove quotes if present
        if (ingredients.startsWith("\""))
            ingredients = ingredients.substring(1, ingredients.length()-1);

        return new SetMenu(menuId, menuName, price, ingredients);
    }

    // Read from CSV using FileUtils
    public void readFromFile() {
        this.clear();
        List<String> lines = FileUtils.readTextFile(pathFile);

        boolean firstLine = true; // Skip header
        for (String line : lines) {
            if (firstLine) { firstLine = false; continue; }

            // Remove BOM if present
            if (line.startsWith("\uFEFF"))
                line = line.substring(1);

            SetMenu menu = dataToObject(line);
            if (menu != null) this.add(menu);
        }
        System.out.println("Loaded " + this.size() + " menus.");
    }
}
```

### Step 7: Create Main Class (30 minutes)
**File:** `dispatcher/Main.java`

**Main method template:**
```java
public static void main(String[] args) {
    // 1. Create shared scanner
    scanner = new Scanner(System.in);
    inputter = new Inputter(scanner);

    // 2. Initialize business objects
    customers = new Customers(CUSTOMER_FILE);
    setMenus = new SetMenus("data/FeastMenu.csv");
    orders = new Orders(ORDER_FILE);

    // 3. Load data
    System.out.println("=== Feast Order System ===");
    setMenus.readFromFile();
    customers.readFromFile();
    orders.readFromFile();

    // 4. Run menu
    runMenu();
}
```

**Menu loop template:**
```java
private static void runMenu() {
    int choice;
    do {
        displayMenu();
        System.out.print("Choice: ");
        try {
            choice = Integer.parseInt(scanner.nextLine());
            processChoice(choice);
        } catch (NumberFormatException e) {
            System.out.println("Invalid!");
            choice = 0;
        }
    } while (choice != 0);
}
```

**Function templates for menu options:**
```java
// Option 1: Add customer
private static void addNewCustomer() {
    System.out.println("\n--- Add Customer ---");
    String id = inputter.getValidString("ID (CGKxxxx): ", Acceptable.CUS_ID_VALID);
    String name = inputter.getValidString("Name: ", Acceptable.NAME_VALID);
    String phone = inputter.getValidString("Phone: ", Acceptable.PHONE_VALID);
    String email = inputter.getValidString("Email: ", Acceptable.EMAIL_VALID);
    customers.addNew(new Customer(id, name, phone, email));
}

// Option 4: Add order
private static void addNewOrder() {
    if (customers.isEmpty()) {
        System.out.println("No customers!");
        return;
    }
    customers.showAll(); // Show available customers

    String cid = inputter.getString("Customer ID: ");
    if (customers.searchById(cid) == null) {
        System.out.println("Not found!");
        return;
    }

    setMenus.showMenuList(); // Show menus
    String mid = inputter.getString("Menu ID: ");
    if (setMenus.searchById(mid) == null) {
        System.out.println("Not found!");
        return;
    }

    String province = inputter.getString("Province: ");
    int tables = inputter.getInt("Tables: ");

    Order order = new Order();
    order.setOrderCodeWithGeneration(); // Auto-generate code
    order.setCustomerId(cid);
    order.setMenuId(mid);
    order.setProvince(province);
    order.setNumOfTables(tables);
    // Set date...

    orders.addNew(order);
}
```

### Step 8: Compile and Test (5 minutes)
```bash
# Compile
javac -d build -sourcepath src src/com/feast/**/*.java

# Run
java -cp build com.feast.dispatcher.Main
```

## Total Estimated Time: 2-3 hours
