package main.java.model.objet;


public class PlaqueMetal extends Objet {

	public static final String NOM = "Plaques de métal";
	public static final String PATH = "src/main/resources/plaque.png";

	public PlaqueMetal(int nbPlaques) {
		super(NOM, nbPlaques, PATH);

	}

	@Override
	public boolean stackable() {
		return true;
	}

}
