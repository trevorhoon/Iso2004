package restaurantorderingsystem;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private final String orderId;
    private final List<OrderItem> orderItems;

    public Order(String orderId) {
        this.orderId = orderId;
        this.orderItems = new ArrayList<OrderItem>();
    }

    public String getOrderId() {
        return orderId;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
    }

    public void removeOrderItem(int index) {
        orderItems.remove(index);
    }
}
