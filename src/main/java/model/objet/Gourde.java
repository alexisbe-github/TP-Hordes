package main.java.model.objet;

public class Gourde extends Objet {

	public static final String NOM = "Gourde";
	public static final String PATH = "src/main/resources/gourde.gif";

	public Gourde(int nbGourde) {
		super(NOM, nbGourde, PATH);
	}

	@Override
	public boolean stackable() {
		return false;
	}

}
