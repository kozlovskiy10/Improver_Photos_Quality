package Jrames;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartFrame extends JFrame{
    private final int X_WINDOWS_SIZE = 1300;
    private final int Y_WINDOWS_SIZE = 700;

    private JLabel backgroundImage = new JLabel(new ImageIcon("src/resources/images/startFrameBG.png"));
    private JButton toNextBttn = new JButton(new ImageIcon("src/resources/images/toNextButton.png"));

    public StartFrame(String s) {
        super(s);
        settingFrameOptions();
        paintTheFrameElements();
    }

    protected void settingFrameOptions(){
        setVisible(true);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(X_WINDOWS_SIZE, Y_WINDOWS_SIZE);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    protected void paintTheFrameElements(){
        paintTheBG();
        paintTheToNextButton();
        addElementsOnFrame();
    }

    protected void paintTheBG(){
        backgroundImage.setBounds(0, 0, X_WINDOWS_SIZE, Y_WINDOWS_SIZE);
    }

    protected void paintTheToNextButton(){
        toNextBttn.setBounds(325, 250, 205, 60);
        toNextBttn.setFocusPainted(false);
        toNextBttn.setBorder(null);
        toNextBttn.setContentAreaFilled(false);
        toNextBttn.setRolloverIcon(new ImageIcon("src/resources/images/toNextButtonRolloved.png"));
        toNextBttn.addActionListener(new Listener());
    }

    protected void addElementsOnFrame(){
        add(toNextBttn);
        add(backgroundImage);
    }

    protected class Listener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            try {
                if (event.getSource() == toNextBttn) {
                    new ImageLoaderFrame("Improver Photos Quality");
                    dispose();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "System error");
            }
        }
    }
}