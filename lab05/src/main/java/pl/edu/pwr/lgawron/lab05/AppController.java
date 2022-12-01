package pl.edu.pwr.lgawron.lab05;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pl.edu.pwr.lgawron.lab05.flow.ApplicationFlow;

public class AppController {
    @FXML
    private TextField labTechnicianNumber;
    @FXML
    private TextField organismNumber;
    @FXML
    private HBox mainBox;
    @FXML
    private VBox distributorBox;
    @FXML
    private VBox labTechniciansBox;
    @FXML
    private VBox nourishmentBox;
    @FXML
    private VBox staminaBox;

    private ApplicationFlow flow;

    public AppController() {
    }

    @FXML
    protected void onStartSimulationClick() {
        int orgNum = 10; // Integer.parseInt(organismNumber.getText());
        int assistantNum = 5; // Integer.parseInt(labTechnicianNumber.getText());
        // distributorBox.getChildren().add(new Label("distrib"));

        flow = new ApplicationFlow(orgNum, assistantNum, distributorBox, labTechniciansBox, nourishmentBox, staminaBox);
        flow.init();
    }

    @FXML
    public void onStopSimulationClick(ActionEvent actionEvent) {
    }

    public void onExitApplication() {
    }
}