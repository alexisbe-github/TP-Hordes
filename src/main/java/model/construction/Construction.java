package main.java.model.construction;

public abstract class Construction {

	protected int avancement;

	public Construction() {
		this.avancement = -1;
	}

	public int getAvancement() {
		return this.avancement;
	}

	public boolean constructionFinie() {
		return this.avancement == this.getNbPa();
	}

	public boolean estEnConstruction() {
		return this.avancement != -1;
	}

	public void commencerConstruction() {
		this.avancement = 0;
	}

	public int ajouterPa(int pa) {
		int retrait = 0;
		if (this.avancement + pa > this.getNbPa()) {
			retrait = this.getNbPa() - this.avancement;
			this.avancement = this.getNbPa();
		} else {
			this.avancement += pa;
			retrait = pa;
		}
		return retrait;
	}

	public abstract int getResistanceAuxZombies();

	public abstract int getNbPlanches();

	public abstract int getNbPlaques();

	public abstract int getNbPa();

	public abstract String getPath();

	public abstract String getNom();
}
