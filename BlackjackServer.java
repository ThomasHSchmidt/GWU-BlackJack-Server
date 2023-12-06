import java.util.*;
import java.net.*;
import java.io.*;

public class BlackjackServer extends Thread {

    ServerSocket serverSock;
    static ArrayList<Socket> connections;
    ArrayList<String> playerList;
    boolean playerIn;

    public BlackjackServer(int port){

        //Instantiates all of the components, including the server socket, member names, and all active connections
        try {
            serverSock = new ServerSocket(port);
            playerList = new ArrayList<String>();
            connections = new ArrayList<Socket>();
            System.out.println("BlackjackServer started on port " + port);
        }
        catch(Exception e) {
            System.err.println("Cannot establish server socket");
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

                //start the thread
                (new ClientHandler(clientSock, playerList.size())).start();   
                
            //exit serve if exception
            } catch(Exception e) { }
        }
    }

    public void sendUserList()
    {
        // Send user list to all clients
        for (Socket s : connections)
        {
            try
            {
                PrintWriter pw = new PrintWriter(s.getOutputStream());
                pw.println("START_CLIENT_LIST");
                for (String user : playerList)
                {
                    pw.println(user);
                }
                pw.println("END_CLIENT_LIST");
                pw.flush();
            }
            catch (Exception e)
            {
                System.err.println("Error sending user list");
            }
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

        public ClientHandler(Socket sock, int id) {
            this.sock = sock;
            this.id = id;
        }

        public void run() {
            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(sock.getInputStream()));

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

                        // Add name to user list
                        playerList.add(msg);
                        sendUserList();
                        curName = msg;
                        name = false;
                        continue;
                    }


                    for(int i = 0; i < connections.size(); i++) {
                        if(id == i) {
                            PrintWriter pw = new PrintWriter(connections.get(i).getOutputStream());
                            pw.println("Place your bet.");
                            try {
                                int bet = Integer.parseInt(in.readLine());
                        
                                // Validate the bet amount
                                if (bet < Player.MIN_BET || bet > player.getCash()) {
                                    pw.println("Invalid bet amount. Please place a bet within your available chips next round.");
                                    playerIn = false;
                                    return;
                                }
                                player.setCash(player.getCash() - bet);
                        
                                pw.println("Betting phase complete. Starting the game.");
                        
                            } catch (NumberFormatException e) {
                                pw.println("Invalid input. Please enter a valid numeric value for your bet next round.");
                            }
                        }
                    }

                    System.out.println("4");

                    dealing = true;
                    if(dealing) {
                        System.out.println("5");

                        Deck deck = new Deck();
                        deck.shuffle();
                        for(int i = 0; i < connections.size(); i++) {
                            System.out.println("11");
                            PrintWriter out = new PrintWriter(connections.get(i).getOutputStream());
                            System.out.println("12");
                            out.println("Receiving initial cards");
                            player = new Player(curName, i);
                            c1 = player.dealCard(deck.drawCard());
                            c2 = player.dealCard(deck.drawCard());
                            out.println(c1);
                            out.println(c2);
                            out.flush();
                        }
                        System.out.println("6");
                        dealing = false;
                    }
                    

                    if(msg.equals("Hit")) {
                        System.out.println("7");

                        for(int i = 0; i < connections.size(); i++) {
                            if(id == i) {
                                PrintWriter pw = new PrintWriter(connections.get(i).getOutputStream());
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
                        System.out.println("8");

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
                        System.out.println("9");

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

    public static void main(String args[]){
        int port = Integer.parseInt(args[0]);
        BlackjackServer server = new BlackjackServer(port);
        server.serve();
    }
}

/*
                    // If player splits
                    if(msg.equals("Split")){
                            Hand newHand = new Hand();

                            // Draw a new card for the original and new hand
                            newHand.addCard(drawCard());
                            newHand.addCard(drawCard());
                    
                            // Duplicate the bet for the new hand
                            int bet1 = getBet();
                            int bet2 = getBet();
                    
                            // MISSING CODE
                    
                            pw.flush();
                    } 
*/  