package oms;

import java.util.UUID;

public class IDGenerator {

   // UUID idOne = UUID.randomUUID();

    public static String getId()
    {
        return UUID.randomUUID().toString();
    }
}
