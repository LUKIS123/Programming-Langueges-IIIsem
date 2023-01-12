package pl.edu.pwr.lgawron.lab07.seller;

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
import pl.edu.pwr.lgawron.lab07.common.input.InvalidInputException;
import pl.edu.pwr.lgawron.lab07.common.input.ValuesHolder;

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
    private AppFlow appFlow;

    public SellerAppController() {
    }

    @FXML
    public void onStartButtonClick(ActionEvent openPopUpEvent) {
        Node node = (Node) openPopUpEvent.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();

        startButton.setOnAction(
                event -> {
                    VBox dialogVbox = new VBox(20);
                    dialogVbox.setAlignment(Pos.CENTER);
                    dialogVbox.getChildren().add(new Text("Please enter server and port:"));

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

                    dialogVbox.getChildren().add(server);
                    dialogVbox.getChildren().add(port);
                    dialogVbox.getChildren().add(button);
                    dialogVbox.getChildren().add(communicate);

                    Scene dialogScene = new Scene(dialogVbox, 350, 250);

                    Stage dialog = new Stage();
                    dialog.initModality(Modality.APPLICATION_MODAL);
                    dialog.initOwner(thisStage);
                    dialog.setScene(dialogScene);
                    openPopUpEvent.consume();

                    EventHandler<ActionEvent> buttonHandler = inputEvent -> {
                        try {
                            values.setApplicationArguments(server.getText(), port.getText());
                            // initialization & starting server
                            appFlow = new AppFlow(values, presentOrderBox, orderHistoryBox);
                            appFlow.initialize();
                            //
                            inputEvent.consume();
                        } catch (InvalidInputException | RemoteException | NotBoundException e) {
                            communicate.setText(e.getMessage() + "!");
                            communicate.setVisible(true);
                        }
                        if (inputEvent.isConsumed()) {
                            dialog.close();
                            infoLabel.setText("Connected successfully! Proxy: " + values.getServer() + ", " + values.getPort());
                        }
                    };
                    button.setOnAction(buttonHandler);
                    dialog.setScene(dialogScene);
                    dialog.show();
                }
        );
    }

    @FXML
    public void onRefreshButtonClick() {
        if (appFlow == null) {
            return;
        }
        try {
            appFlow.downloadOrdersAndRefresh();
        } catch (RemoteException e) {
            infoLabel.setText("ERROR: Could not download OrderList!");
        }
    }

    public void onExitApplication() {
        if (appFlow == null) {
            return;
        }
        appFlow.killApp();
    }

}
