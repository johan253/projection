package johan.projector.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import johan.projector.models.DatabaseDriver;
import johan.projector.models.Project;

/**
 * Controller class for creating a new Project
 *
 * @author Johan Hernandez
 * @version 1.0.1
 */
public class ProjectController {
    /**
     * The input field for the title
     */
    @FXML
    private TextField titleInput;
    /**
     * The input field for the description
     */
    @FXML
    private TextArea descriptionInput;
    /**
     * The label to display an error when one occurs
     */
    @FXML
    private Label errorLabel;
    /**
     * The button to submit and create a Project
     */
    @FXML
    private Button createButton;

    /**
     * Attempts to create a new Project with the given inputs
     */
    @FXML
    public void attemptCreate() {
        if (titleInput.getText().trim().length() < 3) {
            errorLabel.setText("Error: project title must be at LEAST 3 characters long");
            errorLabel.setStyle("-fx-text-fill: #FF0000");
        } else {
            if(descriptionInput.getText().trim().isEmpty()) {
                descriptionInput.setText("N/A");
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
}
