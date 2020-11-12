package Gui.Controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JTextPane;

import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import Gui.Model;

import java.awt.Color;

public class ClickedButton implements ActionListener {
    /**
     * Permettent de faire la T/C ou T/M
     */
    protected Model model;
    protected String nameSat;
    protected String nameSS;
    protected String message;

    /**
     * Permettent de changer la couleur dans l'ihm
     */
    protected JLabel label;
    protected JTextPane textPane;

    /**
     * Constructeur
     * 
     * @param cc
     * @param nameSat
     * @param nameProcedure
     * @param message
     * @param label
     * @param textPane
     */
    public ClickedButton(Model model, String nameSat, String nameSS, String message, JLabel label, JTextPane textPane) {
        this.model = model;
        this.nameSat = nameSat;
        this.nameSS = nameSS;
        this.message = message;
        this.label = label;
        this.textPane = textPane;
    }

    @Override
    /**
     * Permet de faire effectuer la commande au control center et de changer la
     * couleur
     */
    public void actionPerformed(ActionEvent e) {
        System.out.println(nameSat + ":" + nameSS + ":" + message);

        // fait appel au model pour exécuter la commande
        model.doTM(nameSat, nameSS, message);

        // Si la commande est valide, on met le nom du sous-système en vert
        // et on ajoute la commande dans la console de sortie
        if (model.getStatusCommand()) {
            appendToPane(textPane, nameSat + ":" + nameSS + ":" + message + "\n", Color.GREEN);
            label.setForeground(Color.GREEN);
        }
        // sinon en rouge
        else {
            appendToPane(textPane, nameSat + ":" + nameSS + ":" + message + "\n", Color.RED);
            label.setForeground(Color.RED);
        }
    }

    /**
     * ajoute la T/C ou T/M la couleur dans la console de sortie et lui met une
     * couleur
     * 
     * @param textPane
     * @param msg
     * @param c
     */
    public void appendToPane(JTextPane textPane, String msg, Color c) {
        textPane.setEditable(true);
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        int len = textPane.getDocument().getLength();
        textPane.setCaretPosition(len);
        textPane.setCharacterAttributes(aset, false);
        textPane.replaceSelection(msg);
        textPane.setEditable(false);
    }
};
