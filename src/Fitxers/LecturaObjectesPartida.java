package Fitxers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import Lògica.Partida;

/**
 * Classe encarregada de la lectura de els objectes Partida del fitxer.
 * @author Francesc Vinent i Harpo Joan
 */
public class LecturaObjectesPartida {
    private ObjectInputStream ois;
    
    /**
     * Inicialitzam l'ObjectInputStream
     * @param fitxer 
     */
    public LecturaObjectesPartida(String fitxer) {
        try {
            ois = new ObjectInputStream(new FileInputStream(fitxer));
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }
    
    /**
     * Metode de lectura
     * @return 
     */
    public Partida lectura() {
        try {
            Partida partida = (Partida) ois.readObject();
            return partida;
        } catch (IOException e) {
            System.out.println(e.toString());
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.toString());
        }
        return null;
    }

    public void close() {
        try {
            ois.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
