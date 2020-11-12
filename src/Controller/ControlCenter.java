package Plateforme;

import java.util.ArrayList;
import java.util.Scanner;

import Plateforme.Satellites.SatelliteFamilies.Satellite;

public class ControlCenter {

    /** archive des mesures effectuées */
    private ArrayList<String> archive;

    /** ensemble des satellites visible par le ControlCenter */
    private ArrayList<Satellite> constellation;

    /** décompte utilisé pour quantifier le nombre de mesures archivées */
    private int mesureCompt = 1;

    private String stateCommand = "";

    /**
     * Constructeur, avec archive et constellation
     * 
     * @param archive
     * @param constellation
     */

    public ControlCenter(ArrayList<String> archive, ArrayList<Satellite> constellation) {
        this.archive = archive;
        this.constellation = constellation;
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

    /**
     * Vérifie que le ControlCenter possède le satellite demandé et envoie la suite
     * de l'ordre au satellite imprime les résultats d'ordre : OK ou KO avec
     * spécification et éventuellement archive la mesure si demandé.
     */
    public void command(String message) {
        // System.out.println("Veuillez rentrer une commande du type
        // SATELLITE:SUBSYSTEM:COMMAND/DATA");

        // Lecture de l'ordre de l'utilisateur
        Scanner sc = new Scanner(message);
        String order = sc.next();

        Scanner s = new Scanner(order).useDelimiter(":");
        String satOperator = s.next();

        // On vérifie que l'ordre possède plus que juste le satellite, sinon on
        // redemmande l'ordre

        // Commande qui permet d'arrêter d'envoyer des ordres
        if (satOperator.equals("stop")) {
            sc.close();
            s.close();
            System.exit(0);
        }

        String correctCommand = satOperator;

        if (correctCommand.length() == order.length()) {
            return;
        }

        String subsysOperator = s.next();

        // On vérifie que l'ordre possède plus que juste le satellite et le sous-système
        correctCommand = correctCommand + ":" + subsysOperator;
        if (!(correctCommand.length() == order.length())) {
            String doesOperator = s.next();

            // On vérifie que l'ordre ne possède pas trop données
            correctCommand = correctCommand + ":" + doesOperator;
            if (correctCommand.length() == order.length()) {
                // compteur qui s'incrémente si le satellite demandé par l'utilisateur n'est pas
                // le satellite regardé dans le parcours de la boucle for
                int compt = 0;

                // Parcours la liste des satellites du ControlCenter
                for (Satellite satel : constellation) {

                    // Vérifie si le satellite demandé par l'opérateur correspond à celui dans le
                    // parcours de la boucle for
                    if (satel.getName().equals(satOperator)) {

                        // On envoie la commande au satellite en question. Il nous retourne "OK", "KO"
                        // (avec spécifications) ou la mesure.
                        String data = satel.getSatelliteControl().invokeCommand(subsysOperator, doesOperator,
                                this.mesureCompt);
                        this.stateCommand = data;
                        // Si on doit archiver la mesure alors data contiendra "mesure"

                        if (data.contains("mesure")) {

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
                // }

            }
        }

    }

}
