module johan.projector {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;


    opens johan.projector to javafx.fxml;
    exports johan.projector;
    opens johan.projector.controllers to javafx.fxml;
    exports johan.projector.controllers;
}