# Quick Reference Card - Print This!

## Regex Patterns (Copy These!)
```java
// Customer ID: CGK + 4 digits
String CUS_ID = "^[CcGgKk]\\d{4}$";

// Name: 2-25 chars
String NAME = "^.{2,25}$";

// Phone: 0 + 9 digits
String PHONE = "^0\\d{9}$";

// Email: standard
String EMAIL = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";

// Menu ID: PW + 3 digits
String MENU = "^PW\\d{3}$";

// Positive integer
String INT = "^[1-9]\\d*$";

// Positive double
String DBL = "^[1-9]\\d*(\\.\\d+)?$";
```

## NetBeans Shortcuts
```
Alt+Insert    Generate code
Ctrl+Space    Auto-complete
Ctrl+Shift+I Fix imports
Ctrl+/        Comment
Alt+Shift+F   Format
```

## File Structure
```
model/        - Customer, SetMenu, Order
business/     - Customers, SetMenus, Orders
tools/        - Acceptable, Inputter, FileUtils
dispatcher/   - Main
```

## Model Class Template
```java
public class ClassName implements Serializable {
    private type field;

    public ClassName() {}
    public ClassName(type field) { this.field = field; }

    public type getField() { return field; }
    public void setField(type field) { this.field = field; }

    @Override
    public String toString() { return "format"; }
}
```

## Business Class Template
```java
public class Classes extends ArrayList<Class> {
    public void addNew(Class x) { this.add(x); }
    public void update(Class x) { /* find and set */ }
    public Class searchById(String id) { /* loop and return */ }
    public void showAll() { /* print header, items, footer */ }
}
```

## CSV Read Template
```java
String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
if (str.startsWith("\"")) str = str.substring(1, str.length()-1);
```

## Generic File I/O Template
```java
// Read
try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
    while (available > 0) list.add((T) ois.readObject());
}

// Write
try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
    for (T item : list) oos.writeObject(item);
}
```

## Date Template
```java
// Parse
SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
Date date = sdf.parse(dateString);

// Format code
SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
String code = sdf.format(new Date());
```

## Main Menu Template
```java
do {
    displayMenu();
    choice = Integer.parseInt(scanner.nextLine());
    switch (choice) {
        case 1: function1(); break;
        // ...
        case 0: break;
    }
} while (choice != 0);
```

## Common Imports
```java
import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Date;
```
