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


    private class ClientHandler extends Thread{

        Socket sock;
        int id;
        int chips;

        public ClientHandler(Socket sock, int id){
            this.sock=sock;
            this.id = id;
            chips = 1000;
        }

        public void run(){
            BufferedReader in=null;
            try{
                //Creates the input/output corresponding to the sockets stream
                boolean firstTime = true;
               

                in = new BufferedReader(new InputStreamReader(sock.getInputStream()));

                //read and echo back continuously
                while(true){

                    //Checks first to see if the message is null
                    String msg = in.readLine();
                    if(msg == null){
                        for(int i=0; i<connections.size(); i++){
                            if(connections.get(i) == sock){
                                connections.remove(i);
                            }
                        }
                    }


                    // If player hits
                    if(msg.equals("Hit")){
                        PrintWriter pw = new PrintWriter(connections.get(i).getOutputStream());
                        int cardValue = drawCard();

                        // Notify the player about the drawn card
                        pw.println("Card drawn: " + cardValue);

                        // Check if the player busts
                        // NEED isBust FUNCTION
                        if (isBust()) {
                            pw.println("Bust! You lose.");
                            playerIn = true;
                        }
                        playerIn = true;
                        pw.flush();
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
            } catch(Exception e){}

                //note the loss of the connection
                System.out.println("Connection lost: " + sock.getRemoteSocketAddress());
                System.out.println("This error is occurring");

        }


        public isBust() {
            
        }


        public static void main(String args[]){
            int port = Integer.parseInt(args[0]);
            BlackjackServer server = new BlackjackServer(port);
            server.serve();
        }
    }
}



// Must go into ClientListener
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
        
                // Notify other players about the bet
                sendMessage("Player " + id + " placed a bet of " + bet + " chips.");
        
                // Continue with the game, perhaps move to the card dealing phase
                sendMessage("Betting phase complete. Starting the game.");
                // Add logic to deal cards, manage turns, etc.
                // ...
        
            } catch (NumberFormatException e) {
                sendMessage("Invalid input. Please enter a numeric value for your bet.");
                handleBettingPhase(); // Allow the player to try again
            } catch (Exception e) {
                e.printStackTrace(); // Handle other exceptions as needed
            }
        }
 */