package com.myapp.shoestore.Controller;

import com.myapp.shoestore.HelloApplication;
import com.myapp.shoestore.Model.User;
import com.myapp.shoestore.Service.UserService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class RegisterController implements Initializable {

    @FXML
    public TextField Emailtxt;

    @FXML
    public TextField Passtxt;

    @FXML
    public TextField Phonetxt;

    @FXML
    public TextField CCCDtxt;

    @FXML
    public TextField IDtxt;

    @FXML
    public TextField Nametxt;

    @FXML
    public MenuButton Gendertxt;

    @FXML
    public TextField Addressed;

    @FXML
    public DatePicker Birthtxt;

    @FXML
    public CheckBox Admin;

    @FXML
    public Button RegBtn;

    @FXML
    public MenuItem Maletxt;

    @FXML
    public MenuItem Femaletxt;

    @FXML
    public Button BackToLogin;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private Stage stage;

    public void Regis() throws InterruptedException {

        if(!CheckEmpty() && CheckNum()) {
            UserService.getInstance();
            try {
                UserService.Check(Emailtxt.getText(),IDtxt.getText()).get();
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }

            if(UserService.getUser() != null ){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Register Fail");
                alert.setHeaderText(null);
                alert.setContentText("Account Exist");
                alert.showAndWait();
                ResetField();
            }else{
                User user = new User(
                        IDtxt.getText(),
                        Nametxt.getText(),
                        Emailtxt.getText(),
                        Passtxt.getText(),
                        Phonetxt.getText(),
                        Date.valueOf(Birthtxt.getValue()),
                        CCCDtxt.getText(),
                        Addressed.getText(),
                        Gendertxt.getText(),
                        Admin.isSelected()
                );

                UserService.Insert(user);

                Alert alert = getAlert();
                alert.showAndWait();
                UserService.ToString();

            }


        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Register invalid");
            alert.setHeaderText(null);
            alert.setContentText("Field Incorrect");
            alert.showAndWait();
            ResetField();

        }

    }

    private @NotNull Alert getAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Register Success");
        alert.setOnCloseRequest(event->{
            SceneManager.getInstance().switchToLoginScene();

        });
        return alert;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Maletxt.setOnAction(event -> {
            Gendertxt.setText(Maletxt.getText());
        });

        Femaletxt.setOnAction(event -> {
            Gendertxt.setText(Femaletxt.getText());
        });

        BackToLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
                Node node = (Node) event.getSource();
                stage = (Stage) node.getScene().getWindow();
                try {
                    stage.setScene(new Scene(loader.load()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    public Boolean CheckEmpty(){
        return Emailtxt.getText().isEmpty()  || Passtxt.getText().isEmpty() || Phonetxt.getText().isEmpty() || IDtxt.getText().isEmpty() ||
                Nametxt.getText().isEmpty() || Gendertxt.getText().equals("Gender") || Birthtxt.getEditor().getText() == null;
    }

    public Boolean CheckNum(){
        char[] PhoneCheck = Phonetxt.getText().toCharArray();
        char[] CCCDCheck = CCCDtxt.getText().toCharArray();

        for(char c : PhoneCheck){
            if(!Character.isDigit(c)){
                return false;
            }
        }

        for(char c : CCCDCheck){
            if(!Character.isDigit(c)){
                return false;
            }
        }
        return true;
    }

    public void ResetField(){
        Emailtxt.setText("");
        Passtxt.setText("");
        Phonetxt.setText("");
        Gendertxt.setText("Gender");
        IDtxt.setText("");
        CCCDtxt.setText("");
        Birthtxt.getEditor().setText("");
        Admin.setSelected(false);
        Addressed.setText("");
        Nametxt.setText("");
    }

}
