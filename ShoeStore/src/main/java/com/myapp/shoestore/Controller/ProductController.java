package com.myapp.shoestore.Controller;

import com.myapp.shoestore.Model.Cart;
import com.myapp.shoestore.Model.InfoCart;
import com.myapp.shoestore.Model.Product;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ProductController implements Initializable {
    public Label ProductName;
    public Label Priciest;
    public Label Sixtieth;
    public Button Cart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Cart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                InfoCart infoCart = new InfoCart(ProductName.getText(),Priciest.getText(),Sixtieth.getText());
                SharedData.getInstance().getInfo().add(infoCart);
            }
        });

    }
}
