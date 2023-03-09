/*
 * Copyright (c) 2022. Licensed under Creative Commons 4.0. See LICENSE.md for details.
 */
package com.techelevator.vendingmachine.desktop;

import com.techelevator.vendingmachine.core.controller.InsufficientFundsException;
import com.techelevator.vendingmachine.core.model.Inventory;
import com.techelevator.vendingmachine.core.model.Product;
import com.techelevator.vendingmachine.core.model.SalesRecord;
import com.techelevator.vendingmachine.core.model.Transaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * Acts as the front controller for the GUI version
 * Replaces the VendingMachine class of the CLI
 * Is nothing special - just did this to demonstrate why a good, loosely coupled design allows for reuse.
 *
 * The FXML annotations bind view bits and pieces to these controller properties and methods
 */
public class VendingMachine {
    @FXML
    private VBox container;
    /**
     * Shows the current balance of the transaction
     */
    @FXML
    private Label currentBalance;
    /**
     * Holds the inventory just exactly like the CLI, but in a different data structure
     * This data structure watches for changes and places them into the table automatically.
     */
    private ObservableList<Product> inventory;
    /**
     * For messages - because I don't have the console to print into :-)
     */
    Popup popup = new Popup();
    private SalesRecord salesRecord = new SalesRecord();
    /**
     * It's a GUI, so I can use a fancy table for the products
     */
    @FXML
    private TableView table;
    private Transaction transaction = new Transaction();

    /**
     * Grabs the inventory and places it into the table, then adds the Purchase buttons to the table rows
     */
    public void initialize() {
        try {
            String pathToInventory = getClass().getResource("vending-machine.csv").getPath();
            inventory = FXCollections.observableList(new Inventory(pathToInventory).getInventory());
        } catch (RuntimeException e) {
            showPopup(e.getMessage());
        }

        table.setItems(inventory);
        addButtonToTable();
    }

    /**
     * These methods are the simplest way to deal with the buttons.
     */
    @FXML
    protected void addOneDollar() {
        transaction.addMoney(1);
        updateProvidedMoney();
    }

    @FXML
    protected void addTwoDollars() {
        transaction.addMoney(2);
        updateProvidedMoney();
    }

    @FXML
    protected void addFiveDollars() {
        transaction.addMoney(5);
        updateProvidedMoney();
    }

    @FXML
    protected void addTenDollars() {
        transaction.addMoney(10);
        updateProvidedMoney();
    }

    /**
     // Set up CTRL-S for hidden menu
     // This sets up a keyboard event "listener" that waits for the right key combo and then
     // reacts to it...
     */
    private void enableSalesReport(){
        container.getScene().addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            final KeyCombination keyComb = new KeyCodeCombination(KeyCode.S,
                    KeyCombination.CONTROL_DOWN);
            public void handle(KeyEvent ke) {
                if (keyComb.match(ke)) {
                    salesRecord.writeSalesReport();
                    ke.consume(); // <-- stops passing the event to next node
                }
            }
        });

    }



    @FXML
    protected void doExit(ActionEvent event) {
        Stage stage = (Stage) container.getScene().getWindow();
        stage.close();
    }

    /**
     * Shows the 'ChaChing!' message and updates the display for the money - just like the CLI
     */
    @FXML
    protected void completePurchase() {
        showPopup(transaction.completePurchase());
        updateProvidedMoney();
    }

    /**
     * This is almost exactly the same as the CLI.
     * Add the sale the to report
     * Show the 'Yum!' message
     * Remove one of the 5 product items
     * update the dipslay for the money
     *
     *
     * @param product
     * @param index
     */
    private void purchaseItem(Product product, int index) {
        try {
            if (product.getQuantity() > 0) {
                salesRecord.addSale(product.getName());
                enableSalesReport();
                showPopup(transaction.addPurchase(product));
                product.removeOne();
                inventory.set(index, product);
                updateProvidedMoney();
            } else {
                showPopup("That product is Sold Out.");
            }
        } catch (InsufficientFundsException e) {
            showPopup(e.getMessage());
        }
    }

    /**
     * change the balance amount display
     */
    private void updateProvidedMoney() {
        currentBalance.setText("$" + transaction.getBalance().setScale(2).toString());
    }

    /**
     * This is significantly different because of JavaFX and the table - this does not exist in the CLI
     * This adds the button to each row of the table and then allows me to retrieve the product represented
     * by that row
     */
    private void addButtonToTable() {
        TableColumn<Product, Void> colBtn = new TableColumn("$");

        Callback<TableColumn<Product, Void>, TableCell<Product, Void>> cellFactory = new Callback<TableColumn<Product, Void>, TableCell<Product, Void>>() {
            @Override
            public TableCell<Product, Void> call(final TableColumn<Product, Void> param) {
                final TableCell<Product, Void> cell = new TableCell<Product, Void>() {
                    private final Button btn = new Button("Purchase");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Product product = getTableView().getItems().get(getIndex());
                            purchaseItem(product, getIndex());
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        colBtn.setCellFactory(cellFactory);

        table.getColumns().add(colBtn);

    }

    /**
     * Show the messages in a popup!
     *
     * @param message
     */
    private void showPopup(String message) {
        Stage popupwindow = new Stage();
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Alert");
        Label notice = new Label(message);
        Button close = new Button("Close");
        close.setOnAction(e -> popupwindow.close());
        VBox layout = new VBox(10);
        layout.getChildren().addAll(notice, close);
        layout.setAlignment(Pos.CENTER);
        popupwindow.setScene(new Scene(layout, 300, 250));
        popupwindow.showAndWait();
    }

}
