package main.java.model.objet;

public class Planche extends Objet {

	public static final String NOM = "Planches";
	public static final String PATH = "src/main/resources/planche.gif";

	public Planche(int nbPlanches) {
		super(NOM, nbPlanches, PATH);
	}

	@Override
	public boolean stackable() {
		return true;
	}

}
