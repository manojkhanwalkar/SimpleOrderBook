package oms;

import fix.FixMessage;
import model.Detail;
import model.Order;
import model.Transaction;

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

        Detail detail = new Detail();
        detail.setOrderId(orderId);

        Transaction transaction = new Transaction();
        transaction.setOrderId(IDGenerator.getId());
        transaction.setTxnId(IDGenerator.getId());

        oms.getOrderCache().put(orderId, order);

        oms.getDetailCache().put(orderId, detail);

        oms.getTransactionCache().put(orderId,transaction);

    }
}
