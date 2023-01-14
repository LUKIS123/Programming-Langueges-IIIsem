package pl.edu.pwr.lgawron.lab07.client.flow;

import model.ItemType;
import model.Order;
import model.OrderLine;
import pl.edu.pwr.lgawron.lab07.client.utils.OrderBuilder;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCartService {
    private boolean isEmpty;
    private final Map<Integer, Order> clientOrderHistoryMap;
    private OrderBuilder orderBuilder;
    private final ClientAppRenderer renderer;

    public ShoppingCartService(ClientAppRenderer clientAppRenderer) {
        this.clientOrderHistoryMap = new HashMap<>();
        this.isEmpty = true;
        this.renderer = clientAppRenderer;
    }

    public void addOrderLine(ItemType itemType, int quantity, int clientId, String advert) {
        if (isEmpty) {
            orderBuilder = new OrderBuilder(clientId);
            isEmpty = false;
        }
        orderBuilder.addSingleOrderLine(new OrderLine(itemType, quantity, advert));
        renderer.renderCartTotalCost(orderBuilder.getCost());
    }

    public Order buildOrder() {
        Order order = orderBuilder.buildOrder();
        isEmpty = true;
        return order;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public OrderBuilder getOrderBuilder() {
        return orderBuilder;
    }

    public void putToHistory(int orderId, Order order) {
        clientOrderHistoryMap.put(orderId, order);
    }

    public Map<Integer, Order> getClientOrderHistoryMap() {
        return clientOrderHistoryMap;
    }

}
