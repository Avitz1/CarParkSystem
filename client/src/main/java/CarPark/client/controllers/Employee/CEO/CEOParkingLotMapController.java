package CarPark.client.controllers.Employee.CEO;

import CarPark.client.SimpleChatClient;
import CarPark.client.SimpleClient;
import CarPark.entities.CEO;
import CarPark.entities.Employee;
import CarPark.entities.ParkingLotWorker;
import CarPark.entities.ParkingSlot;
import CarPark.entities.messages.Message;
import CarPark.entities.messages.ParkingLotMapMessage;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class CEOParkingLotMapController {

    @FXML
    private AnchorPane window;

    @FXML
    private GridPane parkingLotMap;

    @FXML
    private Button backBtn;

    @FXML
    private ComboBox<String> parkingLotChoice;

    @FXML
    private ComboBox<String> floorChoice;

    @FXML
    private Button submitBtn;


    int rows;
    List<ParkingSlot> parkingSlotsList;
    ParkingSlot changedStatus;

    @FXML
    void goBack(ActionEvent event) throws IOException {
        Employee current_employee = (Employee) SimpleClient.getCurrent_user();
        switch (current_employee.getWorkersRole()) {
            case "CEO" -> SimpleChatClient.setRoot("CEOPage");
            case "Customer Service Worker" -> SimpleChatClient.setRoot("CustomerServicePage");
        }
    }

    //get the size of the parking lot in order to render its map using suitable messages
    @FXML
    void getParkingLotRowNum(ActionEvent event)
    {
        ParkingLotMapMessage message = new ParkingLotMapMessage(Message.MessageType.REQUEST, ParkingLotMapMessage.RequestType.GET_ROW,
                parkingLotChoice.getValue());
        try {
            SimpleClient.getClient().sendToServer(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ParkingLotMapMessage message2 = new ParkingLotMapMessage(Message.MessageType.REQUEST, ParkingLotMapMessage.RequestType.GET_PARKING_SLOTS,
                parkingLotChoice.getValue());
        try {
            SimpleClient.getClient().sendToServer(message2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(floorChoice.getValue() == null)
        {
            createParkingLotMap("A");
            setOccupiedSlots("A");
        }
    }


    @FXML
    void chooseFloor(ActionEvent event)
    {
        ParkingLotMapMessage message = new ParkingLotMapMessage(Message.MessageType.REQUEST,
                ParkingLotMapMessage.RequestType.GET_PARKING_SLOTS, parkingLotChoice.getValue());
        try {
            SimpleClient.getClient().sendToServer(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //by choosing the desired floor we render the chosen floor view
        if(Objects.equals(floorChoice.getValue(), "A"))
        {
            createParkingLotMap("A");
            setOccupiedSlots("A");
        }
        else if(Objects.equals(floorChoice.getValue(), "B"))
        {
            createParkingLotMap("B");
            setOccupiedSlots("B");
        }
        else if(Objects.equals(floorChoice.getValue(), "C"))
        {
            createParkingLotMap("C");
            setOccupiedSlots("C");
        }
    }


    //check the status of the slot
    public int isOccupied(String slotId, String parkingLotId)
    {
        for(ParkingSlot parkingSlot : parkingSlotsList)
        {
            if(!String.valueOf(parkingSlot.getGeneratedValue().charAt(0)).equals(findParkingLotsIndex(parkingLotChoice.getValue())))
            {
                continue;
            }

            if(Objects.equals(Integer.parseInt(parkingLotId), parkingSlot.getParkinglot().getParkingLotId()) &&
                    parkingSlot.getGeneratedValue().substring(2).equals(slotId) &&
                    Objects.equals(String.valueOf(parkingSlot.getSpotStatus()), "EMPTY"))
            {
                return 0;
            }
            else if(Objects.equals(Integer.parseInt(parkingLotId), parkingSlot.getParkinglot().getParkingLotId()) &&
                    parkingSlot.getGeneratedValue().substring(2).equals(slotId) &&
                    Objects.equals(String.valueOf(parkingSlot.getSpotStatus()), "USED"))
            {
                return 1;
            }
            else if(Objects.equals(Integer.parseInt(parkingLotId), parkingSlot.getParkinglot().getParkingLotId()) &&
                    parkingSlot.getGeneratedValue().substring(2).equals(slotId) &&
                    Objects.equals(String.valueOf(parkingSlot.getSpotStatus()), "RESTRICTED"))
            {
                return 2;
            }
            else if(Objects.equals(Integer.parseInt(parkingLotId), parkingSlot.getParkinglot().getParkingLotId()) &&
                    parkingSlot.getGeneratedValue().substring(2).equals(slotId) &&
                    Objects.equals(String.valueOf(parkingSlot.getSpotStatus()), "RESERVED"))
            {
                return 3;
            }
        }
        return -1;
    }


    /**
     * render the basic parking lot map - using the sizes we've got before
     */

    @FXML
    public void createParkingLotMap(String floor)
    {
        parkingLotMap.getChildren().clear();
        for (int row = 0; row < 3; row++)
        {
            for (int col = 0; col < rows; col++)
            {
                Rectangle rect = new Rectangle(100, 50);
                rect.setFill(Color.LIGHTGRAY);
                rect.setStroke(Color.BLACK);
                parkingLotMap.add(rect, col, row);
                Text label = new Text(floor + (1 + row * rows + col));

                parkingLotMap.add(label, col, row);
            }
        }
    }

    /**
     * find the parking lot index through the combo-box order
     */
    public String findParkingLotsIndex(String name)
    {
        List<String> parkingLotList = parkingLotChoice.getItems();
        int index = 1;
        for(String pl_name : parkingLotList)
        {
            if(Objects.equals(pl_name, name))
            {
                break;
            }
            index++;
        }
        return String.valueOf(index);
    }


    /**
     * An important function that allows the parking lot worker to manage the parking lot slots.
     * By clicking the rectangle, representing the slot, the worker changes the status of the slot as follows:
     * (one click for each color and status transition)
     * 1. EMPTY -> RESTRICTED: LightGray -> Red
     * 2. RESTRICTED -> RESERVED: Red -> Blue
     * 3. RESERVED -> EMPTY: Blue -> LightGray
     */
    public void setOccupiedSlots(String floor)
    {
        for (int row = 0; row < 3; row++)
        {
            for (int col = 0; col < rows; col++)
            {
                String label = floor + (1 + row * rows + col);

                Text labelText = new Text(floor + (1 + row * rows + col));
                Rectangle rect = (Rectangle)getNodeByRowColumnIndex(row, col, parkingLotMap);

                if (isOccupied(label, findParkingLotsIndex(parkingLotChoice.getValue())) == 0)
                {
                    rect.setFill(Color.LIGHTGRAY); //dye the rectangle represents the slot in light gray if it's vacant
                    parkingLotMap.add(labelText, col, row);
                }
                else if (isOccupied(label, findParkingLotsIndex(parkingLotChoice.getValue())) == 1)
                {
                    rect.setFill(Color.GREEN); //dye the rectangle represents the parking slot in green if it's occupied
                    parkingLotMap.add(labelText, col, row);
                }
                else if (isOccupied(label, findParkingLotsIndex(parkingLotChoice.getValue())) == 2)
                {
                    rect.setFill(Color.RED); //dye the rectangle represents the parking slot in green if it's faulty
                    parkingLotMap.add(labelText, col, row);
                }
                else if (isOccupied(label, findParkingLotsIndex(parkingLotChoice.getValue())) == 3)
                {
                    rect.setFill(Color.BLUE); //dye the rectangle represents the parking slot in blue if it's reserved
                    parkingLotMap.add(labelText, col, row);
                }

                //By clicking on an empty slot
                Employee current_worker = (Employee) SimpleClient.getCurrent_user();
                if(current_worker.getWorkersRole().equals("Customer Service Worker")) {

                    Paint currentColor = rect.getFill();
                    if (currentColor != Color.GREEN) {
                        rect.setOnMouseClicked(event -> {
                            Paint currentColor2 = rect.getFill();
                            if (currentColor2 == Color.BLUE) {
                                rect.setFill(Color.RED);
                            } else if (currentColor2 == Color.RED) {
                                rect.setFill(Color.LIGHTGRAY);
                            } else {
                                rect.setFill(Color.BLUE);
                            }
                        });
                    }
                }
            }
        }
    }


    public Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane)
    {
        Node result = null;
        ObservableList<Node> children = gridPane.getChildren();

        for (Node node : children) {
            if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column)
            {
                result = node;
                break;
            }
        }

        return result;
    }


    /**
     * By clicking on the Submit button the worker confirms the changes that have been made.
     * The parking slots DB will be updated accordingly, by sending the updated list of objects
     * to the server.
     */

    @FXML
    void submitChanges(ActionEvent event)
    {
        for (ParkingSlot parkingSlot : parkingSlotsList)
        {
            if(!String.valueOf(parkingSlot.getGeneratedValue().charAt(0)).equals(findParkingLotsIndex(parkingLotChoice.getValue())))
            {
                continue;
            }

            Node rect;
            String label;
            for (int row = 0; row < 3; row++)
            {
                for (int col = 0; col < rows; col++)
                {
                    rect = getNodeByRowColumnIndex(row, col, parkingLotMap);
                    label =  floorChoice.getValue() + (1 + row * rows + col);
                    Rectangle rectangle = (Rectangle)rect;
                    Paint currentColor = rectangle.getFill();

                    if (currentColor == Color.RED && parkingSlot.getGeneratedValue().substring(2).equals(label))
                    {
                        parkingSlot.setStatus(ParkingSlot.Status.valueOf("RESTRICTED"));
                    }
                    else if (currentColor == Color.BLUE && parkingSlot.getGeneratedValue().substring(2).equals(label))
                    {
                        parkingSlot.setStatus(ParkingSlot.Status.valueOf("RESERVED"));
                    }
                    else if (currentColor == Color.LIGHTGRAY && parkingSlot.getGeneratedValue().substring(2).equals(label))
                    {
                        parkingSlot.setStatus(ParkingSlot.Status.valueOf("EMPTY"));
                    }
                }
            }
        }

        ParkingLotMapMessage message = new ParkingLotMapMessage(Message.MessageType.REQUEST,
                ParkingLotMapMessage.RequestType.SHOW_PARKING_LOT_MAP, parkingSlotsList);
        try {
            SimpleClient.getClient().sendToServer(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //initialize the GUI
    @FXML
    void initialize() throws IOException {
        EventBus.getDefault().register(this);

        Employee employee =  (Employee) SimpleClient.getCurrent_user();
        if (Objects.equals(employee.getWorkersRole(), "CEO")) {

            submitBtn.setDisable(true);
        }
        parkingLotChoice.getItems().add("Haifa");
        parkingLotChoice.getItems().add("Tel Aviv");
        parkingLotChoice.getItems().add("Jerusalem");
        parkingLotChoice.getItems().add("Be'er Sheva");
        parkingLotChoice.getItems().add("Eilat");

        floorChoice.getItems().add("A");
        floorChoice.getItems().add("B");
        floorChoice.getItems().add("C");
        floorChoice.setPromptText("Choose Floor");
    }

    @Subscribe
    public void newResponse(ParkingLotMapMessage new_message) {
        switch (new_message.response_type) {
            case SET_ROW:
                Platform.runLater(() -> {
                    this.rows = new_message.rows;
                    floorChoice.setValue("Floor");
                });
                break;
            case SEND_PARKING_SLOTS:
                this.parkingSlotsList = new_message.parkingSlots;
                break;
            case SEND_PARKING_LOT_MAP:
                break;
        }
    }

}
