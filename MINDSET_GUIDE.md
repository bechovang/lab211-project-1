# Mindset Guide: Analysis-Driven Approach

> **Project**: J1.L.P0028.TraditionalFeastOrderManagement (200 LOC)
> **Based on**: The exact problem statement provided
> **Method**: Trace each requirement from source text → analysis → implementation

---

## Analysis Principle: Every Line of Code Must Trace Back to a Requirement

```
PROBLEM STATEMENT ──────────────► ANALYSIS ──────────────► IMPLEMENTATION
      Text                              Decision                    Code
```

---

# PART 1: DOMAIN ANALYSIS

## From Background Section → Domain Entities

| Problem Statement Text | Extracted Entity | Why |
|------------------------|------------------|-----|
| "registering new customers" | **Customer** | Explicit entity mentioned |
| "managing feast menu options" | **SetMenu/FeastMenu** | Entity being managed |
| "handling orders" | **Order** | Explicit entity mentioned |
| "payment processing" | **Price** attribute | Orders need cost calculation |
| "feast_order_service.dat" | **File storage** | Orders need persistence |
| "weddings, anniversaries, traditional ceremonies" | **Domain context** | Feast/event catering industry |

**DERIVED ENTITY STRUCTURE:**
```
Customer (who orders)
    ↓ places
Order (what they order)
    ↓ references
SetMenu (what they can choose)
    ↓ has
Price, Ingredients (menu details)
```

## From Background Section → Technical Requirements

| Problem Statement Text | Extracted Technical Requirement | Implementation Impact |
|------------------------|----------------------------------|----------------------|
| "saving data into a binary file (feast_order_service.dat)" | Binary file I/O | Use `ObjectOutputStream`/`ObjectInputStream`, `Serializable` |
| "object-oriented programming (OOP) approach" | Must use OOP | Need class hierarchy, encapsulation, polymorphism, inheritance |
| "abstraction, polymorphism, encapsulation, and inheritance" | All 4 OOP pillars required | Design must demonstrate each explicitly |

**DERIVED TECHNICAL CONSTRAINTS:**
```
1. Classes needed (not procedural)
2. Private fields with public getters/setters (encapsulation)
3. extends/implements relationships (inheritance/abstraction)
4. Method overriding/overloading (polymorphism)
5. Serializable interface (binary file requirement)
```

---

# PART 2: FUNCTION-BY-FUNCTION ANALYSIS

## Function 1: Register Customers (75 LOC)

### Requirement Extraction

| From Problem Statement | Analysis → Implementation |
|------------------------|---------------------------|
| "A unique 5-character string. The first character is 'C', 'G' or 'K', followed by 4 digits" | **Validation pattern**: `^[CGKcgk]\\d{4}$` |
| "Name: A non-empty string between 2 and 25 characters long" | **Validation pattern**: `^.{2,25}$` (non-empty handled separately) |
| "Phone Number: A 10-digit number belonging to a network operator in Vietnam" | **Validation pattern**: `^0\\d{9}$` (starts with 0, exactly 10 digits) |
| "Email: A valid email address in standard format" | **Validation pattern**: standard email regex |
| "Must be unique" | **Duplicate check** required before adding |
| "Prompt the user to either continue entering new customers or return to the main menu" | **Loop structure** with Y/N prompt |

### Derived Data Structure (Customer)

```java
// From: "Customer code", "Name", "Phone Number", "Email"
public class Customer implements Serializable {
    private String id;           // "Customer code"
    private String name;         // "Name"
    private String phone;        // "Phone Number"
    private String email;        // "Email"
}
```

### Derived Workflow (From Operation Steps)

```
Step 1: "Prompt the user to input customer details"
    → Display prompts for each field

Step 2: "Validate each input based on the rules above"
    → Implement validation loops for each field

Step 3: "Save the registration record if all inputs are valid"
    → Add to collection only if all validations pass

Step 4: "Prompt the user to either continue entering new customers or return to the main menu"
    → Add loop structure with continue/exit option
```

### LOC Allocation Analysis (75 LOC)

| Component | Estimated LOC | Reasoning |
|-----------|---------------|-----------|
| Input prompts + validation loops | 30-35 | 4 fields × ~8 LOC each (prompt + validation) |
| Duplicate check logic | 5-10 | Iterate through existing customers |
| Customer object creation + add | 5 | Simple instantiation |
| Continue/exit prompt | 5-10 | Y/N prompt + loop control |
| Error messages | 10-15 | Specific messages for each validation failure |
| Method wrapper/structure | 5-10 | Method declaration, imports, etc. |

### Implementation Skeleton

```java
public void registerCustomer() {
    do {
        // Customer code input with validation (5-char, C/G/K + 4 digits, unique)
        String code = inputter.getValidString("Customer code: ", CUS_ID_PATTERN);
        while (isDuplicate(code)) {
            System.out.println("Customer code already exists!");
            code = inputter.getValidString("Customer code: ", CUS_ID_PATTERN);
        }

        // Name input with validation (2-25 chars, non-empty)
        String name = inputter.getValidString("Name: ", NAME_PATTERN);

        // Phone input with validation (10 digits, starts with 0)
        String phone = inputter.getValidString("Phone: ", PHONE_PATTERN);

        // Email input with validation (standard format)
        String email = inputter.getValidString("Email: ", EMAIL_PATTERN);

        // Create and save customer
        Customer c = new Customer(code, name, phone, email);
        customers.add(c);
        System.out.println("Customer registered successfully!");

    } while (inputter.getString("Continue? (Y/N): ").equalsIgnoreCase("Y"));
}
```

---

## Function 2: Update Customer Information (75 LOC)

### Requirement Extraction

| From Problem Statement | Analysis → Implementation |
|------------------------|---------------------------|
| "update the customer information, which can be updated includes: Name, Phone number, Email" | **Customer code is read-only**, only these 3 fields updatable |
| "If the customer code entered does not exist in the profile, the program will notify the user" | **Existence check** required before update |
| "Keep old information, if not enter new data" | **Optional updates** - empty input means keep existing |
| "Apply the respective validation rules for each field being updated" | **Same validation** as Function 1 for Name, Phone, Email |

### Derived Workflow

```
Step 1: "Prompt the user to enter the Customer Code"
    → Input customer code

Step 2: "Check if the customer exists"
    → Search in collection
    → If NOT found: "This customer does not exist." (exact message)
    → If found: proceed to update

Step 2a: "Prompt the user to update the desired fields"
    → Show current values
    → Prompt for new values

Step 2c: "Save the updated information"
    → Replace existing customer with new values

Step 2d: "Display a success message"
    → Show confirmation

Step 3: "Ask the user whether to continue with another update or return to the main menu"
    → Loop with Y/N prompt
```

### Key Implementation Detail from "Keep old information"

```java
// From: "Keep old information, if not enter new data"
Customer existing = searchById(code);

// Prompt for new name, but if empty, keep old
String newName = inputter.getString("New name (press Enter to keep current): ");
if (newName.isEmpty()) {
    newName = existing.getName();  // Keep old
}

// Same pattern for phone and email
```

### Sample Output Analysis (Exact Format Required)

From the problem statement sample output format, derive the display structure:
```
------------------------------------------------------------------
Code | Customer Name | Phone | Email
------------------------------------------------------------------
C0102 | An, Hoang Thi To | 0938232345 | anhtt@gmail.com
------------------------------------------------------------------
```

This tells us: **Use table format** with specific column headers.

---

## Function 3: Search Customer by Name (50 LOC)

### Requirement Extraction

| From Problem Statement | Analysis → Implementation |
|------------------------|---------------------------|
| "enter either the full name or a partial name" | **Partial matching** - use `contains()` not `equals()` |
| "system will display all matching customer names" | **Multiple results possible** - return List, not single object |
| "displayed in alphabetical order" | **Sort by name** before displaying |
| "Display the list of matching customers with details: customer code, name, phone, email" | **Specific columns** to show |
| "No one matches the search criteria!" | **Exact error message** when no results |

### Derived Algorithm

```
Input: name (full or partial)

1. For each customer in collection:
   - If customer.getName().contains(inputName):
     → Add to results list

2. If results.isEmpty():
   - Print "No one matches the search criteria!"

3. Else:
   - Sort results by name (alphabetical)
   - Print table header
   - Print each result with: code, name, phone, email
   - Print table footer

4. "prompt the user to return to the main menu"
   - Press Enter to continue
```

### Sample Output Analysis

```
Matching Customers: An
------------------------------------------------------------------
Code | Customer Name | Phone | Email
------------------------------------------------------------------
C0102 | An, Hoang Thi To | 0938232345 | anhtt@gmail.com
K0197 | Anh, Nguyen Do Hoang | 0909113355 | anhndh@outlook.com
C0901 | Lan, Vo Hoang Minh | 0389121221 | lanvhm@lovemail.com
------------------------------------------------------------------
```

**Format requirements derived:**
- Header shows search term: "Matching Customers: [search term]"
- Same table structure as Function 1
- Note: "Lan, Vo Hoang Minh" matches "An" (contains, not startsWith)

### LOC Allocation (50 LOC)

| Component | LOC |
|-----------|-----|
| Search input | 5 |
| Filter loop (contains) | 10 |
| Sort logic | 5-10 |
| Empty check + message | 5 |
| Display table | 10-15 |
| Return to menu prompt | 5 |

---

## Function 4: Display Feast Menus (50 LOC)

### Requirement Extraction

| From Problem Statement | Analysis → Implementation |
|------------------------|---------------------------|
| "Check the existing 'feastMenu.csv' file" | **File existence check** required |
| "The file 'feastMenu.csv' is provided with the assignment" | **Input file format is CSV** with specific name |
| "sort it in ascending order based on the price attribute" | **Sort by price** (low to high) before display |
| "Cannot read data from feastMenu.csv. Please check it." | **Exact error message** when file not found |
| After display: "return to the main menu" | **No continue prompt**, just display once |

### Derived CSV Structure

From "Code of SetMenu" and sample showing "PW002", we can infer:
```
CSV Format: Code,Name,Price,Ingredients
Example: PW002,Company year end party,2085000,"+ Khai vị:..."
```

### Derived Workflow

```
1. Check if feastMenu.csv exists
   - If NO: Print "Cannot read data from feastMenu.csv. Please check it."
   - Return to menu

2. Read CSV file
   - Parse each line
   - Extract: Code, Name, Price, Ingredients

3. Sort by price (ascending)

4. Display in table format

5. Return to main menu (automatic)
```

### Key Implementation Detail

From the order sample output showing ingredients:
```
+ Khai vị: Súp gà ngô; Nộm bò rau mầm
+ Món chính: Tôm hấp bia; Bò sốt tiêu đen + bánh mì; …
+ Tráng miệng: Rau câu dừa
```

**Derived:** Ingredients field contains multiline text with special formatting, likely quoted in CSV.

---

## Function 5: Place a Feast Order (100 LOC)

### Requirement Extraction

| From Problem Statement | Analysis → Implementation |
|------------------------|---------------------------|
| "Customer code is only valid if it is in the list of customers who have registered information" | **Foreign key validation** - check if customer exists |
| "Code of SetMenu... only valid if it is in the provided list" | **Foreign key validation** - check if menu exists |
| "Number of tables: must be greater than zero" | **Validation**: `tables > 0` |
| "Preferred event date must be in the future" | **Date comparison**: `eventDate > currentDate` |
| "unique based on the simultaneous combination of 3 attributes: Customer code, Set Menu code and event date" | **Composite key** for uniqueness check |
| "Automatically generate a unique code for the corresponding order" | **Auto-generation** required (timestamp or sequence) |
| "Calculate the total cost of the order based on selected Set Menu and number of tables" | **Formula**: `totalCost = menuPrice × numberOfTables` |

### Derived Data Structure (Order)

```java
// From: "Customer code", "Code of SetMenu", "Number of tables", "Preferred event date"
public class Order implements Serializable {
    private String orderCode;      // "Automatically generate a unique code"
    private String customerId;     // "Customer code"
    private String menuId;         // "Code of SetMenu"
    private int numOfTables;       // "Number of tables"
    private Date eventDate;        // "Preferred event date"
    // Note: Price comes from SetMenu, not stored directly
}
```

### Derived Uniqueness Logic

From: "unique based on the simultaneous combination of 3 attributes"

```java
// Composite key for duplicate detection
public boolean isDuplicate(Order newOrder) {
    for (Order existing : orders) {
        if (existing.getCustomerId().equals(newOrder.getCustomerId()) &&
            existing.getMenuId().equals(newOrder.getMenuId()) &&
            existing.getEventDate().equals(newOrder.getEventDate())) {
            return true;
        }
    }
    return false;
}
```

### Derived Workflow

```
1. Input Customer Code
   - Validate exists in registered customers
   - If not: error and retry

2. Input Menu Code
   - Validate exists in feastMenu.csv
   - If not: error and retry

3. Input Number of Tables
   - Validate > 0

4. Input Event Date
   - Validate is in future
   - If not: error and retry

5. Check for duplicate (customer + menu + date)
   - If duplicate: "Dupplicate data !" (exact message from spec)
   - Return to menu

6. Generate unique order code

7. Calculate total cost = menuPrice × numberOfTables

8. Display order confirmation (exact format from sample)

9. Add to orders list

10. "prompt the user to return to the main menu or continue with place another order"
    - Loop with continue option
```

### Sample Output Analysis - Exact Format Required

```
----------------------------------------------------------------
Customer order information [Order ID: 12]
----------------------------------------------------------------
Code : K0310
Customer name : Yen, Hoang Minh
Phone number : 0351232321
Email : yenhm11@gmail.com
----------------------------------------------------------------
Code of Set Menu: PW002
Set menu name : Company year end party
Event date : 14/02/2025
Number of tables: 8
Price : 2,085,000 Vnd
Ingredients:
+ Khai vị: Súp gà ngô; Nộm bò rau mầm
+ Món chính: Tôm hấp bia; Bò sốt tiêu đen + bánh mì; …
+ Tráng miệng: Rau câu dừa
----------------------------------------------------------------
Total cost : 16,680,000 Vnd
----------------------------------------------------------------
```

**Derived field mappings:**
- Order ID = "Order ID: 12" (appears to be sequential or generated)
- Need to fetch customer name, phone, email from Customer object
- Need to fetch menu name, price, ingredients from SetMenu object
- Total cost needs calculation display with formatting

### LOC Allocation (100 LOC) - Largest Function

| Component | LOC | Reasoning |
|-----------|-----|-----------|
| Customer code input + validation | 15 | Prompt + exists check |
| Menu code input + validation | 15 | Prompt + exists check |
| Tables input + validation | 10 | Prompt + >0 check |
| Date input + validation | 15 | Prompt + future check |
| Duplicate check (3 fields) | 10 | Composite key comparison |
| Order code generation | 5 | Timestamp or sequence |
| Total cost calculation | 5 | Simple multiplication |
| Display order info | 15 | Complex output with customer+menu details |
| Loop control | 10 | Continue option |

---

## Function 6: Update Order Information (50 LOC)

### Requirement Extraction

| From Problem Statement | Analysis → Implementation |
|------------------------|---------------------------|
| "Order ID: The unique identifier of the order" | **Search key** for finding order to update |
| "fields for update: Code of set menu, Number of table, Preferred event date" | **Customer ID is read-only** (not in update list) |
| "Keep old information, if not enter new data" | **Optional updates** like Function 2 |
| "Do not allow updating an order whose event date occurred before the current date" | **Business rule**: past orders are locked |
| "Update price based on set menu code" | **Recalculation** needed when menu changes |

### Derived Workflow

```
1. Input Order ID
   - Search for order

2. "Check if the Order exists"
   - If NOT: "This Order does not exist ."
   - Return to menu

3. Check event date vs current date
   - If eventDate < currentDate: Lock order, cannot update
   - Else: proceed

4. Display current order info

5. Prompt for new values (optional)
   - Menu code (keep old if empty)
   - Number of tables (keep old if empty)
   - Event date (keep old if empty)

6. Validate new inputs
   - Menu code must exist in menu list
   - Tables > 0
   - Date must be in future

7. Update order object

8. Recalculate price if menu changed

9. Display success message

10. "Ask the user whether to continue with another update or return to the main menu"
```

### Key Validation from Problem Statement

From: "Do not allow updating an order whose event date occurred before the current date"

```java
Date today = new Date();
if (existingOrder.getEventDate().before(today)) {
    System.out.println("Cannot update past orders.");
    return;
}
```

---

## Function 7: Save Data to File (50 LOC)

### Requirement Extraction

| From Problem Statement | Analysis → Implementation |
|------------------------|---------------------------|
| "saving customer list data... into the files customers.dat or feast_order_service.dat" | **Two separate files**: customers.dat and feast_order_service.dat |
| "Convert the data into an object format suitable for file storage as a binary object file" | **Use serialization**: `ObjectOutputStream` |
| "Display a confirmation message once the data is successfully saved" | **Success message** required |
| Sample: "Customer data has been successfully saved to 'customers.dat'." | **Exact message format** |

### Derived File Structure

```
Data Directory:
├── customers.dat           ← Customer objects (serialized)
├── feast_order_service.dat ← Order objects (serialized)
└── FeastMenu.csv          ← Menu data (read-only, provided)
```

### Derived Workflow

```
1. "Gather all current data from the program"
   - Get all Customer objects
   - Get all Order objects

2. "Convert the data into an object format"
   - Use Java serialization (Serializable interface)

3. "Write the serialized data to a file"
   - Write customers → customers.dat
   - Write orders → feast_order_service.dat

4. "Display a confirmation message"
   - "Customer data has been successfully saved to 'customers.dat'."
   - "Order data has been successfully saved to 'feast_order_service.dat'."

5. "Return to the Main Menu"
```

### Implementation Pattern

```java
// From: "binary object file"
try (ObjectOutputStream oos = new ObjectOutputStream(
        new FileOutputStream("customers.dat"))) {
    for (Customer c : customers) {
        oos.writeObject(c);
    }
} catch (IOException e) {
    System.out.println("Error saving data.");
}

// Same pattern for feast_order_service.dat with Order objects
```

---

## Function 8: Display Customer or Order Lists (50 LOC)

### Requirement Extraction

| From Problem Statement | Analysis → Implementation |
|------------------------|---------------------------|
| "Provide a menu-based interface for users to choose from" | **Sub-menu** required: 1=Customers, 2=Orders |
| "sort by customer name in alphabetical order" | **Customers**: sort by name ascending |
| "sort by event date in ascending order" | **Orders**: sort by date ascending |
| "Display the following details... Customer code, Name, Phone Number, Email" | **Specific columns** for customer table |
| "Order ID, Event date, Customer ID, Set Menu, Price, Number of tables, Total cost" | **Specific columns** for order table |
| "Does not have any customer information." | **Exact message** when empty |

### Derived Sub-menu Structure

```
--- Display Lists ---
1. Customers
2. Orders
Enter your choice:
```

### Derived Display Format for Customers

From sample output:
```
Customers information:
----------------------------------------------------------------
Code | Customer Name | Phone | Email
----------------------------------------------------------------
C0012 | An, Hoang Thi To | 0987654321 | anhtt@hotmail.com
G0171 | Binh, Ngo Quoc | 0902345678 | binhnq@yahoo.com
K0310 | Yen, Hoang Minh | 0351232321 | yenhm11@gmail.com
----------------------------------------------------------------
```

**Sort requirement**: Alphabetical by name (G0171 "Binh" before C0012 "An")

Wait - looking at the sample, C0012 "An" appears before G0171 "Binh". This contradicts "alphabetical order".
**ANALYSIS NOTE**: The sample may have an error, OR the sorting is by Customer Code, not name.
**DECISION**: Follow the explicit requirement "sort by customer name" - the sample may just be illustrative.

### Derived Display Format for Orders

From sample output:
```
-------------------------------------------------------------------------
ID | Event date |Customer ID| Set Menu| Price | Tables | Cost
-------------------------------------------------------------------------
K0310 | 14/02/2025 | K0310 | PW005 | 2,250,000| 3| 6,750,000
G0171 | 06/03/2025 | G0171 | PW006 | 1,950,000| 20| 39,000,000
C0012 | 08/03/2025 | C0012 | PW003 | 1,850,000| 5| 9,250,000
-------------------------------------------------------------------------
```

**Sort requirement**: Ascending by event date
- 14/02/2025 (Feb 14) - K0310
- 06/03/2025 (Mar 6) - G0171
- 08/03/2025 (Mar 8) - C0012

Sample matches the requirement (February before March).

**Derived calculation**: Cost = Price × Tables
- Row 1: 2,250,000 × 3 = 6,750,000 ✓
- Row 2: 1,950,000 × 20 = 39,000,000 ✓
- Row 3: 1,850,000 × 5 = 9,250,000 ✓

---

# PART 3: VALIDATION RULES DERIVATION

## From Problem Statement → Regex Patterns

| Requirement Text | Pattern | Explanation |
|------------------|---------|-------------|
| "first character is 'C', 'G' or 'K', followed by 4 digits" | `^[CGK]\d{4}$` | Exact match, case-sensitive based on examples |
| "between 2 and 25 characters long" | `^.{2,25}$` | Any character, length 2-25 |
| "10-digit number" | `^\d{10}$` | Exactly 10 digits |
| "belonging to a network operator in Vietnam" | `^0\d{9}$` | Vietnam mobile starts with 0 |
| "valid email address in standard format" | `^[\w.-]+@[\w.-]+\.[a-zA-Z]{2,}$` | Standard email pattern |
| "Number of tables: must be greater than zero" | `^[1-9]\d*$` | Positive integer |
| "Price" fields in samples | `^\d+$` | Positive integer (prices shown as integers) |

## From Problem Statement → Business Rules

| Rule Text | Code Implementation |
|-----------|---------------------|
| "Must be unique" (customer code) | Check if exists before adding |
| "unique based on... Customer code, Set Menu code and event date" (order) | Composite key check |
| "event date must be in the future" | `eventDate.after(new Date())` |
| "Do not allow updating an order whose event date occurred before the current date" | Lock past orders |
| "Keep old information, if not enter new data" | Empty string = keep existing value |

---

# PART 4: CLASS STRUCTURE DERIVATION

## From "OOP approach" Requirement → Class Design

### Problem Statement Analysis for Classes

| Text Evidence | Class Decision |
|---------------|----------------|
| "registering new customers" | **Customer class** needed |
| "managing feast menu options" | **SetMenu class** needed |
| "handling orders" | **Order class** needed |
| "saving data into a binary file" | All classes need **Serializable** |
| "abstraction" | Need **interface or abstract class** |
| "inheritance" | Classes should **extend** something |
| "encapsulation" | **Private fields**, public methods |
| "polymorphism" | **Method overriding/overloading** |

### Derived Class Hierarchy

```
From "abstraction" requirement:
┌─────────────────────────────────────────┐
│     <<interface>> Acceptable            │
│     + validation patterns (static)      │
└─────────────────────────────────────────┘
          ▲ implements
          │
┌─────────────────────────────────────────┐
│     Inputter (utility class)            │
│     + validated input methods           │
└─────────────────────────────────────────┘


From "inheritance" + collection management:
┌─────────────────────────────────────────┐
│     Customers extends ArrayList         │
│     + addNew(), update(), search()      │
└─────────────────────────────────────────┘

┌─────────────────────────────────────────┐
│     SetMenus extends ArrayList          │
│     + readFromCSV(), showMenuList()     │
└─────────────────────────────────────────┘

┌─────────────────────────────────────────┐
│     Orders extends HashSet              │
│     + addNew(), update(), search()      │
└─────────────────────────────────────────┘


Data models (from entities identified):
┌─────────────────────────────────────────┐
│     Customer implements Serializable    │
│     - id, name, phone, email            │
└─────────────────────────────────────────┘

┌─────────────────────────────────────────┐
│     SetMenu implements Serializable     │
│     - menuId, menuName, price, ingredients│
└─────────────────────────────────────────┘

┌─────────────────────────────────────────┐
│     Order implements Serializable       │
│     - orderCode, customerId, menuId, ... │
└─────────────────────────────────────────┘
```

### Why HashSet for Orders?

From problem statement: "unique based on the simultaneous combination of 3 attributes"

**Analysis:**
- HashSet automatically prevents duplicates
- Need to override `equals()` and `hashCode()` using the 3 fields
- More efficient than manual checking

```java
@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Order order = (Order) o;
    return Objects.equals(customerId, order.customerId) &&
           Objects.equals(menuId, order.menuId) &&
           Objects.equals(eventDate, order.eventDate);
}

@Override
public int hashCode() {
    return Objects.hash(customerId, menuId, eventDate);
}
```

---

# PART 5: IMPLEMENTATION ORDER DERIVATION

## Dependency Analysis → Build Order

### Step 1: What are the dependencies?

```
Main (dispatcher) depends on:
    → Customers, SetMenus, Orders (business layer)

Customers depends on:
    → Customer (model)
    → FileUtils (tools)

SetMenus depends on:
    → SetMenu (model)
    → FileUtils (tools)

Orders depends on:
    → Order (model)
    → FileUtils (tools)

Inputter depends on:
    → Acceptable (tools)

All models depend on:
    → Serializable (Java built-in)
```

### Derived Build Order

```
PHASE 1: Base Layer (no dependencies)
1. Acceptable interface
2. Customer model
3. SetMenu model
4. Order model

PHASE 2: Tools Layer
5. Inputter class (uses Acceptable)
6. FileUtils class (standalone utility)

PHASE 3: Business Layer
7. Customers class (uses Customer, FileUtils)
8. SetMenus class (uses SetMenu, FileUtils)
9. Orders class (uses Order, FileUtils)

PHASE 4: Control Layer
10. Main class (uses all business classes)
```

---

# PART 6: TESTING REQUIREMENTS DERIVATION

## From Problem Statement → Test Cases

| Requirement Text | Test Case Derived |
|------------------|-------------------|
| "Customer code: first character is 'C', 'G' or 'K', followed by 4 digits" | Test: C1234 ✓, G5678 ✓, K9999 ✓, A1234 ✗, C123 ✗ |
| "Must be unique" | Test: Add same customer code twice → second should fail |
| "Name: between 2 and 25 characters" | Test: "A" ✗, "Valid Name" ✓, 26+ characters ✗ |
| "Phone: 10-digit number" | Test: 1234567890 ✗, 0123456789 ✓, 01234567890 ✗ |
| "Email: valid format" | Test: user@domain.com ✓, invalid@ ✗ |
| "event date must be in the future" | Test: Past date ✗, Future date ✓ |
| "unique based on... Customer code, Set Menu code and event date" | Test: Same customer+menu+date → duplicate, Different date → allowed |
| "Do not allow updating an order whose event date occurred before the current date" | Test: Update past order ✗, Update future order ✓ |
| "sort by customer name in alphabetical order" | Test: Add Z-name, then A-name → display shows A first |
| "sort by event date in ascending order" | Test: March order, then February order → display shows Feb first |

---

# PART 7: ERROR MESSAGES DERIVATION

## From Problem Statement → Exact Messages

| Error Condition | Exact Message from Problem Statement |
|-----------------|--------------------------------------|
| Customer not found (update) | "This customer does not exist ." |
| No search results | "No one matches the search criteria!" |
| Menu CSV not found | "Cannot read data from 'feastMenu.csv'. Please check it." |
| Duplicate order | "Dupplicate data !" (note: typo in original spec) |
| Order not found (update) | "This Order does not exist ." |
| No customer data | "Does not have any customer information." |
| Save success (customers) | "Customer data has been successfully saved to 'customers.dat'." |
| Save success (orders) | "Order data has been successfully saved to 'feast_order_service.dat'." |

**CRITICAL**: Use these EXACT messages for testing compatibility.

---

# PART 8: SUMMARY CHECKLIST

## Before Coding - Verify You Have Extracted:

- [ ] All 8 functions identified
- [ ] All validation rules documented
- [ ] All error messages recorded
- [ ] All data structures defined
- [ ] All file names confirmed
- [ ] All OOP requirements noted
- [ ] All LOC allocations planned
- [ ] All sample outputs analyzed for format
- [ ] All business rules identified
- [ ] Dependencies mapped

## During Coding - Trace Each Line:

```
Why this class? → "managing feast menu options" requires SetMenu
Why this field? → "Code of SetMenu" requires menuId field
Why this validation? → "first character is C, G, or K" requires regex
Why this loop? → "Prompt the user to either continue... or return" requires while loop
Why this file format? → "binary object file" requires ObjectOutputStream
```

## After Coding - Verify Compliance:

- [ ] Each function matches its specification exactly
- [ ] Each validation rule is implemented
- [ ] Each error message matches the problem statement
- [ ] OOP principles are demonstrated
- [ ] LOC targets are met (minimum 200 total)
- [ ] File operations work (read/write)
- [ ] All test cases pass

---

## Final Reminder

**Every design decision must trace back to a specific sentence in the problem statement.**

If you find yourself adding a feature or validation that's not in the problem statement, ask:
- "Is this necessary for the system to work?"
- "Is this implied by an existing requirement?"
- "Will this violate the LOC budget?"

If the answer is NO to all three, don't add it.
