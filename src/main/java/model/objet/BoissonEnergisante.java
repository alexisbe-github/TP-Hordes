package main.java.model.objet;

public class BoissonEnergisante extends Objet {

	public static final String NOM = "Boisson �nergisante";
	public static final String PATH = "src/main/resources/boisson.png";

	public BoissonEnergisante(int nbBoisson) {
		super(NOM, nbBoisson, PATH);
	}

	@Override
	public boolean stackable() {
		return false;
	}

}
