package pl.edu.pwr.lgawron.lab07.client.flow;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ClientAppRenderer {
    private final Label notificationLabel;
    private final Button notificationButton;
    private final Label cartLabel;
    private final Button cartButton;
    private final VBox infoBox;
    private final VBox itemBox;
    private final VBox orderBox;
    private final Label connectionLabel;
    private final Button registerButton;
    private final Button loginButton;

    public ClientAppRenderer(Label notificationLabel, Button notificationButton, Label cartLabel, Button cartButton, VBox infoBox, VBox itemBox, VBox orderBox) {
        this.notificationLabel = notificationLabel;
        this.notificationButton = notificationButton;
        this.cartLabel = cartLabel;
        this.cartButton = cartButton;
        this.infoBox = infoBox;
        this.itemBox = itemBox;
        this.orderBox = orderBox;
        this.connectionLabel = new Label();
        this.registerButton = new Button("Register");
        this.loginButton = new Button("Login");
    }

    public void renderInfoAfterConnection(String server, int port) {
        infoBox.getChildren().add(new Label(" Connected successfully to server!"));
        infoBox.getChildren().add(new Label(" Proxy:" + server + ", Port: " + port));
        connectionLabel.setText(" NOTE! You can only browse shop offer without logging in!");
        VBox vBox = new VBox();
        vBox.getChildren().add(connectionLabel);
        // todo popupy do rejestracji i logowania
        // todo zmienic implementacje register na taki co loguje, jesli klient znajduje sie w bazie -> za pierwszym razem moge zwracac id 0, pozniej inne dla logowania!
        vBox.getChildren().add(registerButton);
        vBox.getChildren().add(loginButton);
        infoBox.getChildren().add(vBox);
    }
}
