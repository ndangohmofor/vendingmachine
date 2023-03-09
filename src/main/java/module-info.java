module com.techelevator.vendingmachine {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.techelevator.vendingmachine.desktop to javafx.fxml;
    opens com.techelevator.vendingmachine.core.model to javafx.base;
    opens com.techelevator.vendingmachine.core.service to javafx.base;
    opens com.techelevator.vendingmachine.core.view to javafx.base;
    opens com.techelevator.vendingmachine.core.controller to javafx.base;
    exports com.techelevator.vendingmachine.desktop;
}
