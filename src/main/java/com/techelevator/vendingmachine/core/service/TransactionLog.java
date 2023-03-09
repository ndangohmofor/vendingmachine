/*
 * Copyright (c) 2022. Licensed under Creative Commons 4.0. See LICENSE.md for details.
 */
package com.techelevator.vendingmachine.core.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Uses the FileWriter object to append log entries into the transaction log
 *
 */
public class TransactionLog {
    private static final File logFile = new File("Log.txt");
    private TransactionLog(){}

    /**
     * The beginning and ending balance are calculated by the transaction class
     * The action is the required description for the log
     * The date formatter is used to create the correct date format for the log.
     *
     * @param action
     * @param beginningBalance
     * @param endingBalance
     */
    public static void logTransaction(String action, BigDecimal beginningBalance, BigDecimal endingBalance){
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");
        String report = dateTime.format(formatter).toUpperCase()+ " " + action.toUpperCase() +": $"+ beginningBalance.setScale(2, RoundingMode.HALF_UP) + " $" + endingBalance.setScale(2, RoundingMode.HALF_UP) + "\n";
        try(FileWriter writer = new FileWriter(logFile, true)){
            writer.write(report);
            // A FileWriter should be flushed when we are done writing to it
            writer.flush();
        }catch (IOException e){
            // this exception would be rare or impossible to reach
            System.out.println("Cannot log report since the log file cannot be found!");
        }
    }
}
