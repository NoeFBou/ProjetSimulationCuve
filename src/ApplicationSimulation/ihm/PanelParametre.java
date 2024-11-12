package ApplicationSimulation.ihm;

import ApplicationSimulation.Controleur;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.*;

public class PanelParametre extends JPanel implements ActionListener
{
	private Controleur ctrl;
	private char       id;

	private JButton    btnMoinsX;
	private JTextField txtValX;
	private JButton    btnPlusX;

	private JButton    btnMoinsY;
	private JTextField txtValY;
	private JButton    btnPlusY;

	private JTextField txtContenu;

	private JComboBox<String> lstPosInfo;

	public PanelParametre(Controleur ctrl, char id)
	{
		this.ctrl = ctrl;
		this.id   = id;

		this.setLayout(new BorderLayout());

		//Création des panels
		JPanel panelAction   = new JPanel(new GridLayout(4,1));
		JPanel panelX        = new JPanel(new FlowLayout());
		JPanel panelY        = new JPanel(new FlowLayout());
		JPanel panelContenu  = new JPanel(new FlowLayout());
		JPanel panelPosition = new JPanel(new FlowLayout());

		//Création des composants
		this.btnMoinsX  = new JButton("-");
		this.txtValX    = new JTextField("" +       this.ctrl.getPosX(this.id - 'A'), 3);
		this.btnPlusX   = new JButton("+");

		this.btnMoinsY  = new JButton("-");
		this.txtValY    = new JTextField("" +       this.ctrl.getPosY(this.id - 'A'), 3);
		this.btnPlusY   = new JButton("+");

		this.txtContenu = new JTextField("" + (int) this.ctrl.getContenu(this.id - 'A'), 3);

		String[] tabPos = { "Haut", "Bas", "Gauche", "Droite" };
		this.lstPosInfo = new JComboBox<String>(tabPos);

		//Positionnement des composants
		this.add(new JLabel("Appli.metier.Cuve " + this.id, JLabel.CENTER), BorderLayout.NORTH);

		panelX.add(new JLabel("X :"));
		panelX.add(this.btnMoinsX);
		panelX.add(this.txtValX);
		panelX.add(this.btnPlusX);
		panelAction.add(panelX);

		panelY.add(new JLabel("Y :"));
		panelY.add(this.btnMoinsY);
		panelY.add(this.txtValY);
		panelY.add(this.btnPlusY);
		panelAction.add(panelY);	

		panelContenu.add(new JLabel("Contenu :"));
		panelContenu.add(this.txtContenu);
		panelContenu.add(new JLabel("/ " + ctrl.getCapacite(this.id - 'A'))); //à changer
		panelAction.add(panelContenu);

		panelPosition.add(new JLabel("Pos infos :"));
		panelPosition.add(this.lstPosInfo);
		panelAction.add(panelPosition);
		this.add(panelAction, BorderLayout.CENTER);

		//Activation des composants
		this.btnMoinsX .addActionListener(this);
		this.txtValX   .addActionListener(this);
		this.btnPlusX  .addActionListener(this);
		this.btnMoinsY .addActionListener(this);
		this.txtValY   .addActionListener(this);
		this.btnPlusY  .addActionListener(this);
		this.txtContenu.addActionListener(this);
		this.lstPosInfo.addActionListener(this);
	}

	public void majIHM()
	{
		this.txtValX.setText   ("" +       this.ctrl.getPosX   (this.id - 'A'));
		this.txtValY.setText   ("" +       this.ctrl.getPosY   (this.id - 'A'));
		this.txtContenu.setText("" + (int) this.ctrl.getContenu(this.id - 'A'));
	}

	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == this.btnMoinsX)
			this.deplacer('X', '-');

		if(e.getSource() == this.btnMoinsY)
			this.deplacer('Y', '-');

		if(e.getSource() == this.btnPlusX)
			this.deplacer('X', '+');
		
		if(e.getSource() == this.btnPlusY)
			this.deplacer('Y', '+');

		if(e.getSource() == this.txtValX || e.getSource() == this.txtValY)
			this.ctrl.setPosition(this.id - 'A', 
			                      Integer.parseInt(this.txtValX.getText()), 
			                      Integer.parseInt(this.txtValY.getText()));

		if(e.getSource() == this.txtContenu)
			this.ctrl.setContenu(this.id - 'A', Double.parseDouble(this.txtContenu.getText()));

		if(e.getSource() == this.lstPosInfo)
			this.ctrl.setPositionInfos(this.id - 'A', "" + this.lstPosInfo.getSelectedItem());

		this.ctrl.majIHM();
	}

	private void deplacer(char dir, char sens)
	{
		int indice = this.id - 'A';
		int posX   = this.ctrl.getPosX(indice);
		int posY   = this.ctrl.getPosY(indice);

		if(dir == 'X')
		{
			if(sens == '-')
				posX -= 10;
			else
				posX += 10;
		}
		else
		{
			if(sens == '-')
				posY -= 10;
			else
				posY += 10;
		}

		this.ctrl.setPosition(indice, posX, posY);
	}
	
}
