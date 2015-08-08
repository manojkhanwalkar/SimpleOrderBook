public class BookTest1 {

    public static void main(String[] args) {
        OrderBookManager manager = OrderBookManager.getInstance();

        OrderBook book = manager.createOrderBook("IBM");
        book.setSymbol("IBM");

        for (int i=0;i<5;i++)
        {

            Order order = new Order();
            order.setPrice(1000-i);
            order.setOrderId("O1" + i) ;
            order.setQuantity(10);
            order.setSide(Side.Sell);
            order.setSymbol("IBM");
            order.setTimeStamp(System.nanoTime());
            order.setOrderType(OrderType.Limit);

            book.addOrder(order);

        }


  /*      for (int i=0;i<2;i++)
        {

            book.deleteOrder("O1" + i);

        }*/

        Manning manning = new Manning();
        manning.setQuantity(20000);
        manning.setSide(Side.Buy);
        manning.setPrice(1000);
        book.processManning(manning);

        manager.print();


    }

}
