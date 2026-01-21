/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab1.bussiness;

/**
 * Generic interface cho business layer
 * Dam bao nhat quat giua cac class Customers va Orders
 * @param <T> - Loai du lieu (Customer, Order, ...)
 */
public interface Workable<T> {
    // Them moi doi tuong
    void addNew(T x);

    // Cap nhat doi tuong
    void update(T x);

    // Tim kiem theo ID
    T searchById(String id);

    // Hien thi tat ca
    void showAll();
}
