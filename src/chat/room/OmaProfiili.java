package chat.room;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

public class OmaProfiili extends JFrame {
        
        //panelit
        JPanel panelMain = new JPanel(new GridLayout(2,2));
        JPanel oikeaYla = new JPanel(new GridBagLayout());
        JPanel oikeaAla = new JPanel(new GridBagLayout());
        JPanel vasenYla = new JPanel(new GridBagLayout());
        JPanel vasenAla = new JPanel(new GridBagLayout());
        
        //label
        JLabel lbNick = new JLabel("Nickname:      ");
        JLabel lbEtu = new JLabel("Etunimi:      ");
        JLabel lbSuku = new JLabel("Sukunimi:      ");
        JLabel lbBio = new JLabel("Bio:     ");
         JLabel lbIka = new JLabel("Age:     ");
         JLabel lbLocation = new JLabel("Location:     ");
          JLabel lbPic = new JLabel("");
         
        //textfield
        JTextField txtNimi = new JTextField(10);
        JTextField txtEtu = new JTextField(10);
        JTextField txtSuku = new JTextField(10);
        JTextField txtBio = new JTextField(10);
        JTextField txtIka = new JTextField(3);
        JTextField txtLocation = new JTextField(10);
        
        //buttonit
        private JButton btnSave = new JButton("Save");
        private JButton btnEdit = new JButton("Edit");
        private JButton btnBack = new JButton("Back");
        private JButton btnLogout = new JButton("Logout");
        private JButton btnPic = new JButton("Set your picture");
        
        private Database k = new Database();
        
        File sourceimage = null;
        
        
    public OmaProfiili() {
        
       Profiili p = k.getEverythingById(2); 
        String[]tiedot = p.toString().split(",");
        if(tiedot[6].contains("null")){
            tiedot[6] = "";
        }
        
        if(tiedot[7].contains("null")){
            tiedot[7] = "";
        }
        txtEtu.setText(tiedot[2]);
         txtSuku.setText(tiedot[3]);
        txtNimi.setText(tiedot[1]);
        txtBio.setText(tiedot[6]);
        txtIka.setText(tiedot[5]);
        txtLocation.setText(tiedot[7]);
        
        
        this.setTitle("Oma Profiili");
        this.setSize(500, 320);
        
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        asetteleKomponentit();
        setResizable(true);
        this.setVisible(true);
        this.pack();
        
        
        btnSave.setVisible(false);
        txtEtu.setEditable(false);
        txtSuku.setEditable(false);
        txtNimi.setEditable(false);
        txtBio.setEditable(false);
        txtIka.setEditable(false);
        txtLocation.setEditable(false);
        
        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnEdit.setVisible(false);
                btnSave.setVisible(true);
                
                txtNimi.setEditable(true);
                txtBio.setEditable(true);
                txtIka.setEditable(true);
                txtSuku.setEditable(true);
                txtEtu.setEditable(true);
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
                txtEtu.setEditable(false);
                txtSuku.setEditable(false);
                
                Profiili p = k.getEverythingById(2); 
                String[]tiedot = p.toString().split(",");
                if(tiedot[6].contains("null")){
                    tiedot[6] = "";
                }

                if(tiedot[7].contains("null")){
                    tiedot[7] = "";
                }
                txtNimi.setText(tiedot[1]);
                txtBio.setText(tiedot[6]);
                txtIka.setText(tiedot[5]);
                txtLocation.setText(tiedot[7]);
                
                k.insertPicture(sourceimage, 4);
                
            }
        });
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] options = {"Yes",
                    "No"};
            int dialogueResult = JOptionPane.showOptionDialog(null,
            "Are you sure?",
            "Logout confirmation",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,     
            options,  
            options[0]);
            
            if(dialogueResult == JOptionPane.YES_OPTION){
                dispose();
                login k = new login();
            }
            }
        });
        
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        
        btnPic.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                try{
                    
                    JFileChooser chooser = new JFileChooser();
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("png","PNG");
                    chooser.setFileFilter(filter);
                    int status = chooser.showOpenDialog(null);
            if (status == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                if (file == null) {
                    return;
                }

               String  fileName = chooser.getSelectedFile().getAbsolutePath();
               
                    sourceimage = new File(fileName);
                Image image = ImageIO.read(sourceimage);
                
                   
              
                image = resize(image,150,95);
                lbPic.setIcon(new ImageIcon(image));
                

            }

            }catch(IOException es){
                    System.out.println(es);
                    }
                
            }
        });
    }
   
    

    
    private void asetteleKomponentit() {
        
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
         oikeaYla.add(lbEtu,gbc);
         gbc.gridy++;
         oikeaYla.add(lbSuku,gbc);
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
         oikeaYla.add(txtEtu,gbc);
         gbc.gridy++;
         oikeaYla.add(txtSuku,gbc);
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
         
         vasenYla.add(lbPic,gbc);
         vasenAla.add(btnPic,gbc);
         
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
    //pöllin pekalta :D
    public BufferedImage resize(Image img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }
}
