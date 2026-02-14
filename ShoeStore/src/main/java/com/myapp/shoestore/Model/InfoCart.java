package com.myapp.shoestore.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class InfoCart {
    public String Name;
    public String Price;
    public String Size;

    public InfoCart(String name, String price, String size) {
        Name = name;
        Price = price;
        Size = size;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }
    public StringProperty  Nameprt(){
        return new SimpleStringProperty(Name);
    }
    public StringProperty Priceprt(){
        return new SimpleStringProperty(Price);
    }
    public StringProperty Sizeprt(){
        return new SimpleStringProperty(Size);
    }
}
