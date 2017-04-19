package chat.room;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Register extends JFrame {

    private JPanel pohja = new JPanel(new GridLayout(9, 1));
    private JPanel username = new JPanel(new FlowLayout(FlowLayout.CENTER));
    private JPanel titlep = new JPanel(new FlowLayout(FlowLayout.CENTER));
    private JPanel pwp = new JPanel(new FlowLayout(FlowLayout.CENTER));
    private JPanel pwp2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
    private JPanel emailp = new JPanel(new FlowLayout(FlowLayout.CENTER));
    private JPanel registerp = new JPanel(new FlowLayout(FlowLayout.CENTER));
    private JPanel nick = new JPanel(new FlowLayout(FlowLayout.CENTER));
    private JPanel firstName = new JPanel(new FlowLayout(FlowLayout.CENTER));
    private JPanel lastName = new JPanel(new FlowLayout(FlowLayout.CENTER));

    private JLabel lbNimi = new JLabel("Username");
    private JLabel lbTitle = new JLabel("Registeration");
    private JLabel lbPassword = new JLabel("Password");
    private JLabel lbPassword2 = new JLabel("Password Again");
    private JLabel lbEmail = new JLabel("      Email      ");
    private JLabel lbNick = new JLabel("Nickname");
    private JLabel lbFirst = new JLabel("First name");
    private JLabel lbLast = new JLabel("Last name");

    private JTextField tfUsername = new JTextField(15);
    private JPasswordField tfPw = new JPasswordField(15);
    private JPasswordField tfPw2 = new JPasswordField(15);
    private JTextField tfEmail = new JTextField(15);
    private JTextField tfNick = new JTextField(15);
    private JTextField tfFirst = new JTextField(15);
    private JTextField tfLast = new JTextField(15);
    private JButton register = new JButton("Register");
    private JButton cancel = new JButton("Cancel");

    public Register() {
        GridLayout gap = (GridLayout) pohja.getLayout();
        gap.setHgap(25);
        gap.setVgap(0);
        FlowLayout gaps = (FlowLayout) titlep.getLayout();
        gaps.setVgap(25);
        this.setTitle("Register");
        lbTitle.setSize(100, 100);
        this.setSize(245, 520);
        this.setLocationRelativeTo(null);
        register.setPreferredSize(new Dimension(100, 30));
        cancel.setPreferredSize(new Dimension(90, 30));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        asetteleKomponentit();
        this.setVisible(true);

        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Database db = new Database();
                String username = tfUsername.getText();
                String pw = tfPw.getText();
                String nickname = tfNick.getText();
                String etunimi = tfFirst.getText();
                String sukunimi = tfLast.getText();
                String email = tfEmail.getText();
                String pw2 = tfPw2.getText();
                if (username.equals("")) {
                    JOptionPane.showMessageDialog(rootPane, "Field username can't be empty.");
                    return;

                } else if (nickname.equals("")) {
                    JOptionPane.showMessageDialog(rootPane, "Field nickname can't be empty");
                    return;
                } else if (etunimi.equals("")) {
                    JOptionPane.showMessageDialog(rootPane, "Fiel firstname can't be empty");
                    return;
                } else if (sukunimi.equals("")) {
                    JOptionPane.showMessageDialog(rootPane, "Field lastname can't be empty");
                    return;
                } else if (email.equals("")) {
                    JOptionPane.showMessageDialog(rootPane, "Field email can't be empty");
                    return;
                } else if (pw.equals("")) {
                    JOptionPane.showMessageDialog(rootPane, "Field password can't be empty");
                    return;
                } else if (pw2.equals("")) {
                    JOptionPane.showMessageDialog(rootPane, "Field password2 can't be empty");
                    return;

                }

                if (pw.equals(pw2) && db.getIdByNickname(nickname) == 0 && db.getIdByUsername(username) == 0) {
                    db.createUser(username, etunimi, sukunimi, nickname, email, pw);
                    JOptionPane.showMessageDialog(rootPane, "Registeration successful!");
                    ChatRoom k = new ChatRoom();
                    try {
                        k.giveCurrentUserId(db.getIdByNickname(nickname));
                    } catch (IOException | SQLException ex) {
                    }
                    k.setVisible(true);
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Something went wrong, try again!");
                }

            }
        });
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new login();
            }
        });
    }

    private void asetteleKomponentit() {
        fontChange();
        lbTitle.setFont(new Font(lbTitle.getName(), Font.PLAIN, 20));
        titlep.add(lbTitle);
        username.add(lbNimi);
        username.add(tfUsername);
        pwp.add(lbPassword);
        pwp.add(tfPw);
        pwp2.add(lbPassword2);
        pwp2.add(tfPw2);
        emailp.add(lbEmail);
        emailp.add(tfEmail);
        registerp.add(cancel);
        registerp.add(register);
        firstName.add(lbFirst);
        firstName.add(tfFirst);
        lastName.add(lbLast);
        lastName.add(tfLast);
        nick.add(lbNick);
        nick.add(tfNick);
        pohja.add(titlep);
        pohja.add(username);
        pohja.add(emailp);
        pohja.add(firstName);
        pohja.add(lastName);
        pohja.add(nick);
        pohja.add(pwp);
        pohja.add(pwp2);
        pohja.add(registerp);
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
