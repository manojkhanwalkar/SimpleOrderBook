import java.util.HashSet;
import java.util.List;
import java.util.Queue;
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
        orders.put(order.getOrderId(), order);
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

    private void applyManningToSell(Manning manning)
    {
        int remainingQty = manning.getQuantity();
        Queue<Order> orders = sell.mktLevel.orders;
        processMktOrders(manning,orders);

        List<PriceLevel> ordersList = sell.list;
        for (int i=0;i<ordersList.size();i++)
        {
            PriceLevel priceLevel = ordersList.get(i);
            if (priceLevel.getPrice()>manning.getPrice())
                return ;
             orders = priceLevel.orders;
            while (!orders.isEmpty())
            {
                Order order = orders.peek();
                if (order.isDeleted())
                {
                    orders.remove();
                    continue;
                }
                if (order.getQuantity()<=remainingQty)
                {
                    orders.remove();
                    remainingQty-=order.getQuantity();
                }
                else
                {

                    order.setQuantity(order.getQuantity()-remainingQty);
                    remainingQty = 0;
                    return ;
                }
            }
            // queue is empty if control comes here , so delete it
            ordersList.remove(i--);


        }


    }


    public void processManning(Manning manning)
    {
        if (manning.getSide()==Side.Buy)
        {
            applyManningToSell(manning);
        }
        else
        {
            applyManningToBuy(manning);

        }

    }

    private void processMktOrders(Manning manning , Queue<Order> orders)
    {
        int remainingQty = manning.getQuantity();
        { // process mkt queue
            while (!orders.isEmpty()) {
                Order order = orders.peek();
                if (order.isDeleted()) {
                    orders.remove();
                    continue;
                }
                if (order.getQuantity() <= remainingQty) {
                    orders.remove();
                    remainingQty -= order.getQuantity();
                } else {

                    order.setQuantity(order.getQuantity() - remainingQty);
                    remainingQty = 0;

                    return;
                }
            }
        }

    }

   /* private boolean shouldProcess(double price1 , double price2)
    {
        if (price1>price2)
            return true ;
        else
            return false ;
    }*/

    private void applyManningToBuy(Manning manning)
    {
        int remainingQty = manning.getQuantity();
        Queue<Order> orders = buy.mktLevel.orders;
        processMktOrders(manning,orders);


        List<PriceLevel> ordersList = buy.list;

        for (int i=ordersList.size()-1;i>=0;i--)
        {
            PriceLevel priceLevel = ordersList.get(i);
            if (priceLevel.getPrice()<manning.getPrice())
                return ;
             orders = priceLevel.orders;
            while (!orders.isEmpty())
            {
                Order order = orders.peek();
                if (order.isDeleted())
                {
                    orders.remove();
                    continue;
                }
                if (order.getQuantity()<=remainingQty)
                {
                    orders.remove();
                    remainingQty-=order.getQuantity();
                }
                else
                {

                    order.setQuantity(order.getQuantity()-remainingQty);
                    remainingQty = 0;
                    return ;
                }
            }
            // queue is empty if control comes here , so delete it
            ordersList.remove(i);

            //i++; // adjust i as list size decreases .


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




