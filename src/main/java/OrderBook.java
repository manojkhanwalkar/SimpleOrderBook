import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class OrderBook {

    PriceLevels buy = new PriceLevels();
    PriceLevels sell = new PriceLevels();

    ConcurrentMap<String,Order> orders = new ConcurrentHashMap<>();

    String symbol ;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void addOrder(Order order)
    {
        orders.put(order.getOrderId(),order);
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

    /*
    1. Process orders opposite to the Manning event
    2. First check the mkt order list and pop orders off in qty .  An order may be partially executed - so pop an order only if fully executed or just adjust the quantity .
    3. Once orders are complete in the mkt , then look at orders in the limit - in decreasing price levels (for buy ) and increasing price levels (for sell )
    4. For limit Buy - manning price has to be lower . Stop if the buy limit price is lower the manning price
    5. For limit sell , stop when sell price is higher than the manning price .
    6. If price level is empty , delete the price level .
    7. While popping the order check if it has been deleted .
    8. stop if the manning qty is exhausted or if the manning price is above / below the buy / sell price .

     */

    public void processManning(Manning manning)
    {

    }

    public void deleteOrder(String  orderId)
    {
        // lazy delete - will be removed while popping orders for execution .
        Order order = orders.get(orderId);
        if (order!=null)
            order.setDeleted(true);

 /*       switch (order.getSide())
        {
            case Buy :
                buy.deleteOrder(order);
                break ;
            case Sell :
                sell.deleteOrder(order);
                break ;

        }*/

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




