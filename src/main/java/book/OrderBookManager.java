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


        if (symbol == null || symbol.isEmpty())
            throw new IllegalArgumentException("Symbol is required");
        // return the existing book rather than silently replacing it (and dropping its orders)
        return books.computeIfAbsent(symbol, s -> {
            OrderBook book = new OrderBook();
            book.setSymbol(s);
            return book;
        });

    }

    public void print()
    {
        System.out.println(books);
    }

}
