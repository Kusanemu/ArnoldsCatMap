/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package catmap;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author kusan
 */
public class Catmap {

    public static void main(String[] args) {
        try {
            //Carga la image orignal
            BufferedImage originalImg = ImageIO.read(new File("original.png"));
            
            //Aplica la transformación 10 veces
            int num = 10;
            BufferedImage transformedImage = aCatMap(originalImg, num);
            
            //Guarda la imagen ya transformada
            ImageIO.write(transformedImage, "png", new File("salida.png"));
            
            System.out.println("Transformación completada con "+num+" iteraciones.");
            
        } catch (IOException e) {
            System.err.println("Error al procesar la imagen: " + e.getMessage());
        }
    }
    
    public static BufferedImage aCatMap(BufferedImage img, int num) {
        int width = img.getWidth();
        int height = img.getHeight();
        
        //La imagen debe ser cuadrada
        if (width != height) {
            System.out.println("La imagen debe ser cuadrada para aplicar la transformacion");
            
        }
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        //Copia la imagen original
        for (int x = 0; x < width; x++) {
            for (int y = 0; y <width; y++) {
                result.setRGB(x, y, img.getRGB(x, y));
            }
        }
        
        //Aplica la transformación según el num que se pidió
        for (int k = 0; k < num; k++) {
            BufferedImage temp = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < width; y++) {
                    //Fórmula
                    int newX = (x + y)%width;
                    int newY = (x + 2 * y)%height;
                    
                    temp.setRGB(newX, newY, result.getRGB(x, y));
                }
            }
            result = temp;
        }
        return result;
    }
    
}
