import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;

class Player extends Gambler {
    public static final int MIN_BET = 25;
    public static final int STARTING_MONEY = 200;
    private int bet;
    private int cash;
    private TableGUI gui;
    private Socket sock;
    private PrintWriter pw;
    private BufferedReader br;
    private boolean connected;
    private Thread thr;

    public Player(String name, TableGUI gui, Deck deck, String ip, String port) {
        super(name, deck);
        try{
            // Initialize fields
            bet = MIN_BET;
            cash = STARTING_MONEY;
            this.gui = gui;
            sock = new Socket(ip, Integer.parseInt(port));
            pw = new PrintWriter(sock.getOutputStream());
            br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            thr = new ReadingThread();

            // Send initial handshake and name
            pw.println("SECRET");
            pw.println("3c3c4ac618656ae32b7f3431e75f7b26b1a14a87");
            pw.println("NAME");
            pw.println(name);
            pw.flush();

            // Start client thread
            thr.start();
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
    
    public int getBet() {
        return this.bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public int getCash() {
        return this.cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public int doubleDown() {
        this.setBet(this.getBet()*2);;
        return this.hit();
    }
}