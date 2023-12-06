import java.net.*;
import javax.swing.*;
import java.io.*;

public class TableClientListener extends Thread {
    private Socket sock;
    private JTextArea txtArea;
    private JList<String> userList;
    private int id;
    private int[] starpos = {725, 475, 585, 560, 430, 643, 280, 560, 140, 480};
    Deck deck;
    Player player;
    boolean playerIn;
    boolean dealing;
    String c1;
    String c2;
 
    public TableClientListener(Socket sock, int id)
    {
        this.sock = sock;
        this.id = id;

    }

    public void run()
    {
        for (int i = 0; i < 5; i++) {
            if (id == i) {
                TableGUI.setStar(starpos[2*i], starpos[(2*i)+1]);
            }
        }
        try
        {
            String line;
            boolean message = false;
            boolean endOfUserList = false;
            String users = "";

            PrintWriter pw = new PrintWriter(sock.getOutputStream());
            BufferedReader in =
                new BufferedReader(
                        new InputStreamReader(sock.getInputStream()));

            while ((line = in.readLine()) != null)
            {
                // Check for message
                if (line.equals("START MSG"))
                {
                    // Start message, continue to next line
                    message = true;
                    continue;
                }
                else if (line.equals("END MSG"))
                {
                    // End message, continue to next line
                    // Scroll to bottom
                    //txtArea.setCaretPosition(txtArea.getDocument().getLength());
                    message = false;
                    continue;
                }
                else if (message) 
                {
                    // Add message to text area
                    txtArea.append(line + "\n");
                    continue;
                }

                // Check for update of user list
                if (line.equals("START_CLIENT_LIST"))
                {
                    // Start user list, continue to next line
                    endOfUserList = true;
                    continue;
                }
                else if (line.equals("END_CLIENT_LIST"))
                {
                    // End update of users
                    endOfUserList = false;
                    users = users.replaceAll("^,|,$", ""); // Remove first and last comma
                    // Update user list
                    userList.setListData(users.split(","));
                    users = "";
                    continue;
                }
                // End of the list = add user to end
                else if (endOfUserList)
                {
                    // Add to user list
                    users += "," + line;
                    continue;
                }
            }
            //close the stream
            pw.close();

            //close the input stream
            in.close();
        }
        catch(Exception e){ }
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
                        //playerList.add(msg);
                        //sendUserList();
                        curName = msg;
                        name = false;
                        continue;
                    }


                    for(int i = 0; i < BlackjackServer.connections.size(); i++) {
                        if(id == i) {
                            PrintWriter pw = new PrintWriter(BlackjackServer.connections.get(i).getOutputStream());
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
                        System.out.println("11");
                        deck.shuffle();
                        for(int i = 0; i < BlackjackServer.connections.size(); i++) {
                            PrintWriter out = new PrintWriter(BlackjackServer.connections.get(i).getOutputStream());
                            out.println("Receiving initial cards");
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

                        for(int i = 0; i < BlackjackServer.connections.size(); i++) {
                            if(id == i) {
                                PrintWriter pw = new PrintWriter(BlackjackServer.connections.get(i).getOutputStream());
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

                        for(int i = 0; i < BlackjackServer.connections.size(); i++) {
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

                        for(int i = 0; i < BlackjackServer.connections.size(); i++) {
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
                System.out.println("Connection lost: " + sock.getRemoteSocketAddress() + " " + e.getMessage());
                System.out.println("This error is occurring");

            }
        
    }
}
