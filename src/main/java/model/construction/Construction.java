package main.java.model.construction;

public abstract class Construction {

	protected int avancement;

	public Construction() {
		this.avancement = -1;
	}

	public int getAvancement() {
		return this.avancement;
	}

	public boolean estEnConstruction() {
		return this.avancement != -1;
	}

	public void commencerConstruction() {
		this.avancement = 0;
	}

	public void ajouterPa(int pa) {
		this.avancement += pa;
	}

	public abstract int getResistanceAuxZombies();

	public abstract int getNbPlanches();

	public abstract int getNbPlaques();

	public abstract int getNbPa();

	public abstract String getPath();

	public abstract String getNom();
}
