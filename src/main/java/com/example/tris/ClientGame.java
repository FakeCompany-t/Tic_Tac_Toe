package com.example.tris;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import static com.example.tris.Game.dim;

public class ClientGame {

    int x,y;//coordinate
    int nmossa;
    int porta = 49152;

    Socket socket;

    public void handshaking() throws IOException {
        socket = new Socket("localhost", porta);
    }

    public void messaggio() throws IOException, ClassNotFoundException {
        Game game = new Game();
        Scanner scanner = new Scanner(System.in);
        Game.val client = Game.val.O;
        Game.val[][] board = new Game.val[Game.dim][Game.dim];

        while (!game.isDraw(board)){
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            // Ricezione della matrice
            board = (Game.val[][]) inputStream.readObject();

            game.stampa(board);

            //mossa
            System.out.print("Inserisci la cordinata X: ");
            int x = scanner.nextInt();

            System.out.print("Inserisci la cordinata Y: ");
            int y = scanner.nextInt();

            System.out.println("Hai inserito la coppia di cordinate: " + x + " e " + y);

            //scanner.close();

            if (game.isEmpty(board,x,y)){
                board[x][y] = client;
            }
            else
                System.out.println("Casella occupata");
            //fine mossa

            game.stampa(board);

            if (game.checkWin(board,x,y)){
                System.out.println("Fine del gioco");
            }

            System.out.println("----Fine del Turno----");

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            // Invio della matrice
            outputStream.writeObject(board);

        }
        //close  dopo la fine del gioco
        socket.close();

    }
}