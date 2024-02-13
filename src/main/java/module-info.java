module johan.projector {
    requires javafx.controls;
    requires javafx.fxml;


    opens johan.projector to javafx.fxml;
    exports johan.projector;
    exports johan.projector.controllers;
    opens johan.projector.controllers to javafx.fxml;
}