package ApplicationCreationCuves.Appli1_GUI;

import javax.swing.*;
import java.awt.BorderLayout;

public class FrameCuves extends JFrame
{
	private ControleurGUI ctrl;
	
	private PanelNbCuves panelNbCuves;
	private PanelCapacites panelCapacites;
	private PanelLiaisons panelLiaisons;
	
	public FrameCuves(ControleurGUI ctrl)
	{
		this.ctrl = ctrl;

		this.setTitle("Cuves");
		this.setLocation(50, 50);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Création des panels
		this.panelNbCuves   = new PanelNbCuves(this.ctrl, this);
		this.panelCapacites = null;		// on ne peut pas créer ce panel tant qu'on a pas choisi le nombre de cuves
		this.panelLiaisons  = null;		// on ne peut pas créer ce panel tant qu'on a pas choisi le nombre de cuves

		// Positionnement du composent
		this.add(this.panelNbCuves, BorderLayout.NORTH);

		this.pack();
		this.setVisible ( true );
	}

	public void afficherPopUp(String message)
	{
		JOptionPane.showMessageDialog(this, message);
	}

	public void changeToPanelCapacite(int nbCuves)
	{
		this.ctrl.creerMetier(nbCuves);

		this.panelCapacites = new PanelCapacites(this.ctrl, this);
		this.add(this.panelCapacites, BorderLayout.CENTER);
		
		this.pack();
	}
	
	public void changeToPanelLiaison()
	{
		this.panelLiaisons = new PanelLiaisons(this.ctrl, this);
		this.add(this.panelLiaisons,BorderLayout.SOUTH);

		this.pack();
	}
}