
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.text.JTextComponent;
import javax.swing.JButton;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import Plateforme.Satellites.Satellite;
import Plateforme.Satellites.SubSystem;

import Actions.ClickedButton;
import Actions.ClickedButtonONOFF;

//new JLabel("<html><font color=black size=+10> + ss.getName() + </i></font></html>")

public class Ihm {

    private JFrame window;

    public Ihm() throws IOException {

        // Création du ControlCenter
        PlateformCreation c = new PlateformCreation();
        Plateforme.ControlCenter cc = c.createCC();

        // main window
        JFrame window = new JFrame("Control Center");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ajout de la console de sortie
        JTextArea textArea = new JTextArea(5, 30);
        JScrollPane scrollPane = new JScrollPane(textArea);

        // tabs
        JTabbedPane tabs = new JTabbedPane();

        // pour chaque satellite
        for (int i = 0; i < cc.getConstellation().size(); i++) {
            Satellite sat = cc.getConstellation().get(i);
            int nbSS = sat.getSatelliteControl().getEquipments().size();
            JPanel pannel = new JPanel();
            pannel.setLayout(new GridLayout(nbSS, 1));

            // pour chaque sous-système de satellite
            for (int j = 0; j < nbSS; j++) {
                SubSystem ss = sat.getSatelliteControl().getEquipments().get(j);
                JLabel label = new JLabel(ss.getName());

                // Création de boutons
                JButton button_ON = new JButton("ON");
                JButton button_OFF = new JButton("OFF");
                JButton button_DATA = new JButton("DATA");

                // Raccord des signaux
                button_ON.addActionListener(new ClickedButtonONOFF(cc, sat.getName(), ss.getName(), "ON", label,
                        textArea, button_ON, button_OFF));
                button_OFF.addActionListener(new ClickedButtonONOFF(cc, sat.getName(), ss.getName(), "OFF", label,
                        textArea, button_ON, button_OFF));
                button_DATA
                        .addActionListener(new ClickedButton(cc, sat.getName(), ss.getName(), "DATA", label, textArea));

                // Assemblage
                pannel.add(label);
                pannel.add(button_ON);
                pannel.add(button_OFF);
                pannel.add(button_DATA);
            }

            // on met le tout dans l'onglet
            tabs.addTab(sat.getName(), pannel);
        }

        // on met le tout dans le main window
        window.add(tabs);

        window.add(scrollPane, BorderLayout.SOUTH);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(dim.width / 2 - window.getWidth() / 2, dim.height / 2 - window.getHeight() / 2);
        window.pack();
        window.setVisible(true);
    }

    public JFrame getWindow() {
        return this.window;
    }

}
