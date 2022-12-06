package main.java.controller;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.List;

import main.java.view.VueJeu;

public class ControleurMouvementSouris implements MouseMotionListener{
	
	private VueJeu vj;

	public ControleurMouvementSouris(VueJeu vj) {
		this.vj = vj;
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Point p = new Point(e.getX(),e.getY());
		if(vj.isOnSlot(p)) {
			this.vj.setHover(vj.getIndiceSlot(p));
			this.vj.repaint();
		}else {
			this.vj.setHover(-1);
			this.vj.repaint();
		}
	}

}
