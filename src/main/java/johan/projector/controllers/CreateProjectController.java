package johan.projector.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class CreateProjectController {
    @FXML
    private Button createButton;

    @FXML
    public void attemptCreate() {
        createButton.getScene().getWindow().hide();
    }
}
