package com.myapp.shoestore.Service;

import com.myapp.shoestore.Controller.ConnectDB;
import com.myapp.shoestore.Controller.ListProduct;
import com.myapp.shoestore.Model.Product;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ProductService{
    public ProductService() {
        products = new ArrayList<>();
        executorService = Executors.newSingleThreadExecutor();
    }

    public static synchronized ProductService getInstance(){
        if(productService == null){
            productService = new ProductService();
        }
        return productService;
    }

    private static ProductService productService;
    private static Product product;



    private static ExecutorService executorService;
    private static List<Product> products;
    private static ListProduct Grid;

    public static Future<?> GetAll() {
        return executorService.submit(()->{
            if(!products.isEmpty()){
            products.clear();
            }
            try {
                Connection connection = ConnectDB.getConnection();
                String sql = "select * from [Product]";
                PreparedStatement pstm = connection.prepareStatement(sql);
                ResultSet resultSet = pstm.executeQuery();
                while (resultSet.next()) {
                    Product product1 = new Product(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getInt(3),
                            resultSet.getInt(4),
                            resultSet.getInt(5)
                    );

                    products.add(product1);
                }
                pstm.close();
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        });
    }

//    public static Future<?> Run(){
//        return executorService.submit(()->{
//           for(Product product1 : products){
//               try {
//                   Grid.Addcard(product1);
//                   System.out.println("Success");
//               } catch (IOException e) {
//                   System.out.println("Add Grid Fail");;
//               }
//           }
//        });
//    }

    public static Future<?> Search(String id, String name, int price, int size, int quantity){
        return executorService.submit(()->{
            if(!products.isEmpty()){
                products.clear();
            }
            StringBuilder query = new StringBuilder("select * from [Product] where 1=1 ");
            if(!id.isEmpty()){
                query.append(" and productID = ? ");
            }
            if(!name.isEmpty()){
                query.append(" and productName = ? ");
            }
            try {
                Connection connection = ConnectDB.getConnection();
                PreparedStatement pstm = connection.prepareStatement(query.toString());
                int i=1;
                if(!id.isEmpty()){
                    pstm.setString(i++,id);
                }
                if(!name.isEmpty()){
                    pstm.setString(i,name);
                }
                ResultSet resultSet = pstm.executeQuery();
                while (resultSet.next()){
                    Product product1 = new Product(
                            id,
                            name,
                            price,
                            size,
                            quantity
                    );
                    product = product1;
                }
                resultSet.close();
                pstm.close();
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static Future<?> Insert(Product product2){
        return executorService.submit(()->{
            try {
                Connection connection = ConnectDB.getConnection();
                String sql = "select * from [Product] where productName = ? and productPrice = ? and productSize = ? and quantity = ?";
                PreparedStatement pstm = connection.prepareStatement(sql);
                pstm.setInt(4,product2.getQuantity());
                pstm.setString(1,product2.getProductName());
                pstm.setInt(2,product2.getProductPrice());
                pstm.setInt(3,product2.getProductSize());
                ResultSet resultSet = pstm.executeQuery();
                if(!resultSet.next()){
                    String insert = "insert into [Product] values (?,?,?,?,?)";
                    pstm = connection.prepareStatement(insert);
                    pstm.setString(1,product2.getProductID());
                    pstm.setInt(2,product2.getProductPrice());
                    pstm.setInt(3,product2.getProductSize());
                    pstm.setInt(4,product2.getQuantity());
                    pstm.executeUpdate();
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setTitle("Error");
                    alert.setContentText("Product is Exist");
                    alert.showAndWait();
                }
                resultSet.close();
                pstm.close();
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static Future<?> Update(Product product2){
        return executorService.submit(()->{
            try {
                Connection connection = ConnectDB.getConnection();
                String sql = "update [Product] set productName = ?, productPrice = ?, productSize = ?, quantity = ?, productID =?";
                PreparedStatement pstm = connection.prepareStatement(sql);
                pstm.setInt(4,product2.getQuantity());
                pstm.setString(1,product2.getProductName());
                pstm.setInt(2,product2.getProductPrice());
                pstm.setInt(3,product2.getProductSize());
                pstm.setString(5,product2.getProductID());
                pstm.executeUpdate();
                pstm.close();
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static Future<?> Delete(String ID){
        return executorService.submit(()->{
            try {
                Connection connection = ConnectDB.getConnection();
                String sql = "delete from [Product] where productID = ?";
                PreparedStatement pstm = connection.prepareStatement(sql);
                pstm.setString(1,ID);
                pstm.executeUpdate();
                pstm.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        });
    }

    public static Future<?> Stop(){
        return executorService.submit(()->{
            executorService.shutdown();
        });
    }


    // Getter And Setter


    public static Product getProduct() {
        return product;
    }

    public static void setProduct(Product product) {
        ProductService.product = product;
    }

    public static ExecutorService getExecutorService() {
        return executorService;
    }

    public static void setExecutorService(ExecutorService executorService) {
        ProductService.executorService = executorService;
    }

    public static List<Product> getProducts() {
        return products;
    }

    public static void setProducts(List<Product> products) {
        ProductService.products = products;
    }
}
