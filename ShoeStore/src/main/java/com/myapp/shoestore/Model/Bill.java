package com.myapp.shoestore.Model;

import java.sql.Date;

public class Bill {

    private String BillID;

    private Date DateBill;

    private int TotalAmout;

    private String UserID;

    public Bill(String billID, Date dateBill, int totalAmout, String userID) {
        BillID = billID;
        DateBill = dateBill;
        TotalAmout = totalAmout;
        UserID = userID;
    }

    public Bill() {
    }

    public String getBillID() {
        return BillID;
    }

    public void setBillID(String billID) {
        BillID = billID;
    }

    public Date getDateBill() {
        return DateBill;
    }

    public void setDateBill(Date dateBill) {
        DateBill = dateBill;
    }

    public int getTotalAmout() {
        return TotalAmout;
    }

    public void setTotalAmout(int totalAmout) {
        TotalAmout = totalAmout;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "BillID='" + BillID + '\'' +
                ", DateBill=" + DateBill +
                ", TotalAmout=" + TotalAmout +
                ", UserID='" + UserID + '\'' +
                '}';
    }
}
