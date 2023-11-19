# TRIS GAME
## Amore - Anzelotti - Bertini
## Work flow

Per sviluppare questo progetto, abbiamo inizialmente optato per la creazione di un gioco basato sugli scacchi. Tuttavia, nel corso dell'implementazione, abbiamo constatato di non possedere le competenze necessarie per gestire un gioco così complesso.

Nelle prime tre settimane, abbiamo tentato di utilizzare Javafx e Scene Builder per sviluppare un menu in grado di gestire la connessione tra client e server. Successivamente, abbiamo riscontrato che i tempi di consegna erano molto stringenti. Di conseguenza, abbiamo preso la decisione di passare all'implementazione del gioco del Tris.


## Struttura del codice

Abbiamo creato numerose classi:

- ### Menu.java
    La classe menu e' la classe conntenente il main ed e' la classe che lancia la prima finestra del menu e lo fa attraverso queste semplici righe che servono per caricare il file "menu.fxml" e impostare il titolo, il logo e lo stile della finestra
        
        @Override
        public void start(Stage stage) throws Exception {
            FXMLLoader fxmlLoader = new FXMLLoader(Menu.class.getResource("menu.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Tic Tac Toe");
            stage.setResizable(false);
            Image logo = new Image(String.valueOf(Menu.class.getResource("logo.png")));
            scene.getStylesheets().addAll(String.valueOf(this.getClass().getResource("style.css")));
            stage.getIcons().add(logo);
            stage.setScene(scene);
            stage.show();    
        }
---
- ### ControllerMenu.java
    La classe controllermenu e' una classe che serve per gestire i **Node** presenti nel file .fxml e quindi i bottoni.
    In questo caso i bottoni eseguono due funzioni diverse:
    
    - #### Bottone **esci**
        
            void btn_esciClicked(ActionEvent event) {
                System.exit(0);
            }
        Chiude il programma.
    - #### Bottone **nuova partita**
            @FXML
            void btn_nuovapartitaClicked(ActionEvent event) throws IOException {
                FXMLLoader fxmlLoader = new FXMLLoader(ControllerMenu.class.getResource("HostScene.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scena = new Scene(fxmlLoader.load());
                stage.setScene(scena);
                stage.show();
            }
        Chiama la scena **"HostScene.fxml"**
    
    - #### Bottone **Unisciti a partita esistente**
            @FXML
            void btn_uniscitipartitaClicked(ActionEvent event) throws IOException {
                FXMLLoader fxmlLoader = new FXMLLoader(ControllerMenu.class.getResource("ClientScene.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scena = new Scene(fxmlLoader.load());
                stage.setScene(scena);
                stage.show();
            }
        Chiama la scena **"ClientScene.fxml"**
---
- ### ControllerServer.java
    La classe ControllerServer e' la classe che server per gestire i **Node** di **HostScene.fxml**
    
    - #### Bottone **BTM**
            @FXML
            void btnBTMClicked(ActionEvent event) throws IOException {
                root = FXMLLoader.load(getClass().getResource("menu.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scena = new Scene(root);
                stage.setScene(scena);
                stage.show();
            }
        Bottone che permette di tornare alla schermata di menu iniziale
    
    - #### Bottone **Submit**

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
                ControllerGame controllerGame = new ControllerGame();

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

        Una volta premuto il pulsante di Submit prende il testo all'interno del label e richiama la funzione di handshaking
    
    
---

- ### ControllerClient.java

    La classe ControllerClient é la classe che serve per gestire i **Node** di **ClientScene.fxml**

  - #### Bottone **BTM**

             @FXML
            void btnBTMClicked(ActionEvent event) throws IOException {
                FXMLLoader fxmlLoader = new FXMLLoader(ControllerClient.class.getResource("menu.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scena = new Scene(fxmlLoader.load());
                stage.setScene(scena);
                stage.show();

            }
        Bottone che permette di tornare alla schermata di menú iniziale

  - #### Bottone Submit

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
     Una volta premuto il pulsante di Submit prende il testo all'interno del label e richiama la funzione di handshaking

---
- ### ControllerLoading.java
La classe ControllerLoading é la classe che si occupa di caricare la schermata del gioco una volta finita la fase di handshaking

    public class ControllerLoading extends Application {

    private Scene scena;
    private Stage stage;


    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(ControllerClient.class.getResource("Game.fxml"));
        Parent root = fxmlLoader.load();
        scena = new Scene(root);
        stage.setScene(scena);
        stage.show();
    }
    }
---
- ### ControllerGame.java
    La classe che gestisce i **Node** di **Game.fxml**

    - #### Aggiornamento GridPane

            public void updateGridPane(Game.val[][] matrix) {
                for (int row = 0; row < dim; row++) {
                    for (int col = 0; col < dim; col++) {
                        Label label = getLabel(row, col);
                        label.setText(String.valueOf(matrix[row][col]));
                        // Aggiungi eventuali stili o gestori degli eventi ai label
                    }
                }
            }
        Metodo che aggiorna la GUI con la matrice corrente
---

- ### Game.java


    La classe Game è quella che contiene tutte le meccaniche e le regole del gioco. All'interno, sono presenti diverse funzioni per gestire il flusso di gioco, quali il controllo della vittoria, la verifica di una situazione di pareggio, il controllo della presenza di caselle vuote e, infine, l'inizializzazione della matrice con valori "vuoti".

  - #### Controllo vittoria sulle righe

        private boolean checkRow(val[][] board, int x) {
                    if (board[x][0].equals(board[x][1])&&
                    board[x][0].equals(board[x][2])){
                        winner = board[x][0];
                        return true;
                    }
                return false;
            }

  - #### Controllo vittoria sulle colonne

        private boolean checkCol(val[][] board, int y) {
                    if (board[0][y].equals(board[1][y])&&
                            board[0][y].equals(board[2][y])) {
                        winner = board[0][y];
                        return true;
                    }
                return false;
            }

  - #### Controllo vittoria diagonale sinistra

        private boolean checkS(val[][] board) {
            if (board[0][0].equals(board[1][1])&&
            board[0][0].equals(board[2][2])&& !isEmpty(board,0,0)){
                winner = board[0][0];
                return true;
            }
            return false;
        }

  - #### Controllo vittoria diagonale destra

        private boolean checkD(val[][] board) {
                if (board[0][2].equals(board[1][1])&&
                board[0][2].equals(board[2][0])&& !isEmpty(board,0,2)){
                    winner = board[0][2];
                    return true;
                }
                return false;
            }

  - #### Controllo pareggio

        boolean isDraw(val[][] board, int nmossa){
                if (nmossa == 8 && !checkWin(board,1,1)){
                    System.out.println("Pareggio");
                    return true;
                }
                else {
                    return false;
                }

            }

  - #### Controllo caselle vuote

        boolean isEmpty(val[][] board, int x, int y){
                if(board[x][y]==val.V)
                    return true;
                else
                    return false;
            }

  - #### Inizializzazione matrice "vuote"

        void inizialize_board(val[][] matrice){
                for (int i = 0; i < dim; i++) {
                    for (int j = 0; j < dim; j++) {
                        matrice[i][j] = val.V;
                    }
                }
            }

  - #### Stampa matrice

        void stampa(val[][] board){

                for (int i = 0; i < dim; i++) {
                    for (int j = 0; j < dim; j++) {
                        System.out.print(board[i][j] + " ");
                    }
                    System.out.println();
                }

            }

---

- ### ServerGame.java
    Questa classe contiene i metodi di handshaking e di scambio dei messaggi lato server.

    - **Handshaking**

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
        
    - **Messaggi**

            void messaggi(int porta) throws IOException, ClassNotFoundException {
            //handshaking(porta);
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
        
        Questo metodo é il cuore del gioco lato server visto che si occupa della fase di input dei dati e lo scambio di messaggi con il client.

---

- ### ClientGame.java
    Questa classe contiene i metodi di handshaking e di scambio dei messaggi lato client.

    - **Handshaking**

            public void handshaking(int porta) throws IOException {
            socket = new Socket("localhost", porta);
            }

    - **Messaggi**

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
                        socket.close();
                        System.exit(0);
                    }

                    System.out.println("----Fine del Turno----");

                } while (!game.isDraw(board,nmossa));

                //close  dopo la fine del gioco
                socket.close();

            }
        Questo metodo é il cuore del gioco lato client visto che si occupa della fase di input dei dati e lo scambio di messaggi con il server.







## Manuale utente 

### Come avviare una partita :

Per stabilire la connessione:
- Eseguire il file Menu.java due volte per avviare due istanze del menu.
- Nella prima istanza, selezionare l'opzione "Crea nuova partita" per creare una partita.
- Digitare il numero di porta desiderato per stabilire la connessione.
- Nella seconda istanza, selezionare l'opzione "Uniscit a una partita esistente" per unirsi a una partita.
- Inserire il numero di porta precedentemente scelto dal server.

Come giocare: 

Prima di iniziare con la spiegazione di come si gioca, é necessario dire che il player "X" é rappresentato dal Server, mentre il player "O" é rappresentato dal client.

I punti da conoscere per giocare sono i seguenti:

- La prima mossa è sempre effettuata dal giocatore "X".
- Al giocatore "X" viene mostrato a schermo il campo da gioco iniziale, che è quindi vuoto.
- Per effettuare una mossa, i giocatori devono inserire le coordinate richieste a schermo, prima la coordinata x e poi quella y.
- Ogni volta che viene effettuata una mossa, il campo di gioco viene aggiornato, stampato e visualizzato da entrambi i partecipanti.
- Quando uno dei due giocatori completa un "tris", viene comunicato a entrambi i giocatori il vincitore, seguito da un messaggio con scritto "Fine del gioco".



## Conclusioni

Al termine del tempo disponibile per completare il progetto, non siamo riusciti a implementare l'interfaccia grafica (GUI) all'interno del gioco. Durante tutta la durata antecedente alla scadenza del progetto abbiamo avuto l'opportunitá di studiare, apprendere, mettere in pratica tutte quelle che sono le competenze trasversali riguardo l'apprendimento di un nuovo linguaggio di programmazione come Java.
Inoltre abbiamo appreso al meglio quelli che sono i principali paradigmi della programmazione a oggetti.

I principali problemi riscontrati risiedono nella implementazione della interfaccia grafica con la nostra logica del programma, e di conseguenza si sono palesati dei problemi riguardanti la sincronizzazione.





