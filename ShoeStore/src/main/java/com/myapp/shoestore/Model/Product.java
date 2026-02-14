package com.myapp.shoestore.Model;

public class Product {

    private String ProductID;

    private String ProductName;

    private int ProductPrice;

    private int ProductSize;

    private int Quantity;

    public Product(String productID, String productName, int productPrice, int productSize, int quantity) {
        ProductID = productID;
        ProductName = productName;
        ProductPrice = productPrice;
        ProductSize = productSize;
        Quantity = quantity;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public int getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(int productPrice) {
        ProductPrice = productPrice;
    }

    public int getProductSize() {
        return ProductSize;
    }

    public void setProductSize(int productSize) {
        ProductSize = productSize;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public Product() {
    }

    @Override
    public String toString() {
        return "Product{" +
                "ProductID='" + ProductID + '\'' +
                ", ProductName='" + ProductName + '\'' +
                ", ProductPrice=" + ProductPrice +
                ", ProductSize=" + ProductSize +
                ", Quantity=" + Quantity +
                '}';
    }
}
