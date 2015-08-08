package oms;

public class OMS {


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




}
