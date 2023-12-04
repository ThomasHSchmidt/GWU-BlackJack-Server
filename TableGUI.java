import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.TabExpander;

public class TableGUI extends JFrame {

    private JTextField name;
    private JTextField betAmt;

    private JButton bet;
    private JButton stand;
    private JButton hit;
    private JButton doble;
    private JButton split;
    private JButton join;
    private JButton start;
    private JButton leave;

    private JLabel table;
    private JLabel total;
    private JLabel p1Bet;
    private JLabel p2Bet;
    private JLabel p3Bet;
    private JLabel p4Bet;
    private JLabel p5Bet;
    private JLabel p1tot;
    private JLabel p2tot;
    private JLabel p3tot;
    private JLabel p4tot;
    private JLabel p5tot;

    private ImageIcon img;

    public TableGUI() {
        super();

        name = new JTextField("Name", 4);
        betAmt =  new JTextField("Bet Amount", 6);
        bet = new JButton("Bet");
        stand = new JButton("Stand");
        hit = new JButton("Hit");
        doble = new JButton("Double Down");
        split = new JButton("Split");
        join = new JButton("Join");
        leave = new JButton("Leave");
        start = new JButton("Start");
        table = new JLabel();
        total = new JLabel("500");
        img = new ImageIcon(new ImageIcon("casino.png").getImage().getScaledInstance(800, 800, Image.SCALE_DEFAULT));
        table.setIcon(img);

        JPanel betpanel = new JPanel(new BorderLayout());
        betpanel.add(betAmt, BorderLayout.WEST);
        betpanel.add(bet, BorderLayout.CENTER);

        JPanel bpanel = new JPanel(new FlowLayout());
        bpanel.add(name);
        bpanel.add(betpanel);
        bpanel.add(hit);
        bpanel.add(stand);
        bpanel.add(doble);
        bpanel.add(split);
        bpanel.add(join);
        bpanel.add(start);

    
        total = new JLabel("$500");
        total.setForeground(Color.BLACK);
        total.setFont(new Font("Serif", Font.BOLD, 40));
        total.setBounds(700, 625, 100, 100);

        p1Bet = new JLabel("$25");
        p1Bet.setForeground(Color.BLACK);
        p1Bet.setFont(new Font("Serif", Font.BOLD, 20));
        p1Bet.setBounds(675, 348, 100, 100);

        p2Bet = new JLabel("$25");
        p2Bet.setForeground(Color.BLACK);
        p2Bet.setFont(new Font("Serif", Font.BOLD, 20));
        p2Bet.setBounds(538, 429, 100, 100);

        p3Bet = new JLabel("$25");
        p3Bet.setForeground(Color.BLACK);
        p3Bet.setFont(new Font("Serif", Font.BOLD, 20));
        p3Bet.setBounds(385, 508, 100, 100);

        p4Bet = new JLabel("$25");
        p4Bet.setForeground(Color.BLACK);
        p4Bet.setFont(new Font("Serif", Font.BOLD, 20));
        p4Bet.setBounds(234, 429, 100, 100);

        p5Bet = new JLabel("$25");
        p5Bet.setForeground(Color.BLACK);
        p5Bet.setFont(new Font("Serif", Font.BOLD, 20));
        p5Bet.setBounds(95, 350, 100, 100);

        p1tot = new JLabel("10");
        p1tot.setForeground(Color.BLACK);
        p1tot.setFont(new Font("Serif", Font.BOLD, 25));
        p1tot.setBounds(630, 390, 100, 100);

        p2tot = new JLabel("10");
        p2tot.setForeground(Color.BLACK);
        p2tot.setFont(new Font("Serif", Font.BOLD, 25));
        p2tot.setBounds(493, 470, 100, 100);

        p3tot = new JLabel("10");
        p3tot.setForeground(Color.BLACK);
        p3tot.setFont(new Font("Serif", Font.BOLD, 25));
        p3tot.setBounds(340, 553, 100, 100);

        p4tot = new JLabel("10");
        p4tot.setForeground(Color.BLACK);
        p4tot.setFont(new Font("Serif", Font.BOLD, 25));
        p4tot.setBounds(190, 470, 100, 100);

        p5tot = new JLabel("10");
        p5tot.setForeground(Color.BLACK);
        p5tot.setFont(new Font("Serif", Font.BOLD, 25));
        p5tot.setBounds(50, 390, 100, 100);
               
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



        this.add(table, BorderLayout.CENTER);
        this.add(bpanel, BorderLayout.SOUTH);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
    }


    public static void main(String args[]) {
        TableGUI f = new TableGUI();
        f.pack();
        f.setVisible(true);
    }
}
