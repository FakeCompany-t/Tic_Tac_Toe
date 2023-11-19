package com.example.tris;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ControllerLoading extends Application {

    private Scene scena;
    private Stage stage;


    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(ControllerClient.class.getResource("Game.fxml"));
        Parent root = fxmlLoader.load();
        scena = new Scene(root);
        stage.setScene(scena);
        stage.show();
    }


}
