import book.OrderBook;
import book.OrderBookManager;
import model.Manning;
import model.Order;
import model.OrderType;
import model.Side;

/**
 * Regression checks for order book input validation and matching limits.
 * Run with: java -cp <classes>:<deps> OrderBookSafetyTest  (exits non-zero on failure)
 */
public class OrderBookSafetyTest {

    static int failures = 0;

    public static void main(String[] args) {
        rejectsInvalidOrders();
        rejectsDuplicateOrderId();
        priceAboveAllLevelsIsAccepted();
        manningNeverOverfills();
        createOrderBookKeepsExistingBook();

        if (failures > 0) {
            System.err.println(failures + " check(s) failed");
            System.exit(1);
        }
        System.out.println("All checks passed");
    }

    static Order order(String id, Side side, OrderType type, double price, int qty) {
        Order o = new Order();
        o.setOrderId(id);
        o.setSide(side);
        o.setOrderType(type);
        o.setPrice(price);
        o.setQuantity(qty);
        o.setSymbol("IBM");
        return o;
    }

    static void check(boolean condition, String name) {
        if (!condition) {
            failures++;
            System.err.println("FAIL: " + name);
        }
    }

    static void expectRejected(OrderBook book, Order o, String name) {
        try {
            book.addOrder(o);
            check(false, name);
        } catch (IllegalArgumentException expected) {
            // ok
        }
    }

    static void rejectsInvalidOrders() {
        OrderBook book = new OrderBook();
        expectRejected(book, order("n1", Side.Sell, OrderType.Limit, 10, -5), "negative quantity rejected");
        expectRejected(book, order("n2", Side.Sell, OrderType.Limit, 10, 0), "zero quantity rejected");
        expectRejected(book, order("n3", Side.Sell, OrderType.Limit, Double.NaN, 5), "NaN price rejected");
        expectRejected(book, order("n4", Side.Sell, OrderType.Limit, -1, 5), "negative price rejected");
        expectRejected(book, order(null, Side.Sell, OrderType.Limit, 10, 5), "null id rejected");
        expectRejected(book, order("n5", null, OrderType.Limit, 10, 5), "null side rejected");
        expectRejected(book, order("n6", Side.Sell, null, 10, 5), "null type rejected");
    }

    static void rejectsDuplicateOrderId() {
        OrderBook book = new OrderBook();
        book.addOrder(order("d1", Side.Sell, OrderType.Limit, 10, 5));
        expectRejected(book, order("d1", Side.Sell, OrderType.Limit, 11, 5), "duplicate order id rejected");
    }

    static void priceAboveAllLevelsIsAccepted() {
        OrderBook book = new OrderBook();
        book.addOrder(order("p1", Side.Sell, OrderType.Limit, 10, 5));
        try {
            book.addOrder(order("p2", Side.Sell, OrderType.Limit, 20, 5));
        } catch (RuntimeException e) {
            check(false, "order priced above all levels accepted (" + e + ")");
        }
    }

    static void manningNeverOverfills() {
        OrderBook book = new OrderBook();
        Order mkt = order("m1", Side.Sell, OrderType.Market, 0, 10);
        Order lim = order("l1", Side.Sell, OrderType.Limit, 100, 10);
        book.addOrder(mkt);
        book.addOrder(lim);

        Manning manning = new Manning();
        manning.setSide(Side.Buy);
        manning.setPrice(100);
        manning.setQuantity(10);
        book.processManning(manning);

        // the 10 lots are consumed by the market order; the limit order must stay untouched
        check(lim.getQuantity() == 10, "limit order not filled beyond manning quantity");
        check(manning.getRemainingQty() == 0, "manning fully used");
    }

    static void createOrderBookKeepsExistingBook() {
        OrderBookManager manager = OrderBookManager.getInstance();
        OrderBook first = manager.createOrderBook("SAFETY");
        OrderBook second = manager.createOrderBook("SAFETY");
        check(first == second, "existing book is not replaced");
        check("SAFETY".equals(first.getSymbol()), "book symbol is set");
    }
}
