package chat.room;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
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
import javax.swing.JOptionPane;
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
    private JButton closeBtn = new JButton();
    private JLabel FriendLabel = new JLabel();
    private JButton sendBtn = new JButton();
    private JTextArea chatArea = new JTextArea();

    private int currentUserId;
    private int currentFriendId;
    Client client = new Client("localhost", 1500, null, this);

    private boolean connected;


    public Chat(int userid,int friendid) {
        currentUserId = userid;
        currentFriendId = friendid;
        Database k = new Database();
        BufferedImage kuva = null;
        try {
            System.out.println(friendid);
            kuva = k.getBufferedImageById(friendid);
            if(kuva==null){
                File file = new File("Pic.png");
                kuva = ImageIO.read(file);
                
            }
        } catch (Exception ex) {
        }
        kuva = resize(kuva, 45, 45);
        FriendLabel.setIcon(new ImageIcon(kuva));


        this.setTitle("Chat with "+k.getNicknameById(friendid)); // tähän lisätään chat with kaverin nimi
        this.setSize(410, 350);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        javax.swing.border.Border b = BorderFactory.createLineBorder(Color.BLACK);
        viesti.setBorder(BorderFactory.createCompoundBorder(b,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
//        viesti.setText("kirjoitat tähän");
        sendBtn.setText("Send");
        closeBtn.setText("Close");
        closeBtn.setBackground(Color.GRAY);
        closeBtn.setForeground(Color.BLACK);
        asetteleKomponentit();
        chatArea.setEditable(false);
        sendBtn.setBackground(Color.GRAY);
        sendBtn.setForeground(Color.BLACK);
        viesti.setText("Write here");
        chatArea.requestFocus();
        Border roundedBorder = new LineBorder(null, 2, true); // the third parameter - true, says it's round
        viesti.setBorder(roundedBorder);
        //yritän tehdä kirjoitus areasta pyöreän
        FriendLabel.addMouseListener(new MouseListener() {
            InffoIkkuna tama= new InffoIkkuna(currentFriendId); // friend id = 2 vaihda sitte myöhemmin
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                tiedotIkkunaan(tama);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                tama.setVisible(false);
            }
        
        });
        closeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        chatArea.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                viesti.requestFocus();
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        viesti.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

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
            public void keyPressed(KeyEvent e) {
            }
        });

        viesti.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (viesti.getText().equals("kirjoitat tähän")) {
                    viesti.setText("");
                }

            }

            @Override
            public void focusLost(FocusEvent e) {

                if (viesti.getText().equals("")) {
                    viesti.setText("kirjoitat tähän");
                }
            }
        });

    }
    private void tiedotIkkunaan(InffoIkkuna k){
        k.setLocation(this.getX()+10,this.getY()+80);
        //k.setLocationRelativeTo(FriendLabel);
        k.setVisible(true);
    }

    public BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

    public void giveCurrentUserIdAndFriend(int id,int friendid) {
        currentUserId = id;
        currentFriendId = friendid;
        client.getUsername(currentUserId);
        run();
    }

    private void asetteleKomponentit() {
        ylaosa.add(FriendLabel, BorderLayout.LINE_START);
        ylaosa.add(closeBtn, BorderLayout.LINE_END);
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


            String fileName = chooser.getSelectedFile().getAbsolutePath();


            File sourceimage = new File(fileName);
            Image image = ImageIO.read(sourceimage);

            JFrame frame = new JFrame();
            frame.setSize(300, 300);
            JLabel label = new JLabel(new ImageIcon(image));
            frame.add(label);
            frame.setVisible(true);

        }

            }

            catch(IOException e){
                    System.out.println(e);
                    }

    }

}
