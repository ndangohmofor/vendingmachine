/*
 * Copyright (c) 2022. Licensed under Creative Commons 4.0. See LICENSE.md for details.
 */
package com.techelevator.vendingmachine.cli;

import com.techelevator.vendingmachine.core.model.ActionNames;

public class Main {
    /**
     * This static as it is used in the static context of Main()
     */
    private static final VendingMachine vendingMachine = new VendingMachine();

    public static void main(String[] args) {
        // The default menu is the main menu
        ActionNames actionName = ActionNames.MAIN;
        // Loop forever
        while (true) {
            // Return the next action to be taken and then call
            // performAction and pass that action right back in
            // Writing it this way allows me to easily test
            // the vendingMachine.performAction() method
            // with any/all actions in the application
           actionName = vendingMachine.performAction(actionName);
        }
    }
}
