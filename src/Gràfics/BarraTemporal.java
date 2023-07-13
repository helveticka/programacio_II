package Gràfics;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JProgressBar;

/**
 * Classe que utilitzem per inicialitzar i configurar la barra temporal
 * @author Francesc Vinent i Harpo Joan
 */
public class BarraTemporal extends JProgressBar {
    private int minim=0;
    private int maxim=100;
    
    /**
     * Parametres inicials
     * @param dimensioX
     * @param dimensioY 
     */
    public  BarraTemporal(int dimensioX, int dimensioY) {
        super();
        setMaximum(maxim);
        setMinimum(minim);
        setValue(0);
        setStringPainted(true);
        setPreferredSize(new Dimension(dimensioX,dimensioY));
        setForeground(Color.RED);
        setBackground(Color.YELLOW);
    }
    
    //GETTERS I SETTERS
    public int getMaxim() {
        return maxim;
    }
    
    public int getMinim() {
        return minim;
    }
    
    public int getValorBarra() {
        return getValue();
    }
    
    public void setMaxim(int valor) {
        maxim=valor;
        setMaximum(maxim);
    }
    
    public void setMinim(int valor) {
        minim=valor;
        setMinimum(minim);
    }
    
    public void setValorBarra(int valor) {
        setValue(valor);
    }
}
