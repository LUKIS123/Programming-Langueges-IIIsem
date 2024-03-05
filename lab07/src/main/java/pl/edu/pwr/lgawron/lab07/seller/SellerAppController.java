package pl.edu.pwr.lgawron.lab07.seller;

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

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class SellerAppController {
    @FXML
    private Button refreshButton;
    @FXML
    private VBox presentOrderBox;
    @FXML
    private VBox orderHistoryBox;
    @FXML
    private Label infoLabel;
    @FXML
    private Button startButton;
    private final ValuesHolder values = new ValuesHolder();
    private SellerAppFlow sellerAppFlow;

    public SellerAppController() {
    }

    @FXML
    public void onStartButtonClick(ActionEvent openPopUpEvent) {
        TextField server = new TextField();
        server.setPromptText("Server");
        server.setText("localhost");
        TextField port = new TextField();
        port.setPromptText("Port");
        port.setText("8085");
        Button button = new Button("Connect");
        button.setAlignment(Pos.BOTTOM_CENTER);
        Label communicate = new Label();
        communicate.setVisible(false);

        startButton.setOnAction(
                event -> {
                    EventHandler<ActionEvent> buttonHandler = inputEvent -> {
                        try {
                            values.setApplicationArguments(server.getText(), port.getText());
                            // initialization & starting server
                            sellerAppFlow = new SellerAppFlow(values, presentOrderBox, orderHistoryBox);
                            sellerAppFlow.initialize();
                            //
                            inputEvent.consume();
                        } catch (InvalidInputException | RemoteException | NotBoundException e) {
                            communicate.setText(e.getMessage() + "!");
                            communicate.setVisible(true);
                        }
                        if (inputEvent.isConsumed()) {
                            Node source = (Node) inputEvent.getSource();
                            Stage sourceStage = (Stage) source.getScene().getWindow();
                            sourceStage.close();
                            infoLabel.setText("Connected successfully! Proxy: " + values.getServer() + ", " + values.getPort());
                        }
                    };
                    Stage dialog = RenderUtils.renderStartingPupUp("Please enter server and port:",
                            openPopUpEvent, port, server, button, communicate, buttonHandler);
                    dialog.show();
                }
        );
    }

    @FXML
    public void onRefreshButtonClick() {
        if (sellerAppFlow == null) {
            return;
        }
        try {
            sellerAppFlow.downloadOrdersAndRefresh();
        } catch (RemoteException e) {
            infoLabel.setText("ERROR: Could not download OrderList!");
        }
    }

    public void onExitApplication() {
        if (sellerAppFlow == null) {
            return;
        }
        sellerAppFlow.killApp();
    }

}
