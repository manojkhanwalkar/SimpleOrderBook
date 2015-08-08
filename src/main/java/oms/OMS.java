package oms;

import cache.Cache;
import fix.FixMessage;
import model.Detail;
import model.Execution;
import model.Order;
import model.Transaction;

import java.util.List;

public class OMS {

    Cache<String, Order> orderCache = new Cache<>();
    Cache<String, Execution> executionCache = new Cache<>();
    Cache<String, Detail> detailCache = new Cache<>();
    Cache<String, Transaction> transactionCache = new Cache<>();
    Cache<String, List> listCache = new Cache<>();


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

    public void process(FixMessage message)
    {

    }




}
