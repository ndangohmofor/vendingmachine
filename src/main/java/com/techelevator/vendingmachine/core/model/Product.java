/*
 * Copyright (c) 2022. Licensed under Creative Commons 4.0. See LICENSE.md for details.
 */
package com.techelevator.vendingmachine.core.model;

import java.math.BigDecimal;

/**
 * This is a POJO (Plain ole Java Object)
 *
 * It can be improved, but we haven't had those lesson's yet :-)
 */
public abstract class Product {
    private final String category;
    private final String name;
    private final BigDecimal price;
    private String slotNumber;
    private int quantity;

    public Product(String slotNumber, String name, BigDecimal price, String category, int quantity) {
        this.slotNumber = slotNumber;
        this.name = name;
        this.price = price;
        this.category = category;
        this.quantity = quantity;
    }
    abstract String getSound();

    public void removeOne(){
        quantity -= 1;
        // Probably should remove it completely as I think that is what  the require actaully says.
        if(quantity == 0) {
           slotNumber = "SOLD OUT";
        }
    }

    public String getSlotNumber() {
        return slotNumber;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Used to provide the product list print out for each Producct
     * @return
     */
    @Override
    public String toString() {
        return String.format("%s | %-20s | $%s | %5s | %3s", slotNumber, name , price , category, quantity);
    }

    public int getQuantity() {
        return quantity;
    }
    public String getCategory() {return category;}

    public void removeOne(int i) {
        quantity = i;
    }
}
