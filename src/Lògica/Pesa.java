package Lògica;

import java.awt.Image;

/**
 * Metode que ens permet crear peces del puzzle i guarden la seva posicio correcta
 * @author Francesc Vinent i Harpo Joan
 */
public class Pesa {
    private Image image;
    private  final int posicioCorrectaX;
    private  final int posicioCorrectaY;
    
    /**
     * Inicialitza les peces
     * @param image
     * @param posicioCorrectaX
     * @param posicioCorrectaY 
     */
    public Pesa(Image image, int posicioCorrectaX, int posicioCorrectaY) {
        this.image = image;
        this.posicioCorrectaX = posicioCorrectaX;
        this.posicioCorrectaY = posicioCorrectaY;
    }
    
    //GETTERS I SETTERS
    public Image getImage() {
        return image;
    }

    public int getPosicioCorrectaX() {
        return posicioCorrectaX;
    }

    public int getPosicioCorrectaY() {
        return posicioCorrectaY;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
