package ApplicationSimulation.metier;

import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.File;
import java.util.Scanner;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Structure {
	private Cuve[] tabCuve;
	private int[][] tabRelation;

	public Structure(int nbCuve) {
		Cuve.reinitialiserId();
		this.tabCuve = new Cuve[nbCuve];
		this.tabRelation = new int[nbCuve][nbCuve];
	}

	public Structure(File fichier) {
		int nbCuve = this.getNbCuveTXT(fichier);

		Cuve.reinitialiserId();
		this.tabCuve = new Cuve[nbCuve];
		this.tabRelation = new int[nbCuve][nbCuve];

		this.txtToStructure(fichier, nbCuve);
	}

	public void creerCuve(int indice, int quantite) {
		this.tabCuve[indice] = Cuve.fabriquerCuve(quantite);
	}

	public boolean ajouterRelation(char c1, char c2, int section) {
		// Verification que les valeurs font parti des cuves existants
		if (c1 < 'A' || c1 >= (char) ('A' + this.tabCuve.length) ||
				c2 < 'A' || c2 >= (char) ('A' + this.tabCuve.length))
			return false;

		// Verification que la valeur de la section est correcte
		if (section < 2 || section > 10)
			return false;

		// Verification que la relation n'existe pas déja
		if (this.tabRelation[(int) c1 - 'A'][(int) c2 - 'A'] != 0)
			return false;

		// Verification que les cuves ne sont pas identiques
		if (c1 == c2)
			return false;

		this.tabRelation[(int) c1 - 'A'][(int) c2 - 'A'] = section;
		this.tabRelation[(int) c2 - 'A'][(int) c1 - 'A'] = section;
		return true;
	}

	public void optimiserPositions() {
		int x = 0;
		int y = 0;
		int rayon = 250;

		for (int j = 0; j < this.getNbCuves(); j++) {
			x = (int) (rayon * Math.cos(360 / this.getNbCuves() * j) + 400);

			y = (int) (rayon * Math.sin(360 / this.getNbCuves() * j) + 300);
			this.tabCuve[j].setPosX(x);
			this.tabCuve[j].setPosY(y);
		}
	}

	public int getNbCuves() {
		return this.tabCuve.length;
	}

	public int getRelation(int c1, int c2) {
		return this.tabRelation[c1][c2];
	}

	public char getIdCuve(int indice) {
		return this.tabCuve[indice].getIdCuve();
	}

	public double getContenu(int indice) {
		return this.tabCuve[indice].getContenu();
	}

	public int getCapacite(int indice) {
		return this.tabCuve[indice].getCapacite();
	}

	public int getPosX(int indice) {
		return this.tabCuve[indice].getPosX();
	}

	public int getPosY(int indice) {
		return this.tabCuve[indice].getPosY();
	}

	public String getPosInfos(int indice) {
		return this.tabCuve[indice].getPosInfos();
	}

	public void setPosition(int indice, int posX, int posY) {
		this.tabCuve[indice].setPosX(posX);
		this.tabCuve[indice].setPosY(posY);
	}

	public boolean setPositionInfos(int indice, String position) {
		return this.tabCuve[indice].setPosInfos(position);
	}

	public boolean setContenu(int indice, double quantite) {
		return this.tabCuve[indice].setContenu(quantite);
	}

	public boolean genererTxt(String typeStructure, String nomFichier) {
		String[] typeRelation = { "lstAdj", "matCout", "matCoutOpt" };

		for (String t : typeRelation)
			if (typeStructure.equals(t)) {
				try {
					if (nomFichier.equals(""))
						nomFichier = "Appli.metier.Structure";

					PrintWriter pw = new PrintWriter(new FileOutputStream(nomFichier + ".txt"));

					pw.print(typeStructure);

					for (int cpt1 = 0; cpt1 < this.tabCuve.length; cpt1++) {
						pw.print("\n" + this.tabCuve[cpt1].getIdCuve() + "\t" +
								this.tabCuve[cpt1].getCapacite() + "\t");

						if (typeStructure.equals("lstAdj")) {
							for (int cpt2 = 0; cpt2 < this.tabCuve.length; cpt2++)
								if (this.tabRelation[cpt1][cpt2] != 0)
									pw.print((char) ('A' + cpt2) + "" + this.tabRelation[cpt1][cpt2] + "\t");
						} else if (typeStructure.equals("matCout")) {
							for (int cpt2 = 0; cpt2 < this.tabCuve.length; cpt2++)
								pw.print(this.tabRelation[cpt1][cpt2] + "\t");
						} else {
							for (int cpt2 = 0; cpt2 < cpt1; cpt2++)
								pw.print(" \t");

							for (int cpt2 = cpt1; cpt2 < this.tabCuve.length; cpt2++)
								pw.print(this.tabRelation[cpt1][cpt2] + "\t");
						}
					}

					pw.close();
				} catch (Exception e) {
					e.printStackTrace();
				}

				return true;
			}

		return false;
	}

	private int getNbCuveTXT(File fichier) {
		int nbCuve = 0;
		try {
			Scanner tmpSc = new Scanner(new FileReader(fichier));

			while (tmpSc.hasNextLine()) {
				tmpSc.nextLine();
				nbCuve++;
			}

			tmpSc.close();
		} catch (Exception e) {
		}

		return nbCuve - 1;
	}

	private boolean txtToStructure(File fichier, int nbCuve) {
		String[] tabTmpString;

		try {
			Scanner sc = new Scanner(new FileReader(fichier));

			this.tabCuve = new Cuve[nbCuve];
			this.tabRelation = new int[nbCuve][nbCuve];

			String typeStructure = sc.nextLine();

			switch (typeStructure) {
				case "lstAdj" -> {
					for (int cpt = 0; sc.hasNextLine(); cpt++) {
						tabTmpString = sc.nextLine().split("	");
						this.creerCuve(cpt, Integer.parseInt(tabTmpString[1]));

						char c1, c2;
						int section;
						for (int relation = 2; relation < tabTmpString.length; relation++) {
							c1 = tabTmpString[0].charAt(0);
							c2 = tabTmpString[relation].charAt(0);
							section = Integer.parseInt(tabTmpString[relation].substring(1));

							this.ajouterRelation(c1, c2, section);
						}
					}
				}
				case "matCout" -> {
					for (int lig = 0; sc.hasNextLine(); lig++) {
						tabTmpString = sc.nextLine().split("	");
						this.creerCuve(lig, Integer.parseInt(tabTmpString[1]));

						for (int col = 2; col < tabTmpString.length; col++) {
							this.tabRelation[lig][col - 2] = Integer.parseInt(tabTmpString[col]);
						}
					}
				}
				case "matCoutOpt" -> {
					for (int lig = 0; sc.hasNextLine(); lig++) {
						tabTmpString = sc.nextLine().split("	");
						this.creerCuve(lig, Integer.parseInt(tabTmpString[1]));

						for (int col = lig + 2; col < tabTmpString.length; col++) {
							this.tabRelation[lig][col - 2] = Integer.parseInt(tabTmpString[col]);
						}
					}
				}
				default -> {
					return false;
				}
			}

			sc.close();
		} catch (Exception e) {
			System.out.println("ca marche po");
			return false;
		}

		return true;
	}

	/********************************************************************/
	/**                                                                **/
	/**                                                                **/
	/********************************************************************/

	public boolean transvaser() 
    {
		double[] liquideModifier = new double[this.tabCuve.length]; // tableau qui stocke les modifications du contenu
																	// des cuves
		boolean actionEnCours = true;

		// on parcours le tableau des relations entres le cuves puis on applique le
		// transvasage du liquide
		for (int cpt = 0; cpt < this.tabCuve.length; cpt++)
			for (int cpt1 = cpt; cpt1 < this.tabCuve.length; cpt1++)
				if (this.tabRelation[cpt][cpt1] != 0) 
                {
					if ( (this.tabCuve[cpt].getContenu() + liquideModifier[cpt]) > (this.tabCuve[cpt1].getContenu() + liquideModifier[cpt1]) )
						CalculeQuantiteTransfertLiquide(cpt, cpt1, liquideModifier, this.tabRelation[cpt][cpt1]);
					else if ( (this.tabCuve[cpt].getContenu() + liquideModifier[cpt]) < (this.tabCuve[cpt1].getContenu() + liquideModifier[cpt1]) )
						CalculeQuantiteTransfertLiquide(cpt1, cpt, liquideModifier, this.tabRelation[cpt][cpt1]);
				}

		// on verifie s'il y a eut du changement lors de cet iteration
		for ( int cpt = 0; cpt < this.tabCuve.length; cpt++ )
			if (liquideModifier[cpt] < 0.05)
				actionEnCours = false;
			else 
            {
				actionEnCours = true;
				break;
			}

		for ( int cpt = 0; cpt < this.tabCuve.length; cpt++ ) 
        {
			if ( liquideModifier[cpt] != 0 )
				this.tabCuve[cpt].modifierContenu(liquideModifier[cpt]);
 
			liquideModifier[cpt] = 0;
		}
        
		return actionEnCours;
	}

	/********************************************************************/
	/**                                                                **/
	/**                                                                **/
	/********************************************************************/

	// indexCuve1 est l'index de la cuve qui envoye le liquide
	// indexCuve2 est l'index de la cuve qui recoit le liquide
	// liquiModifier est le tableau qui stocke les variations du liquide des cuves
	public void CalculeQuantiteTransfertLiquide( int indexCuve1, int indexCuve2, double[] liquideModifier, int relation ) 
    {
		// on stocke ici la variation du liquide entres les 2 cuves d'indice indexCuve1
		// et indexCuve2
		double modifLiquide = 0;

		// Calcul pour avoir un liquide à l'équilibre
		modifLiquide = this.tabCuve[indexCuve1].getContenu() + this.tabCuve[indexCuve2].getContenu();
		modifLiquide = modifLiquide / 2;
		modifLiquide = modifLiquide - this.tabCuve[indexCuve2].getContenu();

		if ( modifLiquide + this.tabCuve[indexCuve2].getContenu() + liquideModifier[indexCuve2] > this.tabCuve[indexCuve2].getCapacite() )
			modifLiquide = this.tabCuve[indexCuve2].getCapacite() - ( this.tabCuve[indexCuve2].getContenu() + liquideModifier[indexCuve2] );

		if ( this.tabCuve[indexCuve1].getContenu() + liquideModifier[indexCuve1] - modifLiquide < 0 )
			modifLiquide = modifLiquide + (this.tabCuve[indexCuve1].getContenu() + liquideModifier[indexCuve1] - modifLiquide ); 

		// si les 2 cuves sont pleines on fait rien
		if ( this.tabCuve[indexCuve2].getContenu() == this.tabCuve[indexCuve2].getCapacite() )
			modifLiquide = 0;

		// si il y a plus de liquide que de place dans le tuyau entres les 2 cuves
		// (relation), on assigne la valeur du tuyau
		if ( modifLiquide > (double) relation )
			modifLiquide = relation;

		if ( modifLiquide < 0 )
			modifLiquide = 0;

		// arrondi - salary.setScale(2, RoundingMode.HALF_UP);
		BigDecimal bd;
		bd = new BigDecimal(modifLiquide).setScale(4, RoundingMode.HALF_UP );
		modifLiquide = bd.doubleValue();

		// on stock les modifications des cuves dans le tableau
		liquideModifier[indexCuve2] = liquideModifier[indexCuve2] + modifLiquide;
		liquideModifier[indexCuve1] = liquideModifier[indexCuve1] - modifLiquide;
	}
}