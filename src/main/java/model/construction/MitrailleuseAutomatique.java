package main.java.model.construction;

public class MitrailleuseAutomatique extends Construction {

	public MitrailleuseAutomatique() {
		super();
	}

	@Override
	public int getResistanceAuxZombies() {
		return 200;
	}

	@Override
	public int getNbPlanches() {
		return 75;
	}

	@Override
	public int getNbPlaques() {
		return 75;
	}

	@Override
	public int getNbPa() {
		return 50;
	}

	@Override
	public String getPath() {
		return "src/main/resources/tourelle.png";
	}

	@Override
	public String getNom() {
		return "Miradors avec mitrailleuses automatiques";
	}

}
