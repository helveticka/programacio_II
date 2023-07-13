package Lògica;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import javax.swing.*;
import Gràfics.BarraTemporal;
import Fitxers.EscripturaObjectesPartida;
import Fitxers.LecturaObjectesPartida;
import Gràfics.PanellSubimage;

/**
 * Classe principal que conte el main i la logica del Puzzle
 * @author Francesc Vinent i Harpo Joan
 */
public class PracticaFinalProg {
    //Atributs
    private static final String fitxer = "Partides.dat";
    private PanellSubimage panellPartida;
    private static Puzzle puzzle;
    private static Partida partida;
    private static LecturaObjectesPartida ois;
    private static EscripturaObjectesPartida oos;
    private static BarraTemporal progressBar;
    private static Timer timer;
    private static JPanel panellInterfas;
    private JPanel panellImageSolucio;
    private String image;
    private String direccio;
    private boolean partidaComensada;
    private int firstRotation;
    private String nomTemp;
    
    /**
     * @param args 
     */
    public static void main(String[] args) {
        new PracticaFinalProg().inici();
    }
    
    /**
     * Metode que dona inici a l'aplicacio, inicialitzant els grafics del programa
     */
    private void inici() {
        //Iniciam grafics inicials
        JFrame finestra = new JFrame("PRACTICA PRGORAMACIO II - 2022/23 - UIB");
        finestra.setSize(900, 600);
        finestra.setLocationRelativeTo(null);
        Container panellContinguts = finestra.getContentPane();
        JPanel panellMenu = new JPanel();
        JPanel panellVisualitzacions = new JPanel();
        JPanel panellSolucio = new JPanel();
        JPanel panellStandby = new JPanel();
        JPanel panellHisotrial = new JPanel();
        JPanel panellJoc = new JPanel();
        JPanel panellBotons = new JPanel();
        JButton botoContinuar = new JButton("CONTINUAR");
        JMenuBar barraMenu = new JMenuBar();
        JMenu menu = new JMenu("MENU");
        JMenuItem menuNovaPartida = new JMenuItem("NOVA PARTIDA");
        JMenuItem menuClassificacio = new JMenuItem("CLASIFICACIO GENERAL");
        JMenuItem menuHistorial = new JMenuItem("HISTORIAL");
        JMenuItem menuCanviarDirectori = new JMenuItem("CANVIAR DIRECTORI D'IMAGES");
        JMenuItem menuSortir = new JMenuItem("SORTIR");
        JToolBar menuIcones = new JToolBar();
        JButton iconeNovaPartida = new JButton(new ImageIcon("icones/iconeNovaPartida.jpg"));
        JButton iconeClassificacio = new JButton(new ImageIcon("icones/iconeHistorialGeneral.jpg"));
        JButton iconeHistorial = new JButton(new ImageIcon("icones/iconeHistorialSelectiu.jpg"));
        JButton iconeCanviarDirectori = new JButton(new ImageIcon("icones/iconeCanviarDirectori.jpg"));
        JButton iconeSortir = new JButton(new ImageIcon("icones/iconeSortir.jpg"));
        JButton botoNovaPartida = new JButton("NOVA PARTIDA");
        JButton botoClassificacions = new JButton("CLASIFICACIO GENERAL");
        JButton botoHistorial = new JButton("HISTORIAL");
        JButton botoSortir = new JButton("SORTIR");
        firstRotation = 0;
        
        inicialitzarVariables();
        
        panellContinguts.setLayout(new BorderLayout());
        panellMenu.setLayout(new BorderLayout());
        panellSolucio.setLayout(new BorderLayout());
        
        botoContinuar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout carta = (CardLayout) (panellInterfas.getLayout());
                carta.show(panellInterfas, "PANELL 1");
                partidaComensada = false;
            }
        });
        botoContinuar.setBackground(Color.BLACK);
        botoContinuar.setForeground(Color.WHITE);

        panellSolucio.add(panellImageSolucio, BorderLayout.CENTER);
        panellSolucio.add(botoContinuar, BorderLayout.SOUTH);

        carregarImatge(panellStandby, 700, 500, "UIB.jpg");

        panellHisotrial.setPreferredSize(new Dimension(700, 500));

        inicialitzarProgressBar();

        panellJoc.setLayout(new BorderLayout());
        panellJoc.add(panellPartida, BorderLayout.CENTER);
        panellJoc.add(progressBar, BorderLayout.SOUTH);

        panellInterfas.setPreferredSize(new Dimension(700, 500));
        panellInterfas.add(panellStandby, "PANELL 1");
        panellInterfas.add(panellJoc, "PANELL 2");
        panellInterfas.add(panellSolucio, "PANELL 3");
        panellInterfas.add(panellHisotrial, "PANELL 4");

        CardLayout carta = (CardLayout) (panellInterfas.getLayout());
        carta.show(panellInterfas, "PANELL 1");

        panellVisualitzacions.add(panellInterfas);

        menu.setForeground(Color.WHITE);

        menuNovaPartida.setBackground(Color.black);
        menuNovaPartida.setForeground(Color.white);
        menuNovaPartida.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!partidaComensada) {
                    novaPartida();
                } else {
                    JOptionPane.showMessageDialog(null, "Ja has comensat una partida!", "WARNING", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        menu.add(menuNovaPartida);

        menuClassificacio.setBackground(Color.black);
        menuClassificacio.setForeground(Color.white);
        menuClassificacio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!partidaComensada) {
                    historial(null, panellHisotrial);
                } else {
                    JOptionPane.showMessageDialog(null, "No pots realitzar aquesta operacio fins que acabis la partida!", "WARNING", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        menu.add(menuClassificacio);

        menuHistorial.setBackground(Color.black);
        menuHistorial.setForeground(Color.white);
        menuHistorial.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!partidaComensada) {
                    historialSelectiu(panellHisotrial);
                } else {
                    JOptionPane.showMessageDialog(null, "No pots realitzar aquesta operacio fins que acabis la partida!", "WARNING", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        menu.add(menuHistorial);

        menuCanviarDirectori.setBackground(Color.black);
        menuCanviarDirectori.setForeground(Color.white);
        menuCanviarDirectori.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!partidaComensada) {
                    canviarDirectori();
                } else {
                    JOptionPane.showMessageDialog(null, "No pots realitzar aquesta operacio fins que acabis la partida!", "WARNING", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        menu.add(menuCanviarDirectori);

        menuSortir.setBackground(Color.black);
        menuSortir.setForeground(Color.white);
        menuSortir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menu.add(menuSortir);

        barraMenu.add(menu);
        barraMenu.setBackground(Color.black);
        panellMenu.add(barraMenu, BorderLayout.NORTH);

        iconeNovaPartida.setBackground(Color.black);
        iconeNovaPartida.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!partidaComensada) {
                    novaPartida();
                } else {
                    JOptionPane.showMessageDialog(null, "Ja has comensat una partida!", "WARNING", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        menuIcones.add(iconeNovaPartida);

        iconeClassificacio.setBackground(Color.black);
        iconeClassificacio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!partidaComensada) {
                    historial(null, panellHisotrial);
                } else {
                    JOptionPane.showMessageDialog(null, "No pots realitzar aquesta operacio fins que acabis la partida!", "WARNING", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        menuIcones.add(iconeClassificacio);

        iconeHistorial.setBackground(Color.black);
        iconeHistorial.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!partidaComensada) {
                    historialSelectiu(panellHisotrial);
                } else {
                    JOptionPane.showMessageDialog(null, "No pots realitzar aquesta operacio fins que acabis la partida!", "WARNING", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        menuIcones.add(iconeHistorial);

        iconeCanviarDirectori.setBackground(Color.black);
        iconeCanviarDirectori.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!partidaComensada) {
                    canviarDirectori();
                } else {
                    JOptionPane.showMessageDialog(null, "No pots realitzar aquesta operacio fins que acabis la partida!", "WARNING", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        menuIcones.add(iconeCanviarDirectori);

        iconeSortir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menuIcones.setBackground(Color.BLACK);
        menuIcones.add(iconeSortir);
        panellMenu.add(menuIcones, BorderLayout.SOUTH);

        panellBotons.setLayout(new GridLayout(0, 1));
        botoNovaPartida.setBackground(Color.black);
        botoNovaPartida.setForeground(Color.white);

        botoNovaPartida.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!partidaComensada) {
                    novaPartida();
                } else {
                    JOptionPane.showMessageDialog(null, "Ja has comensat una partida!", "WARNING", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        botoClassificacions.setBackground(Color.black);
        botoClassificacions.setForeground(Color.white);
        botoClassificacions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!partidaComensada) {
                    historial(null, panellHisotrial);
                } else {
                    JOptionPane.showMessageDialog(null, "No pots realitzar aquesta operacio fins que acabis la partida!", "WARNING", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        botoHistorial.setBackground(Color.black);
        botoHistorial.setForeground(Color.white);
        botoHistorial.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!partidaComensada) {
                    historialSelectiu(panellHisotrial);
                } else {
                    JOptionPane.showMessageDialog(null, "No pots realitzar aquesta operacio fins que acabis la partida!", "WARNING", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        botoSortir.setBackground(Color.black);
        botoSortir.setForeground(Color.white);
        botoSortir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        panellBotons.add(botoNovaPartida);
        panellBotons.add(botoClassificacions);
        panellBotons.add(botoHistorial);
        panellBotons.add(botoSortir);

        JSplitPane separador1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panellMenu, panellVisualitzacions);
        JSplitPane separador2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panellBotons, panellVisualitzacions);
        JSplitPane separador3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panellPartida, progressBar);

        panellContinguts.add(separador1);
        panellContinguts.add(separador2);
        panellJoc.add(separador3);

        panellContinguts.add(panellMenu, BorderLayout.NORTH);
        panellContinguts.add(panellBotons, BorderLayout.WEST);
        panellContinguts.add(panellVisualitzacions, BorderLayout.CENTER);

        finestra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        finestra.setVisible(true);
    }
    
    /**
     * Metode que utilitzem per inicialitzar una serie de varibles que necessitarem
     * inicialitzar varies vegades
     */
    private void inicialitzarVariables() {
        panellInterfas = new JPanel();
        panellInterfas.setLayout(new CardLayout());
        panellPartida = new PanellSubimage();
        panellImageSolucio = new JPanel();
        partida = new Partida();
        direccio = "images";
        partidaComensada = false;
        escollirImage();
    }

    /**
     * Metode que s'encarrega de carregar una imatge al panell d'imatges
     * @param panell
     * @param amplada
     * @param altura
     * @param image 
     */
    private void carregarImatge(JPanel panell, int amplada, int altura, String image) {
        ImageIcon icone = new ImageIcon(image);
        Image imageEscalada = icone.getImage().getScaledInstance(amplada, altura, Image.SCALE_SMOOTH);
        icone = new ImageIcon(imageEscalada);
        JLabel labelImage = new JLabel(icone);
        panell.add(labelImage);
    }
    
    /**
     * Metode que s'encarrega d'escollir una imatge aleatoria de la carpeta
     */
    private void escollirImage() {
        Random rnd = new Random();
        File carpeta = new File(direccio);
        File[] arxius = carpeta.listFiles();
        try{
            if(arxius.length == 0){
                JOptionPane.showMessageDialog(null, "El directori d'imatges esta buit!", "WARNING", JOptionPane.WARNING_MESSAGE);
            }else{
                int indexAleatori = rnd.nextInt(arxius.length);
                image = arxius[indexAleatori].toString();
                carregarImatge(panellImageSolucio, 700, 450, image);
            }
        }catch(NullPointerException a){
            JOptionPane.showMessageDialog(null, "El directori no existeix!", "WARNING", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    /**
     * Metode que inicialitza l'objecta de la classe BarraTemporal
     */
    private void inicialitzarProgressBar() {
        progressBar = new BarraTemporal(panellPartida.getWidth(), 25);
        progressBar.setMinim(0);
        progressBar.setMaxim(2000);
        progressBar.setValorBarra(0);
    }
    
    /**
     * Configuram la detencio del timer depenent de si el puzzle es resol o s'acaba
     * el temps
     * @param v 
     */
    private void configuracioTimer(int v) {
        if (timer != null) {
            progressBar.setValorBarra(0);
            timer.stop();
        }

        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (progressBar.getValorBarra() < progressBar.getMaxim()) {
                    progressBar.setValorBarra(progressBar.getValorBarra() + 100/v);
                } else {
                    partidaAcabada(false);
                    timer.stop();
                }
            }
        });
        timer.start();
    }
    
    /**
     * Genera els grafics d'un nova partida a partir dels parametres que passes
     * al popUp que te demanara el nom i el nombre de subdivisions
     */
    private void novaPartida() { 
        nomTemp = "";
        JFrame pestanya = new JFrame();
        pestanya.setSize(300, 150);
        pestanya.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        pestanya.setLayout(new BorderLayout());
        
        JTextField campVertical = new JTextField();
        JTextField campHoritzontal = new JTextField();

        JPanel panellNord = new JPanel(new GridLayout(3, 1));
        JTextField campJugador = new JTextField();
        campJugador.setBackground(Color.WHITE);
        campJugador.setForeground(Color.BLACK);
        JLabel labelJugador = new JLabel("NOM JUGADOR:");
        labelJugador.setBackground(Color.BLACK);
        labelJugador.setForeground(Color.WHITE);
        labelJugador.setOpaque(true);
        
        JLabel labelHoritzontal = new JLabel("NOMBRE SUBDIVISIONS HORITZONTALS:");
        labelHoritzontal.setBackground(Color.BLACK);
        labelHoritzontal.setForeground(Color.WHITE);
        labelHoritzontal.setOpaque(true);
        campHoritzontal.setBackground(Color.WHITE);
        campHoritzontal.setForeground(Color.BLACK);

        JLabel labelVertical = new JLabel("NOMBRE SUVDIVISIONS VERTICALS:");
        labelVertical.setBackground(Color.BLACK);
        labelVertical.setForeground(Color.WHITE);
        labelVertical.setOpaque(true);
        campVertical.setBackground(Color.WHITE);
        campVertical.setForeground(Color.BLACK);

        panellNord.add(labelJugador);
        panellNord.add(campJugador);
        panellNord.add(labelHoritzontal);
        panellNord.add(campHoritzontal);
        panellNord.add(labelVertical);
        panellNord.add(campVertical);

        JButton botoContinuar = new JButton("CONTINUAR");

        botoContinuar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(firstRotation != 0){
                   panellImageSolucio.removeAll();
                   escollirImage();
                }
                try {
                    int subdivisionesVerticales = Integer.parseInt(campVertical.getText());
                    int subdivisionsHoritzontls = Integer.parseInt(campHoritzontal.getText());
                    nomTemp = campJugador.getText();
                    if (!(nomTemp.equals(""))) {
                        partida.setNom(nomTemp); 
                        if ((subdivisionsHoritzontls == 1) && (subdivisionesVerticales == 1)) {
                            puzzle = new Puzzle(image, subdivisionesVerticales, subdivisionsHoritzontls);
                            pestanya.dispose();
                            pestanya.setVisible(false);
                            partidaAcabada(true);
                        } else {
                            puzzle = new Puzzle(image, subdivisionesVerticales, subdivisionsHoritzontls);
                            Pesa[][] piezasFragmentadas = puzzle.getPecesFragmentades();
                            puzzle.mesclarPeces();
                            while(panellPartida.comprovarPuzzleComplet(piezasFragmentadas)) {
                                puzzle.mesclarPeces();
                            }
                            panellPartida.actualitzarPeces(piezasFragmentadas);
                            partidaComensada = true;
                            configuracioTimer(subdivisionsHoritzontls*subdivisionesVerticales);
                            CardLayout carta = (CardLayout)panellInterfas.getLayout();
                            carta.show(panellInterfas, "PANELL 2");
                            pestanya.dispose();
                            firstRotation++;
                            pestanya.setVisible(false);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Els paràmetres d'inici són incorrectes!", "WARNING", JOptionPane.WARNING_MESSAGE);
                        pestanya.setVisible(false);
                        novaPartida();
                    }
                } catch (NullPointerException npe) {
                    File f = new File(direccio);
                    
                    if (f.listFiles().length - 1 == 0) {
                        
                        JOptionPane.showMessageDialog(null, "El directori d'imatges esta buit!", "WARNING", JOptionPane.WARNING_MESSAGE);
                        pestanya.setVisible(false);
                    } 
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(null, "Les subdivisions són incorrectes. Torna-ho a provar!", "WARNING", JOptionPane.WARNING_MESSAGE);
                    pestanya.setVisible(false);
                    novaPartida();
                } 
            }
        });

        pestanya.add(panellNord, BorderLayout.CENTER);
        pestanya.add(botoContinuar, BorderLayout.SOUTH);
        pestanya.setLocationRelativeTo(null);
        pestanya.setVisible(true);
    }
    
    /**
     * Una vegada acbada la partida guarda el timestamp i despues del popUp que
     * indica si has guanyat o no, apareix la imatge resolta
     * @param guanyat 
     */
    public void partidaAcabada(boolean guanyat) {
        JFrame pestanya = new JFrame();
        JPanel panellGameOver = new JPanel(new BorderLayout());
        JDialog popUp = new JDialog(pestanya, "GAME OVER");
        JButton botoContinuar = new JButton("CONTINUAR");
        String hora  = ZonedDateTime.now(ZoneId.of("Europe/Madrid")).format(DateTimeFormatter.ofPattern("MM.dd.yyy, hh.mm.ss a"));
        JLabel label;
        if (!guanyat) {
            label = new JLabel("S'HA ACABAT EL TEMPS - NO HO HAS ACONSEGUIT", SwingConstants.CENTER);
            partida.setPuntuacion(0);
        } else {
            label = new JLabel("HO HAS ACONSEGUIT, ENHORABONA!!!  HAS OBTINGUT " + puzzle.getTamany() + " PUNTS!!!!", SwingConstants.CENTER);
            partida.setPuntuacion(puzzle.getTamany());
        }
        progressBar.setValorBarra(0);
        if (timer != null) {
            timer.stop();
        }

        CardLayout carta = (CardLayout)panellInterfas.getLayout();
        carta.show(panellInterfas, "PANELL 3");

        pestanya.setSize(300, 150);
        pestanya.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        pestanya.setLayout(new BorderLayout());

        popUp.setSize(500, 200);
        popUp.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        panellGameOver.setBackground(Color.BLACK);
        label.setForeground(Color.WHITE);
        
        
        partida.setHora(hora);
        ois = new LecturaObjectesPartida(fitxer);
        File file = new File(fitxer);
        
        if (file.length() != 0) {
            escriptura();
        } else {
            oos = new EscripturaObjectesPartida(fitxer);
            oos.escriptura(partida);
            oos.escriptura(Partida.getCentinela());
            oos.close();
        }
        intercanviFitxers();
        
        botoContinuar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popUp.dispose();
            }
        });
        panellGameOver.removeAll();
        panellGameOver.add(label, BorderLayout.CENTER);
        panellGameOver.add(botoContinuar, BorderLayout.SOUTH);
        panellGameOver.revalidate();
        panellGameOver.repaint();

        popUp.setContentPane(panellGameOver);
        popUp.setLocationRelativeTo(null);
        popUp.setVisible(true);
    }
    
    /**
     * Metode que carrega l'historial complet
     * @param nom
     * @param panellHistorial 
     */
    private void historial(String nom, JPanel panellHistorial) {
        JTextArea areaResultats = new JTextArea();

        if (nom != null) {
            lecturaSelectiva(nom, areaResultats);
        } else {
            lectura(areaResultats);
        }

        panellHistorial.removeAll();
        panellHistorial.add(areaResultats);
        panellHistorial.revalidate();
        panellHistorial.repaint();

        CardLayout carta = (CardLayout)panellInterfas.getLayout();
        carta.show(panellInterfas, "PANELL 4");
    }
    
    /**
     * Metode que carrega l'historial filtrat pel nom d'un jugador
     * @param panelHistorial 
     */
    private void historialSelectiu(JPanel panelHistorial) {
        JFrame pestanya = new JFrame();
        pestanya.setSize(300, 150);
        pestanya.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        pestanya.setLayout(new BorderLayout());

        JPanel panellNord = new JPanel(new GridLayout(2, 1));
        JTextField campJugador = new JTextField();
        campJugador.setBackground(Color.WHITE);
        campJugador.setForeground(Color.BLACK);
        JLabel labelJugador = new JLabel("NOM JUGADOR: ");
        labelJugador.setBackground(Color.BLACK);
        labelJugador.setForeground(Color.WHITE);
        labelJugador.setOpaque(true);
        
        panellNord.add(labelJugador);
        panellNord.add(campJugador);

        JButton botoContinuar = new JButton("CONTINUAR");

        botoContinuar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nom = campJugador.getText();
                historial(nom, panelHistorial);
                pestanya.dispose();
            }
        });

        pestanya.add(panellNord, BorderLayout.CENTER);
        pestanya.add(botoContinuar, BorderLayout.SOUTH);
        pestanya.setLocationRelativeTo(null);
        pestanya.setVisible(true);
    }
    
    /**
     * Metode que utilitzem per poder canviar la carpeta de imatges
     */
    private void canviarDirectori() {
        JFileChooser canviarDirectoriImatges = new JFileChooser();
        canviarDirectoriImatges.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int resposta = canviarDirectoriImatges.showOpenDialog(null);
        if(resposta == JFileChooser.APPROVE_OPTION){
            direccio = canviarDirectoriImatges.getSelectedFile().getAbsolutePath();
        }
        escollirImage();
    }
    
    /**
     * Metode que utilitzem per actualitzar fitxers
     */
    private static void intercanviFitxers() {
        File f1 = new File(fitxer);
        File f2 = new File("tmp.dat");
        if (f1.delete()) {
            f2.renameTo(f1);
        }
    }
    
    /**
     * Metode que utilitzem per comprar dos noms de imatges
     * @param nom1
     * @param nom2
     * @return 
     */
    private boolean comparacioNoms(String nom1, String nom2) {
        if (nom1.length() != nom2.length()) {
            return false;
        }
        for (int i = 0; i < nom1.length(); i++) {
            if (nom1.charAt(i) != nom2.charAt(i)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Metode d'escriptura al fitxer temporal
     */
    private static void escriptura() {
        oos = new EscripturaObjectesPartida("tmp.dat");
        Partida aux = ois.lectura();
        while (!Partida.esCentinela(aux)) {
            oos.escriptura(aux);
            aux = ois.lectura();
        }
        oos.escriptura(partida);
        oos.escriptura(Partida.getCentinela());

        oos.close();
        ois.close();
    }
    
    /**
     * Metode de lectura de l'area de resultats
     * @param areaResultats 
     */
    private void lectura(JTextArea areaResultats) {
        ois = new LecturaObjectesPartida(fitxer);
        Partida partida = ois.lectura();
        String s = "";
        
        while (!Partida.esCentinela(partida)) {
            s += partida.toString() + "\n";
            partida = ois.lectura();
        }
        areaResultats.setText(s);
        areaResultats.setEditable(false);
        ois.close();
    }
    
    /**
     * Metode de lectura selectiva dels resultats per nom
     * @param nom
     * @param areaResultats 
     */
    private void lecturaSelectiva(String nom, JTextArea areaResultats) {
        ois = new LecturaObjectesPartida(fitxer);
        Partida partida = ois.lectura();
        String s = "";
        boolean trobat = false;
        
        while (!Partida.esCentinela(partida)) {
            if (comparacioNoms(partida.getNom(), nom)) {
                s += partida.toString() + "\n";
                areaResultats.append(partida.toString() + "\n");
                trobat = true;
            }
            partida = ois.lectura();
        }
        areaResultats.setText(s);
        areaResultats.setEditable(false);
        
        if (!trobat) {
            JOptionPane.showMessageDialog(null, "NO EXISTEIX CAP PARTIDA AMB EL NOM D'AQUEST JUGADOR '" + nom + "'", "WARNING", JOptionPane.WARNING_MESSAGE);
        }
        ois.close();
    }
}
