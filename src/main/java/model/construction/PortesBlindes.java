package main.java.model.construction;

public class PortesBlindes extends Construction {
	
	public PortesBlindes() {
		super();
	}

	@Override
	public int getResistanceAuxZombies() {
		return 100;
	}

	@Override
	public int getNbPlanches() {
		return 50;
	}

	@Override
	public int getNbPlaques() {
		return 50;
	}

	@Override
	public int getNbPa() {
		return 40;
	}

	@Override
	public String getPath() {
		return "src/main/resources/portes.png";
	}

	@Override
	public String getNom() {
		return "Portes blindées";
	}

}
