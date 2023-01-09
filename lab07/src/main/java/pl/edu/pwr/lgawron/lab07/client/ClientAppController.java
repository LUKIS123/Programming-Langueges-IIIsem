package pl.edu.pwr.lgawron.lab07.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ClientAppController {
    @FXML
    private Button startButton;
    @FXML
    private Label notificationLabel;
    @FXML
    private Button notificationButton;
    @FXML
    private Label cartLabel;
    @FXML
    private Button cartButton;
    @FXML
    private VBox infoBox;
    @FXML
    private VBox itemBox;
    @FXML
    private VBox orderBox;

    public ClientAppController() {

    }

    @FXML
    public void onStartButtonCLick(ActionEvent actionEvent) {

    }

    public void onExitApplication() {

    }
}
