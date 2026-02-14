package com.myapp.shoestore.Controller;

import com.myapp.shoestore.HelloApplication;
import com.myapp.shoestore.Model.Bill;
import com.myapp.shoestore.Model.DetailBill;
import com.myapp.shoestore.Model.InfoCart;
import com.myapp.shoestore.Model.Product;
import com.myapp.shoestore.Service.BillService;
import com.myapp.shoestore.Service.ProductService;
import com.myapp.shoestore.Service.UserService;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class UserMainUI implements Initializable {
    @FXML
    public BorderPane ManUI;
    @FXML
    public Label NameUser;
    @FXML
    public Button ProductBtn;
    @FXML
    public Button InfoBtn;
    @FXML
    public Button CartBtn;
    @FXML
    public Button BillBtn;
    @FXML
    public Button DetailBtn;
    @FXML
    public Button LogOutBtn;
    public VBox AddCart;
    public TableView<InfoCart> TableCart;
    public TableColumn<InfoCart,String> Name;
    public TableColumn<InfoCart,String> Price;
    public TableColumn<InfoCart,String> Size;
    public Button PayBtn;
    public Button InsertCart;
    public Button Cancel;

    @FXML
    public void setNav(){
        ManUI.setLeft(null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Name.setCellValueFactory(celldata->celldata.getValue().Nameprt());
        Price.setCellValueFactory(celldata->celldata.getValue().Priceprt());
        Size.setCellValueFactory(celldata->celldata.getValue().Sizeprt());

        LogOutBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SceneManager.getInstance().switchToLoginScene();
            }
        });
        InfoBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AddCart.setVisible(false);
                Region load;
                try {
                    load = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("UserInfo.fxml")));
                    setNodeToCenter(load);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        CartBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
        ProductBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AddCart.setVisible(true);
                System.out.println("Begin Product");
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(HelloApplication.class.getResource("ProductListUI.fxml"));
                try {
                    ScrollPane box = loader.load();
                    ManUI.setCenter(box);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("End Product");
            }
        });
        PayBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Bill a = new Bill();
                Random e = new Random();
                a.setBillID(e.nextInt() + UserService.getUser().getName() + UserService.getUser().getPass());
                a.setDateBill(Date.valueOf(LocalDate.now()));
                a.setUserID(UserService.getUser().getUserID());
                a.setTotalAmout(0);
                try {
                    BillService.Insert(a);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                ObservableList<InfoCart> b = TableCart.getItems();
                int d=0;

                for(InfoCart c : b){
                    String p="";
                    try {
                        Connection connection = ConnectDB.getConnection();
                        PreparedStatement pstm = connection.prepareStatement("select productID from [Product] where productName = ? and ProductPrice = ? and ProductSize = ?");
                        pstm.setString(1,c.getName());
                        pstm.setInt(2, Integer.parseInt(c.getPrice()));
                        pstm.setInt(3,Integer.parseInt(c.getSize()));
                        ResultSet resultSet = pstm.executeQuery();
                        if(resultSet.next()){
                            p = resultSet.getString(1);
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    DetailBill detailBill = new DetailBill();
                    detailBill.setBillID(a.getBillID());
                    detailBill.setDetailID(e.nextInt(200) + UserService.getUser().getUserID() + p);
                    detailBill.setQuantity(1);
                    detailBill.setProductID(p);
                    detailBill.setUserID(UserService.getUser().getUserID());
                    try {
                        BillService.InsertDetail(detailBill);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    d += Integer.parseInt(c.Price);
                }
                try {
                    BillService.Update(a.getBillID(),d);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                SharedData.getInstance().getInfo().clear();
            }
        });
        InsertCart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ObservableList<InfoCart> i = TableCart.getItems();
                for(InfoCart j : i){
                    String p="";
                    try {
                        Connection connection = ConnectDB.getConnection();
                        PreparedStatement pstm = connection.prepareStatement("select productID from [Product] where productName = ? and ProductPrice = ? and ProductSize = ?");
                        pstm.setString(1,j.getName());
                        pstm.setInt(2, Integer.parseInt(j.getPrice()));
                        pstm.setInt(3,Integer.parseInt(j.getSize()));
                        ResultSet resultSet = pstm.executeQuery();
                        if(resultSet.next()){
                            p = resultSet.getString(1);
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    Random a = new Random();
                    try {
                        String sql = "insert into [Cart] values (?,?,?)";
                        Connection connection = ConnectDB.getConnection();
                        PreparedStatement pstm = connection.prepareStatement(sql);
                        pstm.setString(1,a.nextInt(25) + UserService.getUser().getUserID() + p);
                        pstm.setString(2,UserService.getUser().getUserID());
                        pstm.setString(3,p);
                        pstm.executeUpdate();
                        pstm.close();
                        connection.close();

                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                SharedData.getInstance().getInfo().clear();

            }
        });
        Cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SharedData.getInstance().getInfo().clear();
            }
        });
        TableCart.setItems(SharedData.getInstance().getInfo());
        NameUser.setText(UserService.getUser().getName());
        AddCart.setVisible(false);

    }
    private void setNodeToCenter(Region node) {
        // Liên kết kích thước của node với kích thước của khu vực Center của BorderPane
        node.prefWidthProperty().bind(ManUI.widthProperty());
        node.prefHeightProperty().bind(ManUI.heightProperty());

        // Đặt node vào khu vực Center của BorderPane
        ManUI.setCenter(node);
    }
}
