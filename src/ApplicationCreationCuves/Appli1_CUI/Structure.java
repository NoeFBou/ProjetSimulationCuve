package ApplicationCreationCuves.Appli1_CUI;

import java.io.PrintWriter;
import java.io.FileOutputStream;

public class Structure
{
    private Cuve[]  tabCuve;
    private int[][] tabRelation;
    
    public Structure(int nbCuve)
    {
        this.tabCuve = new Cuve[nbCuve];
        this.tabRelation = new int[nbCuve][nbCuve];
    }

    public void creerCuve(int indice, int quantite) 
	{
		this.tabCuve[indice] = Cuve.fabriquerCuve(quantite);
	}

	public boolean ajouterRelation(char c1, char c2, int section)
    {
        //Verification que les valeurs font parti des cuves existants
        if( c1 < 'A' || c1 >= (char)('A'+this.tabCuve.length) || 
		    c2 < 'A' || c2 >= (char)('A'+this.tabCuve.length)   ) return false;

		//Verification que la valeur de la section est correcte
		if( section < 2 || section > 10 )                         return false;

		//Verification que la relation n'existe pas déja
		if( this.tabRelation[(int)c1 -'A'][(int)c2 -'A'] != 0 )   return false;

		//Verification que les cuves ne sont pas identiques
		if( c1 == c2 )                                            return false;

        this.tabRelation[(int)c1 -'A'][(int)c2 -'A'] = section;
        this.tabRelation[(int)c2 -'A'][(int)c1 -'A'] = section;
        return true;
    }

	public int getNbCuves() { return this.tabCuve.length; }

    public boolean genererTxt(String typeStructure, String nomFichier)
    {
        String[] typeRelation = {"lstAdj","matCout","matCoutOpt"};
    
        for (String t : typeRelation)
            if (typeStructure.equals(t))
            {
                try
                {
					if (nomFichier.equals("")) nomFichier = "Structure";

                    PrintWriter pw = new PrintWriter(new FileOutputStream(nomFichier + ".txt"));

					pw.print(typeStructure);

					for (int cpt1 = 0; cpt1 < this.tabCuve.length; cpt1++)
					{
						pw.print("\n" + this.tabCuve[cpt1].getIdCuve  () + "\t" + 
						                this.tabCuve[cpt1].getCapacite() + "\t"   );

						if      (typeStructure.equals("lstAdj"))
						{
							for (int cpt2 = 0 ; cpt2 < this.tabCuve.length ; cpt2++) 
								if(this.tabRelation[cpt1][cpt2] != 0)
									pw.print((char)('A'+ cpt2) + "" + this.tabRelation[cpt1][cpt2] + "\t");
						}
						else if (typeStructure.equals("matCout"))
						{
							for (int cpt2 = 0 ; cpt2 < this.tabCuve.length ; cpt2++) 
								pw.print(this.tabRelation[cpt1][cpt2]);
						}
						else
						{
							for (int cpt2 = 0 ; cpt2 < cpt1 ; cpt2++)
								pw.print(" ");

							for (int cpt2 = cpt1 ; cpt2 < this.tabCuve.length ; cpt2++)
								pw.print(this.tabRelation[cpt1][cpt2]);
						}
					}
                        
                    pw.close();
                }
				catch(Exception e)
				{
					e.printStackTrace();
				}

                return true;                
            }

        return false;
    }
}
