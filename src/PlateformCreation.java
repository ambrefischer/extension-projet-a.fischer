import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import Models.OnBoardSystem;
import Models.SatelliteFamilies.*;
import Models.SubSystems.*;

public class PlateformCreation {

    public ArrayList<Satellite> createConstellation() throws IOException {

        // Création de ISAESAT de la famille des ISAESatellite avec deux sous-systèmes
        // de type Bitmap et son systeme de contrôle embarqué
        SubSystem eqISAE1 = new Imager("IMAGER1", false);
        SubSystem eqISAE2 = new Imager("IMAGER2", false);
        SubSystem eqISAE3 = new Imager("IMAGER3", false);
        SubSystem eqISAE4 = new Imager("IMAGER4", false);
        ArrayList<SubSystem> equiISAE = new ArrayList<>();
        equiISAE.add(eqISAE1);
        equiISAE.add(eqISAE2);
        equiISAE.add(eqISAE3);
        equiISAE.add(eqISAE4);
        OnBoardSystem onBoardISAE = new OnBoardSystem(equiISAE);
        Satellite ISAESAT = new ISAESatellite("SAT", onBoardISAE);
        createFile(ISAESAT);

        // Création de XSAT de la famille des XSatellite avec deux sous-systèmes de type
        // Bitmap et son systeme de contrôle embarqué
        SubSystem eqX1 = new Imager("IMAGER1", false);
        SubSystem eqX2 = new Imager("IMAGER2", false);
        ArrayList<SubSystem> equiX = new ArrayList<>();
        equiX.add(eqX1);
        equiX.add(eqX2);
        OnBoardSystem onBoardX = new OnBoardSystem(equiX);
        Satellite XSAT = new XSatellite("SAT", onBoardX);
        createFile(XSAT);

        // Création du Centre de Contrôle visualisant les deux satellites ISAESAT et
        // XSAT
        ArrayList<Satellite> constel = new ArrayList<>();
        constel.add(ISAESAT);
        constel.add(XSAT);

        return constel;

    }

    /**
     * Permet de créer un fichier par satellite contenant le nom des ses
     * sous-systèmes
     * 
     * @param sat
     * @throws IOException
     */
    private void createFile(Satellite sat) throws IOException {
        String filename = "src/ARCHI/" + sat.getName();
        File file = new File(filename);
        PrintWriter pw = new PrintWriter(new FileWriter(file));
        int nbSS = sat.getSatelliteControl().getEquipments().size();
        for (int i = 0; i < nbSS; i++) {
            SubSystem ss = sat.getSatelliteControl().getEquipments().get(i);
            pw.println(ss.getName());
        }
        pw.close();
    }
}
