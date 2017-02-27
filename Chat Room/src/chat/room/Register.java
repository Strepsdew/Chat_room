package chat.room;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Register extends JFrame {

    private JPanel pohja = new JPanel(new GridLayout(5, 1));
    private JPanel tunnup = new JPanel(new FlowLayout(FlowLayout.CENTER));
    private JPanel titlep = new JPanel(new FlowLayout(FlowLayout.CENTER));
    private JPanel pwp = new JPanel(new FlowLayout(FlowLayout.CENTER));
    private JPanel pwp2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
    private JPanel loginp = new JPanel(new FlowLayout(FlowLayout.CENTER));

    private JLabel lbNimi = new JLabel("Username");
    private JLabel lbTitle = new JLabel("Registeration");
    private JLabel lbPassword = new JLabel("Password");
    private JLabel lbPassword2 = new JLabel("Password Again");

    
    private JTextField tfNimi = new JTextField(15);
    private JTextField tfPw = new JTextField(15); 
    private JTextField tfPw2 = new JTextField(15);   
    private JButton create = new JButton("Create user");


    public Register() {
        GridLayout gap = (GridLayout) pohja.getLayout();
        gap.setHgap(25);
        gap.setVgap(0);
        FlowLayout gaps = (FlowLayout) titlep.getLayout();
        gaps.setVgap(25);
        this.setTitle("Register");
        lbTitle.setSize(100, 100);
        this.setSize(250, 320);
        this.setLocationRelativeTo(null);
        create.setPreferredSize(new Dimension(120, 30));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        asetteleKomponentit();
        this.setVisible(true);
        
        //create.addActionListener(new AlsCreate() {
        //    public void actionPerformed(ActionEvent e) {
        //        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        //    }
        //});
    }

    private void asetteleKomponentit() {
        fontChange();
        titlep.add(lbTitle);
        tunnup.add(lbNimi);
        tunnup.add(tfNimi);
        pwp.add(lbPassword);
        pwp.add(tfPw);
        pwp2.add(lbPassword2);
        pwp2.add(tfPw2);
        loginp.add(create);
        pohja.add(titlep);
        pohja.add(tunnup);
        pohja.add(pwp);
        pohja.add(pwp2);
        pohja.add(loginp);
        this.add(pohja);
    }

    public static void main(String[] args) {
        Register k = new Register();
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
