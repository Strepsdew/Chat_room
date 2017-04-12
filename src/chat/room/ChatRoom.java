package chat.room;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class ChatRoom extends JFrame {

    int currentUserId;
    Database db = new Database();
    Kaveri kaverit = null;
    JFrame frame = new JFrame();
    JPanel pohja = new JPanel(new GridLayout(8, 1));
    JPanel rivi1 = new JPanel(new FlowLayout());
    JPanel rivi2 = new JPanel(new GridLayout(1, 2));
    JPanel rivi3 = new JPanel(new FlowLayout());

    JButton searchbt = new JButton("Add");
    JButton addbt = new JButton("Search");

    JLabel lbTitle = new JLabel("Oma profiili");
    JLabel nimi = new JLabel("Oma Nimi", SwingConstants.CENTER);

    public ChatRoom() {
        GridLayout gap = (GridLayout) pohja.getLayout();
        gap.setHgap(25);
        gap.setVgap(0);
        this.setTitle("ChatRoom");
        lbTitle.setSize(100, 100);
        this.setSize(210, 400);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        asetteleKomponentit();
    }

    public void giveCurrentUserId(int id) throws IOException, SQLException {
        this.currentUserId = id;
        kaverit = db.getFriendsByIdInKaveri(currentUserId);
        asetteleKaverit();
        nimi.setText(db.getNicknameById(id));
    }

    private void asetteleKaverit() throws IOException, SQLException {
        for (int i = 0; i < kaverit.getIds().size(); i++) {
            int id = kaverit.getIds().get(i);
            BufferedImage image = db.getBufferedImageById(id);
            JPanel kaveri = new JPanel(new GridLayout(1, 3));
            JLabel nimi = new JLabel(kaverit.getFriendnames().get(i), SwingConstants.CENTER);
            if (Objects.isNull(image)) {
                JPanel pallo = new JPanel() {
                    public void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        g.drawOval(0, 0, 45, 45);
                    }
                };
                kaveri.add(nimi);
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
                        g.drawImage(img, 0, 0, null);
                    }
                };
                kaveri.add(nimi);
                kaveri.add(pallo);
            }
            pohja.add(kaveri);
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

    private void asetteleKomponentit() {
        fontChange();
        rivi2.add(nimi);
        rivi3.add(searchbt);
        rivi3.add(addbt);

        pohja.add(rivi1);
        pohja.add(rivi2);
        pohja.add(rivi3);

        rivi1.add(lbTitle);
        this.add(pohja);
    }

    public static void main(String[] args) {
        ChatRoom cr = new ChatRoom();

    }

    private void fontChange() {
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
