package pl.edu.pwr.lgawron.lab07.common.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import model.SubmittedOrder;

public class RenderUtils {
    // https://www.tutorialspoint.com/how-to-create-an-alert-in-javafx
    public static Button renderOrderDetailsButton(String orderString) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, orderString, ButtonType.OK);
        alert.setHeaderText("Order details...");
        Button button = new Button("Details");
        button.setOnAction(e -> alert.showAndWait());
        return button;
    }

    public static String parseOrderDetails(SubmittedOrder order) {
        StringBuilder stringBuilder = new StringBuilder("Order ID:" + order.getId() + "\n");
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
                    .append(", quantity: ")
                    .append(orderLine.getQuantity())
                    .append(", advert: ")
                    .append(adv)
                    .append(", price: ")
                    .append(orderLine.getCost())
                    .append(" PLN\n");
        });
        return stringBuilder.toString();
    }

}
