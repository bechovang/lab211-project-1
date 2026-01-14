# Traditional Feast Order Management System

> **Course**: LAB211 - OOP with Java Lab
> **Project**: J1.L.P0028.TraditionalFeastOrderManagement (200 LOC)
> **Instructor**: Nguyen Mai Huy

A Java console application for managing traditional feast orders with customer management, menu catalog, and order processing.

---

## Table of Contents

- [Features](#features)
- [Project Structure](#project-structure)
- [Requirements](#requirements)
- [Installation](#installation)
- [How to Run](#how-to-run)
- [Usage Guide](#usage-guide)
- [Input Validation Rules](#input-validation-rules)
- [Technical Highlights](#technical-highlights)
- [File Descriptions](#file-descriptions)
- [Documentation](#documentation)
- [Common Issues](#common-issues)

---

## Features

### 1. Customer Management
- Add new customers with validation
- Update customer information
- Search customers by name (partial match supported)
- Display all customers in table format

### 2. Menu Management
- Pre-loaded 6 menus from CSV file (FeastMenu.csv)
- Display menu list with prices
- Menu options include:
  - PW001 - Wedding party 01 (3,750,000 VND)
  - PW002 - Company year end party (2,085,000 VND)
  - PW003 - Birthday Party 01 (1,850,000 VND)
  - PW004 - Wedding party 02 (3,550,000 VND)
  - PW005 - Birthday Party 02 (2,250,000 VND)
  - PW006 - Meeting Party (1,950,000 VND)

### 3. Order Management
- Place new feast orders
- Update order information
- Search orders by order code
- Auto-generated unique order codes (timestamp-based: yyyyMMddhhmmss)
- Duplicate prevention (same customer + menu + date combination)

### 4. Data Persistence
- Save customers to object file (customers.dat)
- Save orders to object file (orders.dat)
- Load menus from CSV file (FeastMenu.csv)
- Auto-load existing data on startup

---

## Project Structure

```
01_J1.L.P0028.TraditionalFeastOrderManagement__200LOCs/
│
├── src/com/feast/
│   ├── model/                    # Model Layer (Data classes)
│   │   ├── Customer.java         # Customer data model
│   │   ├── SetMenu.java          # Set menu data model
│   │   └── Order.java            # Order data model
│   │
│   ├── business/                 # Business Layer (Logic classes)
│   │   ├── Customers.java        # Customer management (extends ArrayList)
│   │   ├── SetMenus.java         # Menu management (reads from CSV)
│   │   └── Orders.java           # Order management (extends HashSet)
│   │
│   ├── tools/                    # Neutral Layer (Utilities)
│   │   ├── Acceptable.java       # Validation patterns interface
│   │   ├── Inputter.java         # Keyboard input handler
│   │   └── FileUtils.java        # Generic file I/O operations
│   │
│   └── dispatcher/               # Control Layer
│       └── Main.java             # Main entry point & menu
│
├── data/                          # Data files
│   ├── FeastMenu.csv             # Menu data (6 menus)
│   ├── customers.dat             # Customer storage (auto-created)
│   └── orders.dat                # Order storage (auto-created)
│
├── build/                         # Compiled classes
│
├── README.md                      # This file
├── PROJECT_GUIDE.md              # Architecture overview
├── CODING_INSTRUCTIONS.md        # Step-by-step coding guide
├── TIPS_AND_TRICKS.md            # Fast coding techniques
├── QUICK_REFERENCE.md            # Quick reference card
│
└── 01_Guidline_for_Student_TraditionalFeastOrderManagement_LAB211.pdf
```

---

## Requirements

- **JDK**: Java 8 Update 172 or higher
- **IDE**: Apache NetBeans 13 (recommended) or any Java IDE
- **OS**: Windows, Linux, or macOS

---

## Installation

### Option 1: Clone/Copy Project
```bash
# Copy the entire project folder to your workspace
```

### Option 2: Create New NetBeans Project
1. File → New Project → Java Application
2. Project Name: `TraditionalFeastOrderManagement`
3. Create packages: `com.feast.model`, `com.feast.business`, `com.feast.tools`, `com.feast.dispatcher`
4. Copy source files from `src/`
5. Copy `FeastMenu.csv` to project root

---

## How to Run

### Using NetBeans (Recommended)
1. Open NetBeans
2. File → Open Project → Select project folder
3. Right-click project → **Run**

### Using Command Line
```bash
# Navigate to project directory
cd "D:\FPT_Ky3\LAB211\LAB_PROJECT\project 1\01_J1.L.P0028.TraditionalFeastOrderManagement__200LOCs"

# Compile all Java files
javac -d build -sourcepath src src/com/feast/**/*.java

# Run the application
java -cp build com.feast.dispatcher.Main
```

### Using Batch File (Windows)
Create `run.bat`:
```batch
@echo off
javac -d build -sourcepath src src/com/feast/**/*.java
java -cp build com.feast.dispatcher.Main
pause
```

---

## Usage Guide

### Main Menu

```
=================== MENU ===================
1.  Add new customer
2.  Update customer information
3.  Search for customer information by name
4.  Add new order
5.  Update order information
6.  Search for order information by order code
7.  Display menu list
8.  Display Customer or Order lists
9.  Save customers and orders to files
0.  Exit
============================================
```

### Example Workflows

#### Adding a Customer
```
Enter your choice: 1

--- Add New Customer ---
Customer ID (CGKxxxx): CGK1234
Customer Name (2-25 chars): Nguyen Van A
Phone (10 digits): 0123456789
Email: nguyenvana@email.com
Customer added successfully!
```

#### Placing an Order
```
Enter your choice: 4

--- Place New Order ---

Available Customers:
+------------+---------------------------+-----------------+--------------------------------+
| Customer ID| Name                      | Phone           | Email                          |
+------------+---------------------------+-----------------+--------------------------------+
| CGK1234    | Nguyen Van A              | 0123456789      | nguyenvana@email.com           |
+------------+---------------------------+-----------------+--------------------------------+

Enter Customer ID: CGK1234

Available Menus:
+----------+--------------------------------+----------------------+
| Menu ID  | Menu Name                      | Price                |
+----------+--------------------------------+----------------------+
| PW001    | Wedding party 01               | 3,750,000 VND        |
...

Enter Menu ID: PW001
Enter Province/Location: Hanoi
Enter Number of Tables: 10
Enter Event Date (dd/MM/yyyy): 15/01/2026

Order placed successfully! Order Code: 20260114110530
```

---

## Input Validation Rules

| Field | Pattern | Valid Examples | Invalid Examples |
|-------|---------|----------------|------------------|
| **Customer ID** | C/G/K + 4 digits | CGK1234, c0001, K9999 | C123, CGK123, AB1234 |
| **Name** | 2-25 characters | John, Nguyen Van A | A, (empty) |
| **Phone** | 0 + 9 digits | 0123456789, 0987654321 | 123456789, 01234567890 |
| **Email** | Standard format | user@domain.com | user@, @domain.com |
| **Menu ID** | PW + 3 digits | PW001, PW999 | P001, PW01 |
| **Tables** | Positive integer | 1, 10, 100 | 0, -5 |

---

## Technical Highlights

### Architecture: nLayers Pattern
- **Model Layer**: Data classes (Customer, SetMenu, Order)
- **Business Layer**: Business logic (Customers, SetMenus, Orders)
- **Tools Layer**: Utilities (Acceptable, Inputter, FileUtils)
- **Control Layer**: Main entry point

### OOP Principles Applied
- **Encapsulation**: Private fields with public getters/setters
- **Inheritance**: Customers extends ArrayList, Orders extends HashSet
- **Polymorphism**: Overloaded showAll() methods
- **Abstraction**: Interface with static methods

### Java Technologies Used
- **Collections**: ArrayList, HashSet, List
- **Generics**: Generic file I/O methods
- **Regex**: Pattern validation for inputs
- **Serialization**: Object file persistence (Serializable)
- **Exception Handling**: try-with-resources, try-catch
- **Date Formatting**: SimpleDateFormat for dates and codes

---

## File Descriptions

| File | LOC | Purpose |
|------|-----|---------|
| `Acceptable.java` | 19 | Interface with regex validation constants |
| `Inputter.java` | 63 | Handles keyboard input with validation |
| `FileUtils.java` | 60 | Generic read/write operations for files |
| `Customer.java` | 59 | Customer model with id, name, phone, email |
| `SetMenu.java` | 59 | Menu model with id, name, price, ingredients |
| `Order.java` | 110 | Order model with auto-generated code |
| `Customers.java` | 107 | Customer CRUD operations |
| `SetMenus.java` | 79 | CSV file reading and menu display |
| `Orders.java` | 89 | Order CRUD with duplicate prevention |
| `Main.java` | 253 | Main menu and user interaction |
| **Total** | **888** | |

---

## Documentation

- **[README.md](README.md)** - This file (project overview)
- **[PROJECT_GUIDE.md](PROJECT_GUIDE.md)** - Architecture and file descriptions
- **[CODING_INSTRUCTIONS.md](CODING_INSTRUCTIONS.md)** - Step-by-step coding guide
- **[TIPS_AND_TRICKS.md](TIPS_AND_TRICKS.md)** - Fast coding techniques and shortcuts
- **[QUICK_REFERENCE.md](QUICK_REFERENCE.md)** - Quick reference card (print-friendly)

---

## Common Issues

### Issue: "No file found" error
**Solution**: Ensure `data/FeastMenu.csv` exists in the project directory.

### Issue: "ClassNotFoundException" when loading data
**Solution**: Make sure all model classes implement `Serializable`.

### Issue: Scanner NoSuchElementException
**Solution**: Don't close Scanner wrapping System.in. Use shared scanner pattern.

### Issue: Duplicate orders still being added
**Solution**: Check that `equals()` and `hashCode()` in Order class use customerId, menuId, and eventDate.

### Issue: CSV parsing errors
**Solution**: Check for BOM (Byte Order Mark) at start of file. Remove if present.

---

## Grading Criteria (LAB211)

| Total LOC | Grade |
|-----------|-------|
| 750 - 950 | PASSED |
| 951 - 1151 | OOP-Blue YDeveloper |
| 1152 - 1352 | OOP-Yellow YDeveloper |
| 1353+ | OOP-Red YDeveloper |

**This Project LOC**: ~888 (excluding blank lines & comments)

---

## License

Educational purposes only. For FPT University LAB211 Course.

---

## Author

**Student**: [Your Name]
**Class**: [Your Class]
**Course**: LAB211 - OOP with Java Lab
**Semester**: SPRING26

---

**Last Updated**: January 2026
