package ApplicationCreationCuves.Appli1_GUI;

public class Cuve
{
	private static char idCuveAutoIncre = 'A';

	private char   idCuve;
	private int    capacite;
	private double contenu;
	private int[]  positionsXY;
	private String positionInfos;

	// Constructeur
	private Cuve(int capacite) 
	{
		this.idCuve        = Cuve.idCuveAutoIncre++;
		this.capacite      = capacite;
		this.contenu       = 0.0;
		this.positionsXY   = new int[2]; // les valeurs du tableau sont automatiquement égal à 0
		this.positionInfos = null;
	}

	// Fabrique
	public static Cuve fabriquerCuve(int capacite) 
	{
		if (capacite < 200 || capacite > 1000 || Cuve.idCuveAutoIncre > 'Z')
			return null;

		return new Cuve(capacite);
	}

	// Accesseurs
	public char   getIdCuve  () { return this.idCuve;         }
	public int    getCapacite() { return this.capacite;       }
	public double getContenu () { return this.contenu;        }
	public int    getPosX    () { return this.positionsXY[0]; }
	public int    getPosY    () { return this.positionsXY[1]; }
	public String getPosInfos() { return this.positionInfos;  }

	// Modificateurs
	public boolean setContenu(double userContenu)
	{
		if (userContenu > this.capacite)
			return false;

		this.contenu = userContenu;
		return true;
	}

	public void setPosX(int posX) { this.positionsXY[0] = posX; }
	public void setPosY(int posY) { this.positionsXY[1] = posY; }

	public boolean setPosInfos(String position) 
	{
		String[] tabPos = { "Haut", "Bas", "Gauche", "Droite" };

		for (String s : tabPos)
			if (position.equals(s)) 
			{
				this.positionInfos = position;
				return true;
			}

		return false;
	}

	// Methodes
	public void modifierContenu(double quantite)
	{
		if ( this.contenu + quantite > this.capacite )
			this.contenu = (double)(this.capacite);
		
		if(this.contenu - quantite < 0)
			this.contenu = 0.0;
		
		if(quantite > 0)
			this.contenu += quantite;
		else
			this.contenu -= quantite;
	}
}
