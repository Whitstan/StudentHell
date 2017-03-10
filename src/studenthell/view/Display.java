package studenthell.view;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Display {

    private JFrame frame;
    private Canvas canvas;
    private final String title;
    private final int width, height;

    public Display(String title, int width, int height){
        this.title = title;
        this.width = width;
        this.height = height;
        createDisplay();
        addPlayerName();
    }

    private void createDisplay(){
        frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setMaximumSize(new Dimension(width, height));
        canvas.setMinimumSize(new Dimension(width, height));
        canvas.setFocusable(false);

        frame.add(canvas);
        frame.pack();
    }
    
    private void addPlayerName() {
        JFrame playerFrame = new JFrame("Játékos neve");

        JLabel name = new JLabel("Adja meg a nevét");
        JTextField textField = new JTextField(50);
        JButton ok = new JButton("Ok");
        
        playerFrame.add(name, BorderLayout.NORTH);
        playerFrame.add(textField, BorderLayout.CENTER);
        playerFrame.add(ok, BorderLayout.EAST);

        playerFrame.setSize(300, 100);
        playerFrame.setLocationRelativeTo(null);
        playerFrame.setVisible(true);
    }

    public Canvas getCanvas(){
        return canvas;
    }

    public JFrame getFrame(){
        return this.frame;
    }
}