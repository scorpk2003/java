package com.myapp.shoestore.Service;

import com.myapp.shoestore.Controller.ConnectDB;
import com.myapp.shoestore.Model.Bill;
import com.myapp.shoestore.Model.DetailBill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BillService {

    public static void Insert(Bill a) throws SQLException {
        Connection connection = ConnectDB.getConnection();
        String sql = "Insert into [Bill] values (?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,a.getBillID());
        pstm.setDate(2,a.getDateBill());
        pstm.setInt(3,a.getTotalAmout());
        pstm.setString(4,a.getUserID());
        pstm.executeUpdate();
        pstm.close();
        connection.close();
    }

    public static void InsertDetail(DetailBill a) throws SQLException {
        Connection connection = ConnectDB.getConnection();
        String sql = "Insert into [BillDetails] values (?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,a.getDetailID());
        pstm.setString(2,a.getUserID());
        pstm.setString(3,a.getProductID());
        pstm.setString(4,a.getBillID());
        pstm.setInt(5,a.getQuantity());
        pstm.executeUpdate();
        pstm.close();
        connection.close();
    }

    public static void Update(String id,int amout) throws SQLException {
        Connection connection = ConnectDB.getConnection();
        String sql = "update [Bill] set totalAmout = ? where productBillID = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setInt(1,amout);
        pstm.setString(2,id);
        pstm.executeUpdate();
        pstm.close();
        connection.close();
    }
}
