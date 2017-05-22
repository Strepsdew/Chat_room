package chat.room;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class ChatRoom extends JFrame {

    int currentUserId;
    Database db = new Database();
    Kaveri kaverit = null;
    JPanel pohja = new JPanel(new GridLayout(100, 1));
    JPanel rivi1 = new JPanel(new FlowLayout());
    JPanel rivi2 = new JPanel(new GridLayout(1, 2));
    JPanel rivi3 = new JPanel(new FlowLayout());
    JMenuBar menuBar = new JMenuBar();
    JMenu helpmenu = new JMenu("Help");
    JMenu logoutmenu = new JMenu("Logout");
    JMenu refreshmenu = new JMenu("Refresh");
    JLabel addlb = new JLabel("Add Friend");

    JLabel lbTitle = new JLabel("My Profile");
    JLabel nimi = new JLabel("", SwingConstants.CENTER);

    public ChatRoom() {
        GridLayout gap = (GridLayout) pohja.getLayout();
        gap.setHgap(50);
        gap.setVgap(9);
        this.setTitle("ChatRoom");
        lbTitle.setSize(100, 100);
        this.setSize(210, 400);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        asetteleKomponentit();
        this.setResizable(false);
        addlb.addMouseListener(new addL());
        lbTitle.addMouseListener(new msProfile());
        
        
        refreshmenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                try{
                ChatRoom k = new ChatRoom();
                k.giveCurrentUserId(currentUserId);
                k.setVisible(true);
                }catch(Exception ex) {
                    System.out.println(ex);}
                }
        });
        logoutmenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
                new login();
            }
        });
        helpmenu.addMouseListener(new MouseAdapter() {
            @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        File myFile = new File("Käyttöohjeet.pdf");
                        Desktop.getDesktop().open(myFile);
                    } catch (IOException ex) {}
                }
        });
       
    }

    public void giveCurrentUserId(int id) throws IOException, SQLException {
        this.currentUserId = id;
        kaverit = db.getFriendsByIdInKaveri(currentUserId);
        asetteleKaverit();
        nimi.setText(db.getNicknameById(id));
    }

    private void asetteleKaverit() throws IOException, SQLException {
        // hakee kaverit ja asettelee ne
        if (Objects.isNull(kaverit)) {
            return;
        }
        for (int i = 0; i < kaverit.getIds().size(); i++) {
            int id = kaverit.getIds().get(i);
            BufferedImage image = db.getBufferedImageById(id);
            JPanel kaveri = new JPanel(new GridLayout(1, 3));
            JLabel FriendNickname = new JLabel(kaverit.getFriendnames().get(i), SwingConstants.CENTER);
            if (Objects.isNull(image)) {
                JPanel pallo = new JPanel() {
                    public void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        g.drawOval(0, 0, 45, 45);
                    }
                };
                kaveri.add(FriendNickname);
                kaveri.add(pallo);
            } else {
                BufferedImage img = resize(image, 45, 45);
                JPanel pallo = new JPanel() {
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);

                        Graphics2D g2 = (Graphics2D) g;
                        Ellipse2D ellipse = new Ellipse2D.Double(0, 0, 45, 45);
                        Rectangle2D rect = new Rectangle2D.Double(0, 0, 500, 500);
                        g2.setPaint(new Color(255, 0, 0, 150));
                        g2.setClip(ellipse);
                        g2.clip(rect);
                        g.drawImage(img, 0, 0, null)
;                    }
                };
                kaveri.add(FriendNickname);
                kaveri.add(pallo);
            }
            kaveri.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(e.getButton()==MouseEvent.BUTTON3){
                        RemoveIkkuna remove = new RemoveIkkuna (currentUserId,id);
                        tiedotIkkunaan(remove,kaveri);
                    }else if(e.getButton()==MouseEvent.BUTTON1){
                        Chat p = new Chat(currentUserId,id);
                        p.giveCurrentUserIdAndFriend(currentUserId, id);
                        p.setVisible(true);
                    }
                    
      
                }
            });
            kaveri.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                   
                }
            });
            pohja.add(kaveri);
        }
    }
    private void tiedotIkkunaan(RemoveIkkuna k, JPanel panel){
        // Asettaa removeikkunan näky ville oikeeseen kohtaan
        k.setLocationRelativeTo(panel);
        k.setVisible(true);
    }

    public BufferedImage resize(BufferedImage img, int newW, int newH) {
        // vaihtaa kuvan kokoa
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return dimg;
    }

    private void asetteleKomponentit() {
        //Aseteeekomponentit
        fontChange();
        addlb.setFont(new Font(addlb.getName(), Font.PLAIN, 20));
        lbTitle.setFont(new Font(addlb.getName(), Font.PLAIN, 20));
        nimi.setFont(new Font(addlb.getName(), Font.PLAIN, 20));
        rivi2.add(nimi);
        rivi3.add(addlb);
        helpmenu.setSize(100, 100);
        menuBar.add(helpmenu);
        menuBar.add(logoutmenu);
        menuBar.add(refreshmenu);
        menuBar.setSize(1, 1);
        this.setJMenuBar(menuBar);

        pohja.add(rivi1);
        pohja.add(rivi2);
        pohja.add(rivi3);

        rivi1.add(lbTitle);
        this.add(new JScrollPane(pohja));
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

   
    
    
    class addL implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            AddIkkuna info = new AddIkkuna(currentUserId);
            tiedotIkkunaaa(info);
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

    }

    class msProfile implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            try {
                OmaProfiili k = new OmaProfiili(currentUserId);
                k.setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(ChatRoom.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ChatRoom.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

    }

    private void tiedotIkkunaaa(AddIkkuna k) {
         // Asettaa addikkunan näkyville oikeeseen kohtaan
        k.setLocation(this.getX() + 10, this.getY() + 80);
        //k.setLocationRelativeTo(FriendLabel);
        k.setVisible(true);
    }
}
