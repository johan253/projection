package johan.projector.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
    public void addProjectClick() {
        addProjectButton.setText("clicked");
    }
}