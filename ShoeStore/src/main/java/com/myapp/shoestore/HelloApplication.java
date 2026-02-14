package com.myapp.shoestore;

import com.myapp.shoestore.Controller.*;
import com.myapp.shoestore.Model.Product;
import com.myapp.shoestore.Service.ProductService;
import com.myapp.shoestore.Service.UserService;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, InterruptedException {


//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load());
//
//        stage.setResizable(false);
//        stage.setTitle("Hello!");
//        stage.setScene(scene);
//        stage.show();

        SceneManager.getInstance().switchToLoginScene();
        ProductService.getInstance();
        try {
            ProductService.GetAll().get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        UserService.Shutdown();
        ProductService.Stop();
    }

}