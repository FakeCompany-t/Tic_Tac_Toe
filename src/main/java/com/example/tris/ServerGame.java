package com.example.tris;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerGame {

    int nmossa = 0;
    // Inizializza una socket TCP che accetta connessioni da parte dei client
    ServerSocket serverSocket;
    Socket socket;
    static String cPlayer;

    ControllerGame controllerGame = new ControllerGame();


    void handshaking(int porta) throws IOException {
        serverSocket = new ServerSocket(porta);
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



    void messaggi(int porta) throws IOException, ClassNotFoundException {
        Game.val nullo = Game.val.V;
        Game game = new Game();
        Game.val player = Game.val.X;
        Scanner scanner = new Scanner(System.in);
        Game.val[][] board = new Game.val[Game.dim][Game.dim];

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                // Chiude il server socket
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));


        game.inizialize_board(board);

        do {
            game.stampa(board);
            //controllerGame.updateGridPane(board);
            System.out.println("mossa numero: "+nmossa);
            //mossa
            controllerGame.setCurrentPlayer("X");
            cPlayer = controllerGame.getCurrentPlayer();
            System.out.println("E' il turno di: "+cPlayer);

            System.out.print("Inserisci la cordinata X: ");
            int x = scanner.nextInt();
            //x = controllerGame.getX();

            System.out.print("Inserisci la cordinata Y: ");
            int y = scanner.nextInt();
            //y = controllerGame.getY();

            System.out.println("Hai inserito la coppia di cordinate: " + x + " e " + y);

            if (game.isEmpty(board,x,y)){
                board[x][y] = player;
            }
            else
                System.out.println("Casella occupata");
            //fine mossa

            game.stampa(board);
            //controllerGame.updateGridPane(board); // dove 'board' è la matrice corrente
            nmossa++;
            System.out.println("mossa numero: "+nmossa);


            if (game.checkWin(board,x,y)){
                System.out.println("Fine del gioco");
                socket.close();
                System.exit(0);

            }

            System.out.println("----Fine del Turno----");
            controllerGame.setCurrentPlayer("O");
            cPlayer = controllerGame.getCurrentPlayer();
            System.out.println("E' il turno di: "+cPlayer);
            //Invio della matrice
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            // Invio della matrice
            outputStream.writeObject(board);

            //Ricezione della matrice
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            // Ricezione della matrice
            Game.val[][] tmp = (Game.val[][]) inputStream.readObject();

            nmossa++;



            for (int i = 0; i < Game.dim; i++) {
                for (int j = 0; j < Game.dim; j++) {
                    board[i][j] = tmp[i][j];
                }
            }
        }while (!game.isDraw(board,nmossa));

        game.stampa(board);

        //TODO close  dopo la fine del gioco
        socket.close();

    }



}
