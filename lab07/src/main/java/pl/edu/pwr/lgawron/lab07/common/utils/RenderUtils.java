package pl.edu.pwr.lgawron.lab07.common.utils;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Status;
import model.SubmittedOrder;
import pl.edu.pwr.lgawron.lab07.client.ClientAppFlow;
import pl.edu.pwr.lgawron.lab07.client.flow.ClientAppRenderer;
import pl.edu.pwr.lgawron.lab07.client.flow.ShoppingCartService;
import pl.edu.pwr.lgawron.lab07.seller.SellerAppFlow;

import java.rmi.RemoteException;
import java.util.Map;

public class RenderUtils {
    // https://www.tutorialspoint.com/how-to-create-an-alert-in-javafx
    public static Stage renderStartingPupUp(String info, ActionEvent openPopUpEvent, TextField port,
                                            TextField proxy, Button button,
                                            Label communicate, EventHandler<ActionEvent> buttonHandler) {

        Node node = (Node) openPopUpEvent.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();

        VBox dialogVbox = new VBox(20);
        dialogVbox.setAlignment(Pos.CENTER);
        dialogVbox.getChildren().add(new Text(info));

        if (proxy != null) {
            dialogVbox.getChildren().add(proxy);
        }
        dialogVbox.getChildren().add(port);
        dialogVbox.getChildren().add(button);
        dialogVbox.getChildren().add(communicate);

        Scene dialogScene = new Scene(dialogVbox, 350, 250);
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(thisStage);
        dialog.setScene(dialogScene);
        openPopUpEvent.consume();

        button.setOnAction(buttonHandler);
        dialog.setScene(dialogScene);
        return dialog;
    }

    public static Button renderOrderDetailsButton(String orderString) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, orderString, ButtonType.OK);
        alert.setHeaderText("Order details...");
        Button button = new Button("Details");
        button.setOnAction(e -> alert.showAndWait());
        return button;
    }

    public static String parseOrderDetails(SubmittedOrder order) {
        StringBuilder stringBuilder = new StringBuilder(" Order ID:" + order.getId() + "\n");
        stringBuilder
                .append("Client ID: ")
                .append(order.getOrder().getClientID())
                .append(", Status: ")
                .append(order.getStatus())
                .append("\n")
                .append("\nOrderLines:\n");
        order.getOrder().getOll().forEach(orderLine -> {
            String adv = orderLine.getAdvert();
            if (orderLine.getAdvert().length() >= 10) {
                adv = orderLine.getAdvert().substring(0, 10);
            }
            stringBuilder
                    .append(orderLine.getIt().getName())
                    .append(" Quantity: ")
                    .append(orderLine.getQuantity())
                    .append(", advert: ")
                    .append(adv)
                    .append(", price: ")
                    .append(orderLine.getCost())
                    .append(" PLN\n");
        });
        return stringBuilder.toString();
    }

    public static Button renderReceivePackageButton(SubmittedOrder order, ClientAppFlow clientAppFlow) {
        Button button = new Button("Pick Up");
        button.setOnAction(event -> {
            if (order.getStatus() != Status.READY) {
                return;
            }
            try {
                boolean b = clientAppFlow.enrollDelivery(order.getId());
                if (!b) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Could not change order status!", ButtonType.OK);
                    alert.setHeaderText("FAILED!");
                    alert.showAndWait();
                } else {
                    clientAppFlow.downloadSubmittedOrdersAndRefresh();
                }
            } catch (RemoteException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, e.getMessage(), ButtonType.OK);
                alert.setHeaderText("FAILED!");
                alert.showAndWait();
            }
        });
        return button;
    }

    public static Node renderSetProcessingButton(SubmittedOrder order, SellerAppFlow sellerAppFlow) {
        Button button = new Button("Set Processing");
        button.setOnAction(event -> {
            if (order.getStatus() != Status.NEW) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Cannot change order status that isn't NEW!", ButtonType.OK);
                alert.setHeaderText("FAILED!");
                alert.showAndWait();
                return;
            }
            try {
                boolean b = sellerAppFlow.setOrderProcessing(order.getId());
                if (!b) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Could not change order status!", ButtonType.OK);
                    alert.setHeaderText("FAILED!");
                    alert.showAndWait();
                }
                sellerAppFlow.downloadOrdersAndRefresh();
            } catch (RemoteException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, e.getMessage(), ButtonType.OK);
                alert.setHeaderText("FAILED!");
                alert.showAndWait();
            }
        });
        return button;
    }

    public static Node renderSetReadyButton(SubmittedOrder order, SellerAppFlow sellerAppFlow) {
        Button button = new Button("Set Ready");
        button.setOnAction(event -> {
            if (order.getStatus() == Status.READY || order.getStatus() == Status.DELIVERED) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Cannot change order status that isn't NEW/PROCESSING!", ButtonType.OK);
                alert.setHeaderText("FAILED!");
                alert.showAndWait();
                return;
            }
            try {
                boolean b = sellerAppFlow.setOrderReady(order.getId());
                if (!b) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Could not change order status!", ButtonType.OK);
                    alert.setHeaderText("FAILED!");
                    alert.showAndWait();
                }
                sellerAppFlow.downloadOrdersAndRefresh();
            } catch (RemoteException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, e.getMessage(), ButtonType.OK);
                alert.setHeaderText("FAILED!");
                alert.showAndWait();
            }
        });
        return button;
    }

    public static EventHandler<ActionEvent> renderSetNotificationButton(ClientAppRenderer clientAppRenderer, ClientAppFlow clientAppFlow) {
        Map<Integer, Status> integerStatusMap = clientAppFlow.getIntegerStatusMap();
        return event -> {
            if (integerStatusMap.isEmpty()) {
                return;
            }
            Node node = (Node) event.getSource();
            Stage thisStage = (Stage) node.getScene().getWindow();

            VBox dialogVbox = new VBox(20);
            dialogVbox.setMinHeight(100);
            dialogVbox.setAlignment(Pos.CENTER);
            dialogVbox.getChildren().add(new Text("Status change of orders:"));
            for (Map.Entry<Integer, Status> integerStatusEntry : integerStatusMap.entrySet()) {
                //ShoppingCartService
                Label label = new Label(" Order: ID=" + integerStatusEntry.getKey()
                        + "\n Changed to status=" + integerStatusEntry.getValue());
                label.setMinSize(50, 14);
                label.setStyle("-fx-border-style: solid");
                dialogVbox.getChildren().add(label);
            }
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setContent(dialogVbox);
            Scene dialogScene = new Scene(scrollPane, 350, 250);

            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(thisStage);
            dialog.setScene(dialogScene);
            event.consume();

            dialog.setScene(dialogScene);
            dialog.show();
            clientAppRenderer.setNotificationCounter(-1);
            clientAppRenderer.renderAfterNotification();
        };
    }

}
