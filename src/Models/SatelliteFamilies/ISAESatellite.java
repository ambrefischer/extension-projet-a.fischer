package Models.SatelliteFamilies;

import Models.OnBoardSystem;

public class ISAESatellite extends Satellite {

    /**
     * nom du satellite
     */
    private String name;

    /**
     * Constructeur qui ajoute "X" en préfixe du nom du satellite pour faire
     * référence à sa famille d'appartenance et reprend les attributs name et
     * satelliteControl de la super-classe Satellite
     */
    public ISAESatellite(String name, OnBoardSystem satelliteControl) {
        super(name, satelliteControl);
        this.name = "ISAE" + name;
    }

    /**
     * Getter
     * 
     * @return name
     */
    public String getName() {
        return this.name;
    }

}
