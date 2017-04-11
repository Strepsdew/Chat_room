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
        JTextField txtBio = new JTextField(10);
        JTextField txtIka = new JTextField(3);
        JTextField txtLocation = new JTextField(10);
        
        //buttonit
        private JButton btnSave = new JButton("Save");
        private JButton btnEdit = new JButton("Edit");
        private JButton btnBack = new JButton("Back");
        private JButton btnLogout = new JButton("Logout");
        
    public OmaProfiili() {
        
                
        this.setTitle("Oma Profiili");
        this.setSize(600, 320);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        asetteleKomponentit();
        setResizable(true);
        this.setVisible(true);
        
        
    }
   
    

    
    private void asetteleKomponentit() {
        JPanel panelMain = new JPanel(new GridLayout(2,1));
         getContentPane().add(panelMain);
         Border padding = BorderFactory.createEmptyBorder(0, 0, 0, 0);
        JPanel panelForm = new JPanel(new GridBagLayout());
        JPanel ala = new JPanel(new GridBagLayout());
        
         panelMain.add(panelForm);
         panelMain.add(ala);
         
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
         
         gbc.gridx = 0;
         gbc.gridy = 0;
         
        gbc.insets = new Insets(7,7,7,7);
         ala.add(btnEdit, gbc);
         gbc.gridx++;
         ala.add(btnSave, gbc);
         gbc.gridx++;
         ala.add(btnBack, gbc);
         
         gbc.gridx++;
         ala.add(btnLogout);
         
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
