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




