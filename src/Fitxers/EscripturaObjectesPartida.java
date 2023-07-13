package Fitxers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import Lògica.Partida;

/**
 * Classe que utilitzam per escriure obejctes partida al fitxer de resultats
 * @author Francesc Vinent i Harpo Joan
 */
public class EscripturaObjectesPartida {
    private ObjectOutputStream oos;
    
    /**
     * Inicialitzam l'ObjectOutputStream
     * @param fitxer 
     */
    public EscripturaObjectesPartida(String fitxer){        
        try {
            oos = new ObjectOutputStream(new FileOutputStream(fitxer));
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    
    /**
     * Metode d'escriptura
     * @param partida 
     */
    public void escriptura(Partida partida){
        try {
            oos.writeObject(partida);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    
    public void close(){
        try {
            oos.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    } 
}
