package chat.room;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javafx.scene.layout.Border;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Chat extends JFrame {
    private JPanel pohja = new JPanel(new BorderLayout());
    private JPanel ylaosa = new JPanel(new BorderLayout());
    private JPanel keskiosa = new JPanel(new BorderLayout());
    private JPanel alaosa = new JPanel(new BorderLayout());
 
    private JTextArea viesti = new JTextArea();
    private JButton closebtn = new JButton();   
    private JButton Frendbtn = new JButton();
    private JButton Sendbtn = new JButton();
    private JTextArea chatarea = new JTextArea();
    
    
    public Chat() {
        this.setTitle("Chat with KAVERIN_NIMI"); // tähän lisätään chat with kaverin nimi
        this.setSize(410, 350);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        javax.swing.border.Border b = BorderFactory.createLineBorder(Color.BLACK);
        viesti.setBorder(BorderFactory.createCompoundBorder(b, 
        BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        viesti.setText("kirjoitat tähän");
        Sendbtn.setText("Send");
        closebtn.setText("close");
        asetteleKomponentit();
        this.setVisible(true);
        chatarea.setEditable(false);
        
        viesti.addKeyListener(new KeyListener() {
            int jotain;
            @Override
            public void keyTyped(KeyEvent e) {
                if (jotain == 0) {
                viesti.setText("");
                jotain++;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    System.out.println("SEND SOMETHING");
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }
            });
            }
    
    private void asetteleKomponentit() {
        ylaosa.add(Frendbtn, BorderLayout.LINE_START);
        ylaosa.add(closebtn, BorderLayout.LINE_END);
        pohja.add(ylaosa, BorderLayout.PAGE_START);
        keskiosa.add(chatarea);
        pohja.add(keskiosa, BorderLayout.CENTER);
        viesti.setBounds(100,300, 200,200);  
        alaosa.add(viesti, BorderLayout.CENTER);
        alaosa.add(Sendbtn, BorderLayout.LINE_END);
        pohja.add(alaosa, BorderLayout.PAGE_END);
        this.add(pohja);
    }

    public static void main(String[] args) {
       new Chat();
    }
}
