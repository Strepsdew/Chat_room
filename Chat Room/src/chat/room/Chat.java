package chat.room;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

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

    private int currentUserId;
    Client client = new Client("localhost", 1500, null, this);
    
    private boolean connected;

    public Chat() {
       
        this.setTitle("Chat with KAVERIN_NIMI"); // tähän lisätään chat with kaverin nimi
        this.setSize(410, 350);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        javax.swing.border.Border b = BorderFactory.createLineBorder(Color.BLACK);
        viesti.setBorder(BorderFactory.createCompoundBorder(b,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
//        viesti.setText("kirjoitat tähän");
        Sendbtn.setText("Send");
        closebtn.setText("close");
        asetteleKomponentit();
        this.setVisible(true);
        chatarea.setEditable(false);
       Sendbtn.setBackground(Color.GRAY);
        Sendbtn.setForeground(Color.BLACK);
        viesti.requestFocus();

        Border roundedBorder = new LineBorder(null, 2, true); // the third parameter - true, says it's round
         viesti.setBorder(roundedBorder); 
         //yritän tehdä kirjoitus areasta pyöreän
        viesti.addKeyListener(new KeyListener() {
            int jotain;
            

            @Override
            public void keyTyped(KeyEvent e) {
                
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                    if (connected) {
                        if(!viesti.getText().equals("\n")){
                        client.sendMessage(new ChatMessage(ChatMessage.MESSAGE, viesti.getText()));
                        viesti.setText("");
                        viesti.requestFocus();
                        return;
                        }
                    }
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }
            
        });
        
viesti.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(viesti.getText().equals("kirjoitat tähän")){
                    viesti.setText("");
                }
                
            }

            @Override
            public void focusLost(FocusEvent e) {
                
                if(viesti.getText().equals("")){
                viesti.setText("kirjoitat tähän");
                }
            }
        });


    }
    

    public void giveCurrentUserId(int id) {
        currentUserId = id; 
         client.getUsername(currentUserId);
         run();
    }

    private void asetteleKomponentit() {
        ylaosa.add(Frendbtn, BorderLayout.LINE_START);
        ylaosa.add(closebtn, BorderLayout.LINE_END);
        pohja.add(ylaosa, BorderLayout.PAGE_START);
        keskiosa.add(new JScrollPane(chatarea));
        pohja.add(keskiosa, BorderLayout.CENTER);
        viesti.setBounds(100, 300, 200, 200);
        alaosa.add(viesti, BorderLayout.CENTER);
        alaosa.add(Sendbtn, BorderLayout.LINE_END);
        pohja.add(alaosa, BorderLayout.PAGE_END);
        this.add(pohja);
    }

    public static void main(String[] args) {
        new Chat();

    }

    void append(String str) {
        chatarea.append(str);
        chatarea.setCaretPosition(chatarea.getText().length() - 1);
    }

    public void run() {
        if (!client.start()) {
            return;
        }
        connected = true;

    }

}
