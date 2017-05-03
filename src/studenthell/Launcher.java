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
    
    //Captcha
    Captcha c;
    JLabel captchaLabel;
    JLabel captcha;
    JTextField captchaTextField;
            
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
        
        //this.add(buttonOk); this.add(labelName); this.add(textName); this.add(comboBoxDifficulty);
        //Captcha
        c = new Captcha();
        captchaLabel = new JLabel("Captcha: ");
        captcha = new JLabel(c.getCaptha());
        captchaTextField  = new JTextField();
        captchaTextField.setPreferredSize(new Dimension(100, 20));
        
        JPanel p1 = new JPanel(new FlowLayout());
        p1.add(buttonOk);
        p1.add(labelName);
        p1.add(textName);
        p1.add(comboBoxDifficulty);
        this.add(p1);
        
        JPanel p2 = new JPanel(new FlowLayout());
        p2.add(captchaLabel);
        p2.add(captcha);
        p2.add(captchaTextField);
        this.add(p2);
        
        this.setLayout(new GridLayout(0, 1));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
	setTitle("Beiratkozás");
	setLocation(350, 250);
        setSize(650, 250);
        setVisible(true);
        
    }
   
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource() == buttonOk && c.getCaptha().equals(captchaTextField.getText().toUpperCase()) )
        {
            Game game = new Game("StudentHell", 800, 600, (EDifficulty)comboBoxDifficulty.getSelectedItem(), textName.getText());
            game.start();
        } else {
            System.err.println("Wrong CAPTCHA");
            JFrame wrongCaptcha = new JFrame();
            wrongCaptcha.setDefaultCloseOperation(EXIT_ON_CLOSE);
            wrongCaptcha.setTitle("Rossz captcha!");
            wrongCaptcha.setLocation(525, 300);
            wrongCaptcha.setSize(300, 100);
            wrongCaptcha.setVisible(true);
            wrongCaptcha.setLayout(new FlowLayout());
            
            JLabel wc = new JLabel("Rossz captcha!");
            wc.setSize(new Dimension(100, 20));
            JButton wcb = new JButton("Kilépés");
            wcb.setSize(new Dimension(100, 20));
            wrongCaptcha.add(wc);
            wrongCaptcha.add(wcb);
            
            wcb.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
        }
          
        
    }
}
