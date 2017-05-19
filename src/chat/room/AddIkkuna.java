package chat.room;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class AddIkkuna extends JFrame{
    
    private JPanel pohja = new JPanel(new FlowLayout());
    private JTextField tf = new JTextField(20);
    private JButton addbt = new JButton("Add");

    private int currentUserId;
    public AddIkkuna(int id) {
        this.setUndecorated(true);
        this.currentUserId = id;
        this.setSize(240,60);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        asetteleKomponentit();
        addbt.addActionListener(new alsAdd());
    }
    private void asetteleKomponentit() {
        // asettaakomponentit
        pohja.add(tf);
        pohja.add(addbt);
        pohja.setBorder(BorderFactory.createLineBorder(Color.black));
        this.add(pohja);  
    }    
    class alsAdd implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Database d = new Database();
            boolean j = false;
            int kaveri=0;
            if(d.getIdByNickname(tf.getText())==0){
                kaveri = d.getIdByUsername(tf.getText());
            }else{
                kaveri = d.getIdByNickname(tf.getText());
            }
            if(Objects.nonNull(d.haveThisFriend(kaveri,currentUserId))&&Objects.nonNull(d.haveThisFriend(currentUserId,kaveri))){
                if(kaveri != 0) {
                    d.addFriend(currentUserId,kaveri);
                    JOptionPane.showMessageDialog(rootPane, "Successful!");
                    setVisible(false);
                    j = true;
                }   
            }else{
                JOptionPane.showMessageDialog(rootPane,"Something went wrong try again!");
                setVisible(false);
                j = false;
            }
        if(!j){
            JOptionPane.showMessageDialog(rootPane,"Something went wrong try again!");
        }
        setVisible(false);    
        }
    }
    
    public BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }
    public static void main(String[] args) {
    }
}
