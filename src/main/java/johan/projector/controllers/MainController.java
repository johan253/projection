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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import johan.projector.models.DatabaseDriver;
import johan.projector.models.Project;

import javax.swing.text.Position;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        taskData = new ArrayList<>();
        projectSelector.setItems(FXCollections.observableList(myDatabaseDriver.getAllProjects().stream().map(Project::getTitle).toList()));
        projectSelector.setValue(myDatabaseDriver.getAllProjects().get(0).getTitle());
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
    public void editProjectClick() {
        VBox vbox = new VBox();
        Stage stage = new Stage();
        vbox.setPrefWidth(500.0);
        vbox.setPrefHeight(200.0);
        vbox.setAlignment(Pos.CENTER);

        TextField title = new TextField();
        title.setText(projectSelector.getValue());

        TextField description = new TextField();
        description.setText(myDatabaseDriver.getProject(projectSelector.getValue()).getDescription());

        Label errorLabel = new Label("");
        errorLabel.setStyle("-fx-text-fill: #ff0000");
        errorLabel.setWrapText(true);

        Button submit = new Button("Done");
        submit.setOnAction(e -> {
            String newTitle = title.getText();
            String newDesc = description.getText();
            myDatabaseDriver.getProject(projectSelector.getValue()).setDescription(newDesc);
            myDatabaseDriver.getProject(projectSelector.getValue()).setTitle(newTitle);
            projectSelector.setOnAction(a -> System.out.println("resetting selectable projects..."));
            projectSelector.setItems(FXCollections.observableList(myDatabaseDriver.getAllProjects().stream().map(Project::getTitle).toList()));
            projectSelector.setOnAction(b -> refreshData());
            projectSelector.setValue(newTitle);
            stage.hide();
        });
        vbox.getChildren().addAll(title, description, errorLabel, submit);

        Scene scene = new Scene(vbox);
        stage.setTitle("Edit Project");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/logo.png"))));
        stage.setScene(scene);
        //stage.setOnHidden(e -> refreshData());
        stage.show();
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