package book;

import book.PriceLevel;
import model.Order;
import model.OrderType;
import org.apache.commons.collections4.list.TreeList;

public class PriceLevels {

    TreeList<PriceLevel> list = new TreeList<>();

    PriceLevel mktLevel = new PriceLevel();

    public synchronized void addOrder(Order order)
    {
        if (order.getOrderType()== OrderType.Market)
        {
            mktLevel.addOrder(order);
        }
        else {
            PriceLevel priceLevel = getPriceLevel(order.getPrice());
            priceLevel.addOrder(order);
        }
    }

    public synchronized void deleteOrder(Order order)
    {
        if (order.getOrderType()==OrderType.Market)
        {
            mktLevel.deleteOrder(order);
        }
        else {
            PriceLevel priceLevel = getPriceLevel(order.getPrice());
            priceLevel.deleteOrder(order);
        }
    }

    private PriceLevel getPriceLevel(double price)
    {

        PriceLevel level = null;

        if (list.isEmpty())
        {
            level = new PriceLevel();
            level.setPrice(price);
            list.add(level);

        }
        else
        {
            for (int i=0;i<list.size();i++)
            {
                PriceLevel curr = list.get(i);
                if (price == curr.getPrice())
                {
                    level = curr ;
                    break ;
                }
                if (price < curr.getPrice() )
                {
                    level = new PriceLevel();
                    level.setPrice(price);
                    list.add(i,level);
                    break;
                }
            }
            if (list==null)
            {
                level = new PriceLevel();
                level.setPrice(price);
                list.add(level);
            }

        }


        return level ;
    }

    @Override
    public String toString() {
        return "PriceLevels{" +
                "list=" + list +
                ", mktLevel=" + mktLevel +
                '}';
    }
}
