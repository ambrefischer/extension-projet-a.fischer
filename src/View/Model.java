package View;

import Controller.ControlCenter;

public class Model {

    private ControlCenter cc;
    private boolean statusCommand = true;

    public Model(ControlCenter cc) {
        this.cc = cc;
    }

    public boolean getStatusCommand() {
        return this.statusCommand;
    }

}
