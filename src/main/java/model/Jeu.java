package main.java.model;

import java.util.List;
import java.util.Observable;

import main.java.model.carte.Carte;
import main.java.model.carte.Case;
import main.java.model.carte.Ville;
import main.java.model.objet.BoissonEnergisante;
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
	private Journal journal;

	public Jeu(List<Joueur> joueurs) {
		this.init(joueurs);
	}

	private void init(List<Joueur> joueurs) {
		this.joueurs = joueurs;
		this.carte = new Carte(joueurs);
		this.joueurCourant = 0;
		this.tour = 0;
		this.journal = new Journal();
		this.joueurs.get(0).getInventaire().ajouter(new BoissonEnergisante(1));
		this.joueurs.get(0).getInventaire().ajouter(new BoissonEnergisante(1));
		this.joueurs.get(0).getInventaire().ajouter(new BoissonEnergisante(1));
		this.joueurs.get(0).getInventaire().ajouter(new BoissonEnergisante(1));
		this.joueurs.get(0).getInventaire().ajouter(new BoissonEnergisante(1));
	}

	public void commencerJeu() {
		this.joueurCourant = 0;
		this.tour = 0;
		this.carte.init();
		this.updateObservers();
	}

	public Carte getCarte() {
		return this.carte;
	}
	
	public Journal getJournal() {
		return this.journal;
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
			if (this.tour % 24 == 0) {
				this.prochainJour();
			}
		}
		this.updateObservers();
	}
	
	private void prochainJour() {
		this.journal.setJournal();
		for(Joueur j: this.joueurs) {
			if(j.aBu()) {
				j.resetABu();
			}
		}
	}

	private void prochainTour() {
		this.tour += 2;
		for (Joueur j : this.joueurs) {
			j.ajouterPa(4);
			if (j.aBuBoissonEnergisante()) {
				j.incrementCompteurBoissonEnergisante();
				if (j.getCompteurBoissonEnergisante() > 3) {
					j.infligerDegats(5);
				}
			}
			if(j.getPv() <= 0) {
				this.joueurs.remove(j);
			}
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

	public void majCarte(Joueur joueur,Case c, int x, int y) {
		if (this.getJoueurCourant().getPa() > 0) {
			for (Joueur j : this.joueurs) {
				j.updateCarteDuJoueur(x, y, c);
			}
			this.getJoueurCourant().ajouterPa(-1);
			String journalContent = joueur.getNom() + " a mis la carte à jour en x:" + (x-12) + " y:" + (y-12);
			this.journal.addLigne(journalContent);
		}
		this.updateObservers();
	}

	public void updateObservers() {
		setChanged();
		notifyObservers();
	}
}
