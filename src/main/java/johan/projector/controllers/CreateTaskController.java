package johan.projector.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import johan.projector.models.DatabaseDriver;
import johan.projector.models.Project;
import johan.projector.models.Task;

import java.util.NoSuchElementException;

public class CreateTaskController {
    @FXML
    private TextField nameInput;
    @FXML
    private Label errorLabel;
    @FXML
    private Button createButton;
    private Project myProject;
    public void setProject(final Project theProject) {
        if (theProject == null) {
            throw new IllegalArgumentException("Project cannot be null");
        } else if (!DatabaseDriver.getInstance().getAllProjects().contains(theProject)){
            throw new NoSuchElementException("Project cannot be found in database, or some Project access error has occurred.");
        }
        myProject = theProject;
    }
    public void attemptCreate() {
        if (nameInput.getText().trim().length() < 3) {
            errorLabel.setText("Error: Task name cannot be less than 3 characters!");
            errorLabel.setStyle("-fx-text-fill: #FF0000");
        } else {
            final Task task = new Task(nameInput.getText());
            DatabaseDriver.getInstance().addTask(myProject, task);
            createButton.getScene().getWindow().hide();
        }
    }
}
