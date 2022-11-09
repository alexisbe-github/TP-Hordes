package main.java.model;

import java.util.List;
import java.util.Observable;

import main.java.model.carte.Carte;
import main.java.model.carte.Case;
import main.java.model.carte.Ville;
import main.java.model.objet.Gourde;
import main.java.model.objet.Planche;
import main.java.model.objet.PlaqueMetal;
import main.java.model.objet.Ration;
import main.java.utilitaire.Utilitaire;

public class Jeu extends Observable {

	private Carte carte;
	private int tour;
	private int joueurCourant;
	private List<Joueur> joueurs;

	public Jeu(List<Joueur> joueurs) {
		this.init(joueurs);
	}

	private void init(List<Joueur> joueurs) {
		this.joueurs = joueurs;
		this.carte = new Carte(joueurs);
		this.joueurCourant = 0;
		this.tour = 0;
	}

	public void commencerJeu() {
		this.joueurCourant = 0;
		this.joueurs.get(0).ramasserObjet(new Gourde(1));
		this.joueurs.get(0).ramasserObjet(new Planche(50));
		this.joueurs.get(0).ramasserObjet(new PlaqueMetal(200));
		this.joueurs.get(0).ramasserObjet(new Gourde(1));
		this.tour = 0;
		this.carte.init();
		this.updateObservers();
	}

	public Carte getCarte() {
		return this.carte;
	}

	public int getIndexJoueurCourant() {
		return this.joueurCourant;
	}

	public Joueur getJoueurCourant() {
		return this.joueurs.get(this.joueurCourant);
	}

	public Case getCaseDuJoueur(Joueur j) {
		return this.carte.getCarte()[j.getPosX()][j.getPosY()];
	}

	public void prochainJoueur() {
		this.joueurCourant++;
		this.joueurCourant %= this.joueurs.size();
		if (this.joueurCourant == 0) {
			this.prochainTour();
			if(this.tour % 24 == 0) {
				
			}
		}
		this.updateObservers();
	}

	public void prochainTour() {
		this.tour += 2;
		for (Joueur j : this.joueurs) {
			j.ajouterPa(4);
		}
	}

	public List<Joueur> getJoueurs() {
		return this.joueurs;
	}

	public int getJour() {
		return (this.tour + 24) / 24;
	}

	public int getTour() {
		return this.tour % 24;
	}

	/**
	 * Déplace le joueur d'une case sur la gauche sur la carte
	 */
	public void deplacerGauche(Joueur j) {
		if (this.carte.getCarte()[j.getPosX()][j.getPosY()].getNbZombie() == 0 && this.peutBougerGauche(j)) {
			j.deplacerJoueur(-1, 0);
			j.updateCarteDuJoueur(j.getPosX(), j.getPosY(), this.carte.getCarte()[j.getPosX()][j.getPosY()]);
			this.updateObservers();
		}
	}

	private boolean peutBougerGauche(Joueur j) {
		if ((this.getCarte().getCarte()[j.getPosX() - 1][j.getPosY()] instanceof Ville)
				&& !Ville.getVille().getPortesOuvertes())
			return false;
		return j.getPosX() > 0 && j.getPa() > 0
				&& (j.estEnVille() && Ville.getVille().getPortesOuvertes() || !j.estEnVille());
	}

	/**
	 * Déplace le joueur d'une case sur le haut sur la carte
	 */
	public void deplacerHaut(Joueur j) {
		if (this.carte.getCarte()[j.getPosX()][j.getPosY()].getNbZombie() == 0 && this.peutBougerHaut(j)) {
			j.deplacerJoueur(0, -1);
			j.updateCarteDuJoueur(j.getPosX(), j.getPosY(), this.carte.getCarte()[j.getPosX()][j.getPosY()]);
			this.updateObservers();
		}
	}

	private boolean peutBougerHaut(Joueur j) {
		if ((this.getCarte().getCarte()[j.getPosX()][j.getPosY() - 1] instanceof Ville)
				&& !Ville.getVille().getPortesOuvertes())
			return false;
		return j.getPosY() > 0 && j.getPa() > 0
				&& (j.estEnVille() && Ville.getVille().getPortesOuvertes() || !j.estEnVille());
	}

	/**
	 * Déplace le joueur d'une case sur la droite sur la carte
	 */
	public void deplacerDroite(Joueur j) {
		if (this.carte.getCarte()[j.getPosX()][j.getPosY()].getNbZombie() == 0 && this.peutBougerDroite(j)) {
			j.deplacerJoueur(1, 0);
			j.updateCarteDuJoueur(j.getPosX(), j.getPosY(), this.carte.getCarte()[j.getPosX()][j.getPosY()]);
			this.updateObservers();
		}
	}

	private boolean peutBougerDroite(Joueur j) {
		if ((this.getCarte().getCarte()[j.getPosX() + 1][j.getPosY()] instanceof Ville)
				&& !Ville.getVille().getPortesOuvertes())
			return false;
		return j.getPosX() < 24 && j.getPa() > 0
				&& (j.estEnVille() && Ville.getVille().getPortesOuvertes() || !j.estEnVille());
	}

	/**
	 * Déplace le joueur d'une case sur le bas sur la carte
	 */
	public void deplacerBas(Joueur j) {
		if (this.carte.getCarte()[j.getPosX()][j.getPosY()].getNbZombie() == 0 && this.peutBougerBas(j)) {
			j.deplacerJoueur(0, 1);
			j.updateCarteDuJoueur(j.getPosX(), j.getPosY(), this.carte.getCarte()[j.getPosX()][j.getPosY()]);
			this.updateObservers();
		}
	}

	private boolean peutBougerBas(Joueur j) {
		if ((this.getCarte().getCarte()[j.getPosX()][j.getPosY() + 1] instanceof Ville)
				&& !Ville.getVille().getPortesOuvertes())
			return false;
		return (j.getPosY() < 24 && j.getPa() > 0
				&& (j.estEnVille() && Ville.getVille().getPortesOuvertes() || !j.estEnVille()));
	}

	public void fouiller(Case c, Joueur j) {
		if (j.getPa() > 0) {
			j.ajouterPa(-1);
			c.setFouillee(true);
			this.updateObservers();
		}
	}

	public void tuerZombie(Case c) {
		if (this.getJoueurCourant().getPa() > 0 && c.getNbZombie() > 0) {
			this.getJoueurCourant().ajouterPa(-1);
			int x = Utilitaire.genererEntier(0, 10);
			if (x == 0) {
				this.getJoueurCourant().infligerDegats(10);
			}
			c.tuerZombie();
			this.updateObservers();
		}
	}

	public void majCarte(Case c, int x, int y) {
		if (this.getJoueurCourant().getPa() > 0) {
			for (Joueur j : this.joueurs) {
				j.updateCarteDuJoueur(x, y, c);
			}
			this.getJoueurCourant().ajouterPa(-1);
		}
		this.updateObservers();
	}

	public void updateObservers() {
		setChanged();
		notifyObservers();
	}
}
