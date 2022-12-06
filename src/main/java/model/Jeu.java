package main.java.model;

import java.util.List;
import java.util.Observable;

import main.java.model.carte.Carte;
import main.java.model.carte.Case;
import main.java.model.carte.Ville;
import main.java.model.construction.Construction;
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

	/**
	 * Initialisation de l'objet jeu à partir de la liste des joueurs
	 * 
	 * @param joueurs
	 */
	private void init(List<Joueur> joueurs) {
		this.joueurs = joueurs;
		this.carte = new Carte(joueurs);
		this.joueurCourant = 0;
		this.tour = 0;
		this.journal = new Journal();
	}

	/**
	 * Lancement du jeu
	 */
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

	/**
	 * A chaque joueur qui passe son tour cette méthode incrémente toutes les
	 * variables selon l'état du tour
	 */
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

	/**
	 * Lorsqu'on passe un jour on dit que tous les joueurs n'ont pas bu, on lance
	 * l'attaque des zombies et on tourne la page dans le journal
	 */
	private void prochainJour() {
		for (Joueur j : this.joueurs) {
			if (j.aBu()) {
				j.resetABu();
			}
		}
		this.attaque();
		this.journal.setJournal();
	}

	/**
	 * Appel du prochain tour
	 */
	private void prochainTour() {
		this.tour += 2;
		for (Joueur j : this.joueurs) {
			j.ajouterPa(4); // on rajoute 4 PA à chaque joueur lors de passage de tour

			// Si le joueur a déjà bu une boisson énergisante on vérifie si on doit enlever
			// des pv et incrémenter la variable
			if (j.aBuBoissonEnergisante()) {
				j.incrementCompteurBoissonEnergisante();
				if (j.getCompteurBoissonEnergisante() > 3) {
					j.infligerDegats(5);
					if (j.getPv() == 0)
						this.journal.addLigne(j.getNom() + " est mort par manque de boisson énergisante.");
				}
			}

			// Si un joueur est mort on le retire de la liste des joueurs
			if (j.getPv() <= 0) {
				this.joueurs.remove(j);
			}
		}
	}

	/**
	 * Attaque des zombies durant la nuit, qui tue 50% des joueurs en ville si les
	 * résistances sont inopérantes face aux zombies ou si les portes de la ville
	 * sont restées ouvertes
	 */
	private void attaque() {
		// Calcul de la resistance de la ville en fonction des constructions
		int nbZombieAttaque = this.getJour() + Utilitaire.genererEntier(0, 11);
		int resistanceVille = 0;
		for (Construction c : Ville.getVille().getConstructions()) {
			if (c.constructionFinie()) {
				resistanceVille += c.getResistanceAuxZombies();
			}
		}

		// Pour chaque joueur en dehors de la ville on les tue
		for (Joueur j : this.joueurs) {
			if (!j.estEnVille()) {
				this.journal.addLigne(j.getNom() + " est mort en x:" + j.getPosX() + ",y:" + j.getPosY()
						+ " par les zombies en dehors de la ville.");
				this.joueurs.remove(j);
			}
		}

		// Si les défenses sont inopérantes ou les portes de la ville ouvertes on tue
		// 50% des joueurs en ville
		if (nbZombieAttaque > resistanceVille || Ville.getVille().getPortesOuvertes()) {
			int taille = this.joueurs.size() / 2;
			for (int i = 0; i < taille; i++) {
				int index = Utilitaire.genererEntier(0, this.joueurs.size());
				this.journal.addLigne(
						this.joueurs.get(index).getNom() + " est mort cette nuit dans la ville par les zombies.");
				this.joueurs.remove(index);
			}
		} else {
			this.journal.addLigne("Personne n'est mort cette nuit.");
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

	/**
	 * Vérifie si le joueur peut se déplacer sur la case de gauche
	 * 
	 * @param j joueur en question
	 * @return booléen
	 */
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

	/**
	 * Vérifie si le joueur peut se déplacer sur la case du haut
	 * 
	 * @param j joueur en question
	 * @return booléen
	 */
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

	/**
	 * Vérifie si le joueur peut se déplacer sur la case de droite
	 * 
	 * @param j joueur en question
	 * @return booléen
	 */
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

	/**
	 * Vérifie si le joueur peut se déplacer sur la case du bas
	 * 
	 * @param j joueur en question
	 * @return booléen
	 */
	private boolean peutBougerBas(Joueur j) {
		if ((this.getCarte().getCarte()[j.getPosX()][j.getPosY() + 1] instanceof Ville)
				&& !Ville.getVille().getPortesOuvertes())
			return false;
		return (j.getPosY() < 24 && j.getPa() > 0
				&& (j.estEnVille() && Ville.getVille().getPortesOuvertes() || !j.estEnVille()));
	}

	/**
	 * Le joueur fouille une case
	 * 
	 * @param c la Case en question
	 * @param j le joueur qui la fouille
	 */
	public void fouiller(Case c, Joueur j) {
		if (j.getPa() > 0) {
			j.ajouterPa(-1);
			c.setFouillee(true);
			this.updateObservers();
		}
	}

	/**
	 * Un zombie meurt sur la Case c
	 * 
	 * @param c
	 */
	public void tuerZombie(Case c) {
		if (this.getJoueurCourant().getPa() > 0 && c.getNbZombie() > 0 && this.getJoueurCourant().getPv() > 0) {
			this.getJoueurCourant().ajouterPa(-1);
			int x = Utilitaire.genererEntier(0, 10);
			if (x == 0) {
				this.getJoueurCourant().infligerDegats(10);
				if (this.getJoueurCourant().getPv() == 0)
					this.journal.addLigne(this.getJoueurCourant().getNom() + " est mort à cause d'un zombie.");
			}
			c.tuerZombie();
			this.updateObservers();
		}
	}

	/**
	 * Met la case à jour pour tous les autres joueurs
	 * 
	 * @param joueur
	 * @param c
	 * @param x
	 * @param y
	 */
	public void majCarte(Joueur joueur, Case c, int x, int y) {
		if (this.getJoueurCourant().getPa() > 0) {

			// Pour chaque joueur on met à jour sa version de la carte
			for (Joueur j : this.joueurs) {
				j.updateCarteDuJoueur(x, y, c);
			}
			this.getJoueurCourant().ajouterPa(-1);
			String journalContent = joueur.getNom() + " a mis la carte à jour en x:" + (x - 12) + " y:" + (y - 12);
			this.journal.addLigne(journalContent);
		}
		this.updateObservers();
	}

	/**
	 * Permet de notifier aux vues de mettre à jour l'état du jeu
	 */
	public void updateObservers() {
		setChanged();
		notifyObservers();
	}
}
