package Lògica;

import javax.swing.Icon;
import javax.swing.JLabel;

/**
 * Utilitzam aquesta classe per guardar les peces que seleccionam per realitzar
 * l'intercanvi de posicions al puzzle
 * @author Francesc Vinent i Harpo Joan
 */
public class PesaSeleccionada {
    private int pos;
    private JLabel labelPesa;
    
    /**
     * Inicialitzam atributs
     */
    public PesaSeleccionada() {
        this.labelPesa = null;
        this.pos = -1;
    }
    
    //GETTERS I SETTERS
    public int getPos() {
        return pos;
    }

    public JLabel getLabelPesa() {
        return labelPesa;
    }
    
    public void setPos(int pos) {
        this.pos = pos;
    }

    public void setLabelPesa(JLabel labelPesa) {
        this.labelPesa = labelPesa;
    }

    /**
     * Metode que utilitzam per relitzar els intercanvis als arrays i als icones
     * @param labelPeces 
     */
    private void intercanviPeces(PesaSeleccionada[] labelPeces) {
        int posAux = labelPeces[0].pos;
        labelPeces[0].pos = labelPeces[1].pos;
        labelPeces[1].pos = posAux;
        Icon iconeAux = labelPeces[0].labelPesa.getIcon();
        labelPeces[0].labelPesa.setIcon(labelPeces[1].labelPesa.getIcon());
        labelPeces[1].labelPesa.setIcon(iconeAux);
    }
}
