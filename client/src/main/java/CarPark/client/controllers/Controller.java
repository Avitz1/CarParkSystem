package CarPark.client.controllers;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class Controller {
    public static void sendAlert(String error, String title, Alert.AlertType type) {
        Platform.runLater(() -> {
            try {
                Alert alert = new Alert(type);
                alert.setTitle(title);
                alert.setHeaderText(error);
                //alert.setContentText("Are you sure?");
                alert.showAndWait().get();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }

    protected void coolButtonClick(Button button) throws InterruptedException {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            button.setStyle("-fx-background-color: #8c73ea");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            button.setStyle("-fx-background-color: #c6acef");
        });
    }

    public void displayDates(DatePicker dp, LocalDate fromDay, LocalDate toDay) {
        // this function gets a datePicker and shows only the dates between "fromDay" to "toDay"
        dp.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(toDay) > 0 || date.compareTo(fromDay) < 0);
            }
        });
    }

    protected Date getPickedDate(DatePicker dp) { //get the picked localDate and convert it to Date
        Instant instant = Instant.from(dp.getValue().atStartOfDay(ZoneId.systemDefault())); //convert LocalDate to Date
        Date pickedDate = Date.from(instant);
        return pickedDate;
    }

    public void displayDates(DatePicker dp, LocalDate day, boolean past) {
        // this function gets a datePicker and display only the dates before "day" if we chose past==true (or after if past==false)
        dp.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (past)
                    setDisable(empty || date.compareTo(day) > 0);
                else // display future
                    setDisable(empty || date.compareTo(day) < 0);
            }
        });
    }

}