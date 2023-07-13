package Gràfics;

import Lògica.PracticaFinalProg;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import Lògica.Pesa;
import Lògica.PesaSeleccionada;

/**
 * Conte la logica del panell que dibuixa les peces
 * @author Francesc Vinent i Harpo Joan
 */
public class PanellSubimage extends JPanel {
    private PesaSeleccionada pesaSeleccionada;
    private PracticaFinalProg pfp = new PracticaFinalProg();
    
    public PanellSubimage(){
        pesaSeleccionada = null;
    }
    
    /**
     * Actualitza el taulell i afegeix els icones a les seves noves posicions
     * Tambe s'encarrega d'afegir els listeners de les peces
     * @param fragmentsPeces 
     */
    public void actualitzarPeces(Pesa[][] fragmentsPeces) {
        removeAll();

        int numFiles = fragmentsPeces.length;
        int numColumnes = fragmentsPeces[0].length;
        setLayout(new GridLayout(numFiles, numColumnes));
        
        int alturaPanell = 430;
        int ampladaPanell = 680;       
        int altura = alturaPanell/numFiles;
        int amplada = ampladaPanell/numColumnes;

        for (int y = 0; y < numFiles; y++) {
            for (int x = 0; x < numColumnes; x++) {
                Image fragmentImage = fragmentsPeces[y][x].getImage();
                Image fragmentRedimensionat = fragmentImage.getScaledInstance(amplada, altura, Image.SCALE_DEFAULT);
                ImageIcon iconePesa = new ImageIcon(fragmentRedimensionat);
                JLabel labelPesa = new JLabel(iconePesa);
                int pos = y*numColumnes+x;

                labelPesa.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {}

                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (pesaSeleccionada == null) {
                            pesaSeleccionada = new PesaSeleccionada();
                            pesaSeleccionada.setLabelPesa(labelPesa);
                            pesaSeleccionada.setPos(pos);
                        } else {
                            ImageIcon iconeAux = (ImageIcon)pesaSeleccionada.getLabelPesa().getIcon();
                            pesaSeleccionada.getLabelPesa().setIcon(labelPesa.getIcon());
                            labelPesa.setIcon(iconeAux);

                            Pesa pesaAux = fragmentsPeces[pesaSeleccionada.getPos()/numColumnes][pesaSeleccionada.getPos()%numColumnes];
                            fragmentsPeces[pesaSeleccionada.getPos()/numColumnes][pesaSeleccionada.getPos()%numColumnes] = fragmentsPeces[pos/numColumnes][pos%numColumnes];
                            fragmentsPeces[pos/numColumnes][pos%numColumnes] = pesaAux;                           
                            
                            if (comprovarPuzzleComplet(fragmentsPeces)) {
                                pfp.partidaAcabada(true);
                            }
                            pesaSeleccionada = null;
                        }
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {}

                    @Override
                    public void mouseEntered(MouseEvent e) {}

                    @Override
                    public void mouseExited(MouseEvent e) {}
                });
                labelPesa.setBorder(new EmptyBorder(1,1,1,1));
                add(labelPesa);
            }
        }

        revalidate();
        repaint();
    }
    
    /**
     * Comprova si totes les peces del taulell estan a la posicio correcta
     * @param fragmentsPeces
     * @return 
     */
    public boolean comprovarPuzzleComplet(Pesa[][] fragmentsPeces) {
        int numFiles = fragmentsPeces.length;
        int numColumnes = fragmentsPeces[0].length;

        for (int y = 0; y < numFiles; y++) {
            for (int x = 0; x < numColumnes; x++) {
                if (fragmentsPeces[y][x].getPosicioCorrectaX()!= x || fragmentsPeces[y][x].getPosicioCorrectaY()!= y) {
                    return false;
                }
            }
        }
        return true;
    }
}
