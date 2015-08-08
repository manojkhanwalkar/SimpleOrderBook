package oms;

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

}
