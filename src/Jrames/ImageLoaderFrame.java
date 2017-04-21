package Jrames;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class ImageLoaderFrame extends JFrame{
    private final int X_WINDOWS_SIZE = 1300;
    private final int Y_WINDOWS_SIZE = 700;

    private JLabel backgroundImage = new JLabel(new ImageIcon("src/resources/images/ImageLoaderBG.png"));
    private JButton upLoadButton = new JButton(new ImageIcon("src/resources/images/upLoadImageButton.png"));

    private BufferedImage gettingImage;
    public static BufferedImage gettingOriginalImage;
    private String imagePath;

    public ImageLoaderFrame(String s) {
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
        setLocationJFileChooser();
        paintTheBG();
        paintTheUpLoadButton();
        addElementsInFrame();
    }

    protected void setLocationJFileChooser(){
        UIManager.put(
                "FileChooser.saveButtonText", "Відкрити");
        UIManager.put(
                "FileChooser.cancelButtonText", "Відмінити");
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

    protected void paintTheUpLoadButton(){
        upLoadButton.setFont(new Font("Serif", Font.CENTER_BASELINE | Font.BOLD, 15));
        upLoadButton.setFocusPainted(false);
        upLoadButton.setBorder(null);
        upLoadButton.setRolloverIcon(new ImageIcon("src/resources/images/upLoadImageButtonRolloved.png"));
        upLoadButton.setBounds(550, 450, 215, 60);
        upLoadButton.setContentAreaFilled(false);
        upLoadButton.addActionListener(new Listener());
    }

    protected void addElementsInFrame(){
        add(upLoadButton);
        add(backgroundImage);
    }

    protected boolean dialogFileChooser() throws IOException {
        JFileChooser dialog = new  JFileChooser("D://");

        dialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
        dialog.setApproveButtonText("Вибрати");//выбрать название для кнопки согласия
        dialog.setDialogTitle("Виберіть зображення для обробки");// выбрать название
        dialog.setDialogType(JFileChooser.OPEN_DIALOG);
        dialog.setMultiSelectionEnabled(false);

        int result = dialog.showSaveDialog(this);

        // Если файл выбран, то представим его в сообщении
        if (result == dialog.APPROVE_OPTION ) {
            imagePath = dialog.getSelectedFile().getAbsolutePath();
            File file = new File(imagePath);
            gettingImage = ImageIO.read(file);
            gettingOriginalImage = ImageIO.read(file);
            return true;
        }else{
            return false;
        }
    }

    protected class Listener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            try {
                if (event.getSource() == upLoadButton) {
                    if(dialogFileChooser()) {
                        new ImageUpLevelFrame("Improver Photos Quality", imagePath, gettingImage);
                        dispose();
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "System error");
            }
        }
    }
}