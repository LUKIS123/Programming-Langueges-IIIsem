package pl.edu.pwr.lgawron.lab07.client;

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

import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientAppController {
    @FXML
    private Button subscribeButton;
    @FXML
    private Label orderInfo;
    @FXML
    private Button refreshOrderButton;
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
    private final ValuesHolder values = new ValuesHolder();
    private ClientAppFlow clientAppFlow;

    public ClientAppController() {
    }

    @FXML
    public void onStartButtonCLick(ActionEvent openPopUpEvent) {
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
                            clientAppFlow = new ClientAppFlow(values, notificationLabel, notificationButton, cartLabel, cartButton, infoBox, itemBox, orderBox);
                            clientAppFlow.initialize();
                            //
                            orderInfo.setVisible(false);
                            inputEvent.consume();
                        } catch (InvalidInputException | RemoteException | NotBoundException e) {
                            communicate.setText(e.getMessage() + "!");
                            communicate.setVisible(true);
                        }
                        if (inputEvent.isConsumed()) {
                            Node source = (Node) inputEvent.getSource();
                            Stage sourceStage = (Stage) source.getScene().getWindow();
                            sourceStage.close();
                        }
                    };
                    Stage dialog = RenderUtils.renderStartingPupUp("Please enter server and port:",
                            openPopUpEvent, port, server, button, communicate, buttonHandler);
                    dialog.show();
                }
        );
    }

    @FXML
    public void onRefreshOrdersButtonCLick() {
        if (clientAppFlow == null) {
            return;
        }
        if (clientAppFlow.getClientId() == -1) {
            orderInfo.setText("Login first");
            orderInfo.setVisible(true);
            return;
        }
        if (clientAppFlow.getSubmittedOrders().isEmpty()) {
            orderInfo.setText("There is no orders");
            orderInfo.setVisible(true);
            return;
        }
        try {
            clientAppFlow.downloadSubmittedOrdersAndRefresh();
            orderInfo.setVisible(false);
        } catch (RemoteException e) {
            orderInfo.setText("ERROR: Could not download OrderList!");
            orderInfo.setVisible(true);
        }
    }

    public void onSubscribeButtonClick() {
        if (clientAppFlow == null) {
            return;
        }
        if (clientAppFlow.getStatusListenerImplementation() != null) {
            orderInfo.setText("Already subscribed!");
            orderInfo.setVisible(true);
            return;
        }
        try {
            if (!clientAppFlow.subscribe()) {
                orderInfo.setText("ERROR: Could not subscribe!");
                orderInfo.setVisible(true);
            }
            orderInfo.setText("SUBSCRIBED!");
            orderInfo.setVisible(true);
        } catch (RemoteException e) {
            orderInfo.setText("ERROR: Could not subscribe!");
            orderInfo.setVisible(true);
            e.printStackTrace();
        }
    }

    public void onExitApplication() {
        if (clientAppFlow == null) {
            return;
        }
        if (!clientAppFlow.unsubscribe()) {
            orderInfo.setText("Could not unsubscribe!");
        }
        try {
            clientAppFlow.unExportListener();
        } catch (NoSuchObjectException ignored) {
        }
        clientAppFlow.killApp();
    }

}
