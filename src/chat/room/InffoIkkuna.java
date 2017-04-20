package chat.room;

import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class InffoIkkuna extends JFrame{
    
    private JPanel pohja = new JPanel(new GridLayout(6,1));
    private JTextField etunimi = new JTextField("");
    private JTextField sukunimi = new JTextField("");
    private JTextField nickname = new JTextField("");
    private JTextField username = new JTextField("");
    private JTextField email = new JTextField("");
    private JTextField bio  = new JTextField("");

    private int id;
    public InffoIkkuna(int id) {
        this.setUndecorated(true);
        this.id = id;
        Database k = new Database();
        this.setSize(200,100);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        asetteleKomponentit();
        asetaTiedot();
    }
    private void asetaTiedot() {
        Database k = new Database();
        etunimi.setEditable(false);
        sukunimi.setEditable(false);
        nickname.setEditable(false);
        username.setEditable(false);
        email.setEditable(false);
        bio.setEditable(false);
        Profiili profiili = k.getEverythingById(id);
        etunimi.setText("Etunimi : " + profiili.getEtunimi());
        sukunimi.setText("Sukunimi : "+profiili.getSukunimi());
        nickname.setText("Nickname : "+profiili.getNickname());
        username.setText("Username : "+profiili.getUsername());
        if(profiili.getEmail() != null) {
            email.setText("Email : "+profiili.getEmail());
        }else{
            email.setText("Email :");
        }
        if(profiili.getBio() != null) {
            bio.setText("Bio : "+profiili.getBio());
        }else{
            bio.setText("Bio :");
        }
    }
    private void asetteleKomponentit() {
        pohja.add(etunimi);
        pohja.add(sukunimi);
        pohja.add(nickname);
        pohja.add(username);
        pohja.add(email);
        pohja.add(bio); 
        this.add(pohja);
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

