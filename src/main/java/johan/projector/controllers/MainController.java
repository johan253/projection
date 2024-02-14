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
import javafx.stage.Stage;
import johan.projector.App;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private Button addProjectButton;
    @FXML
    private PieChart pieChart;
    private List<PieChart.Data> taskData;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        taskData = new ArrayList<>();
        initData();
        pieChart.setData(FXCollections.observableList(taskData));
    }
    private void initData() {
        taskData.add(new PieChart.Data("complete", 3));
        taskData.add(new PieChart.Data("incomplete", 8));
        taskData.add(new PieChart.Data("in progress", 1));
    }

    @FXML
    public void addProjectClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/create-project.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        addProjectButton.setDisable(true);
        stage.setOnHidden((e) -> addProjectButton.setDisable(false));
        stage.show();
    }
}