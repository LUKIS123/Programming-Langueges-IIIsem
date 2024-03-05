package pl.edu.pwr.lgawron.lab05;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pl.edu.pwr.lgawron.lab05.flow.ApplicationFlow;
import pl.edu.pwr.lgawron.lab05.frameutility.exceptions.InputDataException;
import pl.edu.pwr.lgawron.lab05.frameutility.parse.TextFieldParser;

import java.util.List;

public class AppController {
    @FXML
    public Label communicate;
    @FXML
    public TextField minSleepTime;
    @FXML
    private TextField labTechnicianNumber;
    @FXML
    private TextField organismNumber;
    @FXML
    public HBox descriptionBox;
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
        List<Integer> parsedInput = this.parseInputData();
        if (parsedInput.isEmpty()) {
            return;
        }

        descriptionBox.setVisible(true);
        flow = new ApplicationFlow(parsedInput.get(0), parsedInput.get(1), parsedInput.get(2),
                distributorBox, labTechniciansBox, nourishmentBox, staminaBox);

        flow.initialize();
    }

    @FXML
    public void onStopSimulationClick() {
        if (flow != null) {
            flow.endSimulation();
            flow.clearLabels();
        }
    }

    private List<Integer> parseInputData() {
        try {
            return TextFieldParser.parseSimulationValues(
                    organismNumber.getText(),
                    labTechnicianNumber.getText(),
                    minSleepTime.getText());

        } catch (NumberFormatException formatException) {
            communicate.setText("Try again! Wrong input format!");
            communicate.setVisible(true);

        } catch (InputDataException dataException) {
            communicate.setText(dataException.getMessage());
            communicate.setVisible(true);
        }
        return List.of();
    }

    public void onExitApplication() {
        if (flow != null) {
            flow.endSimulation();
        }
    }
}