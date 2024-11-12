package ApplicationCreationCuves.Appli1_CUI;

public class ControleurCUI
{
	private Structure metier;
	private IHMCUI    ihm;

	public ControleurCUI()
	{
		this.ihm = new IHMCUI(this);
		int nbCuves = this.ihm.lireQuantite("Combien de cuves dans le reseau",1,26);
		this.metier = new Structure(nbCuves);

		int quantite;
		for(int cpt = 0 ; cpt < this.metier.getNbCuves() ; cpt++)
		{
			quantite = this.ihm.lireQuantite("Quelle est la quantitée maximale de la cuve " + (char)('A'+cpt),
			                                 200, 1000                                                        );
			this.metier.creerCuve(cpt, quantite);
		}

		char cuve1 = ' ';
		char cuve2 = ' ';
		int  section;
		while(cuve1 != 'q' && cuve2 != 'q')
		{
			cuve1 = this.ihm.lireCuve("Quelle cuve voulez-vous relier");
			if(cuve1 == 'q') break;

			cuve2 = this.ihm.lireCuve("Avec quelle cuve");
			if(cuve2 == 'q') break;

			section = this.ihm.lireQuantite("Quelle est la taille de la section",2,10);

			if ( !this.metier.ajouterRelation(cuve1, cuve2, section) )
				this.ihm.afficherErreur("Les valeurs saisient sont incorecte");
		} 

		this.metier.genererTxt(this.ihm.lireType(), this.ihm.lireNom());
	}

	public int getNbCuves(){ return this.metier.getNbCuves(); }

	public static void main(String[] args) { new ControleurCUI(); }
}
