package pl.edu.pwr.lgawron.lab07.shop;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.edu.pwr.lgawron.lab07.common.InvalidInputException;
import pl.edu.pwr.lgawron.lab07.common.ValuesHolder;

public class ShopAppController {
    @FXML
    public Button startButton;
    @FXML
    private Label welcomeText;
    private final ValuesHolder values = new ValuesHolder();
    private ShopAppFlow appFlow;

    public ShopAppController() {
    }

    @FXML
    protected void onHelloButtonClick(ActionEvent openPopUpEvent) {
        Node node = (Node) openPopUpEvent.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();

        startButton.setOnAction(
                event -> {
                    VBox dialogVbox = new VBox(20);
                    dialogVbox.setAlignment(Pos.CENTER);
                    dialogVbox.getChildren().add(new Text("Please enter your server and port:"));

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
                            appFlow = new ShopAppFlow(values);
                            appFlow.init();
                            //
                        } catch (InvalidInputException e) {
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
}