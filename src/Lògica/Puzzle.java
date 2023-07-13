package Lògica;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;



/**
 * Classe que utilitzem per a generar el puzzle
 * @author Francesc Vinent i Harpo Joan
 */
public class Puzzle {
    private BufferedImage image;
    private final Pesa[][] peces;
    private final int subdivisionsHorizontals;
    private final int subdivisionsVerticals;
    private int carpetaBuida;
    
    /**
     * Constructor que genera les subdivisions de la image i les afegeix a l'array
     * bidimensional peces.
     * @param ruta Direccio del fitxer de la image
     * @param subdivisionsVerticals Nº subdivisions verticals
     * @param subdivisionsHorizontals Nº subdivisions horitzontals
     */
    public Puzzle(String ruta, int subdivisionsVerticals, int subdivisionsHorizontals) {
        try {
            this.image = ImageIO.read(new File(ruta));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        this.subdivisionsVerticals = subdivisionsVerticals;
        this.subdivisionsHorizontals = subdivisionsHorizontals;
        this.peces = new Pesa[subdivisionsVerticals][subdivisionsHorizontals];
        int amplada = image.getWidth()/subdivisionsHorizontals;
        int altura = image.getHeight()/subdivisionsVerticals;

        for (int y = 0; y < subdivisionsVerticals; y++) {
            for (int x = 0; x < subdivisionsHorizontals; x++) {
                Image imagePesa = image.getSubimage(x * amplada, y * altura, amplada, altura);
                peces[y][x] = new Pesa(imagePesa, x, y);
            }
        }
    }
    
    //GETTERS I SETTERS
    public BufferedImage getImage() {
        return image;
    }
    
    public int getSubdivisionsVerticals() {
        return subdivisionsVerticals;
    }
    
    public int getSubdivisionsHorizontals() {
        return subdivisionsHorizontals;
    }

    public Pesa[][] getPecesFragmentades() {
        return this.peces;
    }
    
    public int getTamany(){
        return peces.length * peces[0].length;
    }
    
    /**
     * Comprova si es la posicio correcta de la pesa
     * @return 
     */
    public boolean posicioCorrecte() {
        for (int y = 0; y < peces.length; y++) {
            for (int x = 0; x < peces[0].length; x++) {                
                if (peces[y][x].getPosicioCorrectaY() != y || peces[y][x].getPosicioCorrectaX() != x) {
                    return false;
                }
            }
        }
        return true;
    }
    
    
    /**
     * Mescla les peces
     */
    public void mesclarPeces() {
        Random rnd = new Random();
        
        if ((subdivisionsHorizontals == 1) && (subdivisionsVerticals != 1)) {
            int aux = rnd.nextInt(subdivisionsVerticals);
            
            Pesa temp = peces[0][0];
            peces[0][0] = peces[aux][0];
            peces[aux][0] = temp;            
            
            for (int j = 0; j <= peces.length - 1; j++) {
                int aux1 = rnd.nextInt(peces.length - 1);

                Pesa auxPesa = peces[j][0];
                peces[j][0] = peces[aux1][0];
                peces[aux1][0] = auxPesa;
            }   
            
        } else if (subdivisionsVerticals == 1 && (subdivisionsHorizontals != 1)) {
            int aux = rnd.nextInt(subdivisionsHorizontals);
            
            Pesa temp = peces[0][0];
            peces[0][0] = peces[0][aux];
            peces[0][aux] = temp;
            
            for (int j = 0; j <= peces.length - 1; j++) {
                int aux1 = rnd.nextInt(peces[j].length - 1);

                Pesa auxPesa = peces[0][j];
                peces[0][j] = peces[0][aux1];
                peces[0][aux1] = auxPesa;
            }   
            
        } else {
            if(subdivisionsHorizontals != 1 && subdivisionsVerticals !=1){
                for (int i = peces.length - 1; i > 0; i--) {
                    for (int j = peces[i].length - 1; j > 0; j--) {
                        int aux1 = rnd.nextInt(i + 1);
                        int aux2 = rnd.nextInt(j + 1);

                        Pesa temp = peces[i][j];
                        peces[i][j] = peces[aux1][aux2];
                        peces[aux1][aux2] = temp;
                    }
                }
                if(posicioCorrecte()){
                    mesclarPeces();
                }
            }
        }
    }
    
}
