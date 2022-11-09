package main.java.model.carte;

import main.java.model.objet.Ration;
import main.java.model.stockage.ListeItems;

public class Ville extends Case {

	private boolean portesOuvertes;
	private static Ville instance;
	private final int NB_RATION_INIT = 50;

	private Ville() {
		this.nbZombie = 0;
		this.portesOuvertes = false;
		this.loot = new ListeItems();
	}

	/**
	 * Initialisation de l'entrepot
	 */
	private void init() {
		for (int i = 0; i < this.NB_RATION_INIT; i++) {
			this.loot.ajouter(new Ration(50));
		}
	}

	/**
	 * Singleton pour récupérer l'instance de la ville
	 * 
	 * @return la ville
	 */
	public static Ville getVille() {
		if (instance == null) {
			instance = new Ville();
		}
		return instance;
	}

	public boolean getPortesOuvertes() {
		return this.portesOuvertes;
	}

	public void ouvrirPortes() {
		this.portesOuvertes = true;
	}

	public void fermerPortes() {
		this.portesOuvertes = false;
	}

}
