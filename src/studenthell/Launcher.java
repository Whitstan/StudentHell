package studenthell;

import studenthell.model.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class Launcher extends JFrame implements ActionListener
{
    public static enum EDifficulty 
    {
        Easy, Medium, Hard, ExtremeSuicideHell;
    }
    JButton buttonOk;
    JTextField textName;
    JLabel labelName;
    JComboBox comboBoxDifficulty;
            
    public Launcher()
    {
        buttonOk = new JButton("Ok");
        textName = new JTextField();
        textName.setPreferredSize(new Dimension(100, 20));
        labelName = new JLabel("Name:");
        comboBoxDifficulty = new JComboBox();
        comboBoxDifficulty.addItem(EDifficulty.Easy);
        comboBoxDifficulty.addItem(EDifficulty.Medium);
        comboBoxDifficulty.addItem(EDifficulty.Hard);
        comboBoxDifficulty.addItem(EDifficulty.ExtremeSuicideHell);
        
        buttonOk.addActionListener(this);
        
        this.add(buttonOk); this.add(labelName); this.add(textName); this.add(comboBoxDifficulty); 
        
        this.setLayout(new FlowLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
	setTitle("RUN BITCH");
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
