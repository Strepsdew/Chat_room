package chat.room;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class login extends JFrame {

    private JPanel pohja = new JPanel(new GridLayout(4, 1));
    private JPanel tunnup = new JPanel(new FlowLayout(FlowLayout.CENTER));
    private JPanel titlep = new JPanel(new FlowLayout(FlowLayout.CENTER));
    private JPanel pwp = new JPanel(new FlowLayout(FlowLayout.CENTER));
    private JPanel loginp = new JPanel(new FlowLayout(FlowLayout.CENTER));

    private JLabel lbNimi = new JLabel("Username");
    private JLabel lbTitle = new JLabel("Chat_Room");
    private JLabel lbPassword = new JLabel("Password");
    private JLabel Register = new JLabel("                        Register                        ");

    private JTextField tfNimi = new JTextField(15);
    private JTextField tfPw = new JTextField(15);   
    private JButton login = new JButton("Login");


    public login() {
        GridLayout gap = (GridLayout) pohja.getLayout();
        gap.setHgap(25);
        gap.setVgap(0);
        FlowLayout gaps = (FlowLayout) titlep.getLayout();
        gaps.setVgap(25);
        this.setTitle("Login form");
        lbTitle.setSize(100, 100);
        this.setSize(250, 320);
        this.setLocationRelativeTo(null);
        login.setPreferredSize(new Dimension(80, 30));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        asetteleKomponentit();
        this.setVisible(true);
        
        Register.addMouseListener(new MouseAdapter()  
                {  
        public void mouseClicked(MouseEvent e)  
         {  
             Register k = new Register();
        }  
    });
    }
    

    
    private void asetteleKomponentit() {
        fontChange();
        titlep.add(lbTitle);
        tunnup.add(lbNimi);
        tunnup.add(tfNimi);
        pwp.add(lbPassword);
        pwp.add(tfPw);
        loginp.add(login);
        loginp.add(Register);
        pohja.add(titlep);
        pohja.add(tunnup);
        pohja.add(pwp);
        pohja.add(loginp);
        this.add(pohja);
    }

    public static void main(String[] args) {
    }

    private void fontChange() {
        Font labelFont = lbTitle.getFont();
        String labelText = lbTitle.getText();

        int stringWidth = lbTitle.getFontMetrics(labelFont).stringWidth(labelText);
        int componentWidth = lbTitle.getWidth();

    // Find out how much the font can grow in width.
        double widthRatio = (double) componentWidth / (double) stringWidth;

        int newFontSize = (int) (labelFont.getSize() * widthRatio);
        int componentHeight = lbTitle.getHeight();

    // Pick a new font size so it will not be larger than the height of label.
        int fontSizeToUse = Math.min(newFontSize, componentHeight);

    // Set the label's font size to the newly determined size.
        lbTitle.setFont(new Font(labelFont.getName(), Font.PLAIN, fontSizeToUse));
    }
}
