package CarPark.client;import CarPark.client.ocsf.AbstractClient;import CarPark.entities.messages.LoginMessage;import CarPark.entities.messages.OrderMessage;import CarPark.entities.messages.ParkingListMessage;import CarPark.entities.messages.PricesMessage;import org.greenrobot.eventbus.EventBus;public class SimpleClient extends AbstractClient {    private static SimpleClient client = null;    public SimpleClient(String host, int port) {        super(host, port);    }    public static void main(String[] args) {    }    public static SimpleClient getClient() {        if (client == null) {            client = new SimpleClient("localhost", 3000);        }        return client;    }    @Override    protected void handleMessageFromServer(Object msg) {     //function handles message from server        Class<?> msgClass = msg.getClass();        if (ParkingListMessage.class.equals(msgClass)) {            ParkingListMessage message = (ParkingListMessage) msg;            EventBus.getDefault().post(message);        }        if (PricesMessage.class.equals(msgClass)) {            PricesMessage message = (PricesMessage) msg;            EventBus.getDefault().post(message);        }        if (OrderMessage.class.equals(msgClass)) {            OrderMessage message = (OrderMessage) msg;            EventBus.getDefault().post(message);        }        if (LoginMessage.class.equals(msgClass)){            LoginMessage message = (LoginMessage) msg;            EventBus.getDefault().post(message);        }    }//    private void pushComplaints(LinkedList<Object> msg) {//        ComplaintHandlerTableController tableController = (ComplaintHandlerTableController) controller;//        tableController.pullComplaints(FXCollections.observableArrayList(((List<Complaint>) msg.get(1))));//    }////    private void pushParkinglots(LinkedList<Object> msg) {//        ParkingListController tableController = (ParkingListController) controller;//        tableController.pullParkinglots(FXCollections.observableArrayList(((List<Parkinglot>) msg.get(1))));//    }}