/*
 * Copyright (c) 2022. Licensed under Creative Commons 4.0. See LICENSE.md for details.
 */
package com.techelevator.vendingmachine.core.model;

import com.techelevator.vendingmachine.core.controller.InsufficientFundsException;
import com.techelevator.vendingmachine.core.service.TransactionLog;

import java.math.BigDecimal;

/**
 * There is only one transaction at any time for the vending machine. So this class
 * takes care of that one transaction.
 *
 * The repeated code for beginning and ending balances are necessary for the transaction log
 */
public class Transaction {
    private BigDecimal balance;
    private BigDecimal purchasedTotal;

    public Transaction() {
        balance = BigDecimal.ZERO;
        purchasedTotal = BigDecimal.ZERO;
    }

    public void addMoney(int money) {
        BigDecimal beginningBalance = balance;
        this.balance = balance.add(BigDecimal.valueOf(money));
        BigDecimal endingBalance = balance;
        TransactionLog.logTransaction("FEED MONEY", beginningBalance, endingBalance);
    }

    public String addPurchase(Product product) throws InsufficientFundsException {
        // This is how I am determining if there is enough money to purchse this Product
        if (balance.compareTo(product.getPrice()) < 0) {
            throw new InsufficientFundsException("There is not enough money to purchase that product.\nPlease add more money.");
        }
        purchasedTotal = purchasedTotal.add(product.getPrice());
        BigDecimal beginningBalance = balance;
        balance = balance.subtract(product.getPrice());
        BigDecimal endingBalance = balance;
        // Write this into the log
        TransactionLog.logTransaction(product.getName(), beginningBalance, endingBalance);
        return product.getSound();
    }

    public int[] makeChange() {
        // Turns something like 3.90 into 390
        int change = balance.multiply(BigDecimal.valueOf(100)).intValue();
        // 390/25 = 15.6 - but it is an int on purpose so it is 15
        int quarters = change / 25;
        // 390 - ( 15 * 25)) / 10 = 1.5 as an int it is 1
        int dimes = (change - (quarters * 25)) / 10;
        // (390 - ((15 * 25) + (1 * 10))) / 5 = 1
        int nickles = (change - ((quarters * 25) + (dimes * 10))) / 5;
        BigDecimal beginningBalance = balance;
        balance = BigDecimal.ZERO;
        BigDecimal endingBalance = balance;
        TransactionLog.logTransaction("GIVE CHANGE", beginningBalance, endingBalance);
        // 390 = {15,1,1}
        return new int[]{quarters, dimes, nickles};
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String completePurchase() {
        if (!(balance.floatValue() == 0.0)) {
            int[] change = makeChange();
            if(change != null) {
                // Formatted string with ternary tests that do:
                // if(change[0] > 0) ?  print quaters else "" - for each of quaters, dimes and nickles
                return String.format("ChaChing! Here is your change: %s%s %s%s %s%s\n\n",
                        (change[0] > 0) ? change[0] + " Quarter" : "", (change[0] > 1) ? "s" : "", (change[1] > 0) ? change[1] + " Dime" : "", (change[1] > 1) ? "s" : "", (change[2] > 0) ? change[2] + " Nickle" : "", (change[2] > 1) ? "s" : "");
            }
        }
        return "No change.";
    }


}
