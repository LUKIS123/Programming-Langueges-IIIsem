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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;
import pl.edu.pwr.lgawron.lab07.client.AppFlow;
import pl.edu.pwr.lgawron.lab07.client.utils.OrderBuilder;
import pl.edu.pwr.lgawron.lab07.common.utils.RenderUtils;

import java.rmi.RemoteException;
import java.util.HashMap;
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
    private final Map<OrderLine, Node> itemBoxCartMap;
    private final Map<Integer, Node> orderNodeMap;

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
        this.itemBoxCartMap = new HashMap<>();
        this.orderNodeMap = new HashMap<>();
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

    public void renderItems(List<ItemType> itemList) {
        // todo: zrobic ladnie w boxach z przyciskami do zamowien, moze podzielone na kategorie -> np kubki w 1 kategorii, rozne kolory
        Platform.runLater(() -> itemList.forEach(itemType -> {
            HBox hBox = new HBox();
            hBox.setMinHeight(30);
            hBox.setMinWidth(300);

            Label itemLabel = new Label(" " + itemType.getName() + ", " + itemType.getPrice() + "PLN ");
            itemLabel.setAlignment(Pos.CENTER_LEFT);

            Button button = new Button("Add to Cart");
            button.setAlignment(Pos.CENTER_RIGHT);
            button.setOnAction(this.setAddToCartPopUp(itemType));

            hBox.setStyle("-fx-border-style: solid");
            hBox.getChildren().add(itemLabel);
            hBox.getChildren().add(button);
            itemBox.getChildren().add(hBox);
        }));
    }

    private EventHandler<ActionEvent> setAddToCartPopUp(ItemType itemType) {
        return openPopUpEvent -> {
            if (appFlow.getClientId() == -1) {
                return;
            }
            Node node = (Node) openPopUpEvent.getSource();
            Stage thisStage = (Stage) node.getScene().getWindow();

            VBox dialogVbox = new VBox(20);
            dialogVbox.setAlignment(Pos.CENTER);
            dialogVbox.getChildren().add(new Text("Enter quantity & advert:"));
            TextField quantity = new TextField();
            quantity.setPromptText("Quantity");
            TextField advert = new TextField();
            advert.setPromptText("Advert");
            Button button = new Button("Confirm");
            button.setAlignment(Pos.BOTTOM_CENTER);
            dialogVbox.getChildren().add(quantity);
            dialogVbox.getChildren().add(advert);
            dialogVbox.getChildren().add(button);

            Scene dialogScene = new Scene(dialogVbox, 350, 250);
            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(thisStage);
            dialog.setScene(dialogScene);
            openPopUpEvent.consume();

            EventHandler<ActionEvent> buttonHandler = inputEvent -> {
                try {
                    appFlow.addToCart(itemType, Integer.parseInt(quantity.getText()), advert.getText());
                    inputEvent.consume();
                } catch (NumberFormatException ignored) {
                }
                if (inputEvent.isConsumed()) {
                    dialog.close();
                }
            };
            button.setOnAction(buttonHandler);
            dialog.setScene(dialogScene);
            dialog.show();
        };
    }

    public void renderShoppingCartPopUp(OrderBuilder orderBuilder) {
        if (orderBuilder.getOrderLineList().isEmpty()) {
            return;
        }
        cartButton.setOnAction(this.setEnterShoppingCartPopUp(orderBuilder.getOrderLineList(), orderBuilder));
    }

    private EventHandler<ActionEvent> setEnterShoppingCartPopUp(List<OrderLine> orderLineList, OrderBuilder orderBuilder) {
        return openPopUpEvent -> {
            Node node = (Node) openPopUpEvent.getSource();
            Stage thisStage = (Stage) node.getScene().getWindow();

            VBox dialogVbox = new VBox(20);
            dialogVbox.setAlignment(Pos.CENTER);
            dialogVbox.setStyle("-fx-border-style: solid");
            Text costInfo = new Text("OrderLines: [" + orderBuilder.getCost() + " PLN]");
            dialogVbox.getChildren().add(costInfo);

            orderLineList.forEach(orderLine -> {
                String adv = orderLine.getAdvert();
                if (orderLine.getAdvert().length() >= 10) {
                    adv = orderLine.getAdvert().substring(0, 10);
                }
                HBox hBox = new HBox(20);
                hBox.setStyle("-fx-border-style: solid");
                itemBoxCartMap.put(orderLine, hBox);
                hBox.setAlignment(Pos.CENTER_LEFT);
                hBox.getChildren().add(new Label("ITEM: " + orderLine.getIt().getName() + ", QUANTITY: "
                        + orderLine.getQuantity() + "\n, TEXT: "
                        + adv + ", COST: "
                        + orderLine.getCost()));

                Button removeButton = new Button("Remove Order Line");
                removeButton.setAlignment(Pos.CENTER_RIGHT);
                EventHandler<ActionEvent> removeHandler = removeEvent -> {
                    orderBuilder.removeOrderLine(orderLine);
                    dialogVbox.getChildren().remove(itemBoxCartMap.get(orderLine));
                    itemBoxCartMap.remove(orderLine);
                    this.renderCartTotalCost(orderBuilder.getCost());
                    costInfo.setText("OrderLines: [" + orderBuilder.getCost() + " PLN]");
                };

                removeButton.setOnAction(removeHandler);
                hBox.getChildren().add(removeButton);
                dialogVbox.getChildren().add(hBox);
            });

            Button submitButton = new Button("Submit to Shop");
            submitButton.setAlignment(Pos.BOTTOM_CENTER);
            dialogVbox.getChildren().add(submitButton);

            Scene dialogScene = new Scene(dialogVbox, 400, 450);
            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(thisStage);
            dialog.setScene(dialogScene);
            openPopUpEvent.consume();

            EventHandler<ActionEvent> buttonHandler = inputEvent -> {
                try {
                    appFlow.submitOrderToShop();
                    inputEvent.consume();
                } catch (RemoteException e) {
                    connectionLabel.setText("Submission failed!");
                }
                dialog.close();
            };
            submitButton.setOnAction(buttonHandler);
            dialog.setScene(dialogScene);
            dialog.show();
        };
    }

    public void renderOrders(List<SubmittedOrder> submittedOrders) {
        // List<SubmittedOrder> clientSubmitted = submittedOrders.stream().filter(submittedOrder -> clientOrderHistoryMap.containsKey(submittedOrder.getId())).toList();
        Platform.runLater(() -> {
            orderNodeMap.forEach((submittedOrderId, node) ->
                    orderBox.getChildren().remove(node));

            submittedOrders.forEach(order -> {
                HBox hBox = new HBox();
                hBox.setStyle("-fx-border-style: solid");
                hBox.setAlignment(Pos.CENTER);
                hBox.setSpacing(10);
                Label orderLabel = new Label(" Order : id=" + order.getId() + ", clientId=" + order.getOrder().getClientID() + ", status=" + order.getStatus().toString());
                hBox.getChildren().add(orderLabel);
                hBox.getChildren().add(RenderUtils.renderOrderDetailsButton(RenderUtils.parseOrderDetails(order)));
                if (order.getStatus() == Status.READY) {
                    hBox.getChildren().add(RenderUtils.renderReceivePackageButton(order, appFlow));
                }

                orderNodeMap.put(order.getId(), hBox);
                orderBox.getChildren().add(hBox);
            });
        });
    }

    public void renderAfterNotification(Integer orderId, Status status) {

    }

    public void renderAfterRegistration(int clientId) {
        Platform.runLater(() ->
                connectionLabel.setText("Successfully logged in! Your id is: " + clientId));
    }

    public void renderCartTotalCost(float cost) {
        Platform.runLater(() ->
                cartLabel.setText("Cart: " + cost + " PLN"));
    }

}

//            TextInputDialog dialog = new TextInputDialog();
//            dialog.setHeaderText("Enter quantity:");
//            button.setOnAction(event -> {
//                if (appFlow.getClientId() == -1) {
//                    return;
//                }
//                Optional<String> s = dialog.showAndWait();
//                s.ifPresent(value -> {
//                    try {
//                        appFlow.addToCart(itemType, Integer.parseInt(value));
//                    } catch (NumberFormatException ignored) {
//                    }
//                });
//            });