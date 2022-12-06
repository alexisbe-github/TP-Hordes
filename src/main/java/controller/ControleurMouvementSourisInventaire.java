package main.java.controller;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import main.java.view.VueInventaire;

public class ControleurMouvementSourisInventaire implements MouseMotionListener{

	private VueInventaire vi;
	
	public ControleurMouvementSourisInventaire(VueInventaire vi) {
		this.vi = vi;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Point p = new Point(e.getX(),e.getY());
		if(vi.isOnSlot(p)) {
			this.vi.setHover(vi.getIndiceSlot(p));
			this.vi.repaint();
		}else {
			this.vi.setHover(-1);
			this.vi.repaint();
		}
	}

}
