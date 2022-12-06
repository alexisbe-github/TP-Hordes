package main.java.model.objet;

import main.java.utilitaire.Utilitaire;

public class Ration extends Objet {

	public static final String NOM = "Ration";
	private final static String[] CHEMINS = { "ration_1.gif", "ration_2.gif", "ration_3.gif", "ration_4.gif" };

	public Ration(int nbRation) {
		super(NOM, nbRation, "src/main/resources/" + CHEMINS[Utilitaire.genererEntier(0, CHEMINS.length - 1)]);
	}

	public Ration(int nbRation, int idSprite) {
		super(NOM, nbRation, "src/main/resources/" + CHEMINS[idSprite]);
	}

	@Override
	public boolean stackable() {
		return false;
	}

}
