package chat.room;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
public class ChatRoom extends JFrame{
  
    
    JFrame frame = new JFrame();
    JPanel pohja = new JPanel(new GridLayout(8,1));
    JPanel kaveri1 = new JPanel(new GridLayout(1,3));
    JPanel kaveri2 = new JPanel(new GridLayout(1,3));
    JPanel kaveri3 = new JPanel(new GridLayout(1,3));
    JPanel kaveri4 = new JPanel(new GridLayout(1,3));
    JPanel kaveri5 = new JPanel(new GridLayout(1,3));
    JPanel rivi1 = new JPanel(new FlowLayout());
    JPanel rivi2 = new JPanel(new GridLayout(1,2));
    JPanel rivi3 = new JPanel(new FlowLayout());
    
    JButton searchbt = new JButton("Add");
    JButton addbt = new JButton("Search");
    JButton chat1 = new JButton("Chat");
    JButton chat2 = new JButton("Chat");
    JButton chat3 = new JButton("Chat");
    JButton chat4 = new JButton("Chat");
    JButton chat5 = new JButton("Chat");
    
   
    
    JPanel circle1 = new JPanel() {
    public void paintComponent(Graphics g) {
        g.drawOval(10, 0, 49, 49);
    }
};
    JPanel circle2 = new JPanel() {
    public void paintComponent(Graphics g) {
        g.drawOval(10, 0, 49, 49);
    }
};
    JPanel circle3 = new JPanel() {
    public void paintComponent(Graphics g) {
        g.drawOval(10, 0, 49, 49);
    }
};
    JPanel circle4 = new JPanel() {
    public void paintComponent(Graphics g) {
        g.drawOval(10, 0, 49, 49);
    }
};
    JPanel circle5 = new JPanel() {
    public void paintComponent(Graphics g) {
        g.drawOval(10, 0, 49, 49);
    }
};
    

    JLabel lbTitle = new JLabel("Oma profiili");
    JLabel nimi = new JLabel("Oma Nimi", SwingConstants.CENTER);
    JLabel tyhjaa = new JLabel("");
    JLabel nimi1 = new JLabel("Kaveri1", SwingConstants.CENTER);
    JLabel nimi2 = new JLabel("Kaveri2", SwingConstants.CENTER);
    JLabel nimi3 = new JLabel("Kaveri3", SwingConstants.CENTER);
    JLabel nimi4 = new JLabel("Kaveri4", SwingConstants.CENTER);
    JLabel nimi5 = new JLabel("Kaveri5", SwingConstants.CENTER);
    JLabel nimi6 = new JLabel("Kaveri6", SwingConstants.CENTER);
    
    public ChatRoom(){
        GridLayout gap = (GridLayout) pohja.getLayout();
        gap.setHgap(25);
        gap.setVgap(0);
        this.setTitle("ChatRoom");
        lbTitle.setSize(100, 100);
        this.setSize(210, 400);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        asetteleKomponentit();
        this.setVisible(true);
        
        
    }
    private void asetteleKomponentit() {
        fontChange();
        rivi2.add(nimi);
        rivi3.add(searchbt);
        rivi3.add(addbt);
        
        
        kaveri1.add(nimi1);
        kaveri1.add(circle1);
        
        
        kaveri2.add(nimi2);
        kaveri2.add(circle2);
        
        kaveri3.add(nimi3);
        kaveri3.add(circle3);
        
        kaveri4.add(nimi4);
        kaveri4.add(circle4);
                
        kaveri5.add(nimi5);
        kaveri5.add(circle5);
        
        pohja.add(rivi1);
        pohja.add(rivi2);
        pohja.add(rivi3);
        pohja.add(kaveri1);
        pohja.add(kaveri2);
        pohja.add(kaveri3);
        pohja.add(kaveri4);
        pohja.add(kaveri5);
        
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
