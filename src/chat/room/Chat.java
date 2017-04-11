package chat.room;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Chat extends JFrame {

    private JPanel pohja = new JPanel(new BorderLayout());
    private JPanel ylaosa = new JPanel(new BorderLayout());
    private JPanel keskiosa = new JPanel(new BorderLayout());
    private JPanel alaosa = new JPanel(new BorderLayout());

    private JTextArea viesti = new JTextArea();
    private JButton backBtn = new JButton();
    private JButton Frendbtn = new JButton();
    private JButton sendBtn = new JButton();
    private JTextArea chatArea = new JTextArea();

    private int currentUserId;
    Client client = new Client("localhost", 1500, null, this);
    
    private boolean connected;

    public Chat()  {
      
        this.setTitle("Chat with KAVERIN_NIMI"); // tähän lisätään chat with kaverin nimi
        this.setSize(410, 350);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        javax.swing.border.Border b = BorderFactory.createLineBorder(Color.BLACK);
        viesti.setBorder(BorderFactory.createCompoundBorder(b,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
//        viesti.setText("kirjoitat tähän");
        sendBtn.setText("Send");
        backBtn.setText("Back");
        asetteleKomponentit();
        this.setVisible(true);
        chatArea.setEditable(false);
       sendBtn.setBackground(Color.GRAY);
        sendBtn.setForeground(Color.BLACK);
        viesti.setText("kirjoitat tähän");
        chatArea.requestFocus();

        Border roundedBorder = new LineBorder(null, 2, true); // the third parameter - true, says it's round
         viesti.setBorder(roundedBorder); 
         //yritän tehdä kirjoitus areasta pyöreän
         
         
         chatArea.addKeyListener(new KeyListener(){
             @Override
            public void keyTyped(KeyEvent e) {}
            
            @Override
            public void keyPressed(KeyEvent e) {viesti.requestFocus();}
                  
            @Override
            public void keyReleased(KeyEvent e) {}
         });
         
         
        viesti.addKeyListener(new KeyListener() {
            
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                    if (connected) {
                        
                        client.sendMessage(new ChatMessage(ChatMessage.MESSAGE, viesti.getText()));
                        viesti.setText("");
                        
                        return;
                        
                    }
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {}
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
        ylaosa.add(backBtn, BorderLayout.LINE_END);
        pohja.add(ylaosa, BorderLayout.PAGE_START);
        keskiosa.add(new JScrollPane(chatArea));
        pohja.add(keskiosa, BorderLayout.CENTER);
        viesti.setBounds(100, 300, 200, 200);
        alaosa.add(viesti, BorderLayout.CENTER);
        alaosa.add(sendBtn, BorderLayout.LINE_END);
        pohja.add(alaosa, BorderLayout.PAGE_END);
        this.add(pohja);
    }

    public static void main(String[] args) {
        new Chat();

    }

    void append(String str) {
        chatArea.append(str);
        chatArea.setCaretPosition(chatArea.getText().length() - 1);
    }

    public void run() {
        if (!client.start()) {
            return;
        }
        connected = true;

    }
    
    public void sendImage(){
        
        try{
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".jpg","JPG");
        chooser.setFileFilter(filter);
            int status = chooser.showOpenDialog(null);
            if (status == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                if (file == null) {
                    return;
                }

               String  fileName = chooser.getSelectedFile().getAbsolutePath();
               
                   File sourceimage = new File(fileName);
                Image image = ImageIO.read(sourceimage);
                   
              
                JFrame frame = new JFrame();
                 frame.setSize(300, 300);
                JLabel label = new JLabel(new ImageIcon(image));
                frame.add(label);
                 frame.setVisible(true);

            }

            }catch(IOException e){
                    System.out.println(e);
                    }
    }
    

}
