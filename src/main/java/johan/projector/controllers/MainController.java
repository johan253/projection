package johan.projector.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import johan.projector.models.DatabaseDriver;
import johan.projector.models.Project;
import johan.projector.models.TaskStatus;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * The Main controller for the application. Direct controller for "main.fxml"
 *
 * @author Johan Hernandez
 * @version 2.3.0
 */
public class MainController implements Initializable, PropertyChangeListener {
    /**
     * The create task button to create a task
     */
    @FXML
    private Button createTaskButton;
    /**
     * The edit project button to edit a project
     */
    @FXML
    private Button editProjectButton;
    /**
     * The delete project button to delete a project
     */
    @FXML
    private Button deleteProjectButton;
    /**
     * The add project button injected from the FXML file
     */
    @FXML
    private Button addProjectButton;
    /**
     * The Pie Chart displaying the task data injected from the FXML file
     */
    @FXML
    private PieChart pieChart;
    /**
     * The label displaying the current selected Project. Injected from the FXML file
     */
    @FXML
    private Label currentProjectTitleLabel;
    /**
     * The label displaying the description of the currently selected project. Injected from the FXML file
     */
    @FXML
    private Label currentProjectDescriptionLabel;
    /**
     * The choice box that allows teh user to select different Projects. Injected from the FXML file
     */
    @FXML
    private ChoiceBox<String> projectSelector;
    /**
     * The panel that displays the unfinished tasks
     */
    @FXML
    private VBox unfinishedTasksBox;
    /**
     * The panel that displays the in progress tasks
     */
    @FXML
    private VBox inProgressTasksBox;
    /**
     * The panel that displays the finished tasks
     */
    @FXML
    private VBox finishedTasksBox;
    /**
     * The list of Task data to be attached to the pie chart
     */
    //TODO: may get rid of and instead directly add to Pie Chart
    private List<PieChart.Data> taskData;
    /**
     * The singleton instance of the Database Driver. Allows interaction and updates to the local SQLite database
     */
    private final DatabaseDriver myDatabaseDriver = DatabaseDriver.getInstance();

    /**
     * Declares what happens on initialization of the main.fxml file
     *
     * @param url the url of the FXML file this controller is attached to
     * @param resourceBundle any resources passed down
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        taskData = new ArrayList<>();
        projectSelector.setItems(FXCollections.observableList(
                myDatabaseDriver.getAllProjects().stream().map(Project::getTitle).toList()));
        projectSelector.setValue(myDatabaseDriver.getAllProjects().get(0).getTitle());
        projectSelector.setOnAction(e -> refreshData());
        refreshData();
    }

    /**
     * Refreshes all the data on the dashboard to reflect currently selected Project
     */
    private void refreshData() {
        taskData.clear();

        Project project = myDatabaseDriver.getProject(projectSelector.getValue());
        currentProjectTitleLabel.setText("Current Project: " + project.getTitle());
        currentProjectDescriptionLabel.setText(project.getDescription());
        int unfinished = project.getAllTasks().stream().filter(t -> t.getStatus() == TaskStatus.UNFINISHED).toList().size();
        int inprogress = project.getAllTasks().stream().filter(t -> t.getStatus() == TaskStatus.INPROGRESS).toList().size();
        int finished = project.getAllTasks().stream().filter(t -> t.getStatus() == TaskStatus.FINISHED).toList().size();
        taskData.add(new PieChart.Data("Unfinished", unfinished));
        taskData.add(new PieChart.Data("Finished", finished));
        taskData.add(new PieChart.Data("In progress", inprogress));
        pieChart.getData().clear();
        pieChart.getData().addAll(taskData);
        refreshTasks(project);
    }

    /**
     * Refreshes the tasks displayed on the dashboard
     *
     * @param theProject the project used to generate the tasks
     */
    private void refreshTasks(final Project theProject) {
        unfinishedTasksBox.getChildren().clear();
        inProgressTasksBox.getChildren().clear();
        finishedTasksBox.getChildren().clear();
        try {
            AtomicReference<FXMLLoader> loader = new AtomicReference<>(new FXMLLoader(getClass().getResource("/fxml/task.fxml")));
            AtomicReference<Parent> p = new AtomicReference<>(loader.get().load());
            AtomicReference<TaskController> ctrl = new AtomicReference<>(loader.get().getController());
            ctrl.get().addPropertyChangeListener(this);
            theProject.getAllTasks().forEach(t -> {
                ctrl.get().setTask(t);
                switch(t.getStatus()) {
                    case UNFINISHED -> unfinishedTasksBox.getChildren().add(p.get());
                    case INPROGRESS -> inProgressTasksBox.getChildren().add(p.get());
                    default -> finishedTasksBox.getChildren().add(p.get());
                }
                loader.set(new FXMLLoader(getClass().getResource("/fxml/task.fxml")));
                try {
                    p.set(loader.get().load());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                ctrl.get().addPropertyChangeListener(this);
                ctrl.set(loader.get().getController());
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Handles a click to the create task button
     */
    @FXML
    public void createTaskClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/create-task.fxml"));
        Scene scene = new Scene(loader.load());
        CreateTaskController ctrl = loader.getController();
        ctrl.setProject(myDatabaseDriver.getProject(projectSelector.getValue()));
        Stage stage = new Stage();
        stage.setScene(scene);
        createTaskButton.setDisable(true);
        stage.setOnHidden((e) -> {
            createTaskButton.setDisable(false);
            projectSelector.setOnAction(a -> System.out.print(""));
            projectSelector.setItems(FXCollections.observableList(myDatabaseDriver.getAllProjects().stream().map(Project::getTitle).toList()));
            projectSelector.setOnAction(b -> refreshData());
            projectSelector.setValue(myDatabaseDriver.getAllProjects().get(0).getTitle());
        });
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/logo.png"))));
        stage.setTitle("Create new Porject");
        stage.setX(addProjectButton.getScene().getWindow().getX() + 50);
        stage.setY(addProjectButton.getScene().getWindow().getY() + 50);
        stage.show();
    }

    /**
     * Handles a click to the edit project button
     */
    @FXML
    public void editProjectClick() {
        // disable button
        editProjectButton.setDisable(true);
        // container
        VBox vbox = new VBox();
        Stage stage = new Stage();
        vbox.setPrefWidth(500.0);
        vbox.setPrefHeight(200.0);
        vbox.setAlignment(Pos.CENTER);

        // title label and text-field
        TextField title = new TextField();
        title.setText(projectSelector.getValue());
        Label titleLabel = new Label("Title:");
        titleLabel.setStyle("-fx-font-family: 'Arial Rounded MT Bold'");

        // description label and text-field
        TextField description = new TextField();
        description.setText(myDatabaseDriver.getProject(projectSelector.getValue()).getDescription());
        Label descriptionLabel = new Label("Description: ");
        descriptionLabel.setStyle("-fx-font-family: 'Arial Rounded MT Bold'");

        Label errorLabel = new Label("");
        errorLabel.setStyle("-fx-text-fill: #ff0000");
        errorLabel.setWrapText(true);

        Button submit = new Button("Done");
        submit.setOnAction(e -> {
            String newTitle = title.getText();
            String newDesc = description.getText();
            myDatabaseDriver.getProject(projectSelector.getValue()).setDescription(newDesc);
            myDatabaseDriver.getProject(projectSelector.getValue()).setTitle(newTitle);
            projectSelector.setOnAction(a -> System.out.print(""));
            projectSelector.setItems(FXCollections.observableList(myDatabaseDriver.getAllProjects().stream().map(Project::getTitle).toList()));
            projectSelector.setOnAction(b -> refreshData());
            projectSelector.setValue(newTitle);
            stage.hide();
        });
        vbox.getChildren().addAll(titleLabel, title, descriptionLabel, description, errorLabel, submit);

        Scene scene = new Scene(vbox);
        stage.setTitle("Edit Project");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/logo.png"))));
        stage.setScene(scene);
        stage.setOnHidden(e -> editProjectButton.setDisable(false));
        stage.show();
    }

    /**
     * Handles a click to the delete project button.
     */
    @FXML
    public void deleteProjectClick() {
        deleteProjectButton.setDisable(true);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("'" + projectSelector.getValue() + "'");
        alert.setContentText("Are you sure you would like to delete this project?\n" +
                             "There is no way to undo this action.");
        alert.setOnHidden(e -> {
            if(alert.getResult().equals(ButtonType.OK)) {
                myDatabaseDriver.deleteProject(projectSelector.getValue());
                projectSelector.setOnAction(a -> System.out.print(""));
                projectSelector.setItems(FXCollections.observableList(myDatabaseDriver.getAllProjects().stream().map(Project::getTitle).toList()));
                projectSelector.setOnAction(b -> refreshData());
                projectSelector.setValue(myDatabaseDriver.getAllProjects().get(0).getTitle());
            }
            deleteProjectButton.setDisable(false);
        });
        alert.show();
    }

    /**
     * Handles a click to the add project button
     *
     * @throws IOException the io exception
     */
    @FXML
    public void addProjectClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/create-project.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        addProjectButton.setDisable(true);
        stage.setOnHidden((e) -> {
            addProjectButton.setDisable(false);
            projectSelector.setOnAction(a -> System.out.print(""));
            projectSelector.setItems(FXCollections.observableList(myDatabaseDriver.getAllProjects().stream().map(Project::getTitle).toList()));
            projectSelector.setOnAction(b -> refreshData());
            projectSelector.setValue(myDatabaseDriver.getAllProjects().get(0).getTitle());
        });
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/logo.png"))));
        stage.setTitle("Create new Porject");
        stage.setX(addProjectButton.getScene().getWindow().getX() + 50);
        stage.setY(addProjectButton.getScene().getWindow().getY() + 50);
        stage.show();
    }

    /**
     * Handles the event of changing a task detail.
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        refreshData();
    }
}