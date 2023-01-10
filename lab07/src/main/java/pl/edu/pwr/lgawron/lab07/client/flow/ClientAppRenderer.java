package pl.edu.pwr.lgawron.lab07.client.flow;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import model.ItemType;
import pl.edu.pwr.lgawron.lab07.client.AppFlow;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public class ClientAppRenderer {
    private final AppFlow appFlow;
    private final Label notificationLabel;
    private final Button notificationButton;
    private final Label cartLabel;
    private final Button cartButton;
    private final VBox infoBox;
    private final VBox itemBox;
    private final VBox orderBox;
    private final Label connectionLabel;
    private final Button registerButton;
    private EventHandler<ActionEvent> registration;

    public ClientAppRenderer(AppFlow appFlow, Label notificationLabel, Button notificationButton, Label cartLabel, Button cartButton, VBox infoBox, VBox itemBox, VBox orderBox) {
        this.appFlow = appFlow;
        this.notificationLabel = notificationLabel;
        this.notificationButton = notificationButton;
        this.cartLabel = cartLabel;
        this.cartButton = cartButton;
        this.infoBox = infoBox;
        this.itemBox = itemBox;
        this.orderBox = orderBox;
        this.connectionLabel = new Label();
        this.registerButton = new Button("Register/Login");
    }

    public void renderInfoAfterConnection(String server, int port) {
        Platform.runLater(() -> {
            infoBox.getChildren().add(new Label(" Connected successfully to server!" +
                    "\n Proxy: " + server + ", Port: " + port));
            connectionLabel.setText(" NOTE! You can only browse shop offer without logging in!");
            VBox vBox = new VBox();
            vBox.setAlignment(Pos.CENTER);
            vBox.getChildren().add(connectionLabel);

            registerButton.getStyleableNode();
            registration = this.setRegistrationActionEvent();

            registerButton.setOnAction(registration);
            vBox.getChildren().add(registerButton);
            infoBox.getChildren().add(vBox);
        });
    }

    private EventHandler<ActionEvent> setRegistrationActionEvent() {
        return openPopUpEvent -> {
            Node node = (Node) openPopUpEvent.getSource();
            Stage thisStage = (Stage) node.getScene().getWindow();

            VBox dialogVbox = new VBox(20);
            dialogVbox.setAlignment(Pos.CENTER);
            dialogVbox.getChildren().add(new Text("Please enter name to register/login:"));
            TextField input = new TextField();
            input.setPromptText("Your Name");
            Button button = new Button("Confirm");
            button.setAlignment(Pos.BOTTOM_CENTER);
            dialogVbox.getChildren().add(input);
            dialogVbox.getChildren().add(button);

            Scene dialogScene = new Scene(dialogVbox, 350, 250);
            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(thisStage);
            dialog.setScene(dialogScene);
            openPopUpEvent.consume();

            EventHandler<ActionEvent> buttonHandler = inputEvent -> {
                try {
                    appFlow.register(input.getText());
                    inputEvent.consume();
                } catch (RemoteException e) {
                    connectionLabel.setText("ERROR: Registration failed!");
                }
                dialog.close();
            };
            button.setOnAction(buttonHandler);
            dialog.setScene(dialogScene);
            dialog.show();
        };
    }

    public void renderAfterRegistration(int clientId) {
        Platform.runLater(() ->
                connectionLabel.setText("Successfully logged in! Your id is: " + clientId));
    }

    public void renderItems(List<ItemType> itemList) {
        // todo: (NIE WIEM) moze jesli dodana zostanie opcja dodawania itemkow
        //  zrobic mape referencji jak w przypadku shopu

        // todo: zrobic ladnie w boxach z przyciskami do zamowien, moze podzielone na kategorie -> np kubki w 1 kategorii, rozne kolory
        Platform.runLater(() -> {
            itemList.forEach(itemType -> {
                Label itemLabel = new Label(itemType.getName() + ", " + itemType.getPrice());
                itemLabel.setMinHeight(10);
                itemLabel.setAlignment(Pos.BASELINE_LEFT);
                itemLabel.setStyle("-fx-border-style: solid");
                itemBox.getChildren().add(itemLabel);
            });
        });
    }
}
