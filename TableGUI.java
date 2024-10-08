import java.awt.*;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.*;

public class TableGUI extends JFrame {

    private JPanel cards;
    private JPanel connectCard;
    private JPanel tableCard;

    private JTextField betAmt;

    private JButton bet;
    private JButton stand;
    private JButton hit;
    private JButton doble;
    private JButton start;
    //private JButton leave;

    private JLabel table;
    static JLabel total  = new JLabel("$250");
    static JLabel p1Bet = new JLabel("");
    static JLabel p2Bet = new JLabel("");
    static JLabel p3Bet = new JLabel("");
    static JLabel p4Bet = new JLabel("");
    static JLabel p5Bet = new JLabel("");
    static JLabel p1tot = new JLabel("");
    static JLabel p2tot = new JLabel("");
    static JLabel p3tot = new JLabel("");
    static JLabel p4tot = new JLabel("");
    static JLabel p5tot = new JLabel("");
    static JLabel dtot = new JLabel("");
    private JLabel dealer;
    private static JLabel star = new JLabel();
    private static JLabel cardpng = new JLabel();

    private ImageIcon tablepng;


    private JTextField name;
    private JLabel ipLabel;
    private JTextField ip;
    private JLabel portLabel;
    private JTextField port;
    private JLabel idLabel;
    private JComboBox<Integer> id;
    private Integer[] playerIDs = {0, 1, 2, 3, 4};
    private JTextArea rules;
    private JButton connect;
    private Socket sock = null;
    private PrintWriter pw = null;
    CardLayout crd;



    public TableGUI() {
        super();

        cards = new JPanel();
        crd = new CardLayout();
        cards.setLayout(crd);
        connectCard = new JPanel();
        tableCard = new JPanel();

        betAmt =  new JTextField("25", 6);
        bet = new JButton("Bet");
        stand = new JButton("Stand");
        hit = new JButton("Hit");
        doble = new JButton("Double Down");
        //leave = new JButton("Leave");
        start = new JButton("Start");
        table = new JLabel();
        dealer = new JLabel("Dealer");
        tablepng = new ImageIcon(new ImageIcon("casino.png").getImage().getScaledInstance(800, 800, Image.SCALE_DEFAULT));
        table.setIcon(tablepng);

        JPanel betpanel = new JPanel(new BorderLayout());
        betpanel.add(betAmt, BorderLayout.WEST);
        betpanel.add(bet, BorderLayout.CENTER);

        JPanel bpanel = new JPanel(new FlowLayout());
        bpanel.add(betpanel);
        bpanel.add(hit);
        bpanel.add(stand);
        bpanel.add(doble);
        bpanel.add(start);
        
        ImageIcon img = new ImageIcon(new ImageIcon("star.png").getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
        star.setIcon(img);
        //star.setBounds(100, 100, 25, 25);
    
        total.setForeground(Color.BLACK);
        total.setFont(new Font("Serif", Font.BOLD, 40));
        total.setBounds(700, 625, 100, 100);

        p1Bet.setForeground(Color.BLACK);
        p1Bet.setFont(new Font("Serif", Font.BOLD, 20));
        p1Bet.setBounds(680, 348, 100, 100);

        p2Bet.setForeground(Color.BLACK);
        p2Bet.setFont(new Font("Serif", Font.BOLD, 20));
        p2Bet.setBounds(543, 429, 100, 100);

        p3Bet.setForeground(Color.BLACK);
        p3Bet.setFont(new Font("Serif", Font.BOLD, 20));
        p3Bet.setBounds(390, 508, 100, 100);

        p4Bet.setForeground(Color.BLACK);
        p4Bet.setFont(new Font("Serif", Font.BOLD, 20));
        p4Bet.setBounds(239, 429, 100, 100);

        p5Bet.setForeground(Color.BLACK);
        p5Bet.setFont(new Font("Serif", Font.BOLD, 20));
        p5Bet.setBounds(100, 350, 100, 100);

        p1tot.setForeground(Color.BLACK);
        p1tot.setFont(new Font("Serif", Font.BOLD, 50));
        p1tot.setBounds(665, 410, 100, 100);

        p2tot.setForeground(Color.BLACK);
        p2tot.setFont(new Font("Serif", Font.BOLD, 50));
        p2tot.setBounds(525, 500, 100, 100);

        p3tot.setForeground(Color.BLACK);
        p3tot.setFont(new Font("Serif", Font.BOLD, 50));
        p3tot.setBounds(375, 580, 100, 100);

        p4tot.setForeground(Color.BLACK);
        p4tot.setFont(new Font("Serif", Font.BOLD, 50));
        p4tot.setBounds(225, 500, 100, 100);

        p5tot.setForeground(Color.BLACK);
        p5tot.setFont(new Font("Serif", Font.BOLD, 50));
        p5tot.setBounds(85, 410, 100, 100);

        dtot.setForeground(Color.BLACK);
        dtot.setFont(new Font("Serif", Font.BOLD, 50));
        dtot.setBounds(400, 100, 100, 100);

        dealer.setForeground(Color.BLACK);
        dealer.setFont(new Font("Serif", Font.BOLD, 50));
        dealer.setBounds(340, 0, 400, 75);
               
        table.add(total);
        table.add(p1Bet);
        table.add(p2Bet);
        table.add(p3Bet);
        table.add(p4Bet);
        table.add(p5Bet);
        table.add(p1tot);
        table.add(p2tot);
        table.add(p3tot);
        table.add(p4tot);
        table.add(p5tot);
        table.add(dtot);
        table.add(star);
        table.add(cardpng);
        table.add(dealer);


        tableCard.add(table, BorderLayout.CENTER);
        tableCard.add(bpanel, BorderLayout.SOUTH);


        name =  new JTextField("", 6);
        ipLabel = new JLabel("IP Address");
        ip =  new JTextField("", 8);
        portLabel = new JLabel("Port");
        port =  new JTextField("", 6);
        idLabel = new JLabel("Player ID");
        id = new JComboBox<Integer>(playerIDs);
        rules = new JTextArea(" Welcome to BlackJack! \n The goal of this game is to draw cards and get as close to 21 without going over it. \n In this game, everyone plays against the dealer and tries to end up closer to 21 than the dealer. \n Minimum bet is $25, and each player will start with a balance of $250 \n Number cards are worth the numbers written on them. Face cards are worth 10. Aces are worth either 1 or 11 depending on your current total. \n Everyone will start with 2 cards, and have 3 options to choose from. \n Hit: Draw another card from the deck \n Stand: End your turn with the cards you have. \n Double Down: Double your bet (Only available at the beginning of your turn). \n Beating the dealer pays 1:1. \n BlackJack(Getting 21 from your first 2 dealt cards) pays 3:2. \n Your position on the table is shown by a star.");
        rules.setEditable(false);
        connect = new JButton("Connect");

        JPanel tPanel = new JPanel(new FlowLayout());
        tPanel.add(ipLabel);
        tPanel.add(ip);
        tPanel.add(portLabel);
        tPanel.add(port);
        tPanel.add(idLabel);
        tPanel.add(id);

        JPanel connectPanel = new JPanel(new BorderLayout());
        connectPanel.add(connect, BorderLayout.EAST);


        connect.addActionListener((e) -> { 
            // Change button text
            connect.setText("Connecting...");
            //id = BlackjackServer.getID();

            

            // Disable inputs
            name.setEnabled(false);
            ip.setEnabled(false);
            port.setEnabled(false);

            // Connect to server
            if(!connectToServer(TableGUI.this)) {
                // Re-enable inputs
                name.setEnabled(true);
                ip.setEnabled(true);
                port.setEnabled(true);


                // Change button text
                connect.setText("Connect");
            }
            else {
                
                TableGUI.this.setTitle("BlackJack (Connected)");
                crd.next(cards);
                connectCard.setVisible(false);
                setSize(800,900);
            }  
        });
        bet.addActionListener((e) -> {
            if (Integer.parseInt(betAmt.getText()) < 25 || Integer.parseInt(betAmt.getText()) > Integer.parseInt(total.getText().substring(1))){
                JOptionPane.showMessageDialog(null, "Invalid bet amount. Please bet within rules.");
            }
            else {
                pw.println("Bet");
                pw.println(betAmt.getText());
                pw.flush();
            }
        });
        hit.addActionListener((e) -> {
            pw.println("Hit");
            pw.flush();
        });
        stand.addActionListener((e) -> {
            pw.println("Stand");
            pw.flush();
        });
        doble.addActionListener((e) -> {
            pw.println("Double Down");
            pw.flush();
        });
        start.addActionListener((e) -> {
            pw.println("Start " + id.getSelectedIndex());
            pw.flush();
        });

        connectCard.add(tPanel, BorderLayout.NORTH);
        connectCard.add(rules, BorderLayout.CENTER);
        connectCard.add(connectPanel, BorderLayout.SOUTH);

        cards.add(connectCard);
        cards.add(tableCard);

        this.add(cards);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
    }

    public void setStar(int x, int y) {
        star.setBounds(x, y, 25, 25);
    }
    public void setTotal(String s) {
        total.setText(s);
    }
    public void setp1Bet(String s) {
        p1Bet.setText(s);
    }
    public void setp2Bet(String s) {
        p2Bet.setText(s);
    }
    public void setp3Bet(String s) {
        p3Bet.setText(s);
    }
    public void setp4Bet(String s) {
        p4Bet.setText(s);
    }
    public void setp5Bet(String s) {
        p5Bet.setText(s);
    }
    public void setp1Tot(String s) {
        p1tot.setText(s);
    }
    public void setp2Tot(String s) {
        p2tot.setText(s);
    }
    public void setp3Tot(String s) {
        p3tot.setText(s);
    }
    public void setp4Tot(String s) {
        p4tot.setText(s);
    }
    public void setp5Tot(String s) {
        p5tot.setText(s);
    }
    public void setdTot(String s) {
        dtot.setText(s);
    }


    public void connectionErrorFrame(JFrame frame, String error) {
		JOptionPane.showMessageDialog(frame, error, "Error", JOptionPane.ERROR_MESSAGE);
	}

	private boolean connectToServer(JFrame frame) {
		// Ensure name, host, and port are given
		if (ip.getText().equals("") || port.getText().equals("") || id.getSelectedItem() == null) {
			connectionErrorFrame(frame, "Please enter a host, port, and Player ID.");
			return false;
		}
      
        try {
           sock = new Socket(ip.getText(),Integer.parseInt(port.getText()));
        }
		catch (Exception e) {
			connectionErrorFrame(frame, "Cannot connect to server");
			return false;
        }

        try {
            pw = new PrintWriter(sock.getOutputStream(), true);
			// Login to server
            pw.println("SECRET");
			pw.println("3c3c4ac618656ae32b7f3431e75f7b26b1a14a87");
			pw.println("NAME");
			pw.println(name.getText());

			Thread t = new TableClientListener(sock, id.getSelectedIndex(), this);
    		t.start();
        }
		catch(Exception e) {
            connectionErrorFrame(frame, "Error logging in to server");
            return false;
        }
		return true;
	}
    public static void isFull() {
        JOptionPane.showMessageDialog(null, "Server is full");
    }
    
    public static void main (String[] argv) {
        TableGUI t = new TableGUI();
        t.setSize(950, 300);
        t.setVisible(true);
    }
}
