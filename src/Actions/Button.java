package Actions;

import Plateforme.ControlCenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import java.awt.Color;

public class Button implements ActionListener {
    private ControlCenter cc;
    private String nameSat;
    private String nameSS;
    private String message;
    private JLabel label;

    public Button(ControlCenter cc, String nameSat, String nameSS, String message, JLabel label) {
        this.cc = cc;
        this.nameSat = nameSat;
        this.nameSS = nameSS;
        this.message = message;
        this.label = label;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(nameSat + ":" + nameSS + ":" + message);
        cc.command(nameSat + ":" + nameSS + ":" + message);
        switch (message) {
            case "ON":
                label.setForeground(Color.GREEN);
                break;

            case "OFF":
                label.setForeground(Color.RED);
                break;

            default:
                break;
        }
    };

}
