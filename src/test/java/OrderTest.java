import fix.FixMessage;
import oms.OMS;
import oms.OMSFactory;
import static fix.TagConstants.*;

public class OrderTest {


    public static void main(String[] args) throws Exception {

        OMS oms = OMS.getInstance();

        FixMessage message = new FixMessage();
        message.add(MsgType,NEWORDERSINGLE);
        message.add("ClientOrderID", "C01");
        message.add("Symbol", "IBM");
        oms.process(message);

      /*  FixMessage message = new FixMessage();
        message.add(MsgType,NEWORDERSINGLE);
        oms.process(message);*/


    }
}
