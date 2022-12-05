package main.java.model;

import java.awt.Point;

import main.java.model.carte.Case;
import main.java.model.carte.Ville;
import main.java.model.objet.BoissonEnergisante;
import main.java.model.objet.Gourde;
import main.java.model.objet.Objet;
import main.java.model.objet.Ration;
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
	private Case[][] carteVersionJoueur; // Chaque joueur a une version diff�rente de la carte en fonction de son
											// exploration

	public Joueur(String nom) {
		this.init(nom); // Initialisation du joueur avec les valeurs constantes de base
	}

	/**
	 * M�thode d'initialisation de l'objet courant
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

	/**
	 * Permet de savoir si le joueur a d�j� bu sa premi�re boisson �nergisante dans
	 * la partie
	 * 
	 * @return
	 */
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
	 * Si le joueur a une ration dans le sac, le joueur la mange et gagne 6 points
	 * d'actions
	 */
	public void mangerRation() {
		Ration r = new Ration(1);
		if (this.sac.contains(r)) {
			this.sac.remove(r);
			this.ajouterPa(6);
		}
	}

	/**
	 * Savoir si le joueur porte au moins une gourde sur lui
	 * 
	 * @return bool�en
	 */
	public boolean aUneGourde() {
		return this.sac.contains(new Gourde(1));
	}

	/**
	 * Permet au joueur de boire de l'eau dans la ville si il n'a pas d�j� bu dans
	 * la journ�e et qu'il est en ville
	 */
	public void boireAuPuitsDeLaVille() {
		if (!this.aBu && this.estEnVille()) {
			this.ajouterPa(6);
			this.aBu = true;
		}
	}

	public boolean aBu() {
		return this.aBu;
	}

	public void resetABu() {
		this.aBu = false;
	}

	/**
	 * Le joueur boit de l'eau si il poss�de une gourde sur lui et la supprime du
	 * sac
	 */
	public void boire() {
		if (!this.aBu && this.aUneGourde()) {
			this.ajouterPa(6);
			this.sac.remove(new Gourde(1));
			this.aBu = true;
		}
	}

	/**
	 * Le joueur boit une boisson �nergisante, on lui r�initialise son compteur
	 */
	public void boireBoissonEnergisante() {
		this.compteurBoissonEnergisante = 0;
		this.ajouterPa(6);
		this.sac.remove(new BoissonEnergisante(1));
	}

	public int getCompteurBoissonEnergisante() {
		return this.compteurBoissonEnergisante;
	}

	/**
	 * Incr�mente le compteur de tour sans boisson �nergisante
	 */
	public void incrementCompteurBoissonEnergisante() {
		this.compteurBoissonEnergisante++;
	}

	/**
	 * M�thode d�cremntant le nombre de point d'action
	 */
	private void decrementPA() {
		if (this.pa >= 1)
			this.pa--;
	}

	/**
	 * Getter de la position transform�e en x dans le tableau de la carte
	 * 
	 * @return entier la position en x + 12
	 */
	public int getPosX() {
		return this.position.x + 12;
	}

	/**
	 * Getter de la position transform�e en y dans le tableau de la carte
	 * 
	 * @return entier la position en y + 12
	 */
	public int getPosY() {
		return this.position.y + 12;
	}

	/**
	 * D�placement du joueur en fonction des param�tres
	 * 
	 * @param dx d�placement en x
	 * @param dy d�placement en y
	 */
	public void deplacerJoueur(int dx, int dy) {
		if (this.pv > 0) {
			this.position.x += dx;
			this.position.y += dy;
			this.pa--;
		}
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

	/**
	 * Met la carte du joueur � jour
	 * 
	 * @param x position en x � mettre � jour
	 * @param y position en y � mettre � jour
	 * @param c objet avec contenant les donn�es mises � jour � placer dans la carte
	 *          du joueur
	 */
	public void updateCarteDuJoueur(int x, int y, Case c) {
		this.carteVersionJoueur[x][y] = c;
	}

	/**
	 * 
	 * @param degats
	 */
	public void infligerDegats(int degats) {
		this.pv -= degats;
	}

	/**
	 * Ajout des points d'action au joueur en v�rifiant qu'il ne d�passe pas la
	 * limite
	 * 
	 * @param dpa
	 */
	public void ajouterPa(int dpa) {
		this.pa += dpa;
		if (this.pa > this.PA_MAX) {
			this.pa = this.PA_MAX;
		}
	}

}
