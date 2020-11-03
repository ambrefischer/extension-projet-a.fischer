package Actions;

import Plateforme.ControlCenter;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.awt.Color;

public class ClickedButtonONOFF extends ClickedButton {
    private JButton button_ON;
    private JButton button_OFF;

    public ClickedButtonONOFF(ControlCenter cc, String nameSat, String nameSS, String message, JLabel label,
            JTextArea textArea, JButton button_ON, JButton button_OFF) {
        super(cc, nameSat, nameSS, message, label, textArea);
        this.button_ON = button_ON;
        this.button_OFF = button_OFF;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(nameSat + ":" + nameSS + ":" + message);
        textArea.append(nameSat + ":" + nameSS + ":" + message + "\n");
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
                textArea.select(5, 7);
                textArea.setSelectionColor(Color.GREEN);
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
