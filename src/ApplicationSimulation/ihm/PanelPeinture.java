package ApplicationSimulation.ihm;

import ApplicationSimulation.Controleur;

import javax.swing.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.geom.Ellipse2D;
import java.awt.event.*;

public class PanelPeinture extends JPanel
{
	private Controleur ctrl;
	private Color[]     tabRouge;
	private Ellipse2D[] tabRond;
	private Integer     idRondCourant;

	public PanelPeinture(Controleur ctrl)
	{
		this.ctrl     = ctrl;
		this.tabRouge = this.genererTabRouge();
		this.tabRond  = new Ellipse2D[this.ctrl.getNbCuves()];
		this.idRondCourant = null;

		this.setPreferredSize(new Dimension(800, 600));
		this.addMouseListener(new gererSouris());
		this.addMouseMotionListener(new gererMouvementSouris());
	}

	public void majIHM()
	{
		super.repaint();		
	}

	public void paint(Graphics g)
	{
		super.paint(g);
		int x, y, diametre;
		String id, valeurs;
		
		Graphics2D g2D = (Graphics2D)(g);

        for(int cpt1=0; cpt1 < this.ctrl.getNbCuves(); cpt1++)
        {
            for(int cpt2=cpt1; cpt2 < this.ctrl.getNbCuves(); cpt2++)
            {
                if(this.ctrl.getRelation(cpt1,cpt2) != 0)
                {
                    g2D.setColor(Color.BLACK);
                    g2D.setStroke(new BasicStroke((float)(this.ctrl.getRelation(cpt1,cpt2))));

                    g2D.drawLine(ctrl.getPosX(cpt1),
                                 ctrl.getPosY(cpt1),
                                 ctrl.getPosX(cpt2),
                                 ctrl.getPosY(cpt2));
                }
            }
        }


		for (int i = 0 ; i < this.ctrl.getNbCuves() ; i++)
		{
			x = this.ctrl.getPosX(i);
			y = this.ctrl.getPosY(i);
			diametre = this.ctrl.getCapacite(i) / 10;

			g.setColor(Color.BLACK);
			g.drawOval(x-(diametre/2), y-(diametre/2), diametre, diametre);

			int indRouge = (int) (this.ctrl.getContenu(i) / this.ctrl.getCapacite(i) * 499);
			g.setColor(this.tabRouge[indRouge]);
			g.fillOval(x-(diametre/2), y-(diametre/2), diametre, diametre);

			this.tabRond[i] = new Ellipse2D.Double((double) x-(diametre/2), (double) y-(diametre/2), 
			                                       (double) diametre      , (double) diametre       );


			id = "" + this.ctrl.getIdCuve(i);
			valeurs = String.format("%04d", (int) this.ctrl.getContenu (i)) + "/" +
			          String.format("%04d",       this.ctrl.getCapacite(i)         );
			g.setColor(Color.BLACK);

			switch(this.ctrl.getPosInfos(i))
			{
				case "Haut"   -> 
				{
					g.drawString(id,      x- 2, y-(diametre/2)-12);
					g.drawString(valeurs, x-27, y-(diametre/2)- 2);
				}
				case "Gauche" -> 
				{
					g.drawString(id,      x-(diametre/2)-34, y   );
					g.drawString(valeurs, x-(diametre/2)-59, y+10);
				} 
				case "Bas"    -> 
				{
					g.drawString(id,      x- 2, y+(diametre/2)+12);
					g.drawString(valeurs, x-27, y+(diametre/2)+22);
				}
				case "Droite" -> 
				{
					g.drawString(id,      x+(diametre/2)+27, y   );
					g.drawString(valeurs, x+(diametre/2)+ 2, y+10);
				}
			}
		}
		
	}
	private class gererSouris extends MouseAdapter
	{
		public void mousePressed (MouseEvent e)	
		{
			for (int cpt = 0 ; cpt < PanelPeinture.this.tabRond.length ; cpt++)
				if (PanelPeinture.this.tabRond[cpt].contains(e.getPoint()))
					PanelPeinture.this.idRondCourant = cpt;

			System.out.println(PanelPeinture.this.idRondCourant);
		}

		public void mouseReleased(MouseEvent e) 
		{
			PanelPeinture.this.idRondCourant = null;
		}
	}

	private class gererMouvementSouris extends MouseMotionAdapter
	{
		public void mouseDragged(MouseEvent e) 
		{
			if (PanelPeinture.this.idRondCourant != null)
			{
				PanelPeinture.this.ctrl.setPosition(PanelPeinture.this.idRondCourant, 
									  (int) (e.getX()),
									  (int) (e.getY()) );
	
									  PanelPeinture.this.ctrl.majIHM();
			}
		}
	}

	private Color[] genererTabRouge()
	{
		Color[] tabRouge = new Color[500];

        int bleu = 252;
        int rouge = 252;
        int vert = 252; 
        int indice = 0;
        for(int i=0; i < 500; i++)
        {
            if(bleu - i <= 0 && vert - i <= 0)
            {
                tabRouge[i] = new Color(rouge - indice, 0, 0);
                indice++;
            }
            else
            {
                tabRouge[i] = new Color(rouge, vert - i, bleu - i);
            }
        }

		return tabRouge;
	}
}