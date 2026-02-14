package com.example.demo1.Controller;

import com.example.demo1.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {

    @FXML
    public static Stage RegState;

    private void BackToLogin(ActionEvent event) throws IOException {

        Node node = (Node) event.getSource();
        RegState = (Stage) node.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("Login.fxml"));
        RegState.setTitle("Login");
        RegState.setScene(new Scene(loader.load()));
        RegState.show();

    }

    @FXML
    private void Back(ActionEvent event) throws IOException {
        BackToLogin(event);
    }

}
