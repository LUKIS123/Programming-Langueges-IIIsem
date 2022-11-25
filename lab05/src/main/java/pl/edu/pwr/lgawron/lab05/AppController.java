package pl.edu.pwr.lgawron.lab05;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AppController {

    @FXML
    private TextField labTechnicianNumber;
    @FXML
    private TextField organismNumber;
    @FXML
    private AnchorPane main;
    @FXML
    private HBox hBox;
    @FXML
    private VBox labTechniciansBox;
    @FXML
    private VBox nourishmentBox;
    @FXML
    private VBox staminaBox;

    @FXML
    protected void onStartSimulationClick() {
        Label test = new Label("test");
        labTechniciansBox.getChildren().add(test);
        nourishmentBox.getChildren().add(new Label("test1"));
        staminaBox.getChildren().add(new Label("test2"));
    }

    @FXML
    public void onStopSimulationClick(ActionEvent actionEvent) {
    }
}