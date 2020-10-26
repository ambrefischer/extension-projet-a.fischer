package Plateforme.Satellites;

public class Satellite {

    /**
     * Full name, les sous-classes changent le nom
     */
    private String name;

    /**
     * Controleur de satellite : Classe, est le même quelque soit la famille de
     * satellites
     */
    private OnBoardSystem satelliteControl;

    /**
     * Constructeur avec name et satelliteControl
     */
    public Satellite(String name, OnBoardSystem satelliteControl) {
        this.name = name;
        this.satelliteControl = satelliteControl;
    }

    /**
     * Getter
     * 
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter
     * 
     * @return satelliteControl
     */
    public OnBoardSystem getSatelliteControl() {
        return this.satelliteControl;
    }

    /**
     * Visualise le nom et les sous-systèmes du satellite
     */
    @Override
    public String toString() {
        return "{" + " name='" + getName() + "'," + getSatelliteControl() + "'" + "}";
    }

}
