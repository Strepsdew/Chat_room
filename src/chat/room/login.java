package chat.room;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private JPasswordField tfPw = new JPasswordField(15);
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

        tfPw.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String username = tfNimi.getText();
                    Database k = new Database();
                    if (k.checkPassword(username, tfPw.getText())) {
                        int id = k.getIdByUsername(username);
                        ChatRoom mainForm = new ChatRoom();
                        System.out.println(id);
                        try {
                            mainForm.giveCurrentUserId(id);
                        } catch (IOException ex) {
                            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (SQLException ex) {
                            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        mainForm.setVisible(true);
                        setVisible(false);

                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Something went wrong try again!");
                    }

                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }
        });
        Register.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                Register k = new Register();

                setVisible(false);

            }
        });
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = tfNimi.getText();
                String pw = tfPw.getText();
                Database k = new Database();
                if (k.checkPassword(username, pw)) {
                    int id = k.getIdByUsername(username);
                    ChatRoom mainForm = new ChatRoom();
                    System.out.println(id);
                    try {
                        mainForm.giveCurrentUserId(id);
                    } catch (IOException ex) {
                        Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    mainForm.setVisible(true);
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Something went wrong try again!");
                }

            }
        });
    }

    private void asetteleKomponentit() {
        //Aseteeekomponentit    
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
        new login();
    }

    private void fontChange() {
         //Vaihataa fontin 
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
