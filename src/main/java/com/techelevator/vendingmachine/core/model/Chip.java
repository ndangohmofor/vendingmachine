package com.techelevator.vendingmachine.core.model;

import java.math.BigDecimal;

public class Chip extends Product {

    public Chip(String slotNumber, String name, BigDecimal price, String category, int quantity) {
        super(slotNumber, name, price, category, quantity);
    }

    @Override
    String getSound() {
        return "Crunch Crunch, Yum!";
    }
}
