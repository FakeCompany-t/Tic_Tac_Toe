package com.example.tris;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ControllerGame {

    int x,y;

    String currentPlayer;

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setCoordinates(int x, int y) {
            this.x = x;
            this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private void setImageAndCoordinates(ImageView imageView) {
        String id = imageView.getId();
        System.out.println(id);
        String[] coordinates = id.split("");
        x = Integer.parseInt(coordinates[1]);
        y = Integer.parseInt(coordinates[2]);

        setCoordinates(x, y);
        //modifica immagine
    }



    @FXML
    public ImageView c00;

    @FXML
    private ImageView c01;

    @FXML
    private ImageView c02;

    @FXML
    private ImageView c10;

    @FXML
    private ImageView c11;

    @FXML
    private ImageView c12;

    @FXML
    private ImageView c20;

    @FXML
    private ImageView c21;

    @FXML
    private Button c22;

    @FXML
    void onImageClicked(MouseEvent event) {
        ImageView clickedImageView = (ImageView) event.getSource();
        setImageAndCoordinates(clickedImageView);
    }

    @FXML
    void c22Clicked(ActionEvent event) {
        x = 2;
        y = 2;
        setCoordinates(x,y);
    }


}
