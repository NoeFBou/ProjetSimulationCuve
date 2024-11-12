package ApplicationSimulation.ihm;
/**
 * Appli.ihm.PanelNbCuves
 */

import javax.swing.*;
import java.awt.event.*;

public class PanelNbCuves extends JPanel implements ActionListener {
	private ControleurGUI ctrl;
	private FrameCuvesGUI frame;

	private JTextField txtNbCuves;
	private JButton btnValider;

	public PanelNbCuves(ControleurGUI ctrl, FrameCuvesGUI frame) {
		this.ctrl = ctrl;
		this.frame = frame;

		// Création des composants
		this.txtNbCuves = new JTextField(10);
		this.btnValider = new JButton("Valider");

		// Positionnement des composants
		this.add(new JLabel("Nombres cuves : ", JLabel.LEFT));
		this.add(this.txtNbCuves);
		this.add(this.btnValider);

		// Activation du composant
		this.btnValider.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		try {
			int nbCuves = Integer.parseInt(this.txtNbCuves.getText());
			if (nbCuves >= 2 && nbCuves <= 26) {
				this.frame.changeToPanelCapacite(nbCuves);
				this.txtNbCuves.setEditable(false);
				this.btnValider.setEnabled(false);
			} else {
				this.txtNbCuves.setText("");
				this.frame.afficherPopUp("La nombre de cuves doit être compris entre 2 et 26");
			}
		} catch (Exception ex) {
			this.txtNbCuves.setText("");
			this.frame.afficherPopUp(
					"La valeur renseignée pour le nombre de cuves doit-être sous la forme d'un entier");
		}
	}
}