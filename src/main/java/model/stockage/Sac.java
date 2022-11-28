package main.java.model.stockage;

import main.java.model.objet.Objet;

public class Sac extends Inventaire {

	private final int TAILLE_INVENTAIRE = 10; // taille de l'inventaire

	public Sac() {
		super();
	}

	/**
	 * Méthode d'insertion propre au sac, qui vérifie si on ne dépasse pas la taille
	 * du sac lors de l'insertion
	 */
	@Override
	public void ajouter(Objet o) {
		if (super.size() < this.TAILLE_INVENTAIRE) {
			this.insertion(o);
		}
	}

}
