package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import java.awt.Color;

import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import View.Controllers.*;
import Controller.ControlCenter;
import Models.SatelliteFamilies.Satellite;

//new JLabel("<html><font color=black size=+10> + ss.getName() + </i></font></html>")

public class Gui {
    /** fenêtre de l'ihm */
    private JFrame window;
    private ArrayList<String> constellation;
    private HashMap<String, HashMap<String, HashMap<String, JButton>>> satMap;
    private HashMap<String, HashMap<String, JLabel>> labelSatMap;
    private JTextPane textPane;

    /**
     * constructeur de l'Ihm qui va initialiser la fenêtre avec les onglets et les
     * boutons
     * 
     * @throws IOException
     */
    public Gui(ArrayList<String> constellation) throws IOException {

        this.constellation = constellation;

        // fenêtre de l'ihm
        JFrame window = new JFrame("Control Center");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ajout de la console de sortie
        EmptyBorder eb = new EmptyBorder(new Insets(50, 50, 50, 50));
        JTextPane textPane = new JTextPane();
        textPane.setBorder(eb);
        JScrollPane scrollPane = new JScrollPane(textPane);

        // ajout des onglets
        JTabbedPane tabs = new JTabbedPane();

        // pour chaque satellite :
        for (int i = 0; i < constellation.size(); i++) {
            String satellite = constellation.get(i);

            // on récupère les noms des sous-systèmes dans le dossier ARCHI
            ArrayList<String> subsystems = readFile("src/ARCHI/" + satellite);
            int nbSS = subsystems.size();

            // on crée un panneau par sous-système
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(nbSS, 1));

            HashMap<String, HashMap<String, JButton>> ssSysMap = new HashMap<String, HashMap<String, JButton>>();
            HashMap<String, JLabel> labelSsSysMap = new HashMap<String, JLabel>();

            // pour chaque sous-système de satellite :
            for (int j = 0; j < nbSS; j++) {

                // On récupère le nom depuis la liste des sous-systèmes
                String ssName = subsystems.get(j);
                JLabel label = new JLabel(ssName);

                labelSsSysMap.put(ssName, label);

                // Création de boutons
                JButton button_ON = new JButton("ON");
                JButton button_OFF = new JButton("OFF");
                JButton button_DATA = new JButton("DATA");

                HashMap<String, JButton> buttonsMap = new HashMap<String, JButton>();
                buttonsMap.put("ON", button_ON);
                buttonsMap.put("OFF", button_OFF);
                buttonsMap.put("DATA", button_DATA);

                ssSysMap.put(ssName, buttonsMap);

                // Raccord des signaux
                // setControllers(button_ON, button_OFF, button_DATA, satellite, ssName, label,
                // tPane);

                // Assemblage
                panel.add(label);
                panel.add(button_ON);
                panel.add(button_OFF);
                panel.add(button_DATA);
            }

            labelSatMap.put(satellite, labelSsSysMap);
            satMap.put(satellite, ssSysMap);
            // on met le tout dans l'onglet
            tabs.addTab(satellite, panel);
        }

        // on met le tout dans la fenêtre principale
        window.add(tabs, BorderLayout.NORTH);
        window.add(scrollPane);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(dim.width / 2 - window.getWidth() / 2, dim.height / 2 - window.getHeight() / 2);
        window.pack();
        window.setVisible(true);
    }

    /**
     * Getter
     * 
     * @return window
     */
    public JFrame getWindow() {
        return this.window;
    }

    public Gui constellation(ArrayList<String> constellation) {
        this.constellation = constellation;
        return this;
    }

    public ArrayList<String> getConstellation() {
        return this.constellation;
    }

    public HashMap<String, HashMap<String, HashMap<String, JButton>>> getSatMap() {
        return this.satMap;
    }

    public void setWindow(JFrame window) {
        this.window = window;
    }

    public void setConstellation(ArrayList<String> constellation) {
        this.constellation = constellation;
    }

    public void setSatMap(HashMap<String, HashMap<String, HashMap<String, JButton>>> satMap) {
        this.satMap = satMap;
    }

    public HashMap<String, HashMap<String, JLabel>> getLabelSatMap() {
        return this.labelSatMap;
    }

    public void setLabelSatMap(HashMap<String, HashMap<String, JLabel>> labelSatMap) {
        this.labelSatMap = labelSatMap;
    }

    public void refresh(JLabel label, Color color, String text) {

        appendToPane(textPane, text + "\n", color);
        label.setForeground(color);

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

    private void setControllers(JButton button_ON, JButton button_OFF, JButton button_DATA, Satellite sat,
            String ssName, JLabel label, JTextPane tPane) {
        button_ON.addActionListener(new ClickedButtonONOFF(new Model(this.cc), sat.getName(), ssName, "ON", label,
                tPane, button_ON, button_OFF));
        button_OFF.addActionListener(new ClickedButtonONOFF(new Model(this.cc), sat.getName(), ssName, "OFF", label,
                tPane, button_ON, button_OFF));
        button_DATA
                .addActionListener(new ClickedButton(new Model(this.cc), sat.getName(), ssName, "DATA", label, tPane));
    }

    /**
     * Permet de lire les fichiers contenant les noms des sous-systèmes des
     * satellites et de les mettre dans une liste.
     * 
     * @param filename
     * @return ArrayList de String
     * @throws IOException
     */
    private ArrayList<String> readFile(String filename) throws IOException {
        File file = new File(filename);
        BufferedReader in = new BufferedReader(new FileReader(file));
        ArrayList<String> subsystems = new ArrayList<String>();
        String line = in.readLine();
        while (line != null) {
            subsystems.add(line);
            line = in.readLine();
        }
        in.close();

        return subsystems;
    }

}
