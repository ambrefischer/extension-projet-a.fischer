package Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;

import Controller.ActionListener.ButtonListener;
import Models.SatelliteFamilies.Satellite;
import View.Gui;

public class ControlCenter {

    /** archive des mesures effectuées */
    private ArrayList<String> archive;

    /** ensemble des satellites visible par le ControlCenter */
    private ArrayList<Satellite> constellation;
    private ArrayList<String> constelString;

    /** décompte utilisé pour quantifier le nombre de mesures archivées */
    private int mesureCompt = 1;

    /**
     * permet de visualiser si la commande a été effectuée : "OK" ou non : "KO:..."
     */
    private String stateCommand = "";

    /** interface graphique : view */
    private Gui view;

    /**
     * Constructeur, avec archive et constellation
     * 
     * @param archive
     * @param constellation
     * @throws IOException
     */
    public ControlCenter(ArrayList<String> archive, ArrayList<Satellite> constellation) throws IOException {
        this.archive = archive;
        this.constellation = constellation;

        // construit constelString à partir des satellites contenus dans constellation
        constelString = new ArrayList<String>();

        for (Satellite satellite : constellation) {
            constelString.add(satellite.getName());
        }

        // construction de la view
        view = new Gui(constelString);
    }

    /**
     * Getter
     * 
     * @return archive
     */
    public ArrayList<String> getArchive() {
        for (String el : archive) {
            System.out.println(el);
        }
        return this.archive;
    }

    /**
     * Getter
     * 
     * @return constellation
     */
    public ArrayList<Satellite> getConstellation() {
        return this.constellation;
    }

    /**
     * Getter
     * 
     * @return stateCommand
     */
    public String getStateCommand() {
        return this.stateCommand;
    }

    /**
     * Ajouter une mesure à l'archive
     * 
     * @param mesure
     */
    private void addArchive(String mesure) {
        archive.add(mesure);
    }

    /**
     * Visualise le ControlCenter
     */
    @Override
    public String toString() {
        return "{" + " archive='" + getArchive() + "'" + ", constellation='" + getConstellation() + "'" + "}";
    }

    /** change la constellation de l'ihm */
    public void initView() {
        view.constellation(constelString);
    }

    /**
     * permet de relier les signaux des boutons aux actions correspondantes
     */
    public void initController() {
        // contient les boutons et les labels
        HashMap<String, HashMap<String, HashMap<String, JButton>>> satMap = view.getSatMap();
        HashMap<String, HashMap<String, JLabel>> labelSatMap = view.getLabelSatMap();

        for (String satKey : satMap.keySet()) {
            HashMap<String, HashMap<String, JButton>> ssSysMap = satMap.get(satKey);
            HashMap<String, JLabel> labelSsSysMap = labelSatMap.get(satKey);

            for (String ssSysKey : ssSysMap.keySet()) {
                HashMap<String, JButton> buttonsMap = ssSysMap.get(ssSysKey);

                // on récupère tous les boutons existants de la HashMap du view pour leur
                // associer un ButtonListener en spécifiant le nom du satellite et le nom du
                // sous-système correspondand à chaque fois
                ButtonListener onListener = new ButtonListener(this, satKey, ssSysKey, "ON", view,
                        labelSsSysMap.get(ssSysKey));
                buttonsMap.get("ON").addActionListener(onListener);

                ButtonListener offListener = new ButtonListener(this, satKey, ssSysKey, "OFF", view,
                        labelSsSysMap.get(ssSysKey));
                buttonsMap.get("OFF").addActionListener(offListener);

                ButtonListener dataListener = new ButtonListener(this, satKey, ssSysKey, "DATA", view,
                        labelSsSysMap.get(ssSysKey));
                buttonsMap.get("DATA").addActionListener(dataListener);
            }
        }

    }

    /**
     * Vérifie que le ControlCenter possède le satellite demandé et envoie la suite
     * de l'ordre au satellite imprime les résultats d'ordre : OK ou KO avec
     * spécification et éventuellement archive la mesure si demandé.
     * 
     * @param satellite
     * @param subsystem
     * @param message
     */
    public void command(String satellite, String subsystem, String message) {

        // compteur qui s'incrémente si le satellite demandé par l'utilisateur n'est pas
        // le satellite regardé dans le parcours de la boucle for
        int compt = 0;

        // Parcours la liste des satellites du ControlCenter
        for (Satellite satel : constellation) {

            // Vérifie si le satellite demandé par l'opérateur correspond à celui dans le
            // parcours de la boucle for
            if (satel.getName().equals(satellite)) {

                // On envoie la commande au satellite en question. Il nous retourne "OK", "KO"
                // (avec spécifications) ou la mesure.
                String data = satel.getSatelliteControl().invokeCommand(subsystem, message, this.mesureCompt);
                this.stateCommand = data;
                // Si on doit archiver la mesure alors data contiendra "mesure"

                if (data.contains("mesure")) {
                    // dans le cas où il s'agit d'une T/M
                    System.out.println("OK");
                    this.stateCommand = "OK";
                    addArchive(data);
                    // Incrémentation du compteur de mesure
                    this.mesureCompt++;
                } else {
                    System.out.println(data);
                }

            }

            // Incrémentation si le satellite demandé n'est pas le satellite regardé
            else {
                compt++;
            }
        }

        // Si le satellite demandé n'est pas connu du ControlCenter
        if (compt == constellation.size()) {
            System.out.println("KO : Wrong satellite");
            this.stateCommand = "KO : Wrong satellite";
        }

    }

}
