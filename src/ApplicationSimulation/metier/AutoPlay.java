package ApplicationSimulation.metier;

import ApplicationSimulation.Controleur;

import java.util.TimerTask;


public class AutoPlay extends TimerTask
{
    private Controleur crtl;
    
    public AutoPlay(Controleur crtl)
    {
        this.crtl=crtl;
    }

    public void run()
    {
        this.crtl.playAction();
    }
}
