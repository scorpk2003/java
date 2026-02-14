package com.myapp.shoestore.Controller;

import com.myapp.shoestore.Model.Product;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Addproduct implements Initializable {
    public TextField ID ;
    public TextField Size;
    public TextField Name;
    public TextField Quantity;
    public TextField Price;
    public Button Add;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Product p = new Product(
                        ID.getText(),
                        Name.getText(),
                        Integer.parseInt(Price.getText()),
                        Integer.parseInt(Quantity.getText()),

                );
            }
        });
    }
}
