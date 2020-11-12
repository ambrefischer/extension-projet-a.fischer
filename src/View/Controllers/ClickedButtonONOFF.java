package View.Controllers;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextPane;

import View.Model;

import java.awt.Color;

public class ClickedButtonONOFF extends ClickedButton {
    /** bouton ON */
    private JButton button_ON;

    /** bouton OFF */
    private JButton button_OFF;

    /**
     * Constructeur hérite de ClickedButton
     * 
     * @param nameSat
     * @param nameProcedure
     * @param message
     * @param label
     * @param textPane
     * @param button_ON
     * @param button_OFF
     */
    public ClickedButtonONOFF(Model model, String nameSat, String nameSS, String message, JLabel label,
            JTextPane textPane, JButton button_ON, JButton button_OFF) {
        super(model, nameSat, nameSS, message, label, textPane);
        this.button_ON = button_ON;
        this.button_OFF = button_OFF;
    }

    /**
     * Permet de faire effectuer la commande au control center et de changer la
     * couleur et de mettre la couleur du bouton bleu si il est actif.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(nameSat + ":" + nameSS + ":" + message);

        // fait appel au model pour exécuter la commande
        model.doTM(nameSat, nameSS, message);

        // si la commande est valide alors on met le nom du sous-système en vert
        // et on ajoute la commande dans la console de sortie
        if (model.getStatusCommand()) {
            label.setForeground(Color.GREEN);
            appendToPane(textPane, nameSat + ":" + nameSS + ":" + message + "\n", Color.GREEN);
        }
        // sinon en rouge
        else {
            label.setForeground(Color.RED);
            appendToPane(textPane, nameSat + ":" + nameSS + ":" + message + "\n", Color.RED);
        }

        switch (message) {
            // si le bouton ON est clické alors seulenemnt lui apparaît en bleu
            case "ON":
                button_ON.setForeground(Color.BLUE);
                button_OFF.setForeground(Color.BLACK);
                break;

            // si le bouton OFF est clické alors seulenemnt lui apparaît en bleu
            case "OFF":
                button_OFF.setForeground(Color.BLUE);
                button_ON.setForeground(Color.BLACK);
                break;

            default:
                break;
        }
    };

}
