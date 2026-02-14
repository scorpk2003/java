package com.example.demo1.Controller;

import com.example.demo1.HelloApplication;
import com.example.demo1.Model.User;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.hibernate.HibernateError;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    public static Stage LogState;

    @FXML
    @NotNull
    public TextField Email;

    @FXML
    public Button LoginBtn;

    @FXML
    public TextField Password;

    @FXML
    public Button RegBtn;

    public LoginController() {
        Password = new TextField();
        Email = new TextField();
    }

    @FXML
    private void Register(MouseEvent event) throws IOException {

        // Into RegisterUI

        Node node = (Node) event.getSource();

        Stage stage = (Stage) node.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("Register.fxml"));
        Scene scene = new Scene(loader.load());

        LogState = stage;
        LogState.setScene(scene);
        LogState.show();

    }

    @FXML
    private void Submit(){
        try {
            String email = Email.getText().toString();
            String pass = Password.getText().toString();
            User user = ConnectDB.Login(email,pass);
            if (user != null) {
                System.out.println("connect database success");
            } else {
                System.out.print("fail");
            }
        } catch (HibernateError exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        RegBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    Register(mouseEvent);
                } catch (IOException e) {
                    System.out.println("Init Fail");
                    throw new RuntimeException(e);
                }
            }
        });

    }
}
