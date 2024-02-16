package johan.projector.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import johan.projector.App;
import johan.projector.models.DatabaseDriver;
import johan.projector.models.Project;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MainController implements Initializable {
    @FXML
    private Button addProjectButton;
    @FXML
    private PieChart pieChart;
    private List<PieChart.Data> taskData;
    private final DatabaseDriver myDatabaseDriver = DatabaseDriver.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        taskData = new ArrayList<>();
        initData();
        pieChart.setData(FXCollections.observableList(taskData));
    }
    private void initData() {
        taskData.clear();
        //TODO: add ability to select Project to display Data
        Project project = myDatabaseDriver.getProject("PROJECTion");
        int unfinished = project.getUnfinishedTasks().size();
        int inprogress = project.getInProgressTasks().size();
        int finished = project.getFinishedTasks().size();
        taskData.add(new PieChart.Data("Unfinished", unfinished));
        taskData.add(new PieChart.Data("Finished", finished));
        taskData.add(new PieChart.Data("inprogress", inprogress));
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