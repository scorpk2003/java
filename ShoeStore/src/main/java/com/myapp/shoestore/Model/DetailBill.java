package com.myapp.shoestore.Model;

public class DetailBill {

    private String DetailID;

    private String UserID;

    private String ProductID;

    private String BillID;

    private int Quantity;

    public DetailBill(String detailID, String userID, String productID, String billID, int quantity) {
        DetailID = detailID;
        UserID = userID;
        ProductID = productID;
        BillID = billID;
        Quantity = quantity;
    }

    public DetailBill() {
    }

    public String getDetailID() {
        return DetailID;
    }

    public void setDetailID(String detailID) {
        DetailID = detailID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public String getBillID() {
        return BillID;
    }

    public void setBillID(String billID) {
        BillID = billID;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    @Override
    public String toString() {
        return "DetailBill{" +
                "DetailID='" + DetailID + '\'' +
                ", UserID='" + UserID + '\'' +
                ", ProductID='" + ProductID + '\'' +
                ", BillID='" + BillID + '\'' +
                ", Quantity=" + Quantity +
                '}';
    }
}
