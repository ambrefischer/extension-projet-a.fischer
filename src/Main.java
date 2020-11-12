import Gui.View;
import Plateforme.ControlCenter;

public class Main {
    public static void main(String[] args) {
        try {
            PlateformCreation pc = new PlateformCreation();
            ControlCenter cc = pc.createCC();
            new View(cc);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
