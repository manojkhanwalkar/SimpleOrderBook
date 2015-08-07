import java.util.Iterator;
import java.util.LinkedList;

public class PriceLevels {

    LinkedList<PriceLevel> list = new LinkedList<>();

    public void addOrder(Order order)
    {
        PriceLevel priceLevel = getPriceLevel(order.getPrice());
        priceLevel.addOrder(order);
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


}
