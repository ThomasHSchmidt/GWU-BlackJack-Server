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
    TableGUI gui;
    
 
    public TableClientListener(Socket sock, int id, TableGUI gui)
    {
        this.sock = sock;
        this.id = id;
        this.gui = gui;
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
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
    }
        
        
    }
}
