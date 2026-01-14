/*
 * FileUtils class for generic file I/O operations
 */
package com.feast.tools;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for handling file read/write operations
 * Uses generic methods to support different data types
 */
public class FileUtils {

    /**
     * Generic method to read objects from file
     *
     * @param <T>       the type of objects to read
     * @param filePath  the path to the file
     * @return a list of objects read from the file
     */
    public static <T> List<T> readFromFile(String filePath) {
        List<T> list = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            return list;
        }

        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            while (fis.available() > 0) {
                @SuppressWarnings("unchecked")
                T obj = (T) ois.readObject();
                list.add(obj);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }

        return list;
    }

    /**
     * Generic method to save objects to file
     *
     * @param <T>      the type of objects to save
     * @param list     the list of objects to save
     * @param filePath the path to the file
     */
    public static <T> void saveToFile(List<T> list, String filePath) {
        File file = new File(filePath);

        // Create parent directories if they don't exist
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            for (T item : list) {
                oos.writeObject(item);
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * Reads text from a file line by line
     *
     * @param filePath the path to the text file
     * @return a list of strings (lines from the file)
     */
    public static List<String> readTextFile(String filePath) {
        List<String> lines = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            return lines;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading text file: " + e.getMessage());
        }

        return lines;
    }
}
