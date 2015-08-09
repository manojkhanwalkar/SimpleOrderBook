package oms;

import fix.FixMessage;
import model.Order;

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

        Order order = new Order();
        order.setOrderId("O1");
        oms.getOrderCache().put("O1",order);


    }
}
