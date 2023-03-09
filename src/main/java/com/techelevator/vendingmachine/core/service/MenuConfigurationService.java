/*
 * Copyright (c) 2022. Licensed under Creative Commons 4.0. See LICENSE.md for details.
 */
package com.techelevator.vendingmachine.core.service;

import com.techelevator.vendingmachine.cli.VendingMachine;
import com.techelevator.vendingmachine.core.model.ActionNames;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * An example of reading a csv file into a List object
 *
 * This also demonstrates how a more complex data structure can be used because the
 * List becomes the values of a Map object.
 *
 * This map contains the Action names (MAIN, PURCHASE, etc) as keys and
 * and Lists of menu items as values for each
 */
public abstract class MenuConfigurationService {
    protected static final Map<ActionNames, List<String>> menus = new HashMap<>();

    public static Map<ActionNames, List<String>> configure(String menuFilePath) {
        try {
            File menuFile = new File(VendingMachine.class.getClassLoader().getResource(menuFilePath).getFile());
            try (Scanner menufileScanner = new Scanner(menuFile)) {
                while (menufileScanner.hasNext()) {
                    // A single line reads a line from file, parses it into an array and places that
                    // directly into a list with Arrays.asList()
                    List<String> menu = new ArrayList<>(Arrays.asList(menufileScanner.nextLine().split(",")));
                    // the .valueOf("MAIN") of an Enum type will return the Enum with that name
                    ActionNames name = ActionNames.valueOf(menu.get(0));
                    // Remove the Action because it is not in the menu
                    menu.remove(0);
                    List<String> items = new ArrayList<>(menu);
                    // Add the List (now without the Action) as the value for this Action
                    menus.put(name, items);
                }
            }
            // If anything goes wrong, the result is the same - we don't get a menu
        } catch (FileNotFoundException | NullPointerException e) {
            System.out.println("The menu could not be constructed because the menu file could not be found. Exiting");
            System.exit(1);
        }
        return menus;
    }

}
