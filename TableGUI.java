import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.TabExpander;

public class TableGUI extends JFrame {

    private JTextField betAmt;

    private JButton bet;
    private JButton stand;
    private JButton hit;
    private JButton doble;
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
    private int p1x;
    private int p1y;
    private int p2x;
    private int p2y;
    private int p3x;
    private int p3y;
    private int p4x;
    private int p4y;
    private int p5x;
    private int p5y;

    private ImageIcon img;

    public TableGUI() {
        super();
        betAmt =  new JTextField("Bet Amount", 6);
        bet = new JButton("Bet");
        stand = new JButton("Stand");
        hit = new JButton("Hit");
        doble = new JButton("Double Down");
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
        bpanel.add(betpanel);
        bpanel.add(hit);
        bpanel.add(stand);
        bpanel.add(doble);
        bpanel.add(start);
        
        ImageIcon img2 = new ImageIcon(new ImageIcon("star.png").getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
        JLabel img22 = new JLabel();
        img22.setIcon(img2);
        img22.setBounds(200, 300, 25, 25);
    
        total = new JLabel("$250");
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
        table.add(img22);



        this.add(table, BorderLayout.CENTER);
        this.add(bpanel, BorderLayout.SOUTH);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
    }

    // public static void main (String[] argv) {
    //     TableGUI t = new TableGUI();
    //     t.setVisible(true);
    // }


}
