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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.edu.pwr.lgawron.lab06.mainlogic.frame.RegisterPopUp;
import pl.edu.pwr.lgawron.lab06.mainlogic.parse.InputDataException;
import pl.edu.pwr.lgawron.lab06.mainlogic.parse.ValuesHolder;

public class AppController {
    @FXML
    public Label connectionInfo;
    @FXML
    public Button startButton;
    @FXML
    private HBox mainBox;
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
                            appFlow.startRegistration(values);
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
//        PlayerSenderSocket playerSenderSocket = new PlayerSenderSocket();
//        playerSenderSocket.sendRequest(values.getPort(), "japierdole");


//        LinkedList<Integer> linkedList = new LinkedList<>();
//        linkedList.add(1);
//        linkedList.add(2);
//        System.out.println(linkedList.pop());
//        System.out.println(linkedList.size());


    }

    public void onExitApplication() {

    }
}