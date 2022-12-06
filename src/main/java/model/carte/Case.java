package main.java.model.carte;

import java.util.Objects;

import main.java.model.stockage.Inventaire;
import main.java.model.stockage.ListeItems;
import main.java.utilitaire.Utilitaire;

public class Case {

	protected Inventaire loot; // une case contient une liste d'objet qui est un Inventaire
	protected int nbZombie;
	private final double PROBA_0_ZOMBIE = 0.3;
	private boolean fouillee;
	private final String[] CHEMINS = { "bg-1.png" };
	protected String path;

	public Case() {
		this.init();
	}

	/**
	 * Constructeur servant pour les cases inconnues des joueurs
	 * 
	 * @param a, entier sans importance servant à distinguer du constructeur sans
	 *           paramètres
	 */
	private Case(int a) {
		this.fouillee = false;
		this.path = "src/main/resources/unknown.png";
		this.loot = new ListeItems();
	}

	/**
	 * Fabrique une case vide
	 * 
	 * @return Case
	 */
	public static Case initCaseVide() {
		return new Case(0);
	}

	public String getPath() {
		return this.path;
	}

	@Override
	public String toString() {
		return "Case [loot=" + loot + ", nbZombie=" + nbZombie + ", PROBA_0_ZOMBIE=" + PROBA_0_ZOMBIE + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Case other = (Case) obj;
		return fouillee == other.fouillee && Objects.equals(loot, other.loot) && nbZombie == other.nbZombie;
	}

	/**
	 * Initialisation d'une case
	 */
	private void init() {
		this.path = "src/main/resources/" + CHEMINS[Utilitaire.genererEntier(0, this.CHEMINS.length)]; // Chargement du
																										// sprite
		this.fouillee = false;
		this.loot = new ListeItems();

		// Génération des zombies sur la case
		int x = Utilitaire.genererEntier(1, 101);
		if (x < PROBA_0_ZOMBIE * 100) {
			this.nbZombie = 0;
		} else {
			this.nbZombie = Utilitaire.genererEntier(1, 8);
		}
	}

	public int getNbZombie() {
		return this.nbZombie;
	}

	public void tuerZombie() {
		this.nbZombie--;
	}

	public Inventaire getLoot() {
		return this.loot;
	}

	public boolean estFouillee() {
		return this.fouillee;
	}

	public void setFouillee(boolean b) {
		this.fouillee = b;
	}
}
