package main.java.model.construction;

public class Mine extends Construction {

	public Mine() {
		super();
	}

	@Override
	public int getResistanceAuxZombies() {
		return 50;
	}

	@Override
	public int getNbPlanches() {
		return 10;
	}

	@Override
	public int getNbPlaques() {
		return 50;
	}

	@Override
	public int getNbPa() {
		return 30;
	}

	@Override
	public String getPath() {
		return "src/main/resources/mine.png";
	}

	@Override
	public String getNom() {
		return "Mines autour de la ville";
	}

}
