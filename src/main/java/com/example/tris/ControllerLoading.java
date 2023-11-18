package com.example.tris;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ControllerLoading extends Application {

    private Scene scena;


    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(ControllerClient.class.getResource("Game.fxml"));
        scena = new Scene(fxmlLoader.load());
        stage.setScene(scena);
        stage.show();
    }

}
