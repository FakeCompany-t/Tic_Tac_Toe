package com.example.tris;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.*;


public class ControllerClient {
    @FXML
    private Button btnBackToMenu;

    @FXML
    private Button btnSubmit;

    @FXML
    private TextField tfCode;

    private Parent root;
    private Scene scena;
    private Stage stage;
    int portNumber;

    @FXML
    void btnBTMClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ControllerClient.class.getResource("menu.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scena = new Scene(fxmlLoader.load());
        stage.setScene(scena);
        stage.show();

    }

    @FXML
    void btnSubmitClicked(ActionEvent event) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(ControllerClient.class.getResource("LoadingScreen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scena = new Scene(fxmlLoader.load());
        stage.setScene(scena);
        stage.show();

        portNumber = Integer.parseInt(tfCode.getText());
        System.out.println(portNumber);
        ClientGame client = new ClientGame();

        client.handshaking(portNumber);

        ControllerLoading loading = new ControllerLoading();
        loading.start(stage);
        stage.setTitle("Client");

        new Thread(()->{
            try {
                client.messaggio(portNumber);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }).start();

    }
}

