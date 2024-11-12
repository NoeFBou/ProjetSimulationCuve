package ApplicationSimulation.ihm;

import ApplicationSimulation.Controleur;

import javax.swing.*;
import java.awt.event.*;

public class PanelBarre extends JPanel implements ActionListener
{
	private Controleur ctrl;

	private JButton  		  btnPause;
	private JButton  		  btnPlay;
	private JButton  		  btnSuivant;
	private String[]		  tabVitesse;
	private JComboBox<String> lstVitesse;
	public PanelBarre(Controleur ctrl)
	{
		this.ctrl = ctrl;

		this.setOpaque(false);

		// Création des composants
		this.tabVitesse = new String[]{"0.25", "0.75", "1", "1.25","1.75","2"};

		this.btnPause   = new JButton(new ImageIcon("ApplicationSimulation/images/pause.png"));
		this.btnPlay    = new JButton(new ImageIcon("ApplicationSimulation/images/play.png"));
		this.btnSuivant = new JButton(">");

		this.lstVitesse = new JComboBox<String>(this.tabVitesse);
		//this.lstVitesse.setPopupAbove();

		// Positionnement des composants
		this.add(this.btnPause  );
		this.add(this.btnPlay   );
		this.add(this.btnSuivant);
		this.add(this.lstVitesse);

		// Activation des composants
		this.btnPause  .addActionListener(this);
		this.btnPlay   .addActionListener(this);
		this.btnSuivant.addActionListener(this);
		this.lstVitesse.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e)
	{
		boolean bPause  = true;
		double  vitesse = 1;

		if(e.getSource() == this.btnPause)
			bPause = false;

		if(e.getSource() == this.btnPlay )
			bPause = true;

		if(e.getSource() == this.btnSuivant)
			System.out.println("Suivant");

		if(e.getSource() == this.lstVitesse)
		{
			vitesse = Double.parseDouble(""+this.lstVitesse.getSelectedItem());
		}

		this.ctrl.pause(bPause, vitesse);
	}
}