package main.java.model.construction;

public class Abris extends Construction{
	
	public Abris() {
		super();
	}

	@Override
	public int getResistanceAuxZombies() {
		return 500;
	}

	@Override
	public int getNbPlanches() {
		return 100;
	}

	@Override
	public int getNbPlaques() {
		return 200;
	}

	@Override
	public int getNbPa() {
		return 60;
	}

	@Override
	public String getPath() {
		return "src/main/resources/bunker.png";
	}

	@Override
	public String getNom() {
		return "Abris anti-atomique";
	}

}
