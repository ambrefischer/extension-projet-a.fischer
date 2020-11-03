package Actions;

import Plateforme.ControlCenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Color;

public class ClickedButton implements ActionListener {
    private ControlCenter cc;
    private String nameSat;
    private String nameSS;
    private String message;
    private JLabel label;
    private JButton button_ON;
    private JButton button_OFF;

    public ClickedButton(ControlCenter cc, String nameSat, String nameSS, String message, JLabel label,
            JButton button_ON, JButton button_OFF) {
        this.cc = cc;
        this.nameSat = nameSat;
        this.nameSS = nameSS;
        this.message = message;
        this.label = label;
        this.button_ON = button_ON;
        this.button_OFF = button_OFF;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(nameSat + ":" + nameSS + ":" + message);
        cc.command(nameSat + ":" + nameSS + ":" + message);
        if (cc.getStateCommand() == "OK") {
            label.setForeground(Color.GREEN);
        } else {
            label.setForeground(Color.RED);
        }
        switch (message) {
            case "ON":
                button_ON.setForeground(Color.BLUE);
                button_OFF.setForeground(Color.BLACK);
                break;

            case "OFF":
                button_OFF.setForeground(Color.BLUE);
                button_ON.setForeground(Color.BLACK);
                break;

            default:
                break;
        }
    };

}
