package main.java.model;

import java.awt.Point;

import main.java.model.carte.Case;
import main.java.model.carte.Ville;
import main.java.model.objet.BoissonEnergisante;
import main.java.model.objet.Gourde;
import main.java.model.objet.Objet;
import main.java.model.stockage.Sac;

public class Joueur {

	private String nom;
	private Point position;
	private int pv, pa;
	private Sac sac;
	private boolean aBu;
	private int compteurBoissonEnergisante;
	private final int PV_MAX = 100;
	private final int PA_MAX = 10;
	private final int PA_INIT = 6;
	private final int POINT_X = 0;
	private final int POINT_Y = 0;
	private Case[][] carteVersionJoueur; // Chaque joueur a une version différente de la carte en fonction de son
											// exploration

	public Joueur(String nom) {
		this.init(nom); // Initialisation du joueur avec les valeurs constantes de base
	}

	/**
	 * Méthode d'initialisation de l'objet courant
	 * 
	 * @param nom du joueur
	 */
	private void init(String nom) {
		this.nom = nom;
		this.pv = this.PV_MAX;
		this.pa = this.PA_INIT;
		this.aBu = false;
		this.compteurBoissonEnergisante = -1;
		this.sac = new Sac();
		this.position = new Point(this.POINT_X, this.POINT_Y);
		this.carteVersionJoueur = new Case[25][25];
		for (int i = 0; i < 25; i++) {
			for (int j = 0; j < 25; j++) {
				if (i == 12 && j == 12) {
					this.carteVersionJoueur[12][12] = Ville.getVille();
				} else {
					this.carteVersionJoueur[i][j] = Case.initCaseVide();
				}
			}
		}
	}
	
	public boolean aBuBoissonEnergisante() {
		return this.compteurBoissonEnergisante != -1;
	}

	public int getPv() {
		return this.pv;
	}

	public int getPa() {
		return this.pa;
	}

	public Case[][] getCarteVersionJoueur() {
		return this.carteVersionJoueur;
	}

	public String getNom() {
		return this.nom;
	}

	public Sac getInventaire() {
		return this.sac;
	}

	/**
	 * Savoir si le joueur porte au moins une gourde sur lui
	 * 
	 * @return
	 */
	public boolean aUneGourde() {
		for (Objet item : this.getInventaire()) {
			if (item.getNom().equals("Gourde"))
				return true;
		}
		return false;
	}
	
	public void boireAuPuitDeLaVille() {
		if(!this.aBu && this.estEnVille()) {
			this.ajouterPa(6);
			this.aBu = true;
		}
	}

	public boolean aBu() {
		return this.aBu;
	}
	
	public void boire() {
		if (!this.aBu && this.aUneGourde()) {
			this.ajouterPa(6);
			this.sac.remove(new Gourde(1));
			this.aBu = true;
		}
	}

	/**
	 * Le joueur boit une boisson énergisante, on lui réinitialise son compteur
	 */
	public void boireBoissonEnergisante() {
		this.compteurBoissonEnergisante = 0;
		this.ajouterPa(6);
		this.sac.remove(new BoissonEnergisante(1));
	}
	
	public int getCompteurBoissonEnergisante() {
		return this.compteurBoissonEnergisante;
	}
	
	public void incrementCompteurBoissonEnergisante() {
		this.compteurBoissonEnergisante++;
	}
	
	/**
	 * Méthode décremntant le nombre de point d'action
	 */
	private void decrementPA() {
		if (this.pa >= 1)
			this.pa--;
	}

	/**
	 * Getter de la position transformée en x dans le tableau de la carte
	 * 
	 * @return entier la position en x + 12
	 */
	public int getPosX() {
		return this.position.x + 12;
	}

	/**
	 * Getter de la position transformée en y dans le tableau de la carte
	 * 
	 * @return entier la position en y + 12
	 */
	public int getPosY() {
		return this.position.y + 12;
	}

	public void deplacerJoueur(int dx, int dy) {
		this.position.x += dx;
		this.position.y += dy;
		this.pa--;
	}

	/**
	 * 
	 * @return si le joueur est en ville (0,0)
	 */
	public boolean estEnVille() {
		return this.position.x == 0 && this.position.y == 0;
	}

	/**
	 * Ouverture des portes de la ville qui coute 1 PA
	 */
	public void ouvrirPortesVille() {
		if (!Ville.getVille().getPortesOuvertes() && this.pa > 0 && this.estEnVille()) {
			Ville.getVille().ouvrirPortes();
			this.pa--;
		}
	}

	/**
	 * Fermeture des portes de la ville qui coute 1 PA
	 */
	public void fermerPortesVille() {
		if (Ville.getVille().getPortesOuvertes() && this.pa > 0 && this.estEnVille()) {
			Ville.getVille().fermerPortes();
			this.pa--;
		}
	}

	public void updateCarteDuJoueur(int x, int y, Case c) {
		this.carteVersionJoueur[x][y] = c;
	}

	public void infligerDegats(int degats) {
		this.pv -= degats;
	}

	public void ajouterPa(int dpa) {
		this.pa += dpa;
		if (this.pa > this.PA_MAX) {
			this.pa = this.PA_MAX;
		}
	}

}
