package Jrames;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

class ResultFrame extends JFrame{
    private final int X_WINDOWS_SIZE = 1300;
    private final int Y_WINDOWS_SIZE = 700;

    private JLabel backgroundImage = new JLabel(new ImageIcon("src/resources/images/mainBg.png")); //Фон окна
    private JButton upLevelButton = new JButton(new ImageIcon("src/resources/images/levelUpButton.png"));
    private JButton saveAsButton = new JButton(new ImageIcon("src/resources/images/saveAsButton.png"));
    private JButton backButton = new JButton(new ImageIcon("src/resources/images/backButton.png"));

    private String imagePath = new String();
    private BufferedImage gotImage;
    private BufferedImage resultImage;
    private JLabel gotImage_label = new JLabel();
    private JLabel resultImage_label = new JLabel();
    private JLabel ImagePath_label;


    public ResultFrame(String s, String imagePath_arg, BufferedImage gettingImage_arg) throws IOException {
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
        setLocationJFileChooser();
        paintTheBG();
        paintTheButtons();
        paintTheGotImagePath();
        paintTheWarning();

        generateGotImage();
        paintGotImage();

        generateResultImage();
        paintTheResultImage();

        generateResultImageFromGotImageForSaving();

        addTheFrameElements();
    }

    protected void setLocationJFileChooser(){
        UIManager.put(
                "FileChooser.saveButtonText", "Зберегти");
        UIManager.put(
                "FileChooser.cancelButtonText", "Відміна");
        UIManager.put(
                "FileChooser.fileNameLabelText", "Ім`я файлу");
        UIManager.put(
                "FileChooser.filesOfTypeLabelText", "Типи файлів");
        UIManager.put(
                "FileChooser.lookInLabelText", "Директорія");
        UIManager.put(
                "FileChooser.saveInLabelText", "Зберегти в директорії");
        UIManager.put(
                "FileChooser.folderNameLabelText", "Шлях");
    }

    protected void paintTheBG(){
        backgroundImage.setBounds(0, 0, X_WINDOWS_SIZE, Y_WINDOWS_SIZE);
    }

    protected void paintTheButtons(){
        paintTheSaveButton();
        paintTheBackButton();
    }

    private void paintTheSaveButton(){
        saveAsButton.setFont(new Font("Serif", Font.CENTER_BASELINE | Font.BOLD, 15));
        saveAsButton.setFocusPainted(false);
        saveAsButton.setBorder(null);
        saveAsButton.setBounds(120, 350, 250, 60);
        saveAsButton.setContentAreaFilled(false);
        saveAsButton.setRolloverIcon(new ImageIcon("src/resources/images/saveAsButtonRolloved.png"));
        saveAsButton.addActionListener(new Listener());
    }

    private void paintTheBackButton(){
        backButton.setFont(new Font("Serif", Font.CENTER_BASELINE | Font.BOLD, 15));
        backButton.setFocusPainted(false);
        backButton.setBorder(null);
        backButton.setBounds(120, 450, 250, 60);
        backButton.setContentAreaFilled(false);
        backButton.setRolloverIcon(new ImageIcon("src/resources/images/backButtonRolloved.png"));
        backButton.addActionListener(new Listener());
    }

    private void paintTheGotImagePath(){
        ImagePath_label = new JLabel(imagePath);
        ImagePath_label.setBounds(65, 150, 400, 25);
        ImagePath_label.setFont(new Font("Arial", Font.CENTER_BASELINE | Font.CENTER_BASELINE, 20));
        ImagePath_label.setForeground(Color.GRAY);
    }

    private void paintTheWarning(){
        Vector<JLabel> warning = new Vector<>();
        warning.add(new JLabel("Зображення буде збережено тільки "));
        warning.add(new JLabel("зі змінами у графіці! "));
        warning.add(new JLabel("Розміри будуть як і в оригіналі."));

        for(int i = 0,
            x = 65,
            y = 215,
            x_size = 350,
            y_size = 25;
            i <  warning.size();
            i++, y += 35){

            warning.get(i).setBounds(x, y, x_size, y_size);
            warning.get(i).setFont(new Font("Arial", Font.CENTER_BASELINE | Font.CENTER_BASELINE, 19));
            warning.get(i).setForeground(Color.GRAY);
            addOneElementOnFrame(warning.get(i));
        }
    }

    private void generateGotImage() throws IOException {
        int height = 600;
        int width = 500;
        Image image = gotImage.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
        BufferedImage changedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = changedImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        gotImage = changedImage;
    }

    private void paintGotImage(){
        gotImage_label.setBounds(470, 30, 705, 615);
        gotImage_label.setForeground(Color.BLACK);
        gotImage_label.setVisible(true);
        gotImage_label.setIcon(new javax.swing.ImageIcon(gotImage));
    }

    private void generateResultImage() throws IOException {
        int height = 600;
        int width = 500;

        Image image = gotImage.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
        BufferedImage changedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = changedImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        Kernel sharpKernel = new Kernel(3, 3, new float[] { 0.0f, -1.0f, 0.0f,
                -1.0f, 5.0f, -1.0f, 0.0f, -1.0f, 0.0f });

        ConvolveOp convolveOp = new ConvolveOp(sharpKernel, ConvolveOp.EDGE_NO_OP, null);
        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        convolveOp.filter(changedImage, output);

        resultImage = output;

        paintTheResultImage();
    }

    private void paintTheResultImage(){
        resultImage_label.setBounds(770, 30, 705, 615);
        resultImage_label.setForeground(Color.BLACK);
        resultImage_label.setVisible(true);
        resultImage_label.setIcon(new javax.swing.ImageIcon(resultImage));
    }
    
    void generateResultImageFromGotImageForSaving() {
        Image image = resultImage.getScaledInstance(ImageLoaderFrame.gettingOriginalImage.getWidth(), ImageLoaderFrame.gettingOriginalImage.getHeight(), Image.SCALE_AREA_AVERAGING);
        BufferedImage changedImage = new BufferedImage(ImageLoaderFrame.gettingOriginalImage.getWidth(), ImageLoaderFrame.gettingOriginalImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = changedImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        Kernel sharpKernel = new Kernel(3, 3, new float[] { 0.0f, -1.0f, 0.0f,
                -1.0f, 5.0f, -1.0f, 0.0f, -1.0f, 0.0f });
        ConvolveOp convolveOp = new ConvolveOp(sharpKernel, ConvolveOp.EDGE_NO_OP, null);

        BufferedImage output = new BufferedImage(ImageLoaderFrame.gettingOriginalImage.getWidth(), ImageLoaderFrame.gettingOriginalImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        convolveOp.filter(ImageLoaderFrame.gettingOriginalImage, output);
        ImageLoaderFrame.gettingOriginalImage = output;
    }

    private void addOneElementOnFrame(Component element){
        add(element);
    }

    protected void addTheFrameElements() {
        add(upLevelButton);
        add(backButton);
        add(saveAsButton);
        add(ImagePath_label);
        add(resultImage_label);
        add(gotImage_label);
        add(backgroundImage);
    }

    protected class Listener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            try {
                if (event.getSource() == backButton) {
                    new ImageLoaderFrame("Improver Photos Quality");
                    dispose();
                }
                if (event.getSource() == saveAsButton) {
                    dialogSaveAsChooser();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "System error");
            }
        }
    }

    private void dialogSaveAsChooser() throws IOException {
        JFileChooser dialog = new  JFileChooser("D://");

        dialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
        dialog.setDialogType(JFileChooser.SAVE_DIALOG);
        dialog.setApproveButtonText("Зберегти");//выбрать название для кнопки согласия
        dialog.setDialogTitle("Збереження файлу");// выбрать название
        dialog.setMultiSelectionEnabled(false);

        // Определение режима - только файл
        int result = dialog.showSaveDialog(this);
        // Если файл выбран, то представим его в сообщении
        if (result == JFileChooser.APPROVE_OPTION ) {
            ImageIO.write(ImageLoaderFrame.gettingOriginalImage, "png", new File("D:\\" + dialog.getName(dialog.getSelectedFile()) + ".png"));
            JOptionPane.showMessageDialog(this, "Файл '" + dialog.getSelectedFile() + ".png" + "  збережено");
        }
    }
}