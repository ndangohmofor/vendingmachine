/*
 * Copyright (c) 2022. Licensed under Creative Commons 4.0. See LICENSE.md for details.
 */
package com.techelevator.vendingmachine.core.service;

/**
 * An example of removing repeated code into a class that provides simple validation services
 */
public abstract class InputValidationService {

    /**
     * The input is parsed as a string or throws - so no "Strings" are allowed since they won't parse into and integer
     * The numberOfChoices is used to ensure a too-large number is not allowed.
     *
     * @param input
     * @param numberOfChoices
     * @return
     * @throws InvalidMenuOptionException
     */
    public static int validateNumericInput(String input, int numberOfChoices) throws InvalidMenuOptionException {
        int choice;
        try {
            choice = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new InvalidMenuOptionException(input);
        }
        if(choice == 99){
            return 99;
        }
        if (choice < 1 || choice > numberOfChoices) {
            throw new InvalidMenuOptionException(input);
        }
        return choice;
    }

}
