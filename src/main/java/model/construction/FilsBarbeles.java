package main.java.model.construction;

public class FilsBarbeles extends Construction{
	
	public FilsBarbeles() {
		super();
	}

	@Override
	public int getResistanceAuxZombies() {
		return 30;
	}

	@Override
	public int getNbPlanches() {
		return 20;
	}

	@Override
	public int getNbPlaques() {
		return 30;
	}

	@Override
	public int getNbPa() {
		return 20;
	}

	@Override
	public String getPath() {
		return "src/main/resources/barbeles.png";
	}

	@Override
	public String getNom() {
		return "Fils barbelés";
	}

}
