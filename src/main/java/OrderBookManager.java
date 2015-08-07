import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

public class OrderBookManager {

static class Holder
{
    static OrderBookManager manager = new OrderBookManager();
}

    private OrderBookManager() {}

    public OrderBookManager getInstance()
    {
        return Holder.manager;
    }

    ConcurrentMap<String,OrderBook> books = new ConcurrentHashMap<>();

    public OrderBook createOrderBook(String symbol)
    {

        OrderBook book = books.putIfAbsent(symbol,new OrderBook());
        return book ;

    }

}
