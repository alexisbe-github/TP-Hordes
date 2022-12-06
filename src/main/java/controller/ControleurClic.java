package main.java.controller;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import main.java.model.objet.Objet;
import main.java.view.VueClicItem;
import main.java.view.VueJeu;

public class ControleurClic implements MouseListener {

	private VueJeu vj;

	public ControleurClic(VueJeu vj) {
		this.vj = vj;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Point p = new Point(e.getX(), e.getY());

		// Si on clic sur un point de la carte
		if (p.getX() > vj.getPadding() && p.getX() < vj.getPadding() + vj.getCote() && p.getY() > vj.getPadding()
				&& p.getY() < vj.getPadding() + vj.getCote()) {
			double longueurCellule = (double) vj.getCote() / 25;
			int x = (int) ((e.getX() - vj.getPadding()) / longueurCellule);
			int y = (int) ((e.getY() - vj.getPadding()) / longueurCellule);
			this.vj.setVueCourant(x, y);
		} else {
			
			//Clique sur les flèches directionnelles sur la carte
			if (vj.getTop().contains(p)) {
				this.vj.getJeu().deplacerHaut(this.vj.getJoueurCourant());
			}
			if (vj.getLeft().contains(p)) {
				this.vj.getJeu().deplacerGauche(this.vj.getJoueurCourant());
			}
			if (vj.getBot().contains(p)) {
				this.vj.getJeu().deplacerBas(this.vj.getJoueurCourant());
			}
			if (vj.getRight().contains(p)) {
				this.vj.getJeu().deplacerDroite(this.vj.getJoueurCourant());
			}
			
			if(vj.getTopEntrepot().contains(p)) {
				this.vj.decrementCompteurEntrepot();
			}
			if(vj.getBotEntrepot().contains(p)) {
				this.vj.incrementCompteurEntrepot();
			}
			
			//Si on clique sur un item par terre sur la case
			if (vj.isOnSlot(p)) {
				Objet o = vj.getClicObjet(p);
				VueClicItem vci = new VueClicItem(o, this.vj.getJoueurCourant().getInventaire(),
						this.vj.getCaseCourante().getLoot(),this.vj.getJeu(), false);
				vci.show(vj, p.x, p.y);
			}
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
