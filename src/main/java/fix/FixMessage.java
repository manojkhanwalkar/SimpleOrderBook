package fix;

import java.util.HashMap;
import java.util.Map;

public class FixMessage {

    Map<String,String> tagValue = new HashMap<>();

    public String get(String tag)
    {
        return tagValue.get(tag);
    }

    public void add(String tag,String value)
    {
        tagValue.put(tag,value);
    }

    public String toFixString()
    {
        return null ;
    }

    public static FixMessage fromFixString(String fix)
    {
        return new FixMessage();
    }


}
