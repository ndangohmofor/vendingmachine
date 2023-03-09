/*
 * Copyright (c) 2022. Licensed under Creative Commons 4.0. See LICENSE.md for details.
 */
package com.techelevator.vendingmachine.core.service;

import com.techelevator.vendingmachine.core.model.ActionNames;

import java.util.Map;

import static com.techelevator.vendingmachine.core.model.ActionNames.MAIN;

/**
 * Provides a lookup service that returns the action given a menu choice String
 * See the ActionConfigurationService and the ActionNames Enum itself for further explanation
 */
public class ActionService {
    private ActionNames currentActionNames;
    private final Map<String, ActionNames> actions;

    public ActionService(String fileName) {
        currentActionNames = MAIN;
        actions = ActionConfigurationService.build(fileName);
    }

    public ActionNames getNextAction(String choice) {
        return actions.get(choice);
    }

    public ActionNames getCurrentAction() {
        return currentActionNames;
    }

    public void setCurrentAction(ActionNames newActionNames) {
        this.currentActionNames = newActionNames;
    }

}
