package pl.edu.pwr.lgawron.lab07.shop;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.edu.pwr.lgawron.lab07.common.input.InvalidInputException;
import pl.edu.pwr.lgawron.lab07.common.input.ValuesHolder;
import pl.edu.pwr.lgawron.lab07.common.utils.RenderUtils;

public class ShopAppController {
    @FXML
    private VBox infoBox;
    @FXML
    private VBox itemBox;
    @FXML
    private VBox clientBox;
    @FXML
    private VBox orderBox;
    @FXML
    private Button startButton;
    private final ValuesHolder values = new ValuesHolder();
    private ShopAppFlow shopAppFlow;

    public ShopAppController() {
    }

    @FXML
    public void onStartButtonClick(ActionEvent openPopUpEvent) {
        TextField port = new TextField();
        port.setPromptText("Port");
        port.setText("8085");
        Button button = new Button("Create Registry");
        button.setAlignment(Pos.BOTTOM_CENTER);
        Label communicate = new Label();
        communicate.setVisible(false);

        startButton.setOnAction(
                event -> {
                    EventHandler<ActionEvent> buttonHandler = inputEvent -> {
                        if (shopAppFlow != null) {
                            // if the app is already working -> return
                            return;
                        }
                        try {
                            values.setApplicationArguments("", port.getText());
                            // initialization & starting server
                            shopAppFlow = new ShopAppFlow(values, itemBox, clientBox, orderBox, infoBox);
                            shopAppFlow.initialize();
                            //
                            inputEvent.consume();
                        } catch (InvalidInputException e) {
                            communicate.setText(e.getMessage() + "!");
                            communicate.setVisible(true);
                        }
                        if (inputEvent.isConsumed()) {
                            Node source = (Node) inputEvent.getSource();
                            Stage sourceStage = (Stage) source.getScene().getWindow();
                            sourceStage.close();
                        }
                    };
                    Stage dialog = RenderUtils.renderStartingPupUp("Please enter port to start Server on:",
                            openPopUpEvent, port, null, button, communicate, buttonHandler);
                    dialog.show();
                }
        );
    }

    public void onExitApplication() {
        if (shopAppFlow == null) {
            return;
        }
        shopAppFlow.killApp();
    }

}