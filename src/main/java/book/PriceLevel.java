package book;

import model.Order;

import java.util.ArrayDeque;
import java.util.Queue;

public class PriceLevel {

    double price ;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    Queue<Order> orders = new ArrayDeque<>();

    public void addOrder(Order order)
    {
        orders.add(order);
    }

    public void deleteOrder(Order order)
    {
        orders.remove(order);
    }

    @Override
    public String toString() {
        return "PriceLevel{" +
                "price=" + price +
                ", orders=" + orders +
                '}';
    }
}
