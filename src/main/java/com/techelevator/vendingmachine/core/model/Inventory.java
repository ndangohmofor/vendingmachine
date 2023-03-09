/*
 * Copyright (c) 2022. Licensed under Creative Commons 4.0. See LICENSE.md for details.
 */
package com.techelevator.vendingmachine.core.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * An advanced way of creating classes from a CSV file
 */
public class Inventory {
    private final List<Product> inventory;

    public Inventory(String inventoryFilePath) {
        inventory = new ArrayList<>();
        File f = new File(inventoryFilePath);
        Class productType = null;
        try(Scanner fs = new Scanner(f)){
            while(fs.hasNext()){
                // Read and parse a line int String[]
                String[] product = fs.nextLine().split("\\|");
                // Create a Class from the Product category (Candy, Chip, Dring, Gum)
                Class n = Class.forName("com.techelevator.vendingmachine.core.model."+product[3]);
                // G
               // Class n1 = n.getClass();
                // Create a constructor that is in the same form as the Class just created
                // This is a Product contstructor
                Constructor constructor = n.getConstructor(new Class[]{String.class,String.class,BigDecimal.class,String.class, int.class});
                // Use the created constructor to create a new Object and the cast it to a Product type
                Product p = (Product) constructor.newInstance(product[0],product[1],new BigDecimal(product[2]),product[3],5);
                // Add that Product to the inventory
                inventory.add(p);
            }
            // Lots of things can go wrong - they all end in the same result - no inventory
        } catch (FileNotFoundException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Inventory file cannot be loaded. \n" + e.getMessage());
        }

    }
    public List<Product> getInventory() {
        return inventory;
    }
}
