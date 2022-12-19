package pl.edu.pwr.lgawron.lab06.player;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.edu.pwr.lgawron.lab06.mainlogic.frame.RegisterPopUp;
import pl.edu.pwr.lgawron.lab06.mainlogic.parse.InputDataException;
import pl.edu.pwr.lgawron.lab06.mainlogic.parse.ValuesHolder;
import pl.edu.pwr.lgawron.lab06.player.flow.PlayerAppFlow;

public class AppController {
    @FXML
    public VBox controlBox;
    @FXML
    public Button up;
    @FXML
    public Button left;
    @FXML
    public Button right;
    @FXML
    public Button down;
    @FXML
    public Button see;
    @FXML
    public Pane mainPane;
    @FXML
    public GridPane mapPane;
    @FXML
    public GridPane playerPane;
    @FXML
    public Button startButton;
    @FXML
    public Button take;
    @FXML
    private Button registerButton;
    private final RegisterPopUp registerPopUp = new RegisterPopUp();
    private final PlayerAppFlow appFlow = new PlayerAppFlow();
    private ValuesHolder values;

    public AppController() {
    }

    @FXML
    public void onRegisterButtonClick(ActionEvent openPopUpEvent) {
        Node node = (Node) openPopUpEvent.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();

        values = new ValuesHolder();

        registerButton.setOnAction(
                event -> {
                    VBox dialogVbox = registerPopUp.renderPopUp();

                    TextField server = new TextField();
                    server.setPromptText("Server");
                    server.setText("localhost");
                    TextField port = new TextField();
                    port.setPromptText("Port");
                    port.setText("8080");
                    Button button = new Button("Login");
                    button.setAlignment(Pos.BOTTOM_CENTER);
                    Label communicate = new Label();
                    communicate.setVisible(false);

                    dialogVbox.getChildren().add(server);
                    dialogVbox.getChildren().add(port);
                    dialogVbox.getChildren().add(button);
                    dialogVbox.getChildren().add(communicate);

                    Scene dialogScene = new Scene(dialogVbox, 300, 200);

                    Stage dialog = new Stage();
                    dialog.initModality(Modality.APPLICATION_MODAL);
                    dialog.initOwner(thisStage);
                    dialog.setScene(dialogScene);
                    openPopUpEvent.consume();

                    EventHandler<ActionEvent> buttonHandler = inputEvent -> {
                        try {
                            values.setApplicationArguments(server.getText(), port.getText());
                            inputEvent.consume();

                            // initialization -> joining game
                            appFlow.startRegistration(values, controlBox, mapPane, playerPane);
                            //

                        } catch (InputDataException e) {
                            communicate.setText(e.getMessage() + "!");
                            communicate.setVisible(true);
                        }
                        if (inputEvent.isConsumed()) {
                            dialog.close();
                        }
                    };

                    button.setOnAction(buttonHandler);
                    dialog.setScene(dialogScene);
                    dialog.show();
                }
        );
    }

    public void onStartButtonClick() {
        appFlow.startAlgo();
    }

    /// controls
    @FXML
    public void moveUp() {
        appFlow.moveUp();
    }

    @FXML
    public void moveLeft() {
        appFlow.moveLeft();
    }

    @FXML
    public void moveRight() {
        appFlow.moveRight();
    }

    @FXML
    public void moveDown() {
        appFlow.moveDown();
    }

    @FXML
    public void see() {
        appFlow.see();
    }

    @FXML
    public void takeTreasure() {
        appFlow.take();
    }

    /// end of controls
    public void onExitApplication() {
        appFlow.killApp();
    }

}