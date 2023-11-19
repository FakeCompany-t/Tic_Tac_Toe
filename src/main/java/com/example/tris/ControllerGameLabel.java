package com.example.tris;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import static com.example.tris.Game.dim;

public class ControllerGameLabel {

    String currentPlayer;

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    @FXML
    private Label c00;

    @FXML
    private Label c01;

    @FXML
    private Label c02;

    @FXML
    private Label c10;

    @FXML
    private Label c11;

    @FXML
    private Label c12;

    @FXML
    private Label c20;

    @FXML
    private Label c21;

    @FXML
    private Label c22;

    @FXML
    GridPane gridpane;

    // Metodo per aggiornare la GUI con la matrice corrente
    public void updateGridPane(Game.val[][] matrix) {
        for (int row = 0; row < dim; row++) {
            for (int col = 0; col < dim; col++) {
                Label label = getLabel(row, col);
                label.setText(String.valueOf(matrix[row][col]));
                // Aggiungi eventuali stili o gestori degli eventi ai label
            }
        }
    }

    private Label getLabel(int row, int col) {
        switch (row) {
            case 0:
                switch (col) {
                    case 0:
                        return c00;
                    case 1:
                        return c01;
                    case 2:
                        return c02;
                }
                break;
            case 1:
                switch (col) {
                    case 0:
                        return c10;
                    case 1:
                        return c11;
                    case 2:
                        return c12;
                }
                break;
            case 2:
                switch (col) {
                    case 0:
                        return c20;
                    case 1:
                        return c21;
                    case 2:
                        return c22;
                }
                break;
        }
        return null;
    }

}
