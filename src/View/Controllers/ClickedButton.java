package View.Controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JTextPane;

import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import View.Gui;
import View.Model;

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
    protected Gui view;
    protected JLabel label;

    /**
     * Constructeur
     * 
     * @param cc
     * @param nameSat
     * @param nameProcedure
     * @param message
     * @param view
     */
    public ClickedButton(Model model, String nameSat, String nameSS, String message, Gui view, JLabel label) {
        this.model = model;
        this.nameSat = nameSat;
        this.nameSS = nameSS;
        this.message = message;
        this.view = view;
        this.label = label;
    }

    @Override
    /**
     * Permet de faire effectuer la commande au control center et de changer la
     * couleur
     */
    public void actionPerformed(ActionEvent e) {
        System.out.println(nameSat + ":" + nameSS + ":" + message);

        // fait appel au model pour exécuter la commande
        cc.command(nameSat + ":" + nameSS + ":" + message);

        // Si la commande est valide, on renvoie true pour que le controlleur change
        // l'interface de l'ihm
        if (cc.getStateCommand().equals("OK")) {
            view.refresh(label, Color.GREEN, nameSat + ":" + nameSS + ":" + message);
        } else {
            view.refresh(label, Color.RED, nameSat + ":" + nameSS + ":" + message);
        }

    }

}
