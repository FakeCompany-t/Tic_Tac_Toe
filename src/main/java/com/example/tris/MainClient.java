package com.example.tris;

import java.io.IOException;

public class MainClient {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        ClientGame gioco = new ClientGame();

        gioco.handshaking();
        gioco.messaggio();

    }
}
