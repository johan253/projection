package johan.projector.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import johan.projector.models.DatabaseDriver;
import johan.projector.models.Project;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MainController implements Initializable {
    @FXML
    private Button addProjectButton;
    @FXML
    private PieChart pieChart;
    @FXML
    private Label currentProjectTitleLabel;
    @FXML
    private Label currentProjectDescriptionLabel;
    @FXML
    private ChoiceBox<String> projectSelector;
    private List<PieChart.Data> taskData;
    private final DatabaseDriver myDatabaseDriver = DatabaseDriver.getInstance();
    private List<Project> myProjects;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        taskData = new ArrayList<>();
        myProjects = myDatabaseDriver.getAllProjects();
        projectSelector.setItems(FXCollections.observableList(myProjects.stream().map(Project::getTitle).toList()));
        projectSelector.setValue(myProjects.get(0).getTitle());
        projectSelector.setOnAction(e -> refreshData());
        refreshData();
    }
    private void refreshData() {
        taskData.clear();
        Project project = myDatabaseDriver.getProject(projectSelector.getValue());
        currentProjectTitleLabel.setText("Current Project: " + project.getTitle());
        currentProjectDescriptionLabel.setText(project.getDescription());
        int unfinished = project.getUnfinishedTasks().size();
        int inprogress = project.getInProgressTasks().size();
        int finished = project.getFinishedTasks().size();
        taskData.add(new PieChart.Data("Unfinished", unfinished));
        taskData.add(new PieChart.Data("Finished", finished));
        taskData.add(new PieChart.Data("In progress", inprogress));
        pieChart.getData().clear();
        pieChart.getData().addAll(taskData);
    }
    @FXML
    public void createTaskClick() {

    }
    @FXML
    public void renameProjectClick() {

    }
    @FXML
    public void deleteProjectClick() {

    }

    @FXML
    public void addProjectClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/create-project.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        addProjectButton.setDisable(true);
        stage.setOnHidden((e) -> addProjectButton.setDisable(false));
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/logo.png"))));
        stage.setTitle("Create new Porject");
        stage.setX(addProjectButton.getScene().getWindow().getX() + 50);
        stage.setY(addProjectButton.getScene().getWindow().getY() + 50);
        stage.show();
    }
}