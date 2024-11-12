package ApplicationSimulation.ihm;

import ApplicationSimulation.Controleur;

import javax.swing.*;
import java.io.File;

import java.awt.event.*;

public class MenuCuves extends JMenuBar implements ActionListener {
	private Controleur ctrl;

	private JMenuItem menuiFichierNouveau;
	private JMenuItem menuiFichierOuvrir;
	private JMenuItem menuiFichierEnregistrer;
	private JMenuItem menuiFichierEnregistrerSous;
	private JMenuItem menuiFichierQuitter;

	public MenuCuves(Controleur ctrl) {
		this.ctrl = ctrl;
		/*-------------------------*/
		/* Création des composants */
		/*-------------------------*/

		// un element de la barre de menu
		JMenu menuFichier = new JMenu("Fichier");
		menuFichier.setMnemonic('F');

		// les items du menu fichier

		this.menuiFichierNouveau = new JMenuItem("Nouveau");
		this.menuiFichierNouveau.setMnemonic('N');
		this.menuiFichierOuvrir = new JMenuItem("Ouvrir");
		this.menuiFichierOuvrir.setMnemonic('O');
		this.menuiFichierEnregistrer = new JMenuItem("Enregistrer");
		this.menuiFichierEnregistrer.setMnemonic('S');
		this.menuiFichierEnregistrerSous = new JMenuItem("EnregistrerSous");
		this.menuiFichierEnregistrerSous.setMnemonic('M');
		this.menuiFichierQuitter = new JMenuItem("Quitter");
		this.menuiFichierQuitter.setMnemonic('Q');

		// ajouts des items au menu correspondant
		menuFichier.add(this.menuiFichierNouveau);
		menuFichier.add(this.menuiFichierOuvrir);
		menuFichier.addSeparator();
		menuFichier.add(this.menuiFichierEnregistrer);
		menuFichier.add(this.menuiFichierEnregistrerSous);
		menuFichier.addSeparator();
		menuFichier.add(this.menuiFichierQuitter);

		// ajout du menu 'Fichier' a la barre de menu
		this.add(menuFichier);

		/*-------------------------------*/
		/* positionnement des composants */
		/*-------------------------------*/
		this.menuiFichierNouveau.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK)); // pour
																													// CTRL+N
		this.menuiFichierOuvrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK)); // pour
																													// CTRL+O
		this.menuiFichierEnregistrer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK)); // pour
																														// CTRL+S
		this.menuiFichierEnregistrerSous.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK)); // pour
																												// CTRL+SHIFT+S
		this.menuiFichierQuitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_DOWN_MASK)); // pour
																													// ALT+F4

		/*-------------------------------*/
		/* Activation des composants */
		/*-------------------------------*/
		this.menuiFichierNouveau.addActionListener(this);
		this.menuiFichierOuvrir.addActionListener(this);
		this.menuiFichierEnregistrer.addActionListener(this);
		this.menuiFichierEnregistrerSous.addActionListener(this);
		this.menuiFichierQuitter.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JMenuItem) {
			JMenuItem menuiTemp = (JMenuItem) e.getSource();
			System.out.println(menuiTemp.getText());
			if (e.getSource() == this.menuiFichierNouveau) {
				this.ctrl.nouveau();
			}
			if (e.getSource() == this.menuiFichierOuvrir) {
				JFileChooser chooser = new JFileChooser(".");

				chooser.showOpenDialog(this);

				this.ctrl.ouvrir(new File(chooser.getSelectedFile().getPath()));
			}
			if (e.getSource() == this.menuiFichierEnregistrer) {
				this.ctrl.exporter();
			}
			if (e.getSource() == this.menuiFichierEnregistrerSous) {
				this.ctrl.exporterSous();
			}
			if (e.getSource() == this.menuiFichierQuitter) {
				this.ctrl.frameDispose();
			}
		}
	}
}