# Traditional Feast Order Management System - Complete Coding Guide

**Project Code:** J1.L.P0028
**LOC:** ~200 per function
**Course:** LAB211 - OOP with Java Lab

---

## Table of Contents

1. [Project Overview](#1-project-overview)
2. [Architecture Design (OOP Principles)](#2-architecture-design-oop-principles)
3. [Step-by-Step Implementation](#3-step-by-step-implementation)
   - Step 1: Project Setup
   - Step 2: Tools Layer (Validation & Utilities)
   - Step 3: Model Layer (Data Classes)
   - Step 4: Business Layer (CRUD Operations)
   - Step 5: Control Layer (Main Menu)
4. [Function Implementation Mapping](#4-function-implementation-mapping)

---

## 1. Project Overview

### Problem Statement Summary
A company offering traditional feast services for special events requires a management program with the following features:
- Register new customers
- Update customer information
- Search customers by name
- Display feast menus
- Place feast orders
- Update order information
- Save data to binary file
- Display customer/order lists

### Technical Requirements
- **Language:** Java
- **Architecture:** nLayers pattern (Model-Business-Tools-Control)
- **Data Persistence:** Binary files (.dat) using Serialization
- **OOP Principles:** Encapsulation, Inheritance, Polymorphism, Abstraction

---

## 2. Architecture Design (OOP Principles)

### Package Structure
```
com.feast/
├── model/          # Data classes (POJOs with Serializable)
├── business/       # Business logic extending collections
├── tools/          # Utilities (validation, file I/O)
└── dispatcher/     # Main controller
```

### OOP Principles Applied

| Principle | Implementation |
|-----------|----------------|
| **Encapsulation** | Private fields with getters/setters in model classes |
| **Inheritance** | Business classes extend ArrayList/HashSet |
| **Polymorphism** | Generic methods in FileUtils, Interface-based validation |
| **Abstraction** | Acceptable interface defines validation contracts |

---

## 3. Step-by-Step Implementation

### STEP 1: Project Setup

```
project/
├── src/
│   └── com/feast/
│       ├── model/
│       ├── business/
│       ├── tools/
│       └── dispatcher/
├── data/
│   └── FeastMenu.csv
└── lib/
```

### STEP 2: Tools Layer (Foundation)

#### 2.1 Create `Acceptable.java` Interface

**Purpose:** Centralized validation patterns using regex

**Key Pattern:**
```java
public interface Acceptable {
    String CUS_ID_VALID = "^[CcGgKk]\\d{4}$";      // C/G/K + 4 digits
    String NAME_VALID = "^.{2,25}$";                // 2-25 chars
    String PHONE_VALID = "^0\\d{9}$";               // 0 + 9 digits
    String EMAIL_VALID = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
    String MENU_ID_VALID = "^PW\\d{3}$";            // PW + 3 digits

    static boolean isValid(String data, String pattern) {
        return data != null && data.matches(pattern);
    }
}
```

**Problem Statement Mapping:**
- Function 1 (Register Customer): Customer code validation
- Function 5 (Place Order): Menu ID validation

---

#### 2.2 Create `Inputter.java` Class

**Purpose:** Reusable user input handler with validation

**Key Methods:**
```java
public class Inputter {
    private Scanner scanner;

    public String getString(String mess)        // Non-empty string
    public int getInt(String mess)              // Positive integer
    public String getValidString(String mess, String pattern)  // Regex-validated
}
```

**Usage Pattern:**
```java
Inputter inputter = new Inputter();
String id = inputter.getValidString("ID: ", Acceptable.CUS_ID_VALID);
```

---

#### 2.3 Create `FileUtils.java` Class

**Purpose:** Generic file I/O operations using Generics

**Key Methods:**
```java
public class FileUtils {
    // Read serialized objects
    public static <T> List<T> readFromFile(String filePath)

    // Write serialized objects
    public static <T> void saveToFile(List<T> list, String filePath)

    // Read text file (CSV)
    public static List<String> readTextFile(String filePath)
}
```

**Generics Benefit:** One method works for Customers, Orders, any Serializable class

---

### STEP 3: Model Layer (Data Classes)

#### 3.1 Create `Customer.java`

**Requirements from Function 1:**
| Field | Validation |
|-------|------------|
| id | Starts with C/G/K, 4 digits |
| name | 2-25 characters |
| phone | 10 digits, Vietnamese operator |
| email | Standard email format |

**Implementation:**
```java
public class Customer implements Serializable {
    private String id;
    private String name;
    private String phone;
    private String email;

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    // ... other getters/setters

    // Display formatting
    @Override
    public String toString() {
        return String.format("| %-10s | %-25s | %-15s | %-30s |",
                id, name, phone, email);
    }

    public static String getHeader() { /* table header */ }
    public static String getFooter() { /* table footer */ }
}
```

**Why Serializable:** Required for binary file storage (Function 7)

---

#### 3.2 Create `SetMenu.java`

**Requirements from Function 4:**
Read from `FeastMenu.csv` with columns: Code, Name, Price, Ingredients

**Implementation:**
```java
public class SetMenu implements Serializable {
    private String menuId;
    private String menuName;
    private double price;
    private String ingredients;

    // Getters, Setters, toString()
}
```

---

#### 3.3 Create `Order.java`

**Requirements from Function 5 & 6:**
- Auto-generate unique order code
- Duplicate detection based on (customerId + menuId + eventDate)

**Implementation:**
```java
public class Order implements Serializable {
    private String orderCode;
    private String customerId;
    private String menuId;
    private int numOfTables;
    private Date eventDate;

    // Auto-generate order code
    private String generateOrderCode() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        return sdf.format(new Date());
    }

    // Duplicate detection for HashSet
    @Override
    public boolean equals(Object o) {
        // Compare customerId, menuId, eventDate (date only, not time)
    }

    @Override
    public int hashCode() {
        // Hash same fields as equals
    }
}
```

---

## Deep Dive: HashSet Duplicate Detection for Orders

### Problem Statement Requirement (Function 5)

> "An order information is considered unique based on the simultaneous combination of 3 attributes:
> **Customer code + Set Menu code + Event date**"

This means the same customer cannot order the same menu on the same date twice.

### How HashSet Works Internally

```
HashSet uses a "bucket" approach:
1. Calculate hashCode() -> determines which bucket
2. If bucket has items, use equals() to find exact match
3. If equals() returns true -> it's a duplicate
```

### Flow Diagram

```
User tries to add a new order:
    │
    ▼
orders.addNew(order)
    │
    ▼
orders.contains(order)  <-- HashSet method
    │
    ├─────────────────────────────────────┐
    ▼                                     ▼
Calculate hashCode()                    Check bucket
    │                                     │
    ▼                                     ▼
hash("K0310", "PW002", "20250214")    Found items?
    = 12345                                 │
    │                                      ▼
    ▼                                   Compare each
Find bucket #5                           │
    │                                      ▼
    │                                   equals() check:
    ▼                                   • customerId match?
Bucket empty?                           • menuId match?
    │                                   • eventDate match? (yyyyMMdd)
    ▼                                      │
 YES            NO                      All match?
    │              │                       │
    ▼              ▼                       ▼
Add new item   Has items           YES → Duplicate!
                continue           NO  → Continue checking
```

### The `equals()` Method (Order.java:157-167)

```java
@Override
public boolean equals(Object o) {
    // Step 1: Reference check (same object in memory)
    if (this == o) return true;

    // Step 2: Null or type check
    if (o == null || getClass() != o.getClass()) return false;

    // Step 3: Cast and compare the 3 key fields
    Order order = (Order) o;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    return Objects.equals(customerId, order.customerId) &&
           Objects.equals(menuId, order.menuId) &&
           Objects.equals(dateFormat.format(eventDate),      // Only date part!
                         dateFormat.format(order.eventDate));
}
```

**Key Point:** The date is formatted as `"yyyyMMdd"` - ignoring time! This ensures orders on the same day (but different times) are treated as duplicates.

### The `hashCode()` Method (Order.java:174-178)

```java
@Override
public int hashCode() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    return Objects.hash(customerId, menuId, dateFormat.format(eventDate));
}
```

**The Contract:** If two objects are `equal()`, they MUST have the same `hashCode()`.

### Why Format Date as "yyyyMMdd"?

**Without formatting:**
```
Order 1: 2025-02-14 09:00:00  -> hashCode() = ABC
Order 2: 2025-02-14 14:30:00  -> hashCode() = XYZ
Result: Different buckets → NOT detected as duplicate ❌
```

**With formatting:**
```
Order 1: 2025-02-14 09:00:00  -> "20250214" -> hashCode() = 123
Order 2: 2025-02-14 14:30:00  -> "20250214" -> hashCode() = 123
Result: Same bucket → equals() checks → DUPLICATE ✓
```

### Practical Example

```java
// Order 1: Successfully added
Order order1 = new Order();
order1.setCustomerId("K0310");
order1.setMenuId("PW002");
order1.setEventDate(parseDate("14/02/2025"));
orders.addNew(order1);  // ✓ SUCCESS

// Order 2: Same customer, same menu, same day
Order order2 = new Order();
order2.setCustomerId("K0310");
order2.setMenuId("PW002");
order2.setEventDate(parseDate("14/02/2025"));
orders.addNew(order2);  // ✗ DUPLICATE! Rejected
```

**What happens internally:**

| Step | Order 1 | Order 2 |
|------|---------|---------|
| `hashCode()` | hash("K0310", "PW002", "20250214") = 12345 | hash("K0310", "PW002", "20250214") = 12345 |
| Bucket | Goes to bucket #5 | Goes to bucket #5 |
| `equals()` check | No comparison (empty bucket) | Compares with Order1 |
| Result | ADDED | DUPLICATE detected |

### Summary Table

| Component | Purpose |
|-----------|---------|
| `hashCode()` | Quickly narrows search to one bucket |
| `equals()` | Precisely compares the 3 business keys |
| `Orders extends HashSet` | Automatic duplicate prevention |
| `"yyyyMMdd"` format | Date-only comparison (ignores time) |

**Time Complexity:** O(1) average for duplicate detection

---

## Deep Dive: CSV Parsing Logic

### The Challenge: Complex CSV Data

The `FeastMenu.csv` file contains a **4th column (Ingredients)** with:
- Multiple commas within the text
- Quoted strings containing special characters
- Multiple lines of text within one field

**Sample CSV Line:**
```csv
PW001,Wedding party 01,3750000,"+ Khai vị: Súp bong bóng cá; Nem rán; Gỏi hoa chuối tôm thịt #+ Món chính: Gà luộc; Bò sốt vang; Lẩu hải sản nấm#+ Tráng miệng: Bánh flan; trái cây"
```

**Problem:** Simple `split(",")` would break the ingredients into 10+ pieces!

---

### The Regex Solution

**Location:** `SetMenus.java:54`

```java
String[] parts = text.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
```

### Regex Breakdown: `,(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)`

| Part | Meaning |
|------|---------|
| `,` | Match a comma (the delimiter) |
| `(?=...)` | **Positive lookahead** - check ahead but don't consume |
| `(?:...)` | **Non-capturing group** - group without remembering |
| `[^\"]*` | Any characters except quotes (0 or more) |
| `\"[^\"]*\"` | A quoted string (opening quote, content, closing quote) |
| `(?:[^\"]*\"[^\"]*\")*` | Zero or more: non-quotes + quoted string |
| `[^\"]*$` | Remaining non-quotes until end of line |

**In plain English:**
> "Split by comma ONLY if looking ahead, we see an even number of quotes (meaning we're **outside** quotes)"

---

### Visual Example

```
Input Line:
PW001,Wedding party 01,3750000,"+ Khai vị: Súp bong bóng cá; Nem rán, Gỏi hoa chuối"

Step-by-step parsing:
┌─────────────────────────────────────────────────────────────────────────────┐
│ Position  │ Char  │ Looking Ahead               │ Even Quotes? │ Split? │
├───────────┼───────┼─────────────────────────────┼──────────────┼────────┤
│ After 0   │ P     │ PW001,W...                  │ 0 (even)     │ NO     │
│ After 4   │ ,     │ Wedding party 01,375...     │ 0 (even)     │ YES ✓  │
│ After 19  │ ,     │ 3750000,"+ Khai vị...       │ 0 (even)     │ YES ✓  │
│ After 26  │ ,     │ "+ Khai vị: Súp...Ném rán,  │ 1 (odd)      │ NO ✗   │
│                                           ↑ inside quotes, ignore comma    │
│ After 50  │ ,     │ Gỏi hoa chuối"              │ 1 (odd)      │ NO ✗   │
│ After 63  │       │ (end of line)               │ 0 (even)     │ -      │
└───────────┴───────┴─────────────────────────────┴──────────────┴────────┘

Result Array (4 elements):
[0] = "PW001"
[1] = "Wedding party 01"
[2] = "3750000"
[3] = "+ Khai vị: Súp bong bóng cá; Nem rán, Gỏi hoa chuối"
```

---

### Complete Parsing Flow (SetMenus.java)

```
┌─────────────────────────────────────────────────────────────────┐
│ 1. FileUtils.readTextFile(pathFile)                            │
│    ├─ Opens BufferedReader on FeastMenu.csv                    │
│    ├─ Reads line by line                                       │
│    └─ Returns List<String> of all lines                        │
└─────────────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────────┐
│ 2. readFromFile() in SetMenus                                   │
│    ├─ Skip first line (header: "Code,Name,Price,Ingredients")  │
│    ├─ Check and remove BOM (\uFEFF) if present                 │
│    └─ Call dataToObject() for each data line                   │
└─────────────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────────┐
│ 3. dataToObject(String text)                                    │
│    ├─ Split line using regex (handles quoted commas)           │
│    ├─ Trim whitespace from each part                           │
│    ├─ Parse price as double                                     │
│    ├─ Remove surrounding quotes from ingredients               │
│    └─ Return new SetMenu(menuId, menuName, price, ingredients) │
└─────────────────────────────────────────────────────────────────┘
                            │
                            ▼
                    Add to SetMenus ArrayList
```

---

### Code Walkthrough with Example

**CSV File (FeastMenu.csv):**
```csv
Code,Name,Price,Ingredients
PW001,Wedding party 01,3750000,"+ Khai vị: Súp bong bóng cá; Nem rán; Gỏi hoa chuối tôm thịt #+ Món chính: Gà luộc; Bò sốt vang; Lẩu hải sản nấm#+ Tráng miệng: Bánh flan; trái cây"
```

**Step 1: Read file (FileUtils.java:79-97)**
```java
// Read all lines from file
List<String> lines = FileUtils.readTextFile("data/FeastMenu.csv");
// lines[0] = "Code,Name,Price,Ingredients"
// lines[1] = "PW001,Wedding party 01,3750000,\"+ Khai vị...\""
```

**Step 2: Process in readFromFile() (SetMenus.java:76-101)**
```java
public void readFromFile() {
    this.clear();
    List<String> lines = FileUtils.readTextFile(pathFile);

    boolean firstLine = true;
    for (String line : lines) {
        if (firstLine) {
            firstLine = false;
            continue;  // Skip header
        }

        // Remove BOM (Byte Order Mark) if file was saved with UTF-8 BOM
        if (line.startsWith("\uFEFF")) {
            line = line.substring(1);
        }

        SetMenu menu = dataToObject(line);
        if (menu != null) {
            this.add(menu);
        }
    }
}
```

**Step 3: Parse with dataToObject() (SetMenus.java:52-71)**
```java
public SetMenu dataToObject(String text) {
    // Split by comma, but NOT when inside quotes
    String[] parts = text.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

    if (parts.length < 4) {
        return null;  // Invalid line
    }

    String menuId = parts[0].trim();                    // "PW001"
    String menuName = parts[1].trim();                  // "Wedding party 01"
    double price = Double.parseDouble(parts[2].trim()); // 3750000.0
    String ingredients = parts[3].trim();              // "\"+ Khai vị...\""

    // Remove surrounding quotes from ingredients
    if (ingredients.startsWith("\"") && ingredients.endsWith("\"")) {
        ingredients = ingredients.substring(1, ingredients.length() - 1);
    }

    return new SetMenu(menuId, menuName, price, ingredients);
}
```

**Result:**
```java
SetMenu {
    menuId: "PW001"
    menuName: "Wedding party 01"
    price: 3750000.0
    ingredients: "+ Khai vị: Súp bong bóng cá; Nem rán; Gỏi hoa chuối tôm thịt #+ Món chính: Gà luộc; Bò sốt vang; Lẩu hải sản nấm#+ Tráng miệng: Bánh flan; trái cây"
}
```

---

### Why the `-1` Parameter?

```java
String[] parts = text.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                                                                  ^^^
```

**Without `-1` (default behavior):**
```java
"a,b,c,".split(",")      // Returns ["a", "b", "c"]
// Trailing empty strings are DISCARDED!
```

**With `-1`:**
```java
"a,b,c,".split(",", -1)  // Returns ["a", "b", "c", ""]
// Trailing empty strings are KEPT!
```

**Why it matters:** If ingredients field is empty `PW001,Name,1000,""`, we still get 4 parts instead of 3.

---

### BOM (Byte Order Mark) Handling

**What is BOM?**
- A special character (`\uFEFF`) at the start of text files
- Indicates the file's encoding (UTF-8 with BOM)
- Invisible to users but can break parsing

**Example:**
```
\uFEFFCode,Name,Price,Ingredients
PW001,Wedding party 01,3750000,...
```

Without BOM removal, `parts[0]` would be `"\uFEFFPW001"` instead of `"PW001"`.

---

### Summary Table

| Step | Method | Purpose |
|------|--------|---------|
| 1 | `FileUtils.readTextFile()` | Read all lines from CSV |
| 2 | `readFromFile()` | Skip header, remove BOM, iterate lines |
| 3 | `dataToObject()` | Parse CSV line with regex, handle quotes |
| 4 | Add to `ArrayList<SetMenu>` | Store parsed menu objects |

**Key Regex Pattern:** `,(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)`
- Splits by comma **only when outside quotes**
- Enables parsing complex CSV data with embedded commas

---

### STEP 4: Business Layer (CRUD Operations)

#### 4.1 Create `Customers.java`

**Design:** Extends `ArrayList<Customer>` for inheritance

**Key Methods mapping to Functions:**

| Function | Method | LOC |
|----------|--------|-----|
| F1: Register Customer | `addNew(Customer)` | ~75 |
| F2: Update Customer | `update(Customer)` | ~75 |
| F3: Search by Name | `filterByName(String)` | ~50 |
| F7: Save to File | `saveToFile()` | ~50 |
| F8: Display List | `showAll()` | ~50 |

**Implementation Pattern:**
```java
public class Customers extends ArrayList<Customer> {
    private String pathFile;
    private boolean isSaved;

    // Function 1: Register
    public void addNew(Customer customer) {
        // Check duplicate ID
        for (Customer c : this) {
            if (c.getId().equals(customer.getId())) {
                System.out.println("Customer ID already exists!");
                return;
            }
        }
        this.add(customer);
        isSaved = false;
    }

    // Function 2: Update
    public void update(Customer customer) {
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getId().equals(customer.getId())) {
                this.set(i, customer);
                isSaved = false;
                return;
            }
        }
    }

    // Function 3: Search by name
    public List<Customer> filterByName(String name) {
        List<Customer> result = new ArrayList<>();
        for (Customer c : this) {
            if (c.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(c);
            }
        }
        return result;
    }
}
```

---

#### 4.2 Create `SetMenus.java`

**Function 4: Display Feast Menus**

**Implementation:**
```java
public class SetMenus extends ArrayList<SetMenu> {
    private String pathFile = "data/FeastMenu.csv";

    // Parse CSV line to SetMenu
    public SetMenu dataToObject(String text) {
        // Split by comma, handle quoted ingredients
        String[] parts = text.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        return new SetMenu(parts[0], parts[1],
            Double.parseDouble(parts[2]), parts[3]);
    }

    public void readFromFile() {
        List<String> lines = FileUtils.readTextFile(pathFile);
        // Skip header, parse each line
    }

    public void showMenuList() {
        // Display sorted by price (ascending)
        this.sort(Comparator.comparing(SetMenu::getPrice));
        // Print table
    }
}
```

---

#### 4.3 Create `Orders.java`

**Design:** Extends `HashSet<Order>` to prevent duplicates

**Key Methods:**

| Function | Method | LOC |
|----------|--------|-----|
| F5: Place Order | `addNew(Order)` | ~100 |
| F6: Update Order | `update(Order)` | ~50 |
| F7: Save to File | `saveToFile()` | ~50 |

**Implementation:**
```java
public class Orders extends HashSet<Order> {
    // HashSet automatically prevents duplicates via equals/hashCode

    public void addNew(Order order) {
        if (this.contains(order)) {  // Uses Order.equals()
            System.out.println("Duplicate data!");
            return;
        }
        this.add(order);
    }
}
```

---

### STEP 5: Control Layer (Main Menu)

#### 5.1 Create `Main.java`

**Responsibilities:**
- Initialize all business objects
- Display menu
- Route to appropriate functions

**Structure:**
```java
public class Main {
    private static Customers customers;
    private static SetMenus setMenus;
    private static Orders orders;
    private static Inputter inputter;

    public static void main(String[] args) {
        // Initialize
        customers = new Customers("data/customers.dat");
        setMenus = new SetMenus();
        orders = new Orders("data/orders.dat");

        // Load existing data
        customers.readFromFile();
        orders.readFromFile();
        setMenus.readFromFile();

        runMenu();
    }

    private static void runMenu() {
        // Display options 1-9, 0
        // Process choice with switch
    }
}
```

---

## 4. Function Implementation Mapping

### Function 1: Register Customers (75 LOC)

**Menu Option:** 1
**Method:** `addNewCustomer()` in Main

**Implementation Steps:**
```java
private static void addNewCustomer() {
    // 1. Input Customer ID (validation: C/G/K + 4 digits)
    String id = inputter.getValidString("Customer ID: ", Acceptable.CUS_ID_VALID);

    // 2. Input Name (validation: 2-25 chars)
    String name = inputter.getValidString("Name: ", Acceptable.NAME_VALID);

    // 3. Input Phone (validation: 10 digits)
    String phone = inputter.getValidString("Phone: ", Acceptable.PHONE_VALID);

    // 4. Input Email (validation: email format)
    String email = inputter.getValidString("Email: ", Acceptable.EMAIL_VALID);

    // 5. Create Customer object
    Customer customer = new Customer(id, name, phone, email);

    // 6. Add to list (checks for duplicate)
    customers.addNew(customer);

    // 7. Ask to continue or return
}
```

**Validation Rules Applied:**
- Customer ID: Must start with C, G, or K + 4 digits
- Must be unique
- Name: Non-empty, 2-25 characters
- Phone: Exactly 10 digits
- Email: Standard format

---

### Function 2: Update Customer Information (75 LOC)

**Menu Option:** 2
**Method:** `updateCustomer()` in Main

**Implementation Steps:**
```java
private static void updateCustomer() {
    // 1. Input Customer Code to update
    String id = inputter.getString("Enter Customer ID: ");

    // 2. Check if customer exists
    Customer existing = customers.searchById(id);
    if (existing == null) {
        System.out.println("This customer does not exist.");
        return;
    }

    // 3. Display current info
    System.out.println(existing);

    // 4. Input new values (validate each)
    String name = inputter.getValidString("New Name: ", Acceptable.NAME_VALID);
    String phone = inputter.getValidString("New Phone: ", Acceptable.PHONE_VALID);
    String email = inputter.getValidString("New Email: ", Acceptable.EMAIL_VALID);

    // 5. Update
    Customer updated = new Customer(id, name, phone, email);
    customers.update(updated);

    // 6. Ask to continue or return
}
```

**Keep Old Information:** The current implementation requires all fields. To keep old info when blank, add:
```java
String input = inputter.getString("New Name (blank to keep): ");
String name = input.isEmpty() ? existing.getName() : input;
```

---

### Function 3: Search Customer by Name (50 LOC)

**Menu Option:** 3
**Method:** `searchCustomerByName()` in Main

**Implementation:**
```java
private static void searchCustomerByName() {
    // 1. Input name (partial or full)
    String name = inputter.getString("Enter name to search: ");

    // 2. Filter matching customers
    List<Customer> result = customers.filterByName(name);

    // 3. Display results sorted alphabetically
    result.sort(Comparator.comparing(Customer::getName));

    // 4. Display table or "No matches found"
    if (result.isEmpty()) {
        System.out.println("No one matches the search criteria!");
    } else {
        System.out.println(Customer.getHeader());
        for (Customer c : result) {
            System.out.println(c);
        }
        System.out.println(Customer.getFooter());
    }
}
```

---

### Function 4: Display Feast Menus (50 LOC)

**Menu Option:** 7 (or separate option)
**Method:** `setMenus.showMenuList()`

**Implementation:**
```java
public void showMenuList() {
    // 1. Check if FeastMenu.csv exists
    File file = new File(pathFile);
    if (!file.exists()) {
        System.out.println("Cannot read data from feastMenu.csv. Please check it.");
        return;
    }

    // 2. Read and parse CSV
    readFromFile();

    // 3. Sort by price (ascending)
    sort(Comparator.comparing(SetMenu::getPrice));

    // 4. Display table
    System.out.println(SetMenu.getHeader());
    for (SetMenu menu : this) {
        System.out.println(menu);
        System.out.println("Ingredients: " + menu.getIngredients());
    }
    System.out.println(SetMenu.getFooter());
}
```

---

### Function 5: Place a Feast Order (100 LOC)

**Menu Option:** 4
**Method:** `addNewOrder()` in Main

**Implementation:**
```java
private static void addNewOrder() {
    // 1. Validate customer exists
    if (customers.isEmpty()) {
        System.out.println("No customers available.");
        return;
    }

    // 2. Show customers and get customer ID
    customers.showAll();
    String customerId = inputter.getString("Enter Customer ID: ");
    if (customers.searchById(customerId) == null) {
        System.out.println("Customer not found!");
        return;
    }

    // 3. Show menus and get menu ID
    setMenus.showMenuList();
    String menuId = inputter.getString("Enter Menu ID: ");
    if (setMenus.searchById(menuId) == null) {
        System.out.println("Menu not found!");
        return;
    }

    // 4. Get number of tables (> 0)
    int numOfTables = inputter.getInt("Number of Tables: ");

    // 5. Get event date (must be future date)
    String dateStr = inputter.getString("Event Date (dd/MM/yyyy): ");
    Date eventDate = parseDate(dateStr);
    if (eventDate == null || eventDate.before(new Date())) {
        System.out.println("Invalid or past date!");
        return;
    }

    // 6. Create Order with auto-generated code
    Order order = new Order();
    order.setOrderCodeWithGeneration();
    order.setCustomerId(customerId);
    order.setMenuId(menuId);
    order.setNumOfTables(numOfTables);
    order.setEventDate(eventDate);

    // 7. Check for duplicate (same customer + menu + date)
    orders.addNew(order);

    // 8. Calculate and display total cost
    SetMenu menu = setMenus.searchById(menuId);
    double total = menu.getPrice() * numOfTables;
    System.out.printf("Total cost: %,.0f Vnd%n", total);
}
```

---

### Function 6: Update Order Information (50 LOC)

**Menu Option:** 5
**Method:** `updateOrder()` in Main

**Implementation:**
```java
private static void updateOrder() {
    // 1. Get Order ID
    String orderCode = inputter.getString("Enter Order Code: ");

    // 2. Check if order exists
    Order existing = orders.searchById(orderCode);
    if (existing == null) {
        System.out.println("This Order does not exist.");
        return;
    }

    // 3. Check if event date already passed
    if (existing.getEventDate().before(new Date())) {
        System.out.println("Cannot update past orders!");
        return;
    }

    // 4. Get new values
    String menuId = inputter.getString("New Menu ID: ");
    int numOfTables = inputter.getInt("New Number of Tables: ");
    String dateStr = inputter.getString("New Event Date: ");
    Date eventDate = parseDate(dateStr);

    // 5. Update
    Order updated = new Order(orderCode, existing.getCustomerId(),
            menuId, numOfTables, eventDate);
    orders.update(updated);
}
```

---

### Function 7: Save Data to File (50 LOC)

**Menu Option:** 9
**Method:** `saveAllData()` in Main

**Implementation:**
```java
private static void saveAllData() {
    // 1. Save customers to customers.dat
    customers.saveToFile();
    System.out.println("Customer data saved to customers.dat");

    // 2. Save orders to feast_order_service.dat
    orders.saveToFile();
    System.out.println("Order data saved to feast_order_service.dat");
}
```

**In Business Classes:**
```java
public void saveToFile() {
    FileUtils.saveToFile(new ArrayList<>(this), pathFile);
    isSaved = true;
}
```

---

### Function 8: Display Customer or Order Lists (50 LOC)

**Menu Option:** 8
**Method:** `displayLists()` in Main

**Implementation:**
```java
private static void displayLists() {
    System.out.println("1. Display all customers");
    System.out.println("2. Display all orders");
    String choice = inputter.getString("Enter choice: ");

    switch (choice) {
        case "1":
            if (customers.isEmpty()) {
                System.out.println("Does not have any customer information.");
            } else {
                customers.sort(Comparator.comparing(Customer::getName));
                customers.showAll();
            }
            break;
        case "2":
            if (orders.isEmpty()) {
                System.out.println("No data in the system.");
            } else {
                orders.showAll();
            }
            break;
    }
}
```

---

## Summary of OOP Principles Used

| Principle | Example in Code |
|-----------|-----------------|
| **Encapsulation** | Private fields in Customer/SetMenu/Order with public getters/setters |
| **Inheritance** | `Customers extends ArrayList`, `Orders extends HashSet` |
| **Polymorphism** | Generic `<T>` in FileUtils, `interface Acceptable` |
| **Abstraction** | Business layer hides implementation details from Main |

---

## File Locations Reference

| File | Path |
|------|------|
| Models | `src/com/feast/model/*.java` |
| Business | `src/com/feast/business/*.java` |
| Tools | `src/com/feast/tools/*.java` |
| Main | `src/com/feast/dispatcher/Main.java` |
| Data | `data/FeastMenu.csv` |
| Output | `data/customers.dat`, `data/orders.dat` |

---

## Build and Run

```bash
# Compile
javac -d build src/com/feast/**/*.java

# Run
java -cp build com.feast.dispatcher.Main
```

---

**Total LOC Distribution:**
- Tools Layer: ~150 LOC
- Model Layer: ~230 LOC
- Business Layer: ~275 LOC
- Control Layer: ~340 LOC
- **Total:** ~995 LOC (approximately 200 LOC per major function)
