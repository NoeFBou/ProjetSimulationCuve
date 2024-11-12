package ApplicationCreationCuves.Appli1_CUI;

import iut.algo.*;

public class IHMCUI
{
	private ControleurCUI ctrl;

	public IHMCUI(ControleurCUI ctrl)
	{
		this.ctrl = ctrl;
	}

	public char lireCuve(String message)
	{
		System.out.print(message + " (entrer 'q' pour quitter) ? : ");
		char id = Clavier.lire_char();
		while((id < 'A' || id >= (char)('A' + this.ctrl.getNbCuves())) && id != 'q')
		{
			this.afficherErreur("La cuve sélectionnée n'existe pas");

			System.out.println(message + " (entrer 'q' pour quitter) ? : ");
			id = Clavier.lire_char();
		}

		return id;
	}

	public int lireQuantite(String message, int min, int max)
	{
		System.out.print(message + " : ");
		int qte = Clavier.lire_int();
		while(qte < min || qte > max)
		{
			this.afficherErreur("La quantité doit être comprise entre " + min + " et " + max);

			System.out.print(message + " : ");
			qte = Clavier.lire_int();
		}	

		return qte;	
	}

	public String lireType()
	{
		System.out.print("Quel type de structure souhaitez-vous : ");
		String type = Clavier.lireString();
		while(!type.equals("lstAdj") && !type.equals("matCout") && !type.equals("matCoutOpt"))
		{
			this.afficherErreur("Le type choisit n'existe pas (lstAdj/matCout/matCoutOpt)");

			System.out.print("Quel type de structure souhaitez-vous : ");
			type = Clavier.lireString();
		}	

		return type;	
	}

	public String lireNom()
	{
		System.out.print("Quel nom voulez-vous donner au fichier : ");
		return Clavier.lireString();
	}

	public void afficherErreur(String message)
	{
		System.out.println(" /!\\ " + message);
	}
}