package main.java.model.construction;

public class MurEnceinte extends Construction {

	public MurEnceinte() {
		super();
	}

	@Override
	public int getResistanceAuxZombies() {
		return 20;
	}

	@Override
	public int getNbPlanches() {
		return 20;
	}

	@Override
	public int getNbPlaques() {
		return 5;
	}

	@Override
	public int getNbPa() {
		return 10;
	}

	@Override
	public String getPath() {
		return "src/main/resources/mur_enceinte.png";
	}

	@Override
	public String getNom() {
		return "Mur d'enceinte";
	}

}
