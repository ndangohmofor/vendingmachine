package com.techelevator.vendingmachine.core.model;

import java.math.BigDecimal;

public class Candy extends Product {

    public Candy(String slotNumber, String name, BigDecimal price, String category, int quantity) {
        super(slotNumber, name, price, category, quantity);
    }

    @Override
    String getSound() {
        return "Munch Munch, Yum!";
    }
}
