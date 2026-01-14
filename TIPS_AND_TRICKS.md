# Tips and Tricks for Fast Coding

## NetBeans Keyboard Shortcuts (MUST KNOW)

| Shortcut | Action | Time Saved |
|----------|--------|------------|
| `Alt + Insert` | Generate code (Getters, Setters, Constructors, equals, hashCode) | **HUGE!** |
| `Ctrl + Space` | Code completion | Essential |
| `Ctrl + Shift + I` | Fix imports | Essential |
| `Ctrl + /` | Comment/uncomment line | Very useful |
| `Ctrl + D` | Delete line | Handy |
| `Tab` | Indent selected lines | Formatting |
| `Shift + Tab` | Unindent selected lines | Formatting |
| `Ctrl + C/V/X` | Copy/Paste/Cut | Standard |
| `Ctrl + Z/Y` | Undo/Redo | Standard |
| `Ctrl + F` | Find in file | Useful |
| `Ctrl + H` | Find and replace | Very useful |
| `Alt + Shift + F` | Format code | Cleanup |
| `Alt + Up/Down` | Move line up/down | Quick reorg |

## Code Generation with Alt+Insert (THE BIGGEST TIME SAVER!)

### For Model Classes:
```
1. Type fields: private String id; private String name;
2. Press Alt+Insert → Constructor → Select all fields → OK
3. Press Alt+Insert → Getter and Setter → Select all → OK
4. Press Alt+Insert → toString() → Select fields → OK
5. (For Order) Alt+Insert → equals() and hashCode() → Select fields → OK
```
**Result:** Complete model class in 30 seconds!

### For Business Classes:
```
1. Type: extends ArrayList<Customer>
2. Alt+Insert → Constructor → Create default constructor
3. Type methods manually (they follow similar patterns)
```

## Copy-Paste Templates (Create Your Own Snippets)

### Getters/Setters Pattern:
```java
// Type once, copy, modify field name and type
private String fieldName;

public String getFieldName() { return fieldName; }
public void setFieldName(String fieldName) { this.fieldName = fieldName; }
```

### toString() Pattern:
```java
@Override
public String toString() {
    return String.format("| %-10s | %-20s |", field1, field2);
}
```

### Validation Loop Pattern:
```java
while (true) {
    String input = scanner.nextLine();
    if (valid) return input;
    System.out.println("Invalid!");
}
```

### Search Pattern:
```java
for (Item item : this) {
    if (item.getId().equals(id)) return item;
}
return null;
```

### Display Pattern:
```java
if (this.isEmpty()) { System.out.println("Empty!"); return; }
System.out.println(Item.getHeader());
for (Item item : this) System.out.println(item);
System.out.println(Item.getFooter());
```

## Regex Patterns to Memorize

```java
// Customer ID: C/G/K (case insensitive) + 4 digits
"^[CcGgKk]\\d{4}$"

// Name: 2-25 characters (any)
"^.{2,25}$"

// Phone: 0 + 9 digits
"^0\\d{9}$"

// Email: basic format
"^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$"

// Menu ID: PW + 3 digits
"^PW\\d{3}$"

// Positive integer (no 0 at start)
"^[1-9]\\d*$"

// Positive double
"^[1-9]\\d*(\\.\\d+)?$"
```

## File I/O Pattern (Copy, Don't Type!)

```java
// Read Object File - Use this template everywhere
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

// Write Object File - Use this template everywhere
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

## CSV Parsing Pattern

```java
// Split comma, respect quotes
String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

// Remove BOM (if present)
if (line.startsWith("\uFEFF"))
    line = line.substring(1);

// Remove surrounding quotes
if (str.startsWith("\"") && str.endsWith("\""))
    str = str.substring(1, str.length() - 1);
```

## Date Handling Pattern

```java
// Parse date
private Date parseDate(String dateStr) {
    try {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        return sdf.parse(dateStr);
    } catch (Exception e) {
        return null;
    }
}

// Generate timestamp code
private String generateCode() {
    Date now = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
    return sdf.format(now);
}
```

## Fast Coding Workflow

### 1. Setup Phase (do once)
```
- Create folder structure
- Copy Acceptable interface (it's the same for every project)
- Copy FileUtils class (it's reusable)
- Copy Inputter class (it's reusable)
```

### 2. Model Classes (15 min each)
```
- Type field declarations
- Alt+Insert → Constructor → All fields
- Alt+Insert → Getters and Setters → All fields
- Type toString() manually (it's specific to your display format)
- (If needed) Alt+Insert → equals() and hashCode()
```

### 3. Business Classes (20 min each)
```
- Copy from similar class
- Find/Replace to change class names and types
- Modify specific logic
```

### 4. Main Class (30 min)
```
- Start with menu display
- Add menu loop with switch
- Implement functions one by one
- Test after each function
```

## Debugging Tips

### 1. Use Print Statements
```java
System.out.println("Debug: var = " + variable);
```

### 2. Test Each Function
```java
// In main, test specific function directly
addNewCustomer();
// Test it before moving to next function
```

### 3. Common Errors to Watch For:
```
- Missing: import java.io.*;
- Missing: implements Serializable
- Scanner closed too early (affects System.in)
- equals() not overridden for Set collections
- Wrong file path (use relative: "data/file.dat")
```

## Testing Strategy

### 1. Test Menu Display First
```
- Run program
- Check if menu appears
- Check if options work
```

### 2. Test File Loading
```
- Put test data in files
- Check if data loads correctly
- Print list size
```

### 3. Test CRUD Operations
```
- Add → Check if added
- Search → Check if found
- Update → Check if changed
- Display → Check format
- Save → Check file exists
```

### 4. Edge Cases to Test:
```
- Empty list
- Duplicate ID
- Invalid input (wrong format)
- File not exists
- Very long names
```

## Time-Saving Techniques

### 1. Build One Complete Pattern First
```
- Finish Customer class completely
- Finish Customers class completely
- Then use it as template for Order/Orders
```

### 2. Use Find/Replace for Mass Changes
```
- Ctrl+H → Replace "Customer" with "Order"
- Be careful! Review each replacement
```

### 3. Comment Out Complex Code
```
// Comment out, implement simple version first
// Then add complexity gradually
```

### 4. Use TODO Comments
```java
// TODO: Add validation
// TODO: Handle this case
// TODO: Fix this logic
```

## Before Submission Checklist

- [ ] All files compile without errors
- [ ] All methods have comments
- [ ] Naming conventions followed
- [ ] No redundant imports
- [ ] No unused variables
- [ ] Exception handling in place
- [ ] File I/O tested
- [ ] All menu functions work
- [ ] Validation works
- [ ] Duplicate prevention works
- [ ] Code formatted cleanly

## Practice Makes Perfect!

**First project:** 4-5 hours
**Second project:** 2-3 hours
**Third project:** 1-2 hours

The key is building a library of reusable patterns!
