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
        
        
    }
}
