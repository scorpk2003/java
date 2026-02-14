package com.myapp.shoestore.Controller;

import com.myapp.shoestore.Model.InfoCart;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SharedData {
    private static SharedData instance = new SharedData();
    private String productname;
    private String price;
    private String Size;
    private ObservableList<InfoCart> info = FXCollections.observableArrayList();

    public SharedData() {
    }
    public static SharedData getInstance(){
        return instance;
    }
    public void setData(String Name, String Price, String Size){
        this.price = Price;
        this.productname = Name;
        this.Size = Size;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public ObservableList<InfoCart> getInfo() {
        return info;
    }

    public void setInfo(ObservableList<InfoCart> Info) {
        this.info = Info;
    }
}
