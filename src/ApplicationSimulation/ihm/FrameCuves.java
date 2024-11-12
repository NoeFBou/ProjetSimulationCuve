package ApplicationSimulation.ihm;

import ApplicationSimulation.Controleur;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.io.IOException;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

public class FrameCuves extends JFrame {
    private Controleur ctrl;

    private PanelPeinture panelPeinture;
    private PanelBarre panelBarre;
    private PanelParametre[] tabPanelPara;

    public FrameCuves(Controleur ctrl) {
        this.ctrl = ctrl;
        int nbCuve = this.ctrl.getNbCuves();

        this.setSize(1200, 800);
        this.setLayout(new BorderLayout());
        this.setJMenuBar(new MenuCuves(ctrl));

        // Création des panels
        JPanel panelVisuel = new JPanel(new BorderLayout());
        this.panelPeinture = new PanelPeinture(this.ctrl);
        this.panelBarre = new PanelBarre(this.ctrl);

        JPanel panelReglage;
        if (nbCuve < 4)
            panelReglage = new JPanel(new GridLayout(6, 1));
        else
            panelReglage = new JPanel(new GridLayout(nbCuve, 1));

        JScrollPane spReglage = new JScrollPane(panelReglage,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        spReglage.setPreferredSize(new Dimension(170, 0));

        this.tabPanelPara = new PanelParametre[this.ctrl.getNbCuves()];
        for (int i = 0; i < this.tabPanelPara.length; i++)
            this.tabPanelPara[i] = new PanelParametre(this.ctrl, (char) ('A' + i));

        // Positionnement des composants
        panelVisuel.add(this.panelPeinture, BorderLayout.CENTER);
        panelVisuel.add(this.panelBarre, BorderLayout.SOUTH);
        this.add(panelVisuel, BorderLayout.CENTER);

        for (int i = 0; i < this.tabPanelPara.length; i++)
            panelReglage.add(this.tabPanelPara[i]);
        this.add(spReglage, BorderLayout.EAST);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    // methodes

    public void majIHM() {
        this.panelPeinture.majIHM();

        for (PanelParametre p : this.tabPanelPara)
            p.majIHM();
    }

    public void exporter() {
        String nomFichier;

        // Importation du panel en image
        Dimension d = this.panelPeinture.getSize();
        BufferedImage image = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        this.panelPeinture.print(g2d);
        g2d.dispose();

        // Choix du nom du fichier
        nomFichier = JOptionPane.showInputDialog("Entrez le nom du fichier");

        // Création du répertoire export si il n'existe pas déjà
        if (nomFichier != null) {
            File f1 = new File("export");

            if (!f1.exists())
                f1.mkdir();

            // Enregistrement du fichier dans le répertoire export
            try {
                ImageIO.write(image, "png", new File("export\\" + nomFichier + ".png"));
                JOptionPane.showMessageDialog(this, "Exportation réussi dans le dossier export");
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erreur lors de l'exportation");
            }
        }
    }

    public void exporterSous() {
        // Ouvrir le menu pour choisir un répertoire de sauvegarde
        JFileChooser choose = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        String filePath = "";

        int res = choose.showSaveDialog(null);

        if (res == JFileChooser.APPROVE_OPTION) {
            // Choix du nom du fichier
            File file = choose.getSelectedFile();
            filePath = file.getAbsolutePath();

            // Importation du panel en image
            Dimension d = this.panelPeinture.getSize();
            BufferedImage image = new BufferedImage(d.width, d.height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = image.createGraphics();
            this.panelPeinture.print(g2d);
            g2d.dispose();

            // Enregistrement du fichier dans le répertoire choisi
            try {
                ImageIO.write(image, "png", new File(filePath + ".png"));
                JOptionPane.showMessageDialog(this, "Exportation réussi");
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erreur lors de l'exportation");
            }
        }
    }
}