/*
 * Copyright (c) 2022. Licensed under Creative Commons 4.0. See LICENSE.md for details.
 */
package com.techelevator.vendingmachine.cli;

import com.techelevator.vendingmachine.core.controller.InsufficientFundsException;
import com.techelevator.vendingmachine.core.controller.MenuController;
import com.techelevator.vendingmachine.core.model.*;
import com.techelevator.vendingmachine.core.service.ActionService;
import com.techelevator.vendingmachine.core.service.InvalidMenuOptionException;

import java.math.RoundingMode;
import java.util.List;

import static com.techelevator.vendingmachine.core.model.ActionNames.*;

/**
 * This class acts as what is called a "Front Controller"
 * It accepts an Action to take and simply delegates to other classes to accomplish the goals of the action
 * This class uses Compostion and creates "has-a" relationships with each class that is delegated to
 *
 * There is some logic in this class that I would rather not be here...perhpas in a refactor someday :-)
 */
public class VendingMachine {
    private final ActionService actions;
    private final List<Product> inventory;
    private final MenuController menuController;
    private final SalesRecord salesRecord;
    private final Transaction transaction;

    public VendingMachine() {
        inventory = new Inventory("vending-machine.csv").getInventory();
        actions = new ActionService("vending-machine-menu-actions.csv");
        menuController = new MenuController();
        transaction = new Transaction();
        salesRecord = new SalesRecord();
    }

    /**
     * This is the only method in the class. It is primarily a very large switch statement that switches on
     * the action that is passed along from the main method.
     * @param actionName
     * @return
     */
    public ActionNames performAction(ActionNames actionName) {
        // Set to Main action in case nothing is passed in
        actionName = (actionName == null) ? MAIN : actionName;
        // Store the choice typed into the console by the user
        String menuChoice;
        if (!actionName.equals(EXIT)) {
            menuController.displayIntro();
        }
        // The app can throw several types of Exceptions, so I catch them all in one catch() clause
        try {
            switch (actionName) {
                // Gracefully close down the app
                case EXIT: {
                    // close the Scanner
                    menuController.close();
                    System.out.println("Thank you for buying our products! Good bye.");
                    System.exit(0);
                }
                // Delegate to menuController to Display view only list of products (2nd argument is false)s
                    // get next action to take (which menu to show)
                    // delegate to menuController
                case DISPLAY_ITEMS: {
                    menuController.displayProducts(inventory, false);
                    menuController.prompt("Press enter to continue", true);
                    actionName = actions.getCurrentAction();
                    break;
                }
                // Display the Purchase - delegate to menuController
                    // show the balance
                    // wait for the user to type a choice
                    // get next action
                case PURCHASE: {
                    actions.setCurrentAction(PURCHASE);
                    menuController.prompt("Current Money Provided: $" + transaction.getBalance().setScale(2, RoundingMode.HALF_UP), false);
                    menuController.displayMenu(PURCHASE);
                    menuChoice = menuController.retrieveMenuChoice(actions.getCurrentAction());
                    actionName = actions.getNextAction(menuChoice);
                    break;
                }
                // Both Feed and Add_Money actions display the Feed menu - this allows the "continually add money" requirement
                    // if the choice contains "$" ($1, $2, $5, $10) the delegate to transaction to add_money
                    // delegate to menuController
                case FEED:
                case ADD_MONEY: {
                    actions.setCurrentAction(FEED);
                    menuController.prompt("Current Money Provided: $" + transaction.getBalance().setScale(2, RoundingMode.HALF_UP), false);
                    menuController.displayMenu(FEED);
                    menuChoice = menuController.retrieveMenuChoice(actions.getCurrentAction());
                    if (menuChoice.contains("$")) {
                        transaction.addMoney(Integer.parseInt(menuChoice.split("\\$")[1]));
                    }
                    actionName = actions.getNextAction(menuChoice);
                    break;
                }
                // Delegate to menuController to display products for purchase (second argument is true)
                case SELECT_PRODUCT: {
                    menuController.displayProducts(inventory, true);
                    menuController.prompt("Current balance: $" + transaction.getBalance().setScale(2, RoundingMode.HALF_UP) + "\n", false);
                    // passing in the size of the inventory to limit the range of valid input values
                    int choice = menuController.retrieveProductChoice(inventory.size());
                    // entering 99 allows user to escape purchse menu without buying anything
                    if (choice == 99) {
                        actionName = PURCHASE;
                        break;
                    }
                    try {
                        // have to reduce choice by one beause list starts at 1 and inventory starts at zero
                        Product productToPurchase = inventory.get(choice - 1);
                        // If we have enough to buy...
                        if (productToPurchase.getQuantity() > 0) {
                            // Delegate to salesRecord
                            salesRecord.addSale(productToPurchase.getName());
                            // Print Product Sound
                            System.out.println(transaction.addPurchase(productToPurchase));
                            // delegate to the Product itself to reduce quantity
                            productToPurchase.removeOne();
                        } else {
                            // Product is sold out - delegate to menuController to notify the user
                            menuController.prompt("That product is Sold Out.", false);
                        }
                        // obviously not enough money...
                    } catch (InsufficientFundsException e) {
                        menuController.prompt(e.getMessage(), true);
                    }
                    actionName = actions.getCurrentAction();
                    break;
                }
                // Delegate to salesRecord, transaction and menuController to carry out the purchase
                case MAKE_PURCHASE:
                case FINISH_TRANSACTION: {
                    // If there are no records in the salesRecord, there is not transaction to complete
                    if (salesRecord.getSalesCount() != 0) {
                        // Delegate to transaction to complete purchase
                        String message = transaction.completePurchase();
                        // Should be change to return...
                        if (message != null) {
                            // Show the quarters, dimes and nickles
                            menuController.prompt(message + " Press enter to continue...", false);
                        }
                    }
                    actionName = MAIN;
                    break;
                }
                // delegate to salesRecord to create the report
                case SALES_REPORT: {
                    actionName = MAIN;
                    String fileName = salesRecord.writeSalesReport();
                    menuController.prompt("Sales report has been prepared in file name: " + fileName + ".\nPress enter to continue...", true);
                    break;
                }
                // MAIN action, main menu
                default: {
                    actions.setCurrentAction(MAIN);
                    menuController.displayMenu(MAIN);
                    menuChoice = menuController.retrieveMenuChoice(actions.getCurrentAction());
                    actionName = actions.getNextAction(menuChoice);
                }
            }
            // Catches anything that does not appear in a menu and shows a message
        } catch (InvalidMenuOptionException exception) {
            menuController.prompt(exception.getMessage() + " Press enter to continue...", true);
            actionName = MAIN;
        }
        return actionName;
    }

}
