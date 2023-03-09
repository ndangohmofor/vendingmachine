/*
 * Copyright (c) 2022. Licensed under Creative Commons 4.0. See LICENSE.md for details.
 */
package com.techelevator.vendingmachine.core.service;

import com.techelevator.vendingmachine.core.model.ActionNames;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * An example of reading a CSV file into a Map
 *
 * Configures the bindings between menu choices and tha actions they invoke
 * Review the vending-machine-menu-actions.csv in the resources folder to see what goes into the map
 */
public abstract class ActionConfigurationService {
    protected static Map<String, ActionNames> build(String mapFilePath) {
        Map<String, ActionNames> actions = new HashMap<>();
        try {
            File mapFile = new File(ClassLoader.getSystemResource(mapFilePath).getFile());
            try (Scanner mapperFileScanner = new Scanner(mapFile)) {
                while (mapperFileScanner.hasNext()) {
                    String[] map = mapperFileScanner.nextLine().split(",");
                    actions.put(map[0], ActionNames.valueOf(map[1]));
                }
            }
        } catch (FileNotFoundException | NullPointerException e) {
            System.out.println("The actions could not be constructed because the action configuration file could not be found. Exiting");
            System.exit(1);
        }
        return actions;
    }
}
