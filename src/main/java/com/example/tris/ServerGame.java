package com.example.tris;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import static com.example.tris.Game.dim;

public class ServerGame {

    int x,y;//coordinate
    int nmossa = 0;
    int porta = 49152;
    // Inizializza una socket TCP che accetta connessioni da parte dei client
    ServerSocket serverSocket = new ServerSocket(porta);
    Socket socket;

    public ServerGame() throws IOException {
    }

    void handshaking() throws IOException {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                serverSocket.close();
                System.out.println("Server socket chiuso correttamente.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));

        socket = serverSocket.accept();
        System.out.println("Connessione accettata da: " + socket);
    }

    void messaggi() throws IOException, ClassNotFoundException {

        handshaking();

        Game.val nullo = Game.val.V;
        Game game = new Game();
        Game.val host = Game.val.X;
        Scanner scanner = new Scanner(System.in);
        Game.val[][] board = new Game.val[Game.dim][Game.dim];

        game.inizialize_board(board);

        do {
            game.stampa(board);
            System.out.println("mossa numero: "+nmossa);
            //mossa
            System.out.print("Inserisci la cordinata X: ");
            int x = scanner.nextInt();

            System.out.print("Inserisci la cordinata Y: ");
            int y = scanner.nextInt();

            System.out.println("Hai inserito la coppia di cordinate: " + x + " e " + y);

            //scanner.close();

            if (game.isEmpty(board,x,y)){
                board[x][y] = host;
            }
            else
                System.out.println("Casella occupata");
            //fine mossa

            game.stampa(board);
            nmossa++;
            System.out.println("mossa numero: "+nmossa);


            if (game.checkWin(board,x,y)){
                System.out.println("Fine del gioco");

            }

            System.out.println("----Fine del Turno----");
            //Invio della matrice
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            // Invio della matrice
            outputStream.writeObject(board);

            //Ricezione della matrice
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            // Ricezione della matrice
            Game.val[][] pisello = (Game.val[][]) inputStream.readObject();

            nmossa++;



            for (int i = 0; i < Game.dim; i++) {
                for (int j = 0; j < Game.dim; j++) {
                    board[i][j] = pisello[i][j];
                }
            }
        }while (!game.isDraw(board,nmossa));

        game.stampa(board);

        //TODO close  dopo la fine del gioco
        socket.close();

    }

}
