package book;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class OrderBookManager {

static class Holder
{
    static OrderBookManager manager = new OrderBookManager();
}

    private OrderBookManager() {}

    public static OrderBookManager getInstance()
    {
        return Holder.manager;
    }

    ConcurrentMap<String, OrderBook> books = new ConcurrentHashMap<>();

    public OrderBook createOrderBook(String symbol)
    {


        OrderBook book = new OrderBook();

        books.put(symbol, book);
        return book ;

    }

    public void print()
    {
        System.out.println(books);
    }

}
