package main.java.controller;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import main.java.model.objet.Objet;
import main.java.view.VueClicItem;
import main.java.view.VueInventaire;

public class ControleurInventaire implements MouseListener{

	private VueInventaire vi;
	
	public ControleurInventaire(VueInventaire vi) {
		this.vi = vi;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Point p = new Point(e.getX(), e.getY());
		if(this.vi.clicOnSlot(p)) {
			Objet o = vi.getClicObjet(p);
			VueClicItem vci = new VueClicItem(o, vi.getJoueurCourant().getInventaire(), vi.getJeu().getCaseDuJoueur(vi.getJoueurCourant()).getLoot(),vi.getJeu(),true);
			vci.show(vi, p.x, p.y);
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
