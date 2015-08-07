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

        }


        return level ;
    }


}
