package main.java.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import main.java.view.VueJeu;

public class ControleurCommandes implements KeyListener {

	private VueJeu vj;

	public ControleurCommandes(VueJeu vj) {
		this.vj = vj;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// Déplacement du joueur sur la grille
		if (e.getKeyCode() == KeyEvent.VK_Z || e.getKeyCode() == KeyEvent.VK_UP) {
			this.vj.getJeu().deplacerHaut(this.vj.getJoueurCourant());
		}
		if (e.getKeyCode() == KeyEvent.VK_Q || e.getKeyCode() == KeyEvent.VK_LEFT) {
			this.vj.getJeu().deplacerGauche(this.vj.getJoueurCourant());
		}
		if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
			this.vj.getJeu().deplacerBas(this.vj.getJoueurCourant());
		}
		if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
			this.vj.getJeu().deplacerDroite(this.vj.getJoueurCourant());
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
