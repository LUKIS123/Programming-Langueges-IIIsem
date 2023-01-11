package pl.edu.pwr.lgawron.lab07.client.utils;

import model.ItemType;
import model.Order;
import model.OrderLine;

import java.util.ArrayList;
import java.util.List;

public class OrderBuilder {
    private final int clientId;
    private final List<OrderLine> orderLineList;

    public OrderBuilder(int clientId) {
        this.clientId = clientId;
        this.orderLineList = new ArrayList<>();
    }

    public OrderBuilder addSingleOrderLine(ItemType type, int quantity, String advert) {
        orderLineList.add(new OrderLine(type, quantity, advert));
        return this;
    }

    public OrderBuilder addSingleOrderLine(OrderLine orderLine) {
        orderLineList.add(orderLine);
        return this;
    }

    public OrderBuilder addOrderLines(List<OrderLine> lines) {
        orderLineList.addAll(lines);
        return this;
    }

    public OrderBuilder removeOrderLine(OrderLine orderLine) {
        orderLineList.remove(orderLine);
        return this;
    }

    public Order buildOrder() {
        Order order = new Order(clientId);
        orderLineList.forEach(order::addOrderLine);
        return order;
    }

    public int getClientId() {
        return clientId;
    }

    public List<OrderLine> getOrderLineList() {
        return orderLineList;
    }

    public float getCost() {
        return orderLineList.stream().map(OrderLine::getCost).reduce(0f, Float::sum);
    }

}
