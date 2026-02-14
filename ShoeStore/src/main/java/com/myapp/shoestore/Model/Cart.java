package com.myapp.shoestore.Model;

public class Cart {
    private String CartID;
    private String UserID;
    private String ProductID;

    public Cart(String cartID, String userID, String productID) {
        CartID = cartID;
        UserID = userID;
        ProductID = productID;
    }

    public String getCartID() {
        return CartID;
    }

    public void setCartID(String cartID) {
        CartID = cartID;
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
}
