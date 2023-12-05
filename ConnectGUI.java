import java.awt.*;
import java.io.PrintWriter;
import java.net.Socket;
import java.awt.event.*;
import javax.swing.*;

public class ConnectGUI extends JFrame {

    private JLabel nameLabel;
    private JTextField name;
    private JLabel ipLabel;
    private JTextField ip;
    private JLabel portLabel;
    private JTextField port;
    private JTextArea rules;
    private JButton connect;
    private Socket sock = null;
    private PrintWriter pw = null;
    private JPanel playerPanel;
	private JLabel playerTitle;
	private JScrollPane playerScoller;
	private JList<String> playerList;

    public ConnectGUI() {
        super();
        nameLabel = new JLabel("Name");
        name =  new JTextField("", 6);
        ipLabel = new JLabel("IP Address");
        ip =  new JTextField("", 8);
        portLabel = new JLabel("Port");
        port =  new JTextField("", 6);
        rules = new JTextArea("Welcome to BlackJack! \n The goal of this game is to draw cards and get as close to 21 without going over it. \n In this game, everyone plays against the dealer and tries to end up closer to 21 than the dealer. \n Minimum bet is $25, and each player will start with a balance of $500 \n Number cards are worth the numbers written on them. Face cards are worth 10. Aces are worth either 1 or 11 depending on your current total. \n Everyone will start with 2 cards, and have 3 options to choose from. \n Hit: Draw another card from the deck \n Stand: End your turn with the cards you have. \n Double Down: Double your bet (Only available at the beginning of your turn). \n Beating the dealer pays 1:1. \n BlackJack(Getting 21 from your first 2 dealt cards) pays 3:2.");
        rules.setEditable(false);
        connect = new JButton("Connect");

        JPanel tPanel = new JPanel(new FlowLayout());
        tPanel.add(nameLabel);
        tPanel.add(name);
        tPanel.add(ipLabel);
        tPanel.add(ip);
        tPanel.add(portLabel);
        tPanel.add(port);

        JPanel bpanel = new JPanel(new BorderLayout());
        bpanel.add(connect, BorderLayout.EAST);

        CoverterActionListener a = new CoverterActionListener();

        connect.addActionListener(a);

        this.add(tPanel, BorderLayout.NORTH);
        this.add(rules, BorderLayout.CENTER);
        this.add(bpanel, BorderLayout.SOUTH);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

    }

    public class CoverterActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            

            connect.addActionListener(new ActionListener(){  
                public void actionPerformed(ActionEvent f) { 
				// Change button text
				connect.setText("Connecting...");

				

				// Disable inputs
				name.setEnabled(false);
				ip.setEnabled(false);
				port.setEnabled(false);

				// Connect to server
				if(!connectToServer(ConnectGUI.this))
				{
					// Re-enable inputs
					name.setEnabled(true);
					ip.setEnabled(true);
					port.setEnabled(true);


					// Change button text
					connect.setText("Connect");
				} else {
                    TableGUI t = new TableGUI();
                    t.setVisible(true);
                    t.setTitle("BlackJack (Connected)");
                    ConnectGUI.this.dispose();
                }
                }  
            });

        }
        public void connectionErrorFrame(JFrame frame, String error)
	{
		JOptionPane.showMessageDialog(frame, error, "Error", JOptionPane.ERROR_MESSAGE);
	}

	private boolean connectToServer(JFrame frame)
	{
		// Ensure name, host, and port are given
		if (name.getText().equals("") || ip.getText().equals("") || port.getText().equals(""))
		{
			connectionErrorFrame(frame, "Please enter a username, host, and port.");
			return false;
		}
      
        try
		{
           sock = new Socket(ip.getText(),Integer.parseInt(port.getText()));
        }
		catch (Exception e)
		{
			connectionErrorFrame(frame, "Cannot connect to server");
			return false;
        }

        try
		{
            pw = new PrintWriter(sock.getOutputStream());
			// Login to server
            pw.println("SECRET");
			pw.println("3c3c4ac618656ae32b7f3431e75f7b26b1a14a87");
			pw.println("NAME");
			pw.println(name.getText());
			pw.flush();

			Thread t = new BlackjackServer(Integer.parseInt(port.getText()));
    		t.start();
        }
		catch(Exception e)
		{
            connectionErrorFrame(frame, "Error logging in to server");
            return false;
        }
		return true;
	}
    }
    public static void main (String[] argv) {
        ConnectGUI c = new ConnectGUI();
        c.setVisible(true);
    }
}
