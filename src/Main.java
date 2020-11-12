import Controller.ControlCenter;
import View.Gui;

public class Main {
    public static void main(String[] args) {
        try {
            PlateformCreation pc = new PlateformCreation();
            ControlCenter c = pc.createCC();
            c.initController();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
