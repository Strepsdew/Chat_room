package chat.room;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class RemoveIkkuna extends JFrame{
    
    private JPanel pohja = new JPanel(new FlowLayout());
    private JButton removebt;
    private int currentUserId;
    private int kaveriId;
    
    public RemoveIkkuna(int Id, int Id2) {
        this.setUndecorated(true);
        this.currentUserId = Id;
        this.kaveriId = Id2;
        removebt = new JButton("Remove friend "+new Database().getNicknameById(kaveriId)){{
            setPreferredSize(new Dimension(170,40));
            setForeground(Color.BLACK);
            setBackground(Color.GRAY);       
        }};
        this.setSize(170,40);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        asetteleKomponentit();
        removebt.addActionListener(new alsRemove());
        removebt.addMouseListener(new MouseAdapter() {
            public void mouseExited(MouseEvent e) {
                dispose();
            }
        });
    }
    private void asetteleKomponentit() { // asettaakomponentit
        pohja.add(removebt);
        pohja.setBorder(BorderFactory.createLineBorder(Color.black));
        this.add(pohja);  
    }    
    class alsRemove implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            new Database().removeFriendById(currentUserId, kaveriId);
            setVisible(false);
        }
    }
    public static void main(String[] args) {
        new RemoveIkkuna(2,3).setVisible(true);
        new Database().addFriend(2,3);
    }
}
