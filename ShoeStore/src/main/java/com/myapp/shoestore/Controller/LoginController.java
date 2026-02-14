package com.myapp.shoestore.Controller;

import com.myapp.shoestore.HelloApplication;
import com.myapp.shoestore.Service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;


public class LoginController implements Initializable {

    public TextField mailtxt;

    @FXML
    private Stage stage;
    @FXML
    public Button LoginBtn;
    @FXML
    public TextField passtxt;
    @FXML
    public Button RegisterBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        RegisterBtn.setOnAction(event -> {
            try {
                RegisUI(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }



    @FXML
    private void Submit() {

        if(!CheckEmpty()){
            UserService.getInstance();
            try {
                UserService.start(mailtxt.getText(),passtxt.getText()).get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
            if(UserService.getUser()!=null){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Login Success");
                alert.setHeaderText(null);
                alert.showAndWait();
                if(UserService.getUser().isAdmin()){
                    SceneManager.getInstance().ChangeScene("AdminUI.fxml");
                }else {
                    SceneManager.getInstance().ChangeScene("UserUI.fxml");
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fail");
                alert.setContentText("Accout not Found");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Fail");
            alert.setContentText("Field Empty");
            alert.setHeaderText(null);
            alert.showAndWait();
        }

    }

    public boolean CheckEmpty(){
        return passtxt.getText().isEmpty() || mailtxt.getText().isEmpty();
    }

    private void RegisUI(ActionEvent event) throws IOException {

        Node node = (Node) event.getSource();

        stage =(Stage) node.getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Register.fxml"));
        stage.setScene(new Scene(fxmlLoader.load()));

    }
}
