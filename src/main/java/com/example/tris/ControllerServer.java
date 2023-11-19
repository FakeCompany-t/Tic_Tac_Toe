package com.example.tris;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.*;

public class ControllerServer {

    @FXML
    private Button btnSubmit;

    @FXML
    private TextField txtfPort;

    @FXML
    private Button btnBackToMenu;

    private Parent root;
    private Scene scena;
    private Stage stage;
    int portNumber;


    @FXML
    void submitclicked(ActionEvent event) throws Exception {

        root = FXMLLoader.load(getClass().getResource("LoadingScreen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scena = new Scene(root);
        stage.setScene(scena);
        stage.show();

        portNumber = Integer.parseInt(txtfPort.getText());
        System.out.println(portNumber);
        ServerGame server = new ServerGame();
        ControllerGameLabel controllerGame = new ControllerGameLabel();

        server.handshaking(portNumber);//questo blocca il processo e quindi rende la schermata non funzionante(TODO)

        ControllerLoading loading = new ControllerLoading();
        loading.start(stage);
        stage.setTitle("Game");

        new Thread(()->{
            try {
                server.messaggi(portNumber);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }).start();

    }

    @FXML
    void btnBTMClicked(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("menu.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scena = new Scene(root);
        stage.setScene(scena);
        stage.show();

    }
}
