package chat.room;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class OmaProfiili extends JFrame {

        //label
        JLabel lbNick = new JLabel("Nickname:      ");
        JLabel lbBio = new JLabel("Bio:     ");
         JLabel lbIka = new JLabel("Age:     ");
         JLabel lbLocation = new JLabel("Location:     ");
          JLabel lbPic = new JLabel("Picture:     ");
         
        //textfield
        JTextField txtNimi = new JTextField(10);
        JTextArea txtBio = new JTextArea();
        JTextField txtIka = new JTextField(3);
        JTextField txtLocation = new JTextField(10);
        
        //buttonit
        private JButton save = new JButton("Save");
        
    public OmaProfiili() {
        
                
        this.setTitle("Oma Profiili");
        this.setSize(600, 175);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        asetteleKomponentit();
        setResizable(true);
        this.setVisible(true);
        
        
    }
   
    

    
    private void asetteleKomponentit() {
        JPanel panelMain = new JPanel();
        getContentPane().add(panelMain);
        Border padding = BorderFactory.createEmptyBorder(20, 20, 20, 20);
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelMain.add(panelForm);
        
        panelForm.setBorder(padding);
        
        GridBagConstraints gbc = new GridBagConstraints();
        
       gbc.insets = new Insets(2,2,2,2); 
       
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        
        //Label:it
        panelForm.add(lbNick, gbc);
        gbc.gridy++;
        panelForm.add(lbBio,gbc);
        gbc.gridy++;
        panelForm.add(lbIka,gbc);
        gbc.gridy++;
        panelForm.add(lbLocation,gbc);
        
        gbc.anchor = GridBagConstraints.LINE_START;
        
        //text boxit
        gbc.gridx = 1;
        gbc.gridy = 0;
        
        panelForm.add(txtNimi, gbc);
        gbc.gridy++;
        panelForm.add(txtBio, gbc);
         gbc.gridy++;
        panelForm.add(txtIka, gbc);
         gbc.gridy++;
        panelForm.add(txtLocation, gbc);
        
        //buttonit
        
        
        //kuva
      
    }

    public static void main(String[] args) {
        OmaProfiili k = new OmaProfiili();
    }

    private void fontChange(JLabel lbTitle) {
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
