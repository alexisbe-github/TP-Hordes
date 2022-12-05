package main.java.model.construction;

public class FosseZombie extends Construction{
	
	public FosseZombie() {
		super();
	}

	@Override
	public int getResistanceAuxZombies() {
		return 50;
	}

	@Override
	public int getNbPlanches() {
		return 50;
	}

	@Override
	public int getNbPlaques() {
		return 25;
	}

	@Override
	public int getNbPa() {
		return 30;
	}

	@Override
	public String getPath() {
		return "src/main/resources/fosse.png";
	}

	@Override
	public String getNom() {
		return "Fosses à zombies";
	}

}
