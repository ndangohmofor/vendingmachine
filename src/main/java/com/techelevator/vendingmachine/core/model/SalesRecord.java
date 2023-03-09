/*
 * Copyright (c) 2022. Licensed under Creative Commons 4.0. See LICENSE.md for details.
 */
package com.techelevator.vendingmachine.core.model;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class SalesRecord {
    /**
     * Each product name is the key and therefore unique in the map
     * The value is simply the number of sales
     */
    private final Map<String, Integer> salesRecord = new HashMap<>();

    public int getSalesCount() {
        return salesRecord.size();
    }

    public void addSale(String name){
        if(!salesRecord.containsKey(name)) {
            salesRecord.put(name, 1);
        } else {
            salesRecord.put(name, salesRecord.get(name) + 1);
        }
    }
    public String writeSalesReport(){
        // Create a formatter for the date`
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy-hh-mm-ss");
        //Create the date
        LocalDateTime dt = LocalDateTime.now();
        // Format the date and concatenate the file extension
        String fileName = dt.format(formatter) + ".txt";
        try(PrintWriter writer = new PrintWriter(fileName)){
            for(Map.Entry<String, Integer> sale : salesRecord.entrySet()){
                // Use the printWriter to write out each map entry set
                writer.println(sale.getKey() + "|" + sale.getValue());
            }

        }catch (FileNotFoundException e){
            // This exception would be rare or impossible to reach
            System.out.println("Could not create the Sales Report because the file was not found.");
        }
        return fileName;
    }
}
