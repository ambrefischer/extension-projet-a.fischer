import java.util.ArrayList;
import Plateforme.ControlCenter;
import Plateforme.Satellites.*;
import Plateforme.Satellites.SatelliteFamilies.*;
import Plateforme.Satellites.SubSystems.Imager;

public class PlateformCreation {

    public ControlCenter createCC() {

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

        // Création de XSAT de la famille des XSatellite avec deux sous-systèmes de type
        // Bitmap et son systeme de contrôle embarqué
        SubSystem eqX1 = new Imager("IMAGER1", false);
        SubSystem eqX2 = new Imager("IMAGER2", false);
        ArrayList<SubSystem> equiX = new ArrayList<>();
        equiX.add(eqX1);
        equiX.add(eqX2);
        OnBoardSystem onBoardX = new OnBoardSystem(equiX);
        Satellite XSAT = new XSatellite("SAT", onBoardX);

        // Création du Centre de Contrôle visualisant les deux satellites ISAESAT et
        // XSAT
        ArrayList<Satellite> constel = new ArrayList<>();
        constel.add(ISAESAT);
        constel.add(XSAT);
        ArrayList<String> arch = new ArrayList<>();
        ControlCenter cc = new ControlCenter(arch, constel);

        System.out.println(cc);

        // Boucle itérative de demande d'ordre venant de l'utilisateur
        return cc;

    }
}
