package main.java.model.carte;

import java.util.LinkedHashSet;
import java.util.Set;

import main.java.model.construction.Construction;
import main.java.model.construction.reflection.ConstructionLoader;
import main.java.model.objet.Objet;
import main.java.model.objet.Planche;
import main.java.model.objet.PlaqueMetal;
import main.java.model.objet.Ration;
import main.java.model.stockage.Entrepot;

public class Ville extends Case {

	private boolean portesOuvertes;
	private static Ville instance;
	private final int NB_RATION_INIT = 50;
	private Set<Construction> constructions;

	private Ville() {
		this.nbZombie = 0;
		this.portesOuvertes = false;
		this.loot = new Entrepot();
		this.loot.add(new Planche(500));
		this.loot.add(new PlaqueMetal(500));
		this.path = "src/main/resources/";
		if (this.portesOuvertes) {
			this.path += "ville1.png";
		} else {
			this.path += "ville2.png";
		}
		this.init();
	}

	/**
	 * Initialisation de l'entrepot et des constructions de la ville
	 */
	private void init() {
		this.loot.ajouter(new Ration(this.NB_RATION_INIT));
		this.constructions = new LinkedHashSet<Construction>();
		try {
			ConstructionLoader.getInstance().chargerConstructions(this.constructions);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Singleton pour récupérer l'instance de la ville
	 * 
	 * @return la ville
	 */
	public static Ville getVille() {
		if (instance == null) {
			instance = new Ville();
		}
		return instance;
	}
	
	public Set<Construction> getConstructions() {
		return this.constructions;
	}
	
	public int getNbPlanches() {
		int res = 0;
		for(Objet o:this.loot) {
			if(o.equals(new Planche(1))) {
				res+= o.getQuantite();
			}
		}
		return res;
	}
	
	public int getNbPlaques() {
		int res = 0;
		for(Objet o:this.loot) {
			if(o.equals(new PlaqueMetal(1))) {
				res+= o.getQuantite();
			}
		}
		return res;
	}

	public boolean getPortesOuvertes() {
		return this.portesOuvertes;
	}

	public void ouvrirPortes() {
		this.path = "src/main/resources/ville1.png";
		this.portesOuvertes = true;
	}

	public void fermerPortes() {
		this.path = "src/main/resources/ville2.png";
		this.portesOuvertes = false;
	}

}
