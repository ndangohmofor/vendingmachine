package com.techelevator.vendingmachine.core.controller;

import com.techelevator.vendingmachine.core.model.ActionNames;
import com.techelevator.vendingmachine.core.model.Product;
import com.techelevator.vendingmachine.core.service.InputValidationService;
import com.techelevator.vendingmachine.core.service.InvalidMenuOptionException;
import com.techelevator.vendingmachine.core.service.MenuConfigurationService;
import com.techelevator.vendingmachine.core.view.Menu;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MenuController {
    private Menu menu;
    private final Map<ActionNames, List<String>> menus;
    private final Scanner userInput = new Scanner(System.in);

    public MenuController() {
        this("vending-machine-menus.csv");
        menu = new Menu();
    }

    public MenuController(String menuFilepath) {
        menus = MenuConfigurationService.configure(menuFilepath);
    }

    public void displayIntro() {
        menu.displayIntro();
    }

    public void displayMenu(ActionNames actionName) {
        List<String> items = menus.get(actionName);
        menu.displayMenu(items);
    }

    public void displayProducts(List<Product> inventory, boolean forPurchase) {
        menu.displayProducts(inventory, forPurchase);
        if (forPurchase) {
            System.out.println("Enter the number of the product to purchase: (enter 99 to leave)");
        }
    }

    public String retrieveMenuChoice(ActionNames currentActionNames) throws InvalidMenuOptionException {
        String input = userInput.nextLine();
        int choice = InputValidationService.validateNumericInput(input, menus.get(currentActionNames).size());
        if(choice == 99) {
            throw new InvalidMenuOptionException("blah");
        }
        return menus.get(currentActionNames).get(choice - 1);

    }

    public int retrieveProductChoice(int numberOfChoices) throws InvalidMenuOptionException {
        String input = userInput.nextLine();
        return InputValidationService.validateNumericInput(input, numberOfChoices);
    }

    public void prompt(String prompt, boolean hitEnter) {
        System.out.println(prompt);
        if (hitEnter) {
            enterKeyToContinue();
        }
    }

    public void enterKeyToContinue() {
        userInput.nextLine();
    }

    public void close() {
        userInput.close();
    }

}
