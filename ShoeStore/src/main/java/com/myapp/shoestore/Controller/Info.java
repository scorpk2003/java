package com.myapp.shoestore.Controller;

import com.myapp.shoestore.Model.User;
import com.myapp.shoestore.Service.UserService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class Info implements Initializable {
    public User user;
    public TextField CCCD ;
    public TextField Address;
    public TextField Phone;
    public TextField Pass;
    public TextField Name;
    public Label ID;
    public Button Change;
    public MenuItem female;
    public MenuItem male;
    public MenuButton Gender;
    public DatePicker Birth;
    public Label Email;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Change.setDisable(true);
        user = UserService.getUser();
        CCCD.setText(user.getCCCD());
        Address.setText(user.getAddress());
        Birth.setValue(user.getBirth().toLocalDate());
        Gender.setText(user.getGender());
        ID.setText(user.getUserID());
        Pass.setText(user.getPass());
        Name.setText(user.getName());
        Phone.setText(user.getPhone());
        Email.setText(user.getEmail());
        male.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Gender.setText(male.getText());
            }
        });
        female.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Gender.setText(female.getText());
            }
        });
        textchange(Name);
        textchange(Address);
        textchange(Pass);
        textchange(Phone);
        textchange(CCCD);
        Gender.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                Apply();
            }
        });
        Birth.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observableValue, LocalDate localDate, LocalDate t1) {
                Apply();
            }
        });
    }

    public void textchange(TextField tx){
        tx.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                Apply();
            }
        });
    }
    public void Apply(){
        Change.setDisable(false);
    }
    @FXML
    public void UpdateUser(){
        User user = new User(
                ID.getText(),
                Name.getText(),
                Email.getText(),
                Pass.getText(),
                Phone.getText(),
                Date.valueOf(Birth.getValue()),
                CCCD.getText(),
                Address.getText(),
                Gender.getText(),
                false
        );
        try {
            UserService.Update(user).get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Success");
        alert.setContentText("Update Success");
        alert.showAndWait();
        Change.setDisable(true);
    }
}
