package ApplicationCreationCuves.Appli1_GUI;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.*;

public class PanelCapacites extends JPanel implements ActionListener
{
    private ControleurGUI ctrl;
	private FrameCuves    frame;

    private JButton      btnValider;
    private JTextField[] tabTxtCapacite;


    public PanelCapacites(ControleurGUI ctrl, FrameCuves frame)
    {
		this.ctrl  = ctrl; 
		this.frame = frame;

		this.setLayout(new GridLayout(1,3)); 

		// Création des panels
		int nbCuves = this.ctrl.getNbCuves();
        JPanel panelLabelCapacite  = new JPanel(new BorderLayout()         );
        JPanel panelLabelCuve      = new JPanel(new BorderLayout()         );
        JPanel panelGestionCuve    = new JPanel(new GridLayout  (nbCuves,2));
        JPanel panelGestionValider = new JPanel(new BorderLayout()         );
        
		// Création des éléments
        this.btnValider     = new JButton("Valider");

        this.tabTxtCapacite = new JTextField[nbCuves];
        for (int cpt = 0; cpt < nbCuves; cpt++)
            this.tabTxtCapacite[cpt] = new JTextField();

		// Positionnement des éléments
        panelLabelCapacite.add(new JLabel("Capacités : ", JLabel.LEFT), BorderLayout.NORTH);
		this.add(panelLabelCapacite);

        panelLabelCuve.add(new JLabel("Cuves",SwingConstants.CENTER), BorderLayout.NORTH );
        panelLabelCuve.add(panelGestionCuve                        , BorderLayout.CENTER);
		for (int cpt = 0; cpt < nbCuves; cpt++)
        {
            panelGestionCuve.add(new JLabel((char)((int)('A'+cpt)) + " : ", JLabel.RIGHT));
            panelGestionCuve.add(this.tabTxtCapacite[cpt]);
        }
		this.add(panelLabelCuve);
		
        panelGestionValider.add(this.btnValider,BorderLayout.SOUTH);
        this.add(panelGestionValider);

		// Activation du composant
        this.btnValider.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e)
	{
		char cuveCourante = 'A';
		boolean verification = true;

		// vérification des valeurs remplis
		for (int cpt = 0; cpt < this.tabTxtCapacite.length; cpt++)
		{
			cuveCourante = (char)('A'+cpt);

			try 
			{
				int capacite = Integer.parseInt(this.tabTxtCapacite[cpt].getText());
				if ( capacite >= 200 && capacite <= 1000 )
				{
					this.ctrl.creerCuve(cpt, capacite);
				}
				else
				{
					this.frame.afficherPopUp("La capacité de la cuve " + cuveCourante + 
					                         " doit-être comprise entre 200 et 1000"    );
					verification = false;
				}
			}
			catch (Exception ex) 
			{
				if (ex instanceof NumberFormatException)
				{
					this.frame.afficherPopUp("La capacité renseignée de la cuve " + cuveCourante + 
					                         " doit-être sous forme d'un entier");
					verification = false;
				}
			}	
		}

		// vérification qu'aucune des valeurs n'ait été fausse
		if (verification)
		{
			this.btnValider.setEnabled (false);
			for (int cpt = 0; cpt < this.tabTxtCapacite.length; cpt++)
				this.tabTxtCapacite[cpt].setEditable(false);

			this.frame.changeToPanelLiaison();
		}

		
	}
}
