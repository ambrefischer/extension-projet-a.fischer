package Gui;

import Plateforme.ControlCenter;

public class Model {

    private ControlCenter cc;
    private boolean statusCommand = true;

    public Model(ControlCenter cc) {
        this.cc = cc;
    }

    public boolean getStatusCommand() {
        return this.statusCommand;
    }

    public void doTM(String nameSat, String nameSS, String message) {
        cc.command(nameSat + ":" + nameSS + ":" + message);

        // Si la commande est valide, on renvoie true pour que le controlleur change
        // l'interface de l'ihm
        if (cc.getStateCommand().equals("OK")) {
            statusCommand = true;
        } else {
            statusCommand = false;
        }

    }

}
