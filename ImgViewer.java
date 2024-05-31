import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;

public class ImgViewer implements ActionListener {
    private Color buttonColor = new Color(255, 255, 255);
    public Color backgroundColor = new Color(238, 238, 238);
    public JTextField widthTextField;
    private JFileChooser fileChooser = new JFileChooser();
    private JButton resizeButton = new JButton("Resize");
    private JButton grayscaleButton = new JButton("Grayscale");
    private JButton brightnessButton = new JButton("Brightness");
    public JButton closeButton = new JButton("Exit");
    public JButton showResizeButton = new JButton("Show Resized Image");
    private JTextField heightTextField;
    public int width;
    public int height;
    private Image img;
    private JFrame frame;
    public int addheigh = 0;
    public int addwigth = 0;
    private File file;
    public JButton selectFileButton = new JButton("Choose Image");
    private JButton showImageButton = new JButton("Show Image");
    private JButton showBrightnessButton = new JButton("Show Brightness Image");
    public JButton backButton = new JButton("Back");
    private JTextField brightnessTextField;

    
    
    private void chooseFileImage() {
        int r = fileChooser.showOpenDialog(null);
        if (r == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
        }

        ImageIcon imageIcon = new ImageIcon(file.getAbsolutePath());

        height = imageIcon.getIconHeight();
        width = imageIcon.getIconWidth();
        double aspectRatio = (double) width / height;
        if (height > 800) {
            addheigh = 800;
            addwigth = (int) (addheigh * aspectRatio);
        }
        if (width > 1500) {
            addwigth = 1500;
            addheigh = (int) (addwigth / aspectRatio);
        }
        img = imageIcon.getImage().getScaledInstance(addwigth, addheigh, Image.SCALE_SMOOTH);
    }

    public static void main(String[] args) {
        new ImgViewer();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == resizeButton) {
            resizePanel();
        } else if (source == showImageButton) {
            showOriginalImage();
        } else if (source == grayscaleButton) {
            grayScaleImage();
        } else if (source == showResizeButton) {
            String widthText = widthTextField.getText();
            String heightText = heightTextField.getText();
            if (widthText.isEmpty() || heightText.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter values");
                return;
            }
            int width = Integer.parseInt(widthText);
            int height = Integer.parseInt(heightText);
            showResizeImage(height, width);
        } else if (source == brightnessButton) {
            brightnessPanel();
        } else if (source == showBrightnessButton) {
            String brightnessText = brightnessTextField.getText();
            if (brightnessText.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a value");
                return;
            }
            float factor = Float.parseFloat(brightnessText);
            showBrightnessImage(factor);
        } else if (source == selectFileButton) {
            chooseFileImage();
        } else if (source == closeButton) {
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        }
    }


    private void brightnessPanel() {
        JPanel brightnessPanel = new JPanel();
        brightnessPanel.setLayout(null);
        brightnessPanel.setBackground(backgroundColor);

        brightnessTextField = new JTextField();
        JLabel brightnessLabel = new JLabel("Brightness");

        showBrightnessButton.setBounds(250, 150, 200, 30);
        backButton.setBounds(250, 200, 200, 30);
        brightnessLabel.setBounds(250, 100, 100, 30);
        brightnessTextField.setBounds(350, 100, 100, 30);

        brightnessPanel.add(brightnessTextField);
        brightnessPanel.add(brightnessLabel);
        brightnessPanel.add(showBrightnessButton);
        brightnessPanel.add(backButton);

        showBrightnessButton.addActionListener(this);
        backButton.addActionListener(_ -> {
            frame.getContentPane().removeAll();
            mainPanel();
            frame.revalidate();
            frame.repaint();
        });

        frame.getContentPane().removeAll();
        frame.add(brightnessPanel);
        frame.revalidate();
        frame.repaint();
    }

    private void showOriginalImage() {
        if (img == null) {
            JOptionPane.showMessageDialog(null, "Please select an image");
            return;
        }
        JFrame tFrame = new JFrame();
        JPanel tPanel = new JPanel();
        tPanel.setBackground(backgroundColor);
        tPanel.setLayout(null);

        backButton.setBounds(10, 10, 100, 30);
        tPanel.add(backButton);

        backButton.addActionListener(_ -> {
            tFrame.dispose();
            frame.getContentPane().removeAll();
            mainPanel();
            frame.revalidate();
            frame.repaint();
        });

        ImageIcon newImage = new ImageIcon(img);
        JLabel label = new JLabel(newImage);
        label.setBounds((1500 - addwigth) / 2, (800 - addheigh) / 2, addwigth, addheigh);
        tPanel.add(label);

        tFrame.setContentPane(tPanel);
        tFrame.setTitle("Image Viewer");
        tFrame.setVisible(true);
        tFrame.setResizable(true);
        tFrame.add(tPanel);
        tFrame.setSize(1500, 800);
    }

    private void grayScaleImage() {
        if (img == null) {
            JOptionPane.showMessageDialog(null, "Please select an image");
            return;
        }

        JFrame tFrame = new JFrame();
        JPanel tPanel = new JPanel();
        tPanel.setBackground(backgroundColor);
        tPanel.setLayout(null);

        backButton.setBounds(10, 10, 100, 30);
        tPanel.add(backButton);

        backButton.addActionListener(_ -> {
            mainPanel();
            tFrame.dispose();
            frame.revalidate();
            frame.repaint();
            frame.getContentPane().removeAll();
        });

        ImageIcon newImage = new ImageIcon(img);
        Image image = newImage.getImage();
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = bufferedImage.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        ImageIcon grayImage = new ImageIcon(bufferedImage);

        JLabel label = new JLabel(grayImage);
        label.setBounds((1500 - addwigth) / 2, (800 - addheigh) / 2, addwigth, addheigh);
        tPanel.add(label);

        tFrame.setContentPane(tPanel);
        tFrame.add(tPanel);
        tFrame.setSize(1500, 800);
        tFrame.setResizable(true);
        tFrame.setTitle("Image Viewer");
        tFrame.setVisible(true);
    }

    private void showBrightnessImage(float factor) {
        if (img == null) {
            JOptionPane.showMessageDialog(null, "Please select an image");
            return;
        }
        JFrame tFrame = new JFrame();
        JPanel tPanel = new JPanel();
        tPanel.setBackground(backgroundColor);
        tPanel.setLayout(null);

        backButton.setBounds(10, 10, 100, 30);
        tPanel.add(backButton);

        backButton.addActionListener(_ -> {
            tFrame.dispose();
            frame.getContentPane().removeAll();
            brightnessPanel();
            frame.revalidate();
            frame.repaint();
        });

        Image newImg = img;
        BufferedImage bufferedImage = new BufferedImage(newImg.getWidth(null), newImg.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();
        g.drawImage(newImg, 0, 0, null);
        RescaleOp op = new RescaleOp(factor, 0, null);
        bufferedImage = op.filter(bufferedImage, null);
        ImageIcon newImage = new ImageIcon(bufferedImage);
        JLabel label = new JLabel(newImage);
        label.setBounds((1500 - addwigth) / 2, (800 - addheigh) / 2, addwigth, addheigh);
        tPanel.add(label);

        tFrame.setVisible(true);
        tFrame.setResizable(true);
        tPanel.setSize(1500, 800);
        tFrame.setTitle("Image Viewer");
        tFrame.setSize(1500, 800);
        tFrame.add(tPanel);
    }

    public ImgViewer() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setTitle("Image Viewer");
        frame.setSize(700, 300);
        frame.setVisible(true);

        selectFileButton.setBackground(buttonColor);
        showImageButton.setBackground(buttonColor);
        resizeButton.setBackground(buttonColor);
        grayscaleButton.setBackground(buttonColor);
        brightnessButton.setBackground(buttonColor);
        closeButton.setBackground(buttonColor);
        showResizeButton.setBackground(buttonColor);
        showBrightnessButton.setBackground(buttonColor);
        backButton.setBackground(buttonColor);

        mainPanel();
    }

    private void mainPanel() {
        int buttonHeight = 50;
        int buttonWidth = 300;

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(backgroundColor);
        mainPanel.setLayout(null);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBounds((700 - buttonWidth * 2) / 2, (300 - buttonHeight * 2) / 2, (buttonWidth * 2), (buttonHeight * 2));
        buttonsPanel.setLayout(new GridLayout(3, 2));

        JLabel label = new JLabel("Image Viewer");
        label.setBounds(312, 50, 75, 30);

        buttonsPanel.add(closeButton);
        buttonsPanel.add(resizeButton);
        buttonsPanel.add(grayscaleButton);
        buttonsPanel.add(selectFileButton);
        buttonsPanel.add(showImageButton);
        buttonsPanel.add(brightnessButton);

        resizeButton.addActionListener(this);
        selectFileButton.addActionListener(this);
        grayscaleButton.addActionListener(this);
        brightnessButton.addActionListener(this);
        closeButton.addActionListener(this);
        showImageButton.addActionListener(this);

        mainPanel.add(label);
        mainPanel.add(buttonsPanel);

        frame.getContentPane().removeAll();
        frame.add(mainPanel);
        frame.revalidate();
        frame.repaint();
    }

    private void showResizeImage(int height, int width) {
        if (img == null) {
            JOptionPane.showMessageDialog(null, "Please select an image");
            return;
        }
        JFrame tFrame = new JFrame();
        JPanel tPanel = new JPanel();
        tPanel.setBackground(backgroundColor);
        tPanel.setLayout(null);

        backButton.setBounds(10, 10, 100, 30);
        tPanel.add(backButton);

        backButton.addActionListener(_ -> {
            tFrame.dispose();
            frame.getContentPane().removeAll();
            resizePanel();
            frame.revalidate();
            frame.repaint();
        });

        Image newImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon newImage = new ImageIcon(newImg);
        JLabel label = new JLabel(newImage);
        label.setBounds((1500 - width) / 2, (800 - height) / 2, width, height);
        tPanel.add(label);

        tPanel.setSize(1500, 800);
        tFrame.setTitle("Image Viewer");
        tFrame.setSize(1500, 800);
        tFrame.setVisible(true);
        tFrame.setResizable(true);
        tFrame.add(tPanel);
    }

    private void resizePanel() {
        JPanel resizePanel = new JPanel();
        resizePanel.setLayout(null);
        resizePanel.setBackground(backgroundColor);

        widthTextField = new JTextField();
        heightTextField = new JTextField();
        JLabel widthLabel = new JLabel("Width");
        JLabel heightLabel = new JLabel("Height");

        widthLabel.setBounds(250, 100, 100, 30);
        heightLabel.setBounds(250, 50, 100, 30);
        showResizeButton.setBounds(250, 150, 200, 30);
        heightTextField.setBounds(350, 50, 100, 30);
        backButton.setBounds(250, 200, 200, 30);
        widthTextField.setBounds(350, 100, 100, 30);

        resizePanel.add(widthLabel);
        resizePanel.add(heightLabel);
        resizePanel.add(showResizeButton);
        resizePanel.add(widthTextField);
        resizePanel.add(heightTextField);
        resizePanel.add(backButton);

        showResizeButton.addActionListener(this);
        backButton.addActionListener(_ -> {
            resizePanel.setVisible(false);
            frame.getContentPane().removeAll();
            mainPanel();
            frame.revalidate();
            frame.repaint();
        });

        frame.getContentPane().removeAll();
        frame.add(resizePanel);
        frame.revalidate();
        frame.repaint();
    }

 
}