package ApplicationCreationCuves.Appli1_GUI;

public class ControleurGUI
{
	private FrameCuves ihm;
	private Structure metier;

	public ControleurGUI()
	{
		this.ihm     = new FrameCuves(this);
		this.metier  = null;    // on ne peut pas créer le metier tant qu'on a pas choisi le nombre de cuves
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

	//accesseurs
	public int getNbCuves()
	{
		return this.metier.getNbCuves();
	}

	public static void main(String[] args) { new ControleurGUI(); }   
}