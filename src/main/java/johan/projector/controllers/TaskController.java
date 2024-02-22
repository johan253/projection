package johan.projector.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import johan.projector.models.Task;
import johan.projector.models.TaskStatus;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

/**
 * This class is responsible for controlling the Task previews on the dashboard of the app
 *
 * @author Johan Hernandez
 * @version 1.4.0
 */
public class TaskController {
    /**
     * Label that displays the title of the task
     */
    @FXML
    private Label taskTitleLabel;
    /**
     * Choicebox that allows you to change the status of a task
     */
    @FXML
    private ChoiceBox<String> statusChoiceBox;
    /**
     * The icon that displays the option to delete a task
     */
    @FXML
    private FontAwesomeIconView deleteIcon;
    /**
     * The task this controller is controlling
     */
    private Task myTask;
    /**
     * The property change support that fires an event when a new status for this task is chosen
     */
    private final PropertyChangeSupport myPcs = new PropertyChangeSupport(this);

    /**
     * Sets the task of this controller. In a sense instantiates the controller to be able to react to changes
     * and display the task data
     *
     * @param theTask the task
     */
    public void setTask(final Task theTask) {
        deleteIcon.setOnMouseClicked(e -> deleteClick());
        statusChoiceBox.setOnAction(e -> System.out.print(""));
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

    /**
     * Add property change listener.
     *
     * @param theListener the listener
     */
    public void addPropertyChangeListener(PropertyChangeListener theListener) {
        myPcs.addPropertyChangeListener(theListener);
    }

    /**
     * Change status of this Task.
     */
    private void changeStatus() {
        TaskStatus status = switch (statusChoiceBox.getValue()) {
            case "Finished" -> TaskStatus.FINISHED;
            case "In Progress" -> TaskStatus.INPROGRESS;
            default -> TaskStatus.UNFINISHED;
        };
        myTask.setStatus(status);
        myPcs.firePropertyChange("", null, null);
    }

    /**
     * Handles a click to the delete icon
     */
    private void deleteClick() {
        System.out.println("deleting task...");
    }

}
