import java.io.IOException;

import Controller.ControlCenter;

public class Main {
    public static void main(String[] args) throws IOException {

        PlateformCreation pc = new PlateformCreation();
        ControlCenter cc = pc.createCC();
        cc.initController();
    }
}
