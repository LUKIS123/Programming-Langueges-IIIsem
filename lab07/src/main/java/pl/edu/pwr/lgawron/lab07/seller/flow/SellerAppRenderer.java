package pl.edu.pwr.lgawron.lab07.seller.flow;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Status;
import model.SubmittedOrder;
import pl.edu.pwr.lgawron.lab07.common.utils.RenderUtils;
import pl.edu.pwr.lgawron.lab07.seller.AppFlow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerAppRenderer {
    private final AppFlow appFlow;
    private final VBox presentOrderBox;
    private final VBox orderHistoryBox;
    private final Map<Integer, Node> presentOrderNodeMap;
    private final Map<Integer, Node> deliveredOrderNodeMap;

    public SellerAppRenderer(AppFlow appFlow, VBox presentOrderBox, VBox orderHistoryBox) {
        this.appFlow = appFlow;
        this.presentOrderBox = presentOrderBox;
        this.orderHistoryBox = orderHistoryBox;
        this.presentOrderNodeMap = new HashMap<>();
        this.deliveredOrderNodeMap = new HashMap<>();
    }

    public void renderOrders(List<SubmittedOrder> submittedOrders) {
        Platform.runLater(() -> {
            presentOrderNodeMap.forEach((submittedOrderId, node) -> {
                node.setVisible(false);
                presentOrderBox.getChildren().remove(node);
            });

            submittedOrders.forEach(order -> {
                HBox hBox = new HBox();
                hBox.setStyle("-fx-border-style: solid");
                hBox.setAlignment(Pos.CENTER);
                hBox.setSpacing(10);
                Label orderLabel = new Label(" Order : id=" + order.getId() +
                        ", clientId=" + order.getOrder().getClientID() + ", status=" + order.getStatus().toString());

                hBox.getChildren().add(orderLabel);
                hBox.getChildren().add(RenderUtils.renderOrderDetailsButton(RenderUtils.parseOrderDetails(order)));
                if (order.getStatus() == Status.DELIVERED) {

                    orderHistoryBox.getChildren().remove(deliveredOrderNodeMap.get(order.getId()));
                    orderHistoryBox.getChildren().add(hBox);
                    deliveredOrderNodeMap.put(order.getId(), hBox);

                    presentOrderNodeMap.remove(order.getId());
                } else {
                    hBox.getChildren().add(RenderUtils.renderSetProcessingButton(order, appFlow));
                    hBox.getChildren().add(RenderUtils.renderSetReadyButton(order, appFlow));
                    presentOrderNodeMap.put(order.getId(), hBox);
                    presentOrderBox.getChildren().add(hBox);
                }
            });
        });
    }

}
