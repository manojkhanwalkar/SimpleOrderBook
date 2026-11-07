package book;

import model.Manning;
import model.Order;
import book.PriceLevel;
import book.PriceLevels;
import model.OrderType;
import model.Side;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class OrderBook {

    PriceLevels buy = new PriceLevels();
    PriceLevels sell = new PriceLevels();

    ConcurrentMap<String, Order> orders = new ConcurrentHashMap<>();

    String symbol ;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public synchronized void addOrder(Order order)
    {
        validateOrder(order);
        // reject duplicate ids: overwriting the map entry would orphan the resting order,
        // which could then never be found again to be cancelled
        if (orders.putIfAbsent(order.getOrderId(), order) != null)
            throw new IllegalArgumentException("Duplicate order id: " + order.getOrderId());
        switch (order.getSide())
        {
            case Buy :
                  buy.addOrder(order);
                  break ;
            case Sell :
                  sell.addOrder(order);
                   break ;

        }

    }

    private static void validateOrder(Order order)
    {
        if (order == null)
            throw new IllegalArgumentException("Order must not be null");
        if (order.getOrderId() == null || order.getOrderId().isEmpty())
            throw new IllegalArgumentException("Order id is required");
        if (order.getSide() == null)
            throw new IllegalArgumentException("Order side is required");
        if (order.getOrderType() == null)
            throw new IllegalArgumentException("Order type is required");
        if (order.getQuantity() <= 0)
            throw new IllegalArgumentException("Order quantity must be positive");
        if (order.getOrderType() == OrderType.Limit
                && !(order.getPrice() > 0 && !Double.isInfinite(order.getPrice())))
            throw new IllegalArgumentException("Limit order price must be a positive finite number");
    }

    private static void validateManning(Manning manning)
    {
        if (manning == null)
            throw new IllegalArgumentException("Manning must not be null");
        if (manning.getSide() == null)
            throw new IllegalArgumentException("Manning side is required");
        if (manning.getQuantity() <= 0)
            throw new IllegalArgumentException("Manning quantity must be positive");
        if (Double.isNaN(manning.getPrice()))
            throw new IllegalArgumentException("Manning price must be a number");
    }

    /*
    1. Process orders opposite to the model.Manning event
    2. First check the mkt order list and pop orders off in qty .  An order may be partially executed - so pop an order only if fully executed or just adjust the quantity .
    3. Once orders are complete in the mkt , then look at orders in the limit - in decreasing price levels (for buy ) and increasing price levels (for sell )
    4. For limit Buy - manning price has to be lower . Stop if the buy limit price is lower the manning price
    5. For limit sell , stop when sell price is higher than the manning price .
    6. If price level is empty , delete the price level .
    7. While popping the order check if it has been deleted .
    8. stop if the manning qty is exhausted or if the manning price is above / below the buy / sell price .

     */

    public synchronized void processManning(Manning manning)
    {
        validateManning(manning);
        int remainingQty;
        if (manning.getSide()== Side.Buy)
        {
            remainingQty = applyManningToSell(manning);
        }
        else
        {
            remainingQty = applyManningToBuy(manning);

        }
        manning.setRemainingQty(remainingQty);

    }

    private int applyManningToSell(Manning manning)
    {
        // the market level consumes part of the manning quantity; only the rest is
        // available for the limit levels
        int remainingQty = fill(sell.mktLevel.orders, manning.getQuantity());

        List<PriceLevel> ordersList = sell.list;
        for (int i=0;i<ordersList.size() && remainingQty>0;i++)
        {
            PriceLevel priceLevel = ordersList.get(i);
            if (priceLevel.getPrice()>manning.getPrice())
                break ;
            remainingQty = fill(priceLevel.orders, remainingQty);
            if (priceLevel.orders.isEmpty())
                ordersList.remove(i--);
        }
        return remainingQty;
    }

    private int applyManningToBuy(Manning manning)
    {
        int remainingQty = fill(buy.mktLevel.orders, manning.getQuantity());

        List<PriceLevel> ordersList = buy.list;
        for (int i=ordersList.size()-1;i>=0 && remainingQty>0;i--)
        {
            PriceLevel priceLevel = ordersList.get(i);
            if (priceLevel.getPrice()<manning.getPrice())
                break ;
            remainingQty = fill(priceLevel.orders, remainingQty);
            if (priceLevel.orders.isEmpty())
                ordersList.remove(i);
        }
        return remainingQty;
    }

    /**
     * Fills orders from the head of the queue until the quantity is exhausted.
     * Deleted and fully filled orders are removed from the queue and from the id index
     * so the book does not grow without bound. Returns the unfilled quantity.
     */
    private int fill(Queue<Order> queue, int remainingQty)
    {
        while (!queue.isEmpty() && remainingQty>0)
        {
            Order order = queue.peek();
            if (order.isDeleted())
            {
                queue.remove();
                orders.remove(order.getOrderId(), order);
                continue;
            }
            if (order.getQuantity()<=remainingQty)
            {
                queue.remove();
                orders.remove(order.getOrderId(), order);
                remainingQty-=order.getQuantity();
            }
            else
            {
                order.setQuantity(order.getQuantity()-remainingQty);
                remainingQty = 0;
            }
        }
        return remainingQty;
    }

    public synchronized void deleteOrder(String  orderId)
    {
        // lazy delete - will be removed while popping orders for execution .
        Order order = orders.get(orderId);
        if (order!=null)
            order.setDeleted(true);

    }

    @Override
    public String toString() {
        return "OrderBook{" +
                "buy=" + buy +
                ", sell=" + sell +
                ", symbol='" + symbol + '\'' +
                '}';
    }
}




