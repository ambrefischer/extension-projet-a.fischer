package Ihm;

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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import Ihm.Actions.ClickedButton;
import Ihm.Actions.ClickedButtonONOFF;
import Plateforme.ControlCenter;
import Plateforme.Satellites.Satellite;

//new JLabel("<html><font color=black size=+10> + ss.getName() + </i></font></html>")

public class Ihm {
    /** fenêtre de l'ihm */
    private JFrame window;

    /**
     * constructeur de l'Ihm qui va initialiser la fenêtre avec les onglets et les
     * boutons
     * 
     * @throws IOException
     */
    public Ihm() throws IOException {

        // Création du ControlCenter
        PlateformCreation c = new PlateformCreation();
        Plateforme.ControlCenter cc = c.createCC();

        // fenêtre de l'ihm
        JFrame window = new JFrame("Control Center");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ajout de la console de sortie
        EmptyBorder eb = new EmptyBorder(new Insets(50, 50, 50, 50));
        JTextPane tPane = new JTextPane();
        tPane.setBorder(eb);
        JScrollPane scrollPane = new JScrollPane(tPane);

        // ajout des onglets
        JTabbedPane tabs = new JTabbedPane();

        // pour chaque satellite :
        for (int i = 0; i < cc.getConstellation().size(); i++) {
            Satellite sat = cc.getConstellation().get(i);

            // on récupère les noms des sous-systèmes dans le dossier ARCHI
            ArrayList<String> subsystems = readFile("src/ARCHI/" + sat.getName());
            int nbSS = subsystems.size();

            // on crée un panneau par sous-système
            JPanel pannel = new JPanel();
            pannel.setLayout(new GridLayout(nbSS, 1));

            // pour chaque sous-système de satellite :
            for (int j = 0; j < nbSS; j++) {

                // On récupère le nom depuis la liste des sous-systèmes
                String ssName = subsystems.get(j);
                JLabel label = new JLabel(ssName);

                // Création de boutons
                JButton button_ON = new JButton("ON");
                JButton button_OFF = new JButton("OFF");
                JButton button_DATA = new JButton("DATA");

                // Raccord des signaux
                button_ON.addActionListener(
                        new ClickedButtonONOFF(cc, sat.getName(), ssName, "ON", label, tPane, button_ON, button_OFF));
                button_OFF.addActionListener(
                        new ClickedButtonONOFF(cc, sat.getName(), ssName, "OFF", label, tPane, button_ON, button_OFF));
                button_DATA.addActionListener(new ClickedButton(cc, sat.getName(), ssName, "DATA", label, tPane));

                // Assemblage
                pannel.add(label);
                pannel.add(button_ON);
                pannel.add(button_OFF);
                pannel.add(button_DATA);
            }

            // on met le tout dans l'onglet
            tabs.addTab(sat.getName(), pannel);
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
