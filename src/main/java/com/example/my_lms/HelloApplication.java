package com.example.my_lms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;


public class HelloApplication extends Application {
    public static int studID;
    public static final String dbUrl = "jdbc:mysql://localhost:3306/library?";
    public static final String userName = "root";
    public static final String password = "JavaSQL123!";


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Library Management System");
        stage.setScene(scene);
        stage.show();
    }

    //used to switch between scenes
    public static void SceneSwitch( String fxmlName,String title, Stage stage) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxmlName));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}