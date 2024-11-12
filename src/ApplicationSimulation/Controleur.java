package ApplicationSimulation;

import ApplicationSimulation.ihm.ControleurGUI;
import ApplicationSimulation.ihm.FrameCuves;
import ApplicationSimulation.metier.AutoPlay;
import ApplicationSimulation.metier.Structure;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class Controleur 
{

	private FrameCuves ihm;
	private Structure metier;
    private boolean pause;

	public Controleur() 
    {
		this.metier = new Structure(0);
		this.metier.optimiserPositions();
		this.ihm = new FrameCuves(this);
        this.pause = false;
	}

	// methodes

	public void creerMetier(int nbCuves) {
		this.metier = new Structure(nbCuves);
	}

	public void nouveau() 
    {
		new ControleurGUI(this);
	}

	public void ouvrir(File fichier) {
		this.metier = new Structure(fichier);
		this.metier.optimiserPositions();

		this.ihm.dispose();
		this.ihm = new FrameCuves(this);
	}

	public void majIHM() 
    {
		this.ihm.majIHM();
	}

	public void frameDispose() {
		this.ihm.dispose();
	}

	public void exporter() {
		this.ihm.exporter();
	}

	public void exporterSous() {
		this.ihm.exporterSous();
	}

	public void pause(boolean bPause, double vitesse) 
    {   
        if (pause)
        {
            System.out.println("pause");
            pause=false;
        }
        else
        {
            System.out.println("action");
            pause=true;
        }
        this.play();
		//this.Appli.metier.transvaser();
        //this.Appli.ihm.majIHM();
        
    }

    public void play() 
    {
        Timer timer = new Timer();
        TimerTask task = new AutoPlay(this);
        timer.schedule(task, 0, 1000);
    }

    public void playAction()
    {
        if(pause)
        {
            if(! this.metier.transvaser())
            {
                
                System.out.println("fin");
                pause = false;
            }
            this.ihm.majIHM();
        }
    }

	// accesseurs
	public int getNbCuves() {
		return this.metier.getNbCuves();
	}

	public int getRelation(int c1, int c2) {
		return this.metier.getRelation(c1, c2);
	}

	public char getIdCuve(int indice) {
		return this.metier.getIdCuve(indice);
	}

	public double getContenu(int indice) {
		return this.metier.getContenu(indice);
	}

	public int getCapacite(int indice) {
		return this.metier.getCapacite(indice);
	}

	public int getPosX(int indice) {
		return this.metier.getPosX(indice);
	}

	public int getPosY(int indice) {
		return this.metier.getPosY(indice);
	}

	public String getPosInfos(int indice) {
		return this.metier.getPosInfos(indice);
	}

	// modificateurs
	public void setPosition(int indice, int posX, int posY) {
		this.metier.setPosition(indice, posX, posY);
	}

	public void setPositionInfos(int indice, String position) {
		this.metier.setPositionInfos(indice, position);
	}

	public boolean setContenu(int indice, double quantite) {
		return this.metier.setContenu(indice, quantite);
	}

	public static void main(String[] args) {
		new Controleur();
	}
}