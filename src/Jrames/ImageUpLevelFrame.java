package Jrames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Vector;

class ImageUpLevelFrame extends JFrame{
    private final int X_WINDOWS_SIZE = 1300;
    private final int Y_WINDOWS_SIZE = 700;

    private JLabel backgroundImage = new JLabel(new ImageIcon("src/resources/images/mainBG.png")); //Фон окна
    private JButton upLevelButton = new JButton(new ImageIcon("src/resources/images/levelUpButton.png"));
    private JButton backButton = new JButton(new ImageIcon("src/resources/images/backButton.png"));

    private String imagePath = new String();
    private JLabel gotImagePath_label;
    private BufferedImage gotImage;
    private JLabel gotImage_label = new JLabel();


    public ImageUpLevelFrame(String s, String imagePath_arg, BufferedImage gettingImage_arg) throws IOException {
        super(s);

        imagePath = imagePath_arg;
        gotImage = gettingImage_arg;

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

    protected void paintTheFrameElements() throws IOException {
        paintTheBG();
        paintTheButtons();
        paintTheGotImagePath();
        paintTheWarning();
        showGotImage();
        addElementsOnFrame();
    }

    protected void paintTheBG(){
        backgroundImage.setBounds(0, 0, X_WINDOWS_SIZE, Y_WINDOWS_SIZE);
    }

    protected void paintTheButtons(){
        paintTheUpLevelBttn();
        paintTheBackBttn();
    }

    private void paintTheUpLevelBttn(){
        upLevelButton.setFont(new Font("Serif", Font.CENTER_BASELINE | Font.BOLD, 15));
        upLevelButton.setFocusPainted(false);
        upLevelButton.setBorder(null);
        upLevelButton.setBounds(150, 400, 250, 60);
        upLevelButton.setContentAreaFilled(false);
        upLevelButton.setRolloverIcon(new ImageIcon("src/resources/images/levelUpButtonRolloved.png"));
        upLevelButton.addActionListener(new Listener());
    }

    private void paintTheBackBttn(){
        backButton.setFont(new Font("Serif", Font.CENTER_BASELINE | Font.BOLD, 15));
        backButton.setFocusPainted(false);
        backButton.setBorder(null);
        backButton.setBounds(150, 480, 250, 60);
        backButton.setContentAreaFilled(false);
        backButton.setRolloverIcon(new ImageIcon("src/resources/images/backButtonRolloved.png"));
        backButton.addActionListener(new Listener());
    }

    private void paintTheGotImagePath(){
        gotImagePath_label = new JLabel(imagePath);
        gotImagePath_label.setBounds(100, 150, 600, 25);
        gotImagePath_label.setFont(new Font("Arial", Font.CENTER_BASELINE | Font.CENTER_BASELINE, 20));
        gotImagePath_label.setForeground(Color.GRAY);
        addOneElementOnFrame(gotImagePath_label);
    }

    private void paintTheWarning(){
        Vector<JLabel> warning = new Vector<>();
        warning.add(new JLabel("При завантаженні зображення"));
        warning.add(new JLabel("можливі зміни масштабу перегляду"));
        warning.add(new JLabel("або розмірів завантажуваного"));
        warning.add(new JLabel("зображення."));

        for(int i = 0,
            x = 100,
            y = 200,
            x_size = 600,
            y_size = 25;
            i <  warning.size();
            i++, y += 35){

            warning.get(i).setBounds(x, y, x_size, y_size);
            warning.get(i).setFont(new Font("Arial", Font.CENTER_BASELINE | Font.CENTER_BASELINE, 19));
            warning.get(i).setForeground(Color.GRAY);
            addOneElementOnFrame(warning.get(i));
        }
    }

    protected void showGotImage() throws IOException {
        int width = 500;
        int height = 600;
        Image image = gotImage.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
        BufferedImage changedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = changedImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        gotImage_label.setBounds(650, 30, 705, 615);
        gotImage_label.setForeground(Color.BLACK);
        gotImage_label.setVisible(true);
        gotImage_label.setIcon(new javax.swing.ImageIcon(changedImage));
        add(gotImage_label);
    }

    private void addOneElementOnFrame(Component element){
        add(element);
    }

    protected void addElementsOnFrame(){
        add(upLevelButton);
        add(backButton);
        add(backgroundImage);
    }

    protected class Listener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            try {
                if (event.getSource() == upLevelButton) {
                    new ResultFrame("Improver Photos Quality", imagePath, gotImage);
                    dispose();
                }
                if (event.getSource() == backButton) {
                    new ImageLoaderFrame("Improver Photos Quality");
                    dispose();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "System error");
            }
        }
    }
}