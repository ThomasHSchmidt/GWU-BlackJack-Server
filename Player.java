import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;

class Player extends Gambler {
    private int bet;
    private TableGUI gui;
    private Socket sock;
    private PrintWriter pw;
    private BufferedReader br;
    private boolean connected;
    private Thread thr;


    public Player(String name, TableGUI gui, String ip, String port) {
        super(name);
        try{
            this.gui = gui;
            sock = new Socket(ip, Integer.parseInt(port));
            pw = new PrintWriter(sock.getOutputStream());
            br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(gui, "Failed connection",  "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private class ReadingThread extends Thread {
        public ReadingThread() {
            super();
        }

        public void run() {
            try {
                boolean isNames = false;
                String names = "";
                String msg;

                while(true) {
                    msg = br.readLine();
                    if(msg == null) {
                        //disconnect();
                        break;
                    }
                    if(msg.equals("START_CLIENT_LIST")) {
                        isNames = true;
                    }
                    else if(msg.equals("END_CLIENT_LIST")) {
                        isNames = false;
                        //gui.updateClients(names);
                        names = "";
                    }
                    else if(isNames) {
                        names += msg + "\n";
                    }
                    else {
                        //gui.newMessage(msg);
                    }
                }
            }
            catch(Exception e) {}
        }
    }

    public boolean isConnected() {
        return connected;
    }

    public synchronized void disconnect() {
        connected = false;

        if(sock != null) {
            try{
                pw.flush();
                sock.close();
            }
            catch(Exception e) {
                JOptionPane.showMessageDialog(gui, "Error",  "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}