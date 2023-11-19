package com.example.tris;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientGame {

    int x,y;//coordinate
    int nmossa = 0;

    public static String getCPlayer() {
        return cPlayer;
    }

    public void setCPlayer(String cPlayer) {
        this.cPlayer = cPlayer;
    }

    static String cPlayer;

    ControllerGame controllerGame = new ControllerGame();

    Socket socket;

    public void handshaking(int porta) throws IOException {
        socket = new Socket("localhost", porta);
    }

    public void messaggio(int porta) throws IOException, ClassNotFoundException {
        Game game = new Game();
        Scanner scanner = new Scanner(System.in);
        Game.val player = Game.val.O;
        Game.val[][] board;



        do {
            controllerGame.setCurrentPlayer("X");
            cPlayer = controllerGame.getCurrentPlayer();
            System.out.println("E' il turno di: "+cPlayer);
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            // Ricezione della matrice
            board = (Game.val[][]) inputStream.readObject();
            game.stampa(board);
            //controllerGame.updateGridPane(board); // dove 'board' è la matrice corrente
            nmossa++;
            System.out.println("mossa numero: "+nmossa);

            controllerGame.setCurrentPlayer("O");
            cPlayer = controllerGame.getCurrentPlayer();
            System.out.println("E' il turno di: "+cPlayer);
            //mossa

            System.out.print("Inserisci la cordinata X: ");
            x = scanner.nextInt();
            //x = controllerGame.getX();

            System.out.print("Inserisci la cordinata Y: ");
            y = scanner.nextInt();
            //y = controllerGame.getY();

            System.out.println("Hai inserito la coppia di cordinate: " + x + " e " + y);

            //scanner.close();

            if (game.isEmpty(board, x, y)) {
                board[x][y] = player;
            } else
                System.out.println("Casella occupata");
            //fine mossa

            game.stampa(board);
            //controllerGame.updateGridPane(board); // dove 'board' è la matrice corrente
            nmossa++;
            System.out.println("mossa numero: "+nmossa);

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            // Invio della matrice
            outputStream.writeObject(board);

            if (game.checkWin(board, x, y)) {
                System.out.println("Fine del gioco");
            }

            System.out.println("----Fine del Turno----");

        } while (!game.isDraw(board,nmossa));

        //close  dopo la fine del gioco
        socket.close();

    }

}
