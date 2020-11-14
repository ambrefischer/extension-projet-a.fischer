import java.io.IOException;
import java.util.ArrayList;

import Controller.ControlCenter;
import Models.SatelliteFamilies.Satellite;
import View.Gui;

public class Main {
    public static void main(String[] args) throws IOException {

        // Création des modeles et initialisation des liens entre les modèles satellites
        // / sous systèmes
        PlateformCreation pc = new PlateformCreation();
        ArrayList<Satellite> constellation = pc.createConstellation();

        // ???
        ArrayList<String> arch = new ArrayList<>();

        // construction de la view
        Gui view = new Gui(constellation);

        ControlCenter cc = new ControlCenter(arch, constellation, view);

        cc.initController();
    }
}
