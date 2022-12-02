package main.java.model.carte;

import java.util.Objects;

import main.java.model.stockage.ListeItems;
import main.java.utilitaire.Utilitaire;

public class Case {

	protected ListeItems loot;
	protected int nbZombie;
	private final double PROBA_0_ZOMBIE = 0.3;
	private boolean fouillee;
	private final String[] CHEMINS = { "bg-1.png" };
	protected String path;

	public Case() {
		this.init();
	}

	private Case(int a) {
		this.fouillee = false;
		this.path = "src/main/resources/unknown.png";
		this.loot = new ListeItems();
	}

	public static Case initCaseVide() {
		return new Case(0);
	}
	
	public String getPath() {
		return this.path;
	}

	@Override
	public String toString() {
		return "Case [loot=" + loot + ", nbZombie=" + nbZombie + ", PROBA_0_ZOMBIE=" + PROBA_0_ZOMBIE + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Case other = (Case) obj;
		return fouillee == other.fouillee && Objects.equals(loot, other.loot) && nbZombie == other.nbZombie;
	}

	private void init() {
		this.path = "src/main/resources/" + CHEMINS[Utilitaire.genererEntier(0, this.CHEMINS.length)];
		this.fouillee = false;
		this.loot = new ListeItems();
		int x = Utilitaire.genererEntier(1, 101);
		if (x < PROBA_0_ZOMBIE * 100) {
			this.nbZombie = 0;
		} else {
			this.nbZombie = Utilitaire.genererEntier(1, 8);
		}
	}

	public int getNbZombie() {
		return this.nbZombie;
	}
	
	public void tuerZombie() {
		this.nbZombie--;
	}

	public ListeItems getLoot() {
		return this.loot;
	}
	
	public boolean estFouillee() {
		return this.fouillee;
	}
	
	public void setFouillee(boolean b) {
		this.fouillee = b;
	}
}
