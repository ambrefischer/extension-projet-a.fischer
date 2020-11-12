package Mesures;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Mesure {
    private String Time;

    /**
     * Constructeur de Mesure, fixe la date et l'heure de la mesure
     */
    public Mesure() {
        Time = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
    }

    /**
     * Permet d'obtenir l'estampille temporelle
     * 
     * @return l'estampille temporelle
     */
    public String getTimeStamp() {
        return this.Time;
    }

    /**
     * Permet de faire la mesure, dépend du type de mesure
     */
    public abstract void take_mesure();

    /**
     * Permet d'afficher la donnée mesurée, dépend du type de mesure
     */
    public abstract void print_mesure();

    public abstract Object getMesure();

}
