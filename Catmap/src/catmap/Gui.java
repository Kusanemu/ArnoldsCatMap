/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package catmap;

/**
 *
 * @author kusan
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Gui extends JFrame {
    private JLabel originalLabel, transformedLabel;
    private BufferedImage originalImage, transformedImage;
    private JButton loadButton, transformButton;
    private JSpinner iterationsSpinner;

    public Gui() {
        setTitle("Arnold's Cat Map");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //Panel para botones
        JPanel topPanel = new JPanel();
        loadButton = new JButton("Cargar Imagen");
        transformButton = new JButton("Transformar");
        iterationsSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        
        topPanel.add(loadButton);
        topPanel.add(new JLabel("Iteraciones:"));
        topPanel.add(iterationsSpinner);
        topPanel.add(transformButton);
        add(topPanel, BorderLayout.NORTH);

        //Panel para las img
        JPanel imagePanel = new JPanel(new GridLayout(1, 2));
        originalLabel = new JLabel("Imagen Original", SwingConstants.CENTER);
        transformedLabel = new JLabel("Imagen Transformada", SwingConstants.CENTER);
        
        originalLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        transformedLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        imagePanel.add(originalLabel);
        imagePanel.add(transformedLabel);
        add(imagePanel, BorderLayout.CENTER);

        //Listeners
        loadButton.addActionListener(e -> loadImage());
        transformButton.addActionListener(e -> transformImage());
    }

    private void loadImage() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                originalImage = ImageIO.read(file);
                
                //Verifica si es cuadrada
                if (originalImage.getWidth() != originalImage.getHeight()) {
                    JOptionPane.showMessageDialog(this, "La imagen debe ser cuadrada.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                //Muestra la img original
                ImageIcon icon = new ImageIcon(originalImage.getScaledInstance(350, 350, Image.SCALE_SMOOTH));
                originalLabel.setIcon(icon);
                transformedLabel.setIcon(null); //Limpiar la transformación 
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al cargar la imagen", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void transformImage() {
        if (originalImage == null) {
            JOptionPane.showMessageDialog(this, "Primero carga la imagen", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int iterations = (int) iterationsSpinner.getValue();
        transformedImage = applyCatMap(originalImage, iterations);
        
        //Muestra la transformación
        ImageIcon transformedIcon = new ImageIcon(transformedImage.getScaledInstance(350, 350, Image.SCALE_SMOOTH));
        transformedLabel.setIcon(transformedIcon);
    }

    //Método para transformar la img
    private BufferedImage applyCatMap(BufferedImage image, int iterations) {
        int n = image.getWidth();
        BufferedImage result = new BufferedImage(n, n, BufferedImage.TYPE_INT_RGB);
        
        //Copia la img original
        for (int x = 0; x < n; x++) {
            for (int y = 0; y < n; y++) {
                result.setRGB(x, y, image.getRGB(x, y));
            }
        }
        
        //Aplica la transformacion
        for (int k = 0; k < iterations; k++) {
            BufferedImage temp = new BufferedImage(n, n, BufferedImage.TYPE_INT_RGB);
            
            for (int x = 0; x < n; x++) {
                for (int y = 0; y < n; y++) {
                    int newX = (x + y) % n;
                    int newY = (x + 2 * y) % n;
                    temp.setRGB(newX, newY, result.getRGB(x, y));
                }
            }
            result = temp;
        }
        return result;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Gui gui = new Gui();
            gui.setVisible(true);
        });
    }
}