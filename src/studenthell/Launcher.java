package studenthell;

import studenthell.model.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class Launcher extends JFrame implements ActionListener
{
    public static enum EDifficulty 
    {
        Jól_megy_a_matek, Nem_az_erősségem_a_matek, Talán_nem_ide_kéne_jelentkezni, Ötször_öt_egyenlő_harminchat;
    }
    JButton buttonOk;
    JTextField textName;
    JLabel labelName;
    JComboBox comboBoxDifficulty;
            
    public Launcher()
    {
        buttonOk = new JButton("Új félév!");
        textName = new JTextField();
        textName.setPreferredSize(new Dimension(100, 20));
        labelName = new JLabel("A Neptun-kódod pedig:");
        comboBoxDifficulty = new JComboBox();
        comboBoxDifficulty.addItem(EDifficulty.Jól_megy_a_matek);
        comboBoxDifficulty.addItem(EDifficulty.Nem_az_erősségem_a_matek);
        comboBoxDifficulty.addItem(EDifficulty.Talán_nem_ide_kéne_jelentkezni);
        comboBoxDifficulty.addItem(EDifficulty.Ötször_öt_egyenlő_harminchat);
        
        buttonOk.addActionListener(this);
        
        this.add(buttonOk); this.add(labelName); this.add(textName); this.add(comboBoxDifficulty); 
        
        this.setLayout(new FlowLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
	setTitle("Beiratkozás");
	setLocation(350, 250);
        setSize(650, 250);
        setVisible(true);
        
    }
   
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource() == buttonOk)
        {
            Game game = new Game("StudentHell", 800, 600, (EDifficulty)comboBoxDifficulty.getSelectedItem(), textName.getText());
            game.start();
        }
          
        
    }
}
