/*
 * Copyright (c) 2022. Licensed under Creative Commons 4.0. See LICENSE.md for details.
 */
package com.techelevator.vendingmachine.core.service;

/**
 * Custom exception with a customized message that is normalized across classes
 */
public class InvalidMenuOptionException extends Exception {
    public InvalidMenuOptionException(String input) {
        super(input + " is not a valid option for that menu.\nPlease enter only numbers.");
    }
}
