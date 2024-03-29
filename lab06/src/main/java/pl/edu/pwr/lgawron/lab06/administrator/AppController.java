package pl.edu.pwr.lgawron.lab06.administrator;

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
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.edu.pwr.lgawron.lab06.administrator.flow.GameFlow;
import pl.edu.pwr.lgawron.lab06.common.frame.RegisterPopUp;
import pl.edu.pwr.lgawron.lab06.common.input.InputDataException;
import pl.edu.pwr.lgawron.lab06.common.input.ValuesHolder;

public class AppController {
    @FXML
    private GridPane mapPane;
    @FXML
    private GridPane playerPane;
    @FXML
    private Button startButton;
    @FXML
    private Label playerInfo;
    @FXML
    private Button hostButton;
    private final RegisterPopUp registerPopUp;
    private final ValuesHolder values;
    private GameFlow game;

    public AppController() {
        this.registerPopUp = new RegisterPopUp();
        this.values = new ValuesHolder();
    }

    @FXML
    public void onHostButtonClick(ActionEvent openPopUpEvent) {
        Node node = (Node) openPopUpEvent.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();

        hostButton.setOnAction(
                event -> {
                    VBox dialogVbox = registerPopUp.renderPopUp("Please enter host and port:");

                    TextField server = new TextField();
                    server.setPromptText("Server");
                    server.setText("localhost");
                    TextField port = new TextField();
                    port.setPromptText("Port");
                    port.setText("8085");
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

                            // initialization & starting server
                            game = new GameFlow(values, mapPane, playerPane, playerInfo);
                            game.initServer();
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
        game.finishRegistration();
        game.firstRender();
        playerPane.setVisible(true);
    }

    public void onExitApplication() {
        if (game == null) {
            return;
        }
        game.killApp();
    }

}
