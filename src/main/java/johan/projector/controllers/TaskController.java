package johan.projector.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import johan.projector.models.Task;
import johan.projector.models.TaskStatus;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TaskController implements Initializable {
    @FXML
    private Label taskTitleLabel;
    @FXML
    private ChoiceBox<String> statusChoiceBox;
    @FXML
    private FontAwesomeIconView deleteIcon;
    private Task myTask;
    public void setTask(final Task theTask) {
        deleteIcon.setOnMouseClicked(e -> deleteClick());
        myTask = theTask;
        taskTitleLabel.setText(myTask.getTitle());
        statusChoiceBox.setItems(FXCollections.observableList(List.of("Unfinished", "Finished", "In Progress")));
        statusChoiceBox.setValue(switch(theTask.getStatus()) {
            case UNFINISHED -> "Unfinished";
            case INPROGRESS -> "In Progress";
            case FINISHED -> "Finished";
        });
        statusChoiceBox.setOnAction(e -> changeStatus());
    }

    public void changeStatus() {
        TaskStatus status = switch (statusChoiceBox.getValue()) {
            case "Finished" -> TaskStatus.FINISHED;
            case "In Progress" -> TaskStatus.INPROGRESS;
            default -> TaskStatus.UNFINISHED;
        };
        myTask.setStatus(status);
    }
    private void deleteClick() {
        System.out.println("deleting task...");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(resourceBundle.toString());
    }
}
