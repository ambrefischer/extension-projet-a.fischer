package Plateforme.Satellites.SubSystems;

import Mesures.Image;

public class Imager extends SubSystem {

    /**
     * Constructeur, reprend les attributs de la super classe SubSystem : name et
     * status
     */
    public Imager(String name, boolean status) {
        super(name, status);
    }

    /**
     * Crée une mesure de type Bipmap accompagnée de sa date et la renvoie en format
     * string afin qu'elle puisse être archivée
     */
    public String createData(int mesureCompt) {
        // Création de la mesure
        Image m = new Image();
        m.take_mesure();

        // Transformation en string
        String mesure = String.valueOf(m.getTimeStamp());
        mesure = mesure + " : mesure " + mesureCompt;
        for (int x = 0; x < m.getMesure().length; x++) {
            for (int y = 0; y < m.getMesure().length; y++) {
                double el = m.getMesure()[x][y];
                mesure = mesure + ", " + String.valueOf(el);
            }
        }

        return mesure;
    }

}
