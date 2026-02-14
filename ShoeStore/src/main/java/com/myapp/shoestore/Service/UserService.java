package com.myapp.shoestore.Service;

import com.myapp.shoestore.Controller.ConnectDB;
import com.myapp.shoestore.Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;


public class UserService {

    public UserService() {
        executorService = Executors.newSingleThreadExecutor();
    }

    public static User getUser() {

        System.out.println("Get User Success");
        return user;
    }

    public void setUser(User user) {
        UserService.user = user;
    }
    private static User user;
    private static ExecutorService executorService;

    private static UserService instance;

    public static synchronized UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public static Future<?> Check(String name, String ID){
        return executorService.submit(()->{
            try {
                Connection conn = ConnectDB.getConnection();
                PreparedStatement pstm = conn.prepareStatement("select * from [User] where userID = ? or email = ? ");
                pstm.setString(1,ID);
                pstm.setString(2,name);
                ResultSet resultSet = pstm.executeQuery();

                if(resultSet.next()){
                    user = new User(resultSet.getString("UserID"),
                            resultSet.getString("Name"),
                            resultSet.getString("Email"),
                            resultSet.getString("Password"),
                            resultSet.getString("Phone"),
                            resultSet.getDate("Birth"),
                            resultSet.getString("CCCD"),
                            resultSet.getString("Address"),
                            resultSet.getString("Gender"),
                            resultSet.getBoolean("Admin"));
                    System.out.println("Check Success");
                }else{
                    System.out.println("Check Fail");
                }

                resultSet.close();
                conn.close();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static Future<?> start(String mail, String pass){
        return executorService.submit(()->{
            try {
                Connection conn = ConnectDB.getConnection();
                PreparedStatement pstm = conn.prepareStatement("select * from [User] where email = ? and password = ?");
                pstm.setString(1,mail);
                pstm.setString(2,pass);
                ResultSet resultSet = pstm.executeQuery();

                if(resultSet.next()){
                    user = new User(resultSet.getString("UserID"),
                            resultSet.getString("Name"),
                            resultSet.getString("Email"),
                            resultSet.getString("Password"),
                            resultSet.getString("Phone"),
                            resultSet.getDate("Birth"),
                            resultSet.getString("CCCD"),
                            resultSet.getString("Address"),
                            resultSet.getString("Gender"),
                            resultSet.getBoolean("Admin"));
                }else{
                    System.out.println("Search Fail");
                }

                resultSet.close();
                conn.close();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static Future<?> Insert(User user1){
        return executorService.submit(()->{
            try {
                Connection connection = ConnectDB.getConnection();
                String sql = "insert into [User] values (?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement pstm = connection.prepareStatement(sql);
                pstm.setString(1,user1.getUserID());
                pstm.setString(2,user1.getName());
                pstm.setString(3,user1.getEmail());
                pstm.setString(4,user1.getPass());
                pstm.setString(5,user1.getPhone());
                pstm.setDate(6,user1.getBirth());
                pstm.setString(7,user1.getCCCD());
                pstm.setString(8,user1.getAddress());
                pstm.setString(9,user1.getGender());
                pstm.setBoolean(10,user1.isAdmin());
                pstm.executeUpdate();
                System.out.println("Insert Success");
                connection.close();
                pstm.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static Future<?> Delete(User user2){
        return executorService.submit(()->{
            try {
                String sql = "delete from [User] where UserID = ?";
                Connection connection = ConnectDB.getConnection();
                PreparedStatement pstm = connection.prepareStatement(sql);
                pstm.setString(1,user2.getUserID());
                pstm.executeUpdate();
                if(user!=null && user == user2){
                    user = null;
                    System.out.println("Delete Success");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static Future<?> Update(User user3){
        return executorService.submit(()->{
            String sql = "update [User] set name = ?," +
                    "password = ? , " +
                    "phone = ? , " +
                    "birth = ? , " +
                    "cccd = ? , " +
                    "address = ? , " +
                    "gender = ? " +
                    "where userID = ? ";
            try {
                Connection connection = ConnectDB.getConnection();
                PreparedStatement pstm = connection.prepareStatement(sql);
                pstm.setString(1,user3.getName());
                pstm.setString(2,user3.getPass());
                pstm.setString(3,user3.getPhone());
                pstm.setDate(4,user3.getBirth());
                pstm.setString(5,user3.getCCCD());
                pstm.setString(6,user3.getAddress());
                pstm.setString(7,user3.getGender());
                pstm.setString(8,user3.getUserID());
                pstm.executeUpdate();
                pstm.close();
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static void ToString(){

        if(user!=null){
            System.out.printf(user.toString());
        }else{
            System.out.println("User is NULL");
        }

    }

    public static void Shutdown(){
        if(executorService != null){
            executorService.shutdown();
        }
    }

}
