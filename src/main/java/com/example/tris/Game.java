package com.example.tris;

import javafx.scene.image.Image;

import java.util.Scanner;




public class Game {
    static final int dim = 3;

//valori che possono essere presenti all'interno del campo
    enum val{V, //vuoto
        X,
        O;
    }

    //val[][] board = new val[dim][dim]; //matrice del campo di gioco

    int x,y;//coordinate

    int nmossa;

    val winner;



    private boolean checkS(val[][] board) {
        if (board[0][0].equals(board[1][1])&&
        board[0][0].equals(board[2][2])&& !isEmpty(board,0,0)){
            val winner = board[0][0];
            System.out.println(winner);
            return true;
        }
        return false;
    }

    private boolean checkD(val[][] board) {
        if (board[0][2].equals(board[1][1])&&
        board[0][2].equals(board[2][0])&& !isEmpty(board,0,2)){
            val winner = board[0][2];
            System.out.println(winner);
            return true;
        }
        return false;
    }

    private boolean checkCol(val[][] board, int y) {
            if (board[0][y].equals(board[1][y])&&
                    board[0][y].equals(board[2][y])) {
                val winner = board[0][y];
                System.out.println(winner);
                return true;
            }
        return false;
    }

    private boolean checkRow(val[][] board, int x) {
            if (board[x][0].equals(board[x][1])&&
            board[x][0].equals(board[x][2])){
                val winner = board[x][0];
                System.out.println(winner);
                return true;
            }
        return false;
    }

    boolean checkWin(val[][] board, int x, int y){
        if (( checkRow(board,x) || checkCol(board,y) || checkD(board) || checkS(board))){
            return true;
        }
        else {
            return false;
        }
    }

    boolean isDraw(val[][] board, int nmossa){
        if (nmossa == 8 && !checkWin(board,1,1)){
            System.out.println("Pareggio");
            return true;
        }
        else {
            return false;
        }

    }

    boolean isEmpty(val[][] board, int x, int y){
        if(board[x][y]==val.V)
            return true;
        else
            return false;
    }

    void stampa(val[][] board){

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }

    }

    void inizialize_board(val[][] matrice){
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                matrice[i][j] = val.V;
            }
        }
    }



}
