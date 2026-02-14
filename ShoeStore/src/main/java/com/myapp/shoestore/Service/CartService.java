package com.myapp.shoestore.Service;

import com.myapp.shoestore.Controller.ConnectDB;
import com.myapp.shoestore.Model.Cart;
import com.myapp.shoestore.Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public class CartService {

    private static CartService cartService;
    private List<Cart> cartList;

    private CartService(){
        if(cartList == null){
            try {
                Connection connection = ConnectDB.getConnection();
                String sql = "select * from [Cart] where userID = ?";
                PreparedStatement pstm = connection.prepareStatement(sql);
                pstm.setString(1,UserService.getUser().getUserID());
                ResultSet resultSet = pstm.executeQuery();
                while(resultSet.next()){
                    cartList.add(new Cart(
                       resultSet.getString(1),
                       resultSet.getString(2),
                       resultSet.getString(3)
                    ));
                }
                resultSet.close();
                pstm.close();
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static CartService getCartService(){
        if(cartService == null){
            cartService = new CartService();
        }
        return cartService;
    }

    public static void Add(String productID) throws SQLException {
        Connection connection = ConnectDB.getConnection();
        Random r = new Random(500);
        String sql = "insert into [Cart] values (?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,productID + UserService.getUser().getUserID() + r.toString());
        pstm.executeUpdate();

        pstm.close();
        connection.close();
    }

}
