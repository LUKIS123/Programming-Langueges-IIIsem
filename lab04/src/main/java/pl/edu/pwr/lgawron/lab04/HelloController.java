package pl.edu.pwr.lgawron.lab04;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HelloController {
    public TextField l1;
    public TextField l2;
    public Button simulate;
    public TextField d;
    public TextField h;
    @FXML
    private Label welcomeText;
    @FXML
    private TextField textField;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onSimulateButtonClick(ActionEvent actionEvent) {
        EventHandler<ActionEvent> onAction = simulate.getOnAction();


    }
}