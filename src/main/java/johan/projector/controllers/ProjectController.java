package johan.projector.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import johan.projector.models.DatabaseDriver;
import johan.projector.models.Project;

public class ProjectController {
    @FXML
    private TextField titleInput;
    @FXML
    private TextArea descriptionInput;
    @FXML
    private Label errorLabel;
    @FXML
    private Button createButton;

    @FXML
    public void attemptCreate() {
        if (titleInput.getText().length() < 3) {
            errorLabel.setText("Error: project title must be at LEAST 3 characters long");
        }
        Project p = new Project(titleInput.getText(), descriptionInput.getText());
        boolean success = DatabaseDriver.getInstance().addProject(p);
        if (!success) {
            errorLabel.setText("Error in creating project");
        } else {
            createButton.getScene().getWindow().hide();
        }
    }
}
