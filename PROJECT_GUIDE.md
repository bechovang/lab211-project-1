# Traditional Feast Order Management System - Project Guide

## Project Overview
A Java console application for managing feast orders with customers, menus, and orders.

## Project Structure (nLayers Architecture)

```
TraditionalFeastOrderManagement__200LOCs/
├── src/
│   └── com/
│       └── feast/
│           ├── model/          [Model Layer - Data classes]
│           ├── business/       [Business Layer - Logic classes]
│           ├── tools/          [Neutral Layer - Utility classes]
│           └── dispatcher/     [Control Layer - Main class]
├── data/
│   └── FeastMenu.csv          [Menu data file]
└── build/                      [Compiled .class files]
```

## File Description

| File | Package | LOC | Purpose |
|------|---------|-----|---------|
| Acceptable.java | tools | 19 | Interface with regex validation patterns |
| Inputter.java | tools | 63 | Keyboard input with validation |
| FileUtils.java | tools | 60 | Generic file I/O operations |
| Customer.java | model | 59 | Customer data model |
| SetMenu.java | model | 59 | Set menu data model |
| Order.java | model | 110 | Order data model (with auto-code generation) |
| Customers.java | business | 107 | Customer management (extends ArrayList) |
| SetMenus.java | business | 79 | Menu management (reads from CSV) |
| Orders.java | business | 89 | Order management (extends HashSet, no duplicates) |
| Main.java | dispatcher | 253 | Main menu and user interaction |

## Key Technical Concepts Used

1. **Encapsulation**: Private fields, public getters/setters
2. **Inheritance**: Customers extends ArrayList, Orders extends HashSet
3. **Polymorphism**: Overloaded methods (showAll)
4. **Interface**: Acceptable with static methods
5. **Generics**: FileUtils with generic methods
6. **Regex**: Pattern validation in Acceptable
7. **Serialization**: Object file I/O
8. **Collection Framework**: ArrayList, HashSet, List
