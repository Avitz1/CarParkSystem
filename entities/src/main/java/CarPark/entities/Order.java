package CarPark.entities;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "Orders")

public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private long customerId;
    private int carId;
    private String parkingLot;
    private String email;
    private LocalDateTime arrivalTime;
    private LocalDateTime estimatedLeavingTime;
    private double ordersPrice;
    private Date date;
    private Status orderStatus = Status.APPROVED;

    @ManyToOne
    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public enum Status {APPROVED, CANCELLED, NOTIFIED};

    public Order(long customerId, int carId, String parkingLot, String email,
                 LocalDateTime arrivalTime, LocalDateTime estimatedLeavingTime, double ordersPrice, Date date)
    {
        super();
        this.customerId = customerId;
        this.carId = carId;
        this.parkingLot = parkingLot;
        this.email = email;
        this.arrivalTime = arrivalTime;
        this.estimatedLeavingTime = estimatedLeavingTime;
        this.ordersPrice = ordersPrice;
        this.date = date;
    }

    public Order() {}

    public long getCustomerId() {
        return customerId;
    }
    public int getCarId() {
        return carId;
    }
    public String getParkingLotId() { return parkingLot; }
    public String getEmail() {
        return email;
    }
    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }
    public LocalDateTime getEstimatedLeavingTime() {
        return estimatedLeavingTime;
    }
    public double getOrdersPrice() { return ordersPrice; }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }
    public void setCarId(int carId) {
        this.carId = carId;
    }
    public void setParkingLotId(String parkingLot) {
        this.parkingLot = parkingLot;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    public void setEstimatedLeavingTime(LocalDateTime estimatedLeavingTime) { this.estimatedLeavingTime = estimatedLeavingTime; }
    public void setOrdersPrice(double ordersPrice) { this.ordersPrice = ordersPrice; }

    public int getId() {
        return id;
    }
    public Boolean getStatus() {
        return orderStatus== Status.APPROVED;
    }
    public void setSpotStatus(Status spotStatus) {
        orderStatus = spotStatus;
    }

    public  Date getDate(){
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }


}