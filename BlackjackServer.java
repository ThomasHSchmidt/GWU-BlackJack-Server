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
    Dealer dealer;

    public BlackjackServer(int port) {

        //Instantiates all of the components, including the server socket, member names, and all active connections
        try {
            deck = new Deck();
            clients = new LinkedList<>();
            serverSock = new ServerSocket(port);
            connections = new ArrayList<Socket>();
            players = new ArrayList<Player>();
            dealer = new Dealer("The Dealer");
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

        public void sendMessage(String message) {
            pw.println(message);
            pw.flush();
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
                    sendHandValues();
                    sendBetValues();

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
                        System.out.println("Waiting for player 1 to bet");
                        while (!msg.equals("Bet")) {
                            msg = in.readLine();
                            System.out.println("out: " + msg);
                        }
                        // msg = in.readLine();
                        for(int i = 0; i < connections.size(); i++) {
                            // PrintWriter pw1 = new PrintWriter(connections.get(i).getOutputStream());
                            // BufferedReader in1 = new BufferedReader(new InputStreamReader(connections.get(i).getInputStream()));
                            System.out.println("Waiting for player " + (i+1) + " to bet");
                            // msg = in1.readLine();
                            
                            while (!msg.equals("Bet " + i)) {
                                msg = in.readLine();
                                System.out.println("mid: " + msg);
                                //System.out.println(msg);
                            }
                                try {
                                    msg = in.readLine();
                                    System.out.println("in: " + msg);
                                    int bet = Integer.parseInt(msg);
                                
                                    // Validate the bet amount
                                    if (bet < Player.MIN_BET || bet > players.get(i).getCash()) {
                                        pw.println("Invalid bet amount. Please place a bet within your available chips next round.");
                                        playerIn = false;
                                        return;
                                    }

                                    players.get(i).setBet(bet);
                                    players.get(i).setCash(players.get(i).getCash() - bet);

                                    pw.println("tot");
                                    pw.println(players.get(i).getCash());
                                    System.out.println("Player " + (i+1) + " bet successful");
                                    // msg = in1.readLine();
                                    } 
                                    catch (NumberFormatException e) {
                                        pw.println("Invalid input. Please enter a valid numeric value for your bet next round.");
                                    }
                            }
                        sendBetValues();
                        System.out.println("** Betting Complete **");

                        dealing = true;
                        if(dealing) {
                            System.out.println("5");

                            if(deck.getCardCount() <= 52) {
                                deck.shuffle();
                            }

                            for(int i = 0; i < players.size(); i++) {
                                System.out.println("Dealing to player " + (i+1));
                                // pw = new PrintWriter(connections.get(i).getOutputStream());
                                c1 = players.get(i).dealCard(deck.drawCard());
                                //pw.println(c1);
                                c2 = players.get(i).dealCard(deck.drawCard());
                                //pw.println(c2);

                                System.out.println("Player " + (i + 1) + " hand value: " + players.get(i).getHandValue());
                                

                                players.get(i).getHand().printHand();
                                    
                                pw.flush();
                            }

                            dealer.dealCard(deck.drawCard());

                            sendHandValues();
                            System.out.println("** Dealing Complete **");
                            dealing = false;
                            msg = in.readLine();
                        }
                    

                        System.out.println("** Player Hit **");

                        for(int i = 0; i < players.size(); i++) {
                            while (!msg.equals("Stand") && !players.get(i).getHand().isBust() && players.get(i).getHandValue() != 21) {
                                // PrintWriter pw1 = new PrintWriter(connections.get(i).getOutputStream());
                                // BufferedReader in1 = new BufferedReader(new InputStreamReader(connections.get(i).getInputStream()));
                                if (msg.equals("Hit")) {
                                    c1 = players.get(i).dealCard(deck.drawCard());
                                    System.out.println("Player " + (i + 1) + " hand value: " + players.get(i).getHandValue());
                                    sendHandValues();
                                }
                                else if (msg.equals("Double Down")) {
                                    players.get(i).setBet(players.get(i).getBet() * 2);
                                    c1 = players.get(i).dealCard(deck.drawCard());
                                    break;
                                }
                                if(players.get(i).getHandValue() < 21)
                                    msg = in.readLine();
                            }
                            msg = "";
                        }

                        if(!allBust()) {
                            dealerTurn();
                            sendHandValues();
                        }

                        this.sleep(3000);

                        resetGame();
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

    public synchronized void sendHandValues() {
        String message = "";

        for(int i = 0; i < players.size(); i++) {
            message += "p" + (i + 1) + "tot\n";
            message += String.valueOf(players.get(i).getHandValue()) + "\n";
        }

        message += "dtot\n" + dealer.getHandValue();

        for(ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

    public synchronized void sendBetValues() {
        String message = "";

        for(int i = 0; i < players.size(); i++) {
            message += "p" + (i + 1) + "Bet\n";
            if(i < players.size() - 1)
                message += String.valueOf(players.get(i).getBet()) + "\n";
            else
                message += String.valueOf(players.get(i).getBet());
        }

        for(ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

    public synchronized void sendNewTotals() {
        for(int i = 0; i < players.size(); i++) {
            clients.get(i).pw.println("tot");
            clients.get(i).pw.println(players.get(i).getCash());
        }
    }

    public boolean allBust() {
        for(Player p : players) {
            if(p.getHand().isBust())
                continue;
            return false;
        }
        return true;
    }

    public synchronized void resetGame() {
        for(Player p : players) {
            p.resetHand();
            p.setBet(0);
        }
        dealer.resetHand();

        sendHandValues();
        sendBetValues();
    }
    
    public synchronized void dealerTurn() {
        while(dealer.getHandValue() < 17) {
            dealer.dealCard(deck.drawCard());
        }
        sendHandValues();

        for(Player p : players) {
            if(!p.getHand().isBust() && p.getHandValue() > dealer.getHandValue()) {
                p.setCash(p.getCash() + (p.getBet() * 2));
            }
            else if(!p.getHand().isBust() && dealer.getHandValue() == p.getHandValue())
                p.setCash(p.getCash() + p.getBet());
        }
        sendNewTotals();
    }

    public static void main(String args[]){
        int port = Integer.parseInt(args[0]);
        BlackjackServer server = new BlackjackServer(port);
        server.serve();
    }
}