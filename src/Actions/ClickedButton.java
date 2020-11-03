package Actions;

import Plateforme.ControlCenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.text.JTextComponent;

import java.awt.Color;

public class ClickedButton implements ActionListener {
    protected ControlCenter cc;
    protected String nameSat;
    protected String nameSS;
    protected String message;
    protected JLabel label;
    protected JTextArea textArea;

    public ClickedButton(ControlCenter cc, String nameSat, String nameSS, String message, JLabel label,
            JTextArea textArea) {
        this.cc = cc;
        this.nameSat = nameSat;
        this.nameSS = nameSS;
        this.message = message;
        this.label = label;
        this.textArea = textArea;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(nameSat + ":" + nameSS + ":" + message);
        cc.command(nameSat + ":" + nameSS + ":" + message);
        if (cc.getStateCommand() == "OK") {
            JPanel panel = new JPanel();
            textArea.append(nameSat + ":" + nameSS + ":" + message + "\n");
            textArea.setForeground(Color.GREEN);
            // textArea.select(0, 46);
            // textArea.setSelectedTextColor(Color.GREEN);
            // textArea.setSelectionColor(Color.GREEN);
            label.setForeground(Color.GREEN);
        } else {
            label.setForeground(Color.RED);
        }
    }
};
