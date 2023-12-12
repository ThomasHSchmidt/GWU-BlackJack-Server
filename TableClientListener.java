import java.net.*;
import javax.swing.*;
import java.io.*;

public class TableClientListener extends Thread {
    private Socket sock;
    // private JTextArea txtArea;
    // private JList<String> userList;
    private int id;
    private int[] starpos = {725, 475, 585, 560, 430, 643, 280, 560, 140, 480};
    Deck deck;
    Player player;
    boolean playerIn;
    boolean dealing;
    String c1;
    String c2;
    TableGUI gui;
    PrintWriter pw;
    
 
    public TableClientListener(Socket sock, int id, TableGUI gui)
    {
        try {
            pw = new PrintWriter(sock.getOutputStream());
            this.sock = sock;
            this.id = id;
            this.gui = gui;
        }
        catch(Exception e) {}
    }

    public void run()
    {
        for (int i = 0; i < 5; i++) {
            if (id == i) {
                gui.setStar(starpos[2*i], starpos[(2*i)+1]);
            }
        }
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            while(true) {
                String msg = in.readLine();
                if (msg.equals("tot")) {
                    msg = in.readLine();
                    gui.setTotal("$" + msg);
                }
                if(msg.equals("p1tot")) {
                    msg = in.readLine();
                    gui.setp1Tot(msg);
                }
                if(msg.equals("p2tot")) {
                    msg = in.readLine();
                    gui.setp2Tot(msg);
                }
                if(msg.equals("p3tot")) {
                    msg = in.readLine();
                    gui.setp3Tot(msg);
                }
                if(msg.equals("p4tot")) {
                    msg = in.readLine();
                    gui.setp4Tot(msg);
                }
                if(msg.equals("p5tot")) {
                    msg = in.readLine();
                    gui.setp5Tot(msg);
                }
                if(msg.equals("dtot")) {
                    msg = in.readLine();
                    gui.setdTot(msg);
                }
                if(msg.equals("p1Bet")) {
                    msg = in.readLine();
                    gui.setp1Bet(msg);
                }
                if(msg.equals("p2Bet")) {
                    msg = in.readLine();
                    gui.setp2Bet(msg);
                }
                if(msg.equals("p3Bet")) {
                    msg = in.readLine();
                    gui.setp3Bet(msg);
                }
                if(msg.equals("p4Bet")) {
                    msg = in.readLine();
                    gui.setp4Bet(msg);
                }
                if(msg.equals("p5Bet")) {
                    msg = in.readLine();
                    gui.setp5Bet(msg);
                }
                if(msg.equals("PBust")){
                    JOptionPane.showMessageDialog(null, "Bust! You Lose.");
                }
                if(msg.equals("PWin")){
                    JOptionPane.showMessageDialog(null, "You Win! Congratulations!");
                }
                if(msg.equals("Push")){
                    JOptionPane.showMessageDialog(null, "You pushed with the dealer!");
                }
                if(msg.equals("PLose")){
                    JOptionPane.showMessageDialog(null, "Dealer's hand is better, you lose!");
                }
                if(msg.equals("PBroke")){
                    JOptionPane.showMessageDialog(null, "Congratulations! You're broke.");
                }
                if(msg.equals("PDouble")){
                    JOptionPane.showMessageDialog(null, "You don't have the funds to double down. Try another input.");
                    msg = null;
                }
            }
        } 
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
