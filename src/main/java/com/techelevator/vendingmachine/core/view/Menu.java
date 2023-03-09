/*
 * Copyright (c) 2022. Licensed under Creative Commons 4.0. See LICENSE.md for details.
 */
package com.techelevator.vendingmachine.core.view;

import com.techelevator.vendingmachine.core.model.Product;

import java.util.List;

public class Menu {

    public void displayMenu(List<String> items) {
        for (int i = 1; i <= items.size(); i++) {
            if(!items.get(i-1).equals("Sales Report")) {
                System.out.printf("(%s) %s\n", i, items.get(i - 1));
            }
        }
        System.out.print("\nPlease enter the number of your choice: ");
    }

    public void displayProducts(List<Product> inventory, boolean forPurchase) {
        for (int i = 0; i < inventory.size(); i++) {
            if(forPurchase){
                if(inventory.get(i).getSlotNumber().equals("SOLD OUT")) {
                    System.out.print("(##) ");
                } else {
                    if(i < 9) {
                        System.out.print("( " + (i + 1) + ") ");
                    } else {
                        System.out.print("(" + (i + 1) + ") ");
                    }
                }
            }
            System.out.println(inventory.get(i));
        }
    }

    public void displayIntro() {
        String banner = "\n" +
                "#=====================================================#\n" +
                "#   Welcome to the Vendo-Matic 800 from Ubrella Corp. #\n" +
                "#=====================================================#\n";
        System.out.print(banner);
    }


}
