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
        try{
            serverSock = new ServerSocket(port);
            users = 0;
            members = new ArrayList<String>();
            connections = new ArrayList<Socket>();
            System.out.println("BlackjackServer started on port " + port);
        }
        catch(Exception e){
            System.err.println("Cannot establish server socket");
            System.exit(1);
        }
    }

    
    public void serve(){
        while(true){
            try{
                //accept incoming connection
                Socket clientSock = serverSock.accept();
                System.out.println("New connection: "+ clientSock.getRemoteSocketAddress());
                
                //If the first line is secret, then a password is created
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSock.getInputStream()));
                PrintWriter out = new PrintWriter(clientSock.getOutputStream());
                out.println("Established connection");
                out.flush();
               
                connections.add(clientSock);
                    //start the thread
                    (new ClientHandler(clientSock, users)).start();   
                    System.out.println("Connection " + users);
                    users++;

                    if (connections.size() == 1){
                        (new GameTracker()).start();
                        System.out.println("GameTracker Thread Started");
                    }

            //exit serve if exception
            }catch(Exception e){}
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

    private class ClientHandler extends Thread{

        Socket sock;
        int id;
        int chips;
        Hand hand;
        Deck deck;

        public ClientHandler(Socket sock, int id){
            this.sock = sock;
            this.id = id;
            chips = 250;
            Hand hand;
            Deck deck;
        }

        public void run(){
            BufferedReader in=null;
            try{
                in = new BufferedReader(new InputStreamReader(sock.getInputStream()));

                // Data being taken from the socket
                String line;
                String curName = "";
                boolean name = false;
                boolean logIn = false;
                playerIn = true;

                while(true){
                    String msg = in.readLine();
                    if (!logIn)
                    {
                        break;
                    }

                    if (line.equals("NAME"))
                    {
                        // Start name
                        name = true;
                        continue;
                    }
                    else if (name)
                    {
                        // Add name to user list
                        members.add(line);
                        sendUserList();
                        curName = line;
                        name = false;
                        continue;
                    }

                    if(msg.equals("Hit")){
                        for(int i = 0; i < users; i++){
                            if(id == i){
                                PrintWriter pw = new PrintWriter(connections.get(i).getOutputStream());
                                int cardValue = hand.addCard(deck.drawCard());

                                // Notify the player about the drawn card
                                pw.println("Card drawn: " + cardValue);

                                // Check if the player busts
                                // NEED isBust FUNCTION
                                if (isBust()) {
                                    pw.println("Bust! You lose.");
                                    playerIn = false;
                                }
                                pw.flush();
                            }
                        }
                    }

                    // If player Stands
                    if(msg.equals("Stand")){
                        PrintWriter pw = new PrintWriter(sock.getOutputStream());           
                        pw.println("You chose to stand. Your turn is over.");
                        pw.flush();
                    }


                    // If player Double Downs
                    if(msg.equals("Double Down")){
                        PrintWriter pw = new PrintWriter(sock.getOutputStream());

                        // NEED getBet FUNCTION
                        int newBet = id.getBet() * 2; // Double the bet

                        pw.println("Your bet is now: " + newBet);

                        // Draw one more card
                        int cardValue = drawCard();
                        pw.println("Card drawn: " + cardValue);

                        // Check if the player busts
                        if (isBust()) {
                            pw.println("Bust! You lose.");

                        }
                    }


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

                }
            
            
            } catch(Exception e){

                //note the loss of the connection
                System.out.println("Connection lost: " + sock.getRemoteSocketAddress());
                System.out.println("This error is occurring");

            }
        }
    }


/*   
    private void handleBettingPhase() {
        sendMessage("Place your bet.");
    
        try {
            // Assume a simple bet amount for now, you may implement a more sophisticated input method
            int bet = Integer.parseInt(in.readLine());
    
            // Validate the bet amount
            if (bet < 0 || bet > chips) {
                sendMessage("Invalid bet amount. Please place a bet within your available chips.");
                handleBettingPhase(); // Allow the player to try again
                return;
            }
    
            // Deduct the bet from the player's chips
            chips -= bet;
    
            // Continue with the game, perhaps move to the card dealing phase
            sendMessage("Betting phase complete. Starting the game.");
            // Add logic to deal cards, manage turns, etc.
            // ...
    
        } catch (NumberFormatException e) {
            sendMessage("Invalid input. Please enter a numeric value for your bet.");
            handleBettingPhase(); // Allow the player to try again
        }
    }

        public static void main(String args[]){
            int port = Integer.parseInt(args[0]);
            BlackjackServer server = new BlackjackServer(port);
            server.serve();
        }
    }
*/