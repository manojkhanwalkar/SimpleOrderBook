package oms;

import model.Execution;
import model.Order;
import model.OrderList;
import model.Transaction;

public class OMSFactory {

    static class Holder {
        static OMSFactory factory = new OMSFactory();
    }

    public static OMSFactory getInstance()
    {
        return Holder.factory;
    }

    private OMSFactory()
    {

    }

    public Order createOrder()
    {
        return new Order();

    }

    public Execution createExecution()
    {
        return new Execution();

    }

    public Transaction createTransaction()
    {
        return new Transaction();

    }

    public OrderList createList()
    {
        return new OrderList();

    }


}
