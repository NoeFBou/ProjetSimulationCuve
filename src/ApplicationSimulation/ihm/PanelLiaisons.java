package ApplicationSimulation.ihm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class PanelLiaisons extends JPanel implements ActionListener {
    private ControleurGUI ctrl;
    private FrameCuvesGUI frame;

    private JComboBox<String> lstCuves1;
    private JComboBox<String> lstCuves2;
    private JTextField txtTailleTube;
    private JLabel lblResultat;
    private JButton btnLstAdj;
    private JButton btnMatCout;
    private JButton btnMatCoutOpt;
    private JButton btnValider;
    private JTextField txtNom;

    public PanelLiaisons(ControleurGUI ctrl, FrameCuvesGUI frame) {
        this.ctrl = ctrl;
        this.frame = frame;

        this.setLayout(new GridLayout(4, 1));

        // Création des panels
        JPanel panelLiaison = new JPanel();
        JPanel panelRes = new JPanel();
        JPanel panelNom = new JPanel();
        JPanel panelType = new JPanel();

        panelLiaison.setLayout(new GridLayout(1, 7));
        panelRes.setLayout(new BorderLayout());
        panelNom.setLayout(new FlowLayout());
        panelType.setLayout(new FlowLayout());

        // Création des composants
        String[] listeCuves = new String[this.ctrl.getNbCuves()];
        for (int cpt = 0; cpt < listeCuves.length; cpt++)
            listeCuves[cpt] = ("cuve " + (char) ((int) ('A' + cpt)));

        this.lstCuves1 = new JComboBox<String>(listeCuves);
        this.lstCuves2 = new JComboBox<String>(listeCuves);
        this.txtTailleTube = new JTextField(2);
        this.lblResultat = new JLabel("Résultat : ");
        JScrollPane spRes = new JScrollPane(this.lblResultat);

        this.btnLstAdj = new JButton("Liste d'adjacence");
        this.btnMatCout = new JButton("Matrice de Cout");
        this.btnMatCoutOpt = new JButton("Matrice de Cout optimisée");
        this.btnValider = new JButton("Valider");
        this.txtNom = new JTextField(20);

        // Positionnement des composants
        panelLiaison.add(new JLabel("Liaisons : "));
        panelLiaison.add(this.lstCuves1);
        panelLiaison.add(new JLabel("relié à", JLabel.CENTER));
        panelLiaison.add(this.lstCuves2);
        panelLiaison.add(new JLabel("de taille", JLabel.CENTER));
        panelLiaison.add(this.txtTailleTube);
        panelLiaison.add(this.btnValider);
        this.add(panelLiaison);

        spRes.add(this.txtNom);
        panelRes.add(spRes, BorderLayout.CENTER);
        this.add(panelRes);

        panelNom.add(new JLabel(" Nom du fichier : "));
        panelNom.add(this.txtNom);
        this.add(panelNom);

        panelType.add(this.btnLstAdj);
        panelType.add(this.btnMatCout);
        panelType.add(this.btnMatCoutOpt);
        this.add(panelType);

        // Activation des composants
        this.btnValider.addActionListener(this);
        this.btnLstAdj.addActionListener(this);
        this.btnMatCout.addActionListener(this);
        this.btnMatCoutOpt.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnValider) {
            try {
                char cuve1 = ((String) this.lstCuves1.getSelectedItem()).charAt(5);
                char cuve2 = ((String) this.lstCuves2.getSelectedItem()).charAt(5);
                int tailleTube = Integer.parseInt(this.txtTailleTube.getText());

                // creer la relation
                if (this.ctrl.ajouterRelation(cuve1, cuve2, tailleTube)) {
                    lblResultat.setText(lblResultat.getText() + "    " + cuve1 + cuve2 +
                            "(" + tailleTube + ")");
                } else {
                    if (tailleTube < 2 || tailleTube > 10)
                        this.frame.afficherPopUp(
                                "La taille du tube doit-être comprise entre 2 et 10");
                    else if (cuve1 == cuve2)
                        this.frame.afficherPopUp(
                                "Relation impossible : une cuve ne peut pas être relié à elle-même");
                    else
                        this.frame.afficherPopUp(
                                "Relation impossible : la relation choisie existe déjà");
                }
            } catch (Exception ex) {
                this.frame.afficherPopUp(
                        "La valeur renseignée pour le taille du tube doit-être sous la forme d'un entier");
            }

            this.txtTailleTube.setText("");
        }

        if (e.getSource() == btnLstAdj)
            ctrl.genererTxt("lstAdj", this.txtNom.getText());

        if (e.getSource() == btnMatCout)
            ctrl.genererTxt("matCout", this.txtNom.getText());

        if (e.getSource() == btnMatCoutOpt)
            ctrl.genererTxt("matCoutOpt", this.txtNom.getText());

        // Ouvre le fichier précedemment créer et l'ouvre dans l'application n°2
        File fichier = new File(this.txtNom.getText() + ".txt");
        this.ctrl.ouvrir(fichier);
    }
}
