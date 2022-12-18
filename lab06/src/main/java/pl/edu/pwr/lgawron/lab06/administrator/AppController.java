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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.edu.pwr.lgawron.lab06.mainlogic.flow.GameFlow;
import pl.edu.pwr.lgawron.lab06.mainlogic.frame.RegisterPopUp;
import pl.edu.pwr.lgawron.lab06.mainlogic.parse.InputDataException;
import pl.edu.pwr.lgawron.lab06.mainlogic.parse.ValuesHolder;

public class AppController {
    @FXML
    public GridPane mapPane;
    @FXML
    public GridPane playerPane;
    @FXML
    public Pane mainPane;
    @FXML
    private Button startButton;
    @FXML
    private Button hostButton;
    private final RegisterPopUp registerPopUp = new RegisterPopUp();
    private final ValuesHolder values = new ValuesHolder();
    private GameFlow game;

    public AppController() {
    }

    @FXML
    public void onHostButtonClick(ActionEvent openPopUpEvent) {
        Node node = (Node) openPopUpEvent.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();

        hostButton.setOnAction(
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

                            // initialization & starting server
                            game = new GameFlow(values, mapPane, playerPane);
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

    public void onStartButtonClick(ActionEvent actionEvent) {
        game.finishRegistration();
        game.firstRender();
    }

    public void onExitApplication() {
        if (game == null) {
            return;
        }
        game.killApp();
    }

}
