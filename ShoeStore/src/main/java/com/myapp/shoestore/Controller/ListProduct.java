package com.myapp.shoestore.Controller;

import com.myapp.shoestore.HelloApplication;
import com.myapp.shoestore.Model.Product;
import com.myapp.shoestore.Service.ProductService;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ListProduct implements Initializable {
    public GridPane Grid;

    @FXML
    public ScrollPane scroll;

    public ListProduct() {
    }

    private void loadProduct(){
        List<Product> products = ProductService.getProducts();
        int col = 0;
        int row = 0;
        Grid.getRowConstraints().clear();
        Grid.getColumnConstraints().clear();
        for(Product product : products){

            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(HelloApplication.class.getResource("ProductUI.fxml"));
                Node root = loader.load();
                ProductController productController = loader.getController();
                productController.ProductName.setText(product.getProductName());
                productController.Priciest.setText(String.valueOf(product.getProductPrice()));
                productController.Sixtieth.setText(String.valueOf(product.getProductSize()));
                Grid.add(root,col,row);
                GridPane.setMargin(Grid, new Insets(10));
                col++;
                if(col == 3){

                    col = 0;

                    ++ row;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (int i = 0; i < 3; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100.0 / 3);
            Grid.getColumnConstraints().add(column);
        }
        loadProduct();

    }
}
