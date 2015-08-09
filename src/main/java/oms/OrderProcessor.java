package oms;

import cache.Tuple;
import fix.FixMessage;
import model.Detail;
import model.Order;
import model.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mkhanwalkar on 8/8/15.
 */
public class OrderProcessor {

    FixMessage message ;
    OMS oms ;
    public OrderProcessor(FixMessage message) {
    }

    public OrderProcessor(FixMessage message, OMS oms) {
        this.message = message ;
        this.oms = oms;
    }

    public void process() {
        String orderId = IDGenerator.getId();

        Order order = new Order();
        order.setOrderId(orderId);
        order.setClientOrderID(message.get("ClientOrderID"));
        order.setSymbol(message.get("Symbol"));

        Detail detail = new Detail();
        detail.setOrderId(orderId);

        Transaction transaction = new Transaction();
        transaction.setOrderId(IDGenerator.getId());
        transaction.setTxnId(IDGenerator.getId());


        Tuple<String,String> t = new Tuple<>("ClientOrderID" , order.getClientOrderID());
        Tuple<String,String> t1 = new Tuple<>("Symbol" , order.getSymbol());
        List<Tuple<String,String>> tuples = new ArrayList<>();
        tuples.add(t);
        tuples.add(t1);



        oms.getOrderCache().put(orderId, order,tuples);

        oms.getDetailCache().put(orderId, detail);

        oms.getTransactionCache().put(orderId,transaction);

    }
}
