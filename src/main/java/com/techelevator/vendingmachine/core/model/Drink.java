package com.techelevator.vendingmachine.core.model;

import java.math.BigDecimal;

public class Drink extends Product {

    public Drink(String slotNumber, String name, BigDecimal price, String category, int quantity) {
        super(slotNumber, name, price, category, quantity);
    }

    @Override
    String getSound() {
        return "Glug Glug, Yum!";
    }
}
