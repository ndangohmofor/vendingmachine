package com.techelevator.vendingmachine.core.model;

import java.math.BigDecimal;

public class Gum extends Product {

    public Gum(String slotNumber, String name, BigDecimal price, String category, int quantity) {
        super(slotNumber, name, price, category,quantity);
    }

    @Override
    String getSound() {
        return "Chew Chew, Yum!";
    }

}
