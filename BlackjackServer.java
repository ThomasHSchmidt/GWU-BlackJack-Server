import java.util.*;
import java.net.*;
import java.io.*;

public class BlackjackServer extends Thread {

    private Deck deck;
    ServerSocket serverSock;
    ArrayList<Socket> connections;
    List<ClientHandler> clients;
    ArrayList<Player> players;
    boolean playerIn;

    public BlackjackServer(int port) {

        //Instantiates all of the components, including the server socket, member names, and all active connections
        try {
            deck = new Deck();
            clients = new LinkedList<>();
            serverSock = new ServerSocket(port);
            connections = new ArrayList<Socket>();
            players = new ArrayList<Player>();
            System.out.println("BlackjackServer started on port " + port);
        }
        catch(Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
    // public static int getID() {
    //     return connections.size();
    // }

    public void serve() {
        while(true) {
            try {
                //accept incoming connection
                Socket clientSock = serverSock.accept();
                connections.add(clientSock);
                players.add(new Player("", players.size()));

                //start the thread
                ClientHandler client = new ClientHandler(clientSock, players.size(), this.deck);
                clients.add(client);
                client.start();   
                
            //exit serve if exception
            } catch(Exception e) { }
        }
    }

    private class ClientHandler extends Thread {

        Socket sock;
        int id;
        Deck deck;
        Player player;
        boolean dealing;
        String c1;
        String c2;
        BufferedReader in;
        PrintWriter pw;

        public ClientHandler(Socket sock, int id, Deck deck) {
            try {
                this.deck = deck;
                this.sock = sock;
                this.id = id;
                pw = new PrintWriter(sock.getOutputStream());
                in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            }
            catch(Exception e) { }
        }

        public void run() {
            // turn ends when player:
            //      stands
            //      double down
            //      busts
            try {
                // Data being taken from the socket
                String curName = "";
                System.out.println("1");
                boolean name = false;
                playerIn = true;

                while(true) {
                    String msg = in.readLine();

                    if (msg.equals("NAME"))
                    {
                        // Start name
                        System.out.println("2");
                        name = true;
                        continue;
                    }
                    else if (name)
                    {
                        System.out.println("3");

                        curName = msg;
                        name = false;
                        continue;
                    }

                    if (msg.equals("Start 0")) {
                        for(int i = 0; i < connections.size(); i++) {
                            if(id == i) {
                                try {
                                    if (msg.equals("Bet")) {
                                        int bet = Integer.parseInt(in.readLine());
                            
                                        // Validate the bet amount
                                        if (bet < Player.MIN_BET || bet > player.getCash()) {
                                            pw.println("Invalid bet amount. Please place a bet within your available chips next round.");
                                            playerIn = false;
                                            return;
                                        }
                                        player.setCash(player.getCash() - bet);
                                        
                                    // if(id == 0) {}
                                    // if (id == 1) {}
                                    // if (id == 2) {}
                                    // if (id == 3) {}
                                    // if (id == 4) {}
                                    }
                                    pw.println("Betting phase complete. Starting the game.");
                                } 
                                catch (NumberFormatException e) {
                                    pw.println("Invalid input. Please enter a valid numeric value for your bet next round.");
                                }
                            }
                        }

                        System.out.println("** Betting Complete **");

                        dealing = true;
                        if(dealing) {
                            System.out.println("5");

                            if(deck.getCardCount() <= 52) {
                                deck.shuffle();
                            }

                            for(int i = 0; i < connections.size(); i++) {
                                System.out.println("11");

                                c1 = players.get(i).dealCard(deck.drawCard());
                                pw.println(c1);
                                c2 = players.get(i).dealCard(deck.drawCard());
                                pw.println(c2);

                                System.out.println("Player " + (i + 1) + " hand value: " + players.get(i).getHandValue());
                                sendHandValues(i);
                                // pw.println("p" + (i + 1) + "tot");
                                // pw.println(String.valueOf(players.get(i).getHandValue()));
                                players.get(i).getHand().printHand();
                                    
                                pw.flush();
                            }
                            System.out.println("** Dealing Complete **");
                            dealing = false;
                            msg = in.readLine();
                        }
                    }

                    if(msg.equals("Hit")) {
                        System.out.println("** Player Hit **");

                        for(int i = 0; i < connections.size(); i++) {
                            if(id == i) {
                                player.dealCard(deck.drawCard());

                                // Check if the player busts
                                if (player.getHand().isBust()) {
                                    pw.println("Bust! You lose.");
                                    playerIn = false;
                                }
                                pw.flush();
                            }
                        }
                    }


                    // If player Stands
                    if(msg.equals("Stand")) {
                        System.out.println("** Player Stand **");

                        for(int i = 0; i < connections.size(); i++) {
                            if(id == i) {
                                PrintWriter pw = new PrintWriter(sock.getOutputStream());           
                                pw.println("You chose to stand. Your turn is over.");
                                pw.flush();
                            }
                        }
                    }


                    // If player Double Downs
                    if(msg.equals("Double Down")){
                        System.out.println("** Player Double Down **");

                        for(int i = 0; i < connections.size(); i++) {
                            if(id == i) {
                                PrintWriter pw = new PrintWriter(sock.getOutputStream());

                                // NEED getBet FUNCTION
                                // Double the bet
                                int newBet = player.getBet() * 2;
                                player.setBet(newBet);

                                pw.println("Your bet is now: " + newBet);

                                // Draw one more card
                                player.dealCard(deck.drawCard());

                                // Check if the player busts
                                if (player.getHand().isBust()) {
                                    pw.println("Bust! You lose.");
                                    playerIn = false;
                                }
                                pw.flush();
                            }
                        }
                    }
                }
            } catch(Exception e) {

                //note the loss of the connection
                System.out.println("Connection lost: " + sock.getRemoteSocketAddress());
                System.out.println("This error is occurring");
                System.out.println(e.getMessage());

            }
        }
    }

    public void sendHandValues(int i) {
        for(ClientHandler client : clients) {
            client.pw.println("p" + (i + 1) + "tot");
            client.pw.println(String.valueOf(players.get(i).getHandValue()));
        }
    }

    public static void main(String args[]){
        int port = Integer.parseInt(args[0]);
        BlackjackServer server = new BlackjackServer(port);
        server.serve();
    }
}