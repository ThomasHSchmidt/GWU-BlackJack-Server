import java.util.*;
import java.net.*;
import java.io.*;

public class BlackjackServer extends Thread {

    ServerSocket serverSock;
    ArrayList<Socket> connections;
    ArrayList<String> members;
    int users;
    boolean playerIn;
    volatile boolean player1Confirm;
    volatile boolean player2Confirm;

    public BlackjackServer(int port){

        //Instantiates all of the components, including the server socket, member names, and all active connections
        try {
            serverSock = new ServerSocket(port);
            users = 0;
            members = new ArrayList<String>();
            connections = new ArrayList<Socket>();
            System.out.println("BlackjackServer started on port " + port);
        }
        catch(Exception e) {
            System.err.println("Cannot establish server socket");
            System.exit(1);
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
                boolean name = false;
                playerIn = true;

                while(true) {
                    String msg = in.readLine();

                    if (msg.equals("NAME"))
                    {
                        // Start name
                        name = true;
                        continue;
                    }
                    else if (name)
                    {
                        // Add name to user list
                        members.add(msg);
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

                    dealing = true;
                    if(dealing) {
                        Deck deck = new Deck();
                        deck.shuffle();
                        for(int i = 0; i < connections.size(); i++) {
                            PrintWriter out = new PrintWriter(connections.get(i).getOutputStream());
                            out.println("Receiving initial cards");
                            c1 = player.dealCard(deck.drawCard());
                            c2 = player.dealCard(deck.drawCard());
                            out.println(c1);
                            out.println(c2);
                            out.flush();
                        }
                        dealing = false;
                    }
                    

                    if(msg.equals("Hit")) {
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

                }
            
            
            } catch(Exception e) {

                //note the loss of the connection
                System.out.println("Connection lost: " + sock.getRemoteSocketAddress());
                System.out.println("This error is occurring");

            }
        }
    }

    
    public void serve() {
        while(true) {
            try {
                //accept incoming connection
                Socket clientSock = serverSock.accept();
                System.out.println("New connection: "+ clientSock.getRemoteSocketAddress());
                
                PrintWriter out = new PrintWriter(clientSock.getOutputStream());
                out.println("Established connection");
                out.flush();
               
                connections.add(clientSock);
                    //start the thread
                    (new ClientHandler(clientSock, users)).start();   
                    System.out.println("Connection " + users);
                    users++;

            //exit serve if exception
            } 
            catch(Exception e) {}
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
                for (String user : members)
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
}
