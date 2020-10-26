package Plateforme.Satellites;

import java.util.ArrayList;

public class OnBoardSystem {

    /**
     * liste des sous-systèmes du satellite gérés par le contrôleur
     */
    private ArrayList<SubSystem> equipments;

    /**
     * Constructeur avec equipements
     * 
     * @param equipments
     */
    public OnBoardSystem(ArrayList<SubSystem> equipments) {
        this.equipments = equipments;
    }

    /**
     * Getter
     * 
     * @return equipments
     */
    public ArrayList<SubSystem> getEquipments() {
        return this.equipments;
    }

    /**
     * Vérifie si le sous-système existe dans sa liste equipments
     * 
     * @param subsysOperator
     * @param doesOperator
     * @return "OK" si il existe ou "KO" (avec spécifications) sinon
     */
    private String existEquipment(String subsysOperator, String doesOperator) {

        // Parcours de la liste des sous-systèmes appartenant au satellite
        for (SubSystem subsys : equipments) {

            // Si le sous systeme demandé correspond au sous-système regardé dans le
            // parcours de la boucle for
            if (subsys.getName().equals(subsysOperator)) {

                // Pour une T/C : on regarde si la commande est possible : la commande doit
                // changer le statut du sous-système
                if ((doesOperator.equals("ON") && subsys.getStatus() == false)
                        || (doesOperator.equals("OFF") && subsys.getStatus() == true)) {
                    // alors on pourra effectuer la commande T/C.
                    return "OK";
                }

                // Pour une T/M : on regarde si le sous-système est en ON
                else if ((doesOperator.equals("DATA") && subsys.getStatus() == true)) {
                    // alors on pourra effectuer la mesure.
                    return "OK";
                }

                // Sinon la T/C ou T/M n'est pas possible.
                return "KO : Wrong command";

            }
        }
        // Si le sous-système demandé n'est pas dans la liste equipments, on renvoie
        // "KO..."
        return "KO : Wrong subsystem";
    }

    /**
     * Procède à l'exécution de la commande ou de la mesure
     * 
     * @param subsysOperator
     * @param doesOperator
     * @param mesureCompt
     * @return
     */
    public String invokeCommand(String subsysOperator, String doesOperator, int mesureCompt) {
        String response = null;

        // Afin de faire la T/C ou T/M, on doit s'assurer que cela est possible
        if (existEquipment(subsysOperator, doesOperator) == "OK") {

            // On retrouve le bon sous-système
            int i = 0;
            SubSystem subsys = equipments.get(i);
            while (!(subsys.getName().equals(subsysOperator))) {
                subsys = equipments.get(i + 1);
            }

            // Si il s'agit d'une T/C
            if (doesOperator.equals("ON") || doesOperator.equals("OFF")) {

                response = subsys.command();

            }

            // Si il s'agit d'une T/M
            else {
                response = subsys.createData(mesureCompt);
            }

        }

        // Si la T/C ou T/M n'est pas possible, on renvoie "KO..."
        else {
            response = existEquipment(subsysOperator, doesOperator);
        }

        return response;

    }

    /**
     * Visualise la liste des equipements du satellite
     */
    @Override
    public String toString() {
        return " equipments='" + getEquipments() + "'" + "}";
    }

}