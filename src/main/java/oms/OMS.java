package oms;

import cache.Cache;
import fix.FixMessage;
import fix.TagConstants;
import model.Detail;
import model.Execution;
import model.Order;
import model.Transaction;
import txn.Context;

import javax.transaction.UserTransaction;
import java.util.List;

import static fix.TagConstants.MsgType;
import static fix.TagConstants.NEWORDERSINGLE;

public class OMS {

    Cache<String, Order> orderCache = new Cache<>();
    Cache<String, Execution> executionCache = new Cache<>();
    Cache<String, Detail> detailCache = new Cache<>();
    Cache<String, Transaction> transactionCache = new Cache<>();
    Cache<String, List> listCache = new Cache<>();

    public Cache<String, Order> getOrderCache() {
        return orderCache;
    }

    public void setOrderCache(Cache<String, Order> orderCache) {
        this.orderCache = orderCache;
    }

    public Cache<String, Execution> getExecutionCache() {
        return executionCache;
    }

    public void setExecutionCache(Cache<String, Execution> executionCache) {
        this.executionCache = executionCache;
    }

    public Cache<String, Detail> getDetailCache() {
        return detailCache;
    }

    public void setDetailCache(Cache<String, Detail> detailCache) {
        this.detailCache = detailCache;
    }

    public Cache<String, Transaction> getTransactionCache() {
        return transactionCache;
    }

    public void setTransactionCache(Cache<String, Transaction> transactionCache) {
        this.transactionCache = transactionCache;
    }

    public Cache<String, List> getListCache() {
        return listCache;
    }

    public void setListCache(Cache<String, List> listCache) {
        this.listCache = listCache;
    }

    static class Holder {
        static OMS factory = new OMS();
    }

    public static OMS getInstance()
    {
        return Holder.factory;
    }

    private OMS()
    {

    }


    //TODO
    /*
    1. Add List , add orders to list
    2. Create Execution
    3. Create Order
    4. Split List
    5. Aggregate List

     */

    public void process(FixMessage message) throws Exception
    {
        String tag = message.get(MsgType);
        UserTransaction txn = Context.getUserTransaction();
        txn.begin();
        switch(tag)
        {
            case NEWORDERSINGLE : {

                OrderProcessor orderProcessor = new OrderProcessor(message, this);
                orderProcessor.process();
                break;
            }
        }

        txn.commit();
        System.out.println(orderCache);

    }




}
