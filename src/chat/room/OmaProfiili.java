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
        
        //panelit
        JPanel oikeaYla = new JPanel(new GridBagLayout());
        JPanel oikeaAla = new JPanel(new GridBagLayout());
        JPanel vasenYla = new JPanel(new GridBagLayout());
        JPanel vasenAla = new JPanel(new GridBagLayout());
        
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
        this.setSize(300, 320);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        asetteleKomponentit();
        setResizable(true);
        this.setVisible(true);
        this.pack();
        btnSave.setVisible(false);
        txtNimi.setEditable(false);
        txtBio.setEditable(false);
        txtIka.setEditable(false);
        txtLocation.setEditable(false);
        
        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnEdit.setVisible(false);
                btnSave.setVisible(true);
                btnSave.setVisible(true);
                txtNimi.setEditable(true);
                txtBio.setEditable(true);
                txtIka.setEditable(true);
                txtLocation.setEditable(true);
                
            }
        });
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnSave.setVisible(false);
                btnEdit.setVisible(true);
                txtNimi.setEditable(false);
                txtNimi.setText("");
                txtBio.setEditable(false); 
                txtBio.setText("");
                txtIka.setEditable(false);
                txtIka.setText("");
                txtLocation.setEditable(false);
                txtLocation.setText("");
            }
        });
    }
   
    

    
    private void asetteleKomponentit() {
        JPanel panelMain = new JPanel(new GridLayout(2,2));
         getContentPane().add(panelMain);
         Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
       
        
         panelMain.add(vasenYla);
         
         panelMain.add(oikeaYla);
         panelMain.add(vasenAla);
         panelMain.add(oikeaAla);
         
         panelMain.setBorder(padding);
         
         GridBagConstraints gbc = new GridBagConstraints();
         
        gbc.insets = new Insets(2,2,2,2); 
        
         gbc.gridx = 0;
         gbc.gridy = 0;
         gbc.anchor = GridBagConstraints.LINE_END;
         
         //Label:it
         oikeaYla.add(lbNick, gbc);
        gbc.gridy++;
         oikeaYla.add(lbBio,gbc);
         gbc.gridy++;
         oikeaYla.add(lbIka,gbc);
         gbc.gridy++;
         oikeaYla.add(lbLocation,gbc);
         
         gbc.anchor = GridBagConstraints.LINE_START;
         
         //text boxit
         gbc.gridx = 1;
         gbc.gridy = 0;
         
         oikeaYla.add(txtNimi, gbc);
         gbc.gridy++;
         oikeaYla.add(txtBio, gbc);
          gbc.gridy++;
         oikeaYla.add(txtIka, gbc);
         gbc.gridy++;
         oikeaYla.add(txtLocation, gbc);
         
         //buttonit
         
         gbc.gridx = 0;
         gbc.gridy = 0;
         
        gbc.insets = new Insets(7,7,7,7);
         oikeaAla.add(btnSave, gbc);
         
         oikeaAla.add(btnEdit, gbc);
         gbc.gridx++;
         
         oikeaAla.add(btnBack, gbc);
         
         gbc.gridx++;
         
         oikeaAla.add(btnLogout);
         
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
