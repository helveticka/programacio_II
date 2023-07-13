package Lògica;

import java.io.Serializable;

/**
 * Classe que almacena la informacio d'una partida i tambe ens servira com a centinela
 * ja que es l'objecta que guardarem al fitxer. Per aixo implementa Serializable
 * @author Francesc Vinent i Harpo Joan
 */
public class Partida implements Serializable{
    private String nom;
    private String hora;
    private int puntuacio;
    private static final Partida centinela = new Partida("","",-1);
    
    /**
     * Constructor principal
     * @param nom
     * @param hora
     * @param puntuacio 
     */
    public Partida(String nom, String hora, int puntuacio) {
        this.nom = nom;
        this.hora = hora;
        this.puntuacio = puntuacio;
    }

    public Partida() {}    
    
    //GETTERS I SETTERS
    public String getNom() {
        return nom;
    }

    public String getHora() {
        return hora;
    }
    
    public int getPuntuacion() {
        return puntuacio;
    }
    
    public static Partida getCentinela(){
        return centinela;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public void setPuntuacion(int puntuacio) {
        this.puntuacio = puntuacio;
    }
    
    /**
     * Metode que comprova si l'objecte es centinela
     * @param partida
     * @return 
     */
    public static boolean esCentinela(Partida partida){
        if(centinela.getNom().equals(partida.getNom()) && centinela.getHora().equals(partida.getHora()) && centinela.getPuntuacion() == partida.getPuntuacion()){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * Retorna una caden de caracters amb la informacio de la partida
     * @return 
     */
    @Override
    public String toString(){
        return "-JUGADOR: "+nom+"  -FECHA: "+hora+" -PUNTS: "+puntuacio+" punts.";
    }
}
