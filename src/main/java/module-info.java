module johan.projector {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires java.sql;


    opens johan.projector to javafx.fxml;
    exports johan.projector;
    opens johan.projector.controllers to javafx.fxml;
    exports johan.projector.controllers;
}