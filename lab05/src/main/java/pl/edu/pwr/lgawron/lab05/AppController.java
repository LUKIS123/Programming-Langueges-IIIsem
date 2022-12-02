package pl.edu.pwr.lgawron.lab05;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pl.edu.pwr.lgawron.lab05.flow.ApplicationFlow;
import pl.edu.pwr.lgawron.lab05.frameutility.TextFieldParser;

import java.util.List;
import java.util.stream.Stream;

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
    private HBox mainBox;
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
        // distributorBox.getChildren().add(new Label("distrib"));

        List<Integer> parsedInput = this.parseInputData();
        if (parsedInput.isEmpty()) {
            return;
        }

        descriptionBox.setVisible(true);
        flow = new ApplicationFlow(parsedInput.get(0), parsedInput.get(1), parsedInput.get(2),
                distributorBox, labTechniciansBox, nourishmentBox, staminaBox);

        flow.init();
    }

    @FXML
    public void onStopSimulationClick() {
        // dodac sleep albo waita -> dodatkowa zmienna stop -> jesli prawda to sleep/wait
    }

    private List<Integer> parseInputData() {
        try {
            return Stream
                    .of(labTechnicianNumber, organismNumber, minSleepTime)
                    .map(TextFieldParser::parseToIntegerValue).toList();

        } catch (NumberFormatException formatException) {
            communicate.setText("Try again! Wrong input format!");
            communicate.setVisible(true);
            return List.of();
        }
    }

    public void onExitApplication() {
    }
}