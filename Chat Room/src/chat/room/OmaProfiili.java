package chat.room;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class OmaProfiili extends JFrame {

    private JPanel pohja = new JPanel(new GridLayout(3, 1));
    private JPanel First = new JPanel(new FlowLayout(FlowLayout.CENTER));
    private JPanel biop = new JPanel(new FlowLayout(FlowLayout.CENTER));

    private JLabel lbNn = new JLabel("Nickname");
    private JLabel lbpic = new JLabel("Picture");
    private JLabel lbload = new JLabel("Select");

    private JLabel lbBio = new JLabel("Bio");

    private JTextField tfNn = new JTextField(15);
    private JTextArea tfBio = new JTextArea("memems");   
    private JButton save = new JButton("Save");
    private JButton Back = new JButton("Back");
    private JButton Load = new JButton("Load");


    public OmaProfiili() {
        
        Asetukset();        
        this.setTitle("Oma Profiili");
        this.setSize(600, 350);
        this.setLocationRelativeTo(null);
        save.setPreferredSize(new Dimension(80, 30));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        asetteleKomponentit();
        this.setVisible(true);
        
    }
    private void Asetukset(){
        GridLayout gap = (GridLayout) pohja.getLayout();
        gap.setHgap(25);
        gap.setVgap(0);
        FlowLayout gaps = (FlowLayout) biop.getLayout();
        gaps.setVgap(25);
        
        tfBio.setWrapStyleWord(true);
        tfBio.setLineWrap(true);
        tfBio.setWrapStyleWord(true);
        tfBio.setLineWrap(true);
        tfBio.setColumns(20);
        tfBio.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        tfBio.setRows(5);
        tfBio.setSize(200,200);
        this.setVisible(true);

    }

    private void asetteleKomponentit() {
        First.add(lbNn);
        First.add(tfNn);
        First.add(lbpic);
        First.add(lbload);
        First.add(Load);
        biop.add(lbBio);
        biop.add(tfBio);
        pohja.add(First);
        pohja.add(biop);
        this.add(pohja);
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
