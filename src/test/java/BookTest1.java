public class BookTest1 {

    public static void main(String[] args) {
        OrderBookManager manager = OrderBookManager.getInstance();

        OrderBook book = manager.createOrderBook("IBM");

        for (int i=0;i<100;i++)
        {

            Order order = new Order();
            order.setPrice(100-i);
            order.setOrderId("O1" + i) ;
            order.setQuantity(10000+i);
            order.setSide(Side.Buy);
            order.setSymbol("IBM");
            order.setTimeStamp(System.nanoTime());

            book.addOrder(order);

        }


    }

}
