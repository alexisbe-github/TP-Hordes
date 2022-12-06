package main.java.model.carte;

import java.util.List;
import java.util.Observable;

import main.java.model.Joueur;
import main.java.model.objet.Planche;
import main.java.model.objet.factory.FabriqueObjet;
import main.java.model.objet.factory.FabriqueObjetBoissonEnergisante;
import main.java.model.objet.factory.FabriqueObjetPlanche;
import main.java.model.objet.factory.FabriqueObjetPlaqueMetal;
import main.java.utilitaire.Utilitaire;

public class Carte extends Observable {

	private final int TAILLE = 25;
	private Case[][] carte; // la carte est un tableau à 2 dimensions de cases
	private List<Joueur> joueurs;
	private final int NB_PLANCHES = 1000;
	private final int NB_PLAQUES = 500;
	private final int NB_BOISSONS = 100;

	public Carte(List<Joueur> joueurs) {
		this.joueurs = joueurs;
	}

	public Case[][] getCarte() {
		return this.carte;
	}

	/**
	 * Initialisation de la carte en public pour que le jeu puisse l'initialiser en
	 * meme temps que le MVC
	 * 
	 * @param joueurs
	 */
	public void init() {
		// Initialisation de la carte
		this.carte = new Case[this.TAILLE][this.TAILLE];
		for (int i = 0; i < this.TAILLE; i++) {
			for (int j = 0; j < this.TAILLE; j++) {
				if (i == 12 && j == 12) {
					this.carte[12][12] = Ville.getVille();
				} else {
					this.carte[i][j] = new Case();
				}
			}
		}

		// Génération des loots dans la carte
		this.fillMap();
	}

	/**
	 * Initialisation des fabriques et appel des méthodes de fabrication
	 */
	private void fillMap() {
		// Création des factory
		FabriqueObjet fboisson = new FabriqueObjetBoissonEnergisante();
		FabriqueObjet fplanche = new FabriqueObjetPlanche();
		FabriqueObjet fplaque = new FabriqueObjetPlaqueMetal();

		// Appel des méthodes de fabrication
		this.fabriquer(fboisson, this.NB_BOISSONS);
		this.fabriquer(fplanche, this.NB_PLANCHES);
		this.fabriquer(fplaque, this.NB_PLAQUES);

		// Notification MVC
		setChanged();
		notifyObservers();
	}

	/**
	 * Selon la fabrique on va fabriquer iterations nombre d'objet
	 * 
	 * @param f          fabrique de l'objet en question
	 * @param iterations
	 */
	private void fabriquer(FabriqueObjet f, int iterations) {
		for (int i = 0; i < iterations; i++) {
			int x, y;
			do {
				x = Utilitaire.genererEntier(0, this.TAILLE);
				y = Utilitaire.genererEntier(0, this.TAILLE);
			} while (x == 12 && y == 12); // on évite de générer les objets sur la ville
			this.carte[x][y].getLoot().ajouter(f.creerObjet(1)); // création de l'objet en x,y
		}
	}
}
