package com.myapp.shoestore.Controller;

import com.myapp.shoestore.HelloApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SceneManager {
    private static SceneManager instance;
    private Stage stage;

    private SceneManager() {
        // Khởi tạo Stage
        stage = new Stage();
    }

    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    public void switchToLoginScene() {
        try {
            // Load LoginScene từ file FXML
            Parent root = FXMLLoader.load(HelloApplication.class.getResource("hello-view.fxml"));
            Scene scene = new Scene(root);

            // Đặt scene và hiển thị nó trên Stage
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ChangeScene(String scene){
        Parent root = null;
        try {
            root = FXMLLoader.load(HelloApplication.class.getResource(scene));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene1 = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene1);
        stage.show();
    }
}
