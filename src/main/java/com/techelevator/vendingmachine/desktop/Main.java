/*
 * Copyright (c) 2022. Licensed under Creative Commons 4.0. See LICENSE.md for details.
 */
package com.techelevator.vendingmachine.desktop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Extends from Application to start the JavaFX application
 *
 * No Loops or "Actions" are required here because those types of things are provided behind the scenes
 * by JavaFX
 */
public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }

    /**
     * Loads the basic page layout and shows the application
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("app.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        stage.setTitle("Vendo-Matic 800");
        stage.setScene(scene);
        stage.show();
    }
}
