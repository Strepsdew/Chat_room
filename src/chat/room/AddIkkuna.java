package chat.room;

import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class AddIkkuna extends JFrame{
    
    private JPanel pohja = new JPanel(new FlowLayout());
    private JTextField tf = new JTextField(20);
    private JButton addbt = new JButton("Add");

    private int id;
    public AddIkkuna(int id) {
        this.setUndecorated(true);
        this.id = id;
        Database k = new Database();
        this.setSize(240,60);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        asetteleKomponentit();
        asetaTiedot();
        addbt.addActionListener(new alsAdd());
    }
    private void asetaTiedot() {
        Database k = new Database();
        
        Profiili profiili = k.getEverythingById(id);
        
    }
    private void asetteleKomponentit() {
        pohja.add(tf);
        pohja.add(addbt);
        this.add(pohja);  
        
      
       }
    
    class alsAdd implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
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
        new InffoIkkuna(2).setVisible(true);
    }
}

