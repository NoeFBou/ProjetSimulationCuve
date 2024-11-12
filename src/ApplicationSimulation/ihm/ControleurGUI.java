package ApplicationSimulation.ihm;

import java.io.*;

import ApplicationSimulation.Controleur;
import ApplicationSimulation.metier.Structure;

public class ControleurGUI 
{
	private FrameCuvesGUI ihm;
	private Structure metier;
	private Controleur ctrl;

	public ControleurGUI(Controleur ctrl)
	{
		this.ihm     = new FrameCuvesGUI(this);
		this.ctrl 	 = ctrl;
		this.metier  = null;    // on ne peut pas créer le Appli.metier tant qu'on a pas choisi le nombre de cuves
	}

	public void creerMetier(int nbCuves)
	{
		this.metier = new Structure(nbCuves);
	}

	//modificateurs
	public boolean AjouterTube(char c1, char c2, int section)
	{
		return this.metier.ajouterRelation(c1, c2, section);
	}

	//methodes
	public void creerCuve(int indice, int capacite)
	{
		this.metier.creerCuve(indice, capacite);
	}

	public boolean ajouterRelation(char c1, char c2, int section)
	{
		return this.metier.ajouterRelation(c1, c2, section);
	}

	public void genererTxt(String type, String nomFichier)
	{
		if (this.metier.genererTxt(type, nomFichier))
			this.ihm.dispose();
	}
	
	public void ouvrir(File fichier)
	{
		this.ctrl.ouvrir(fichier);
	}

	//accesseurs
	public int getNbCuves()
	{
		return this.metier.getNbCuves();
	}
}