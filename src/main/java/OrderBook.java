

public class OrderBook {

    PriceLevels buy = new PriceLevels();
    PriceLevels sell = new PriceLevels();



    public void addOrder(Order order)
    {
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

    public void deleteOrder(String orderId)
    {

    }

    public void modifyOrder(Order order)
    {

    }



}




