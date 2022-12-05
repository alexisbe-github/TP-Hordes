package main.java.model;

import java.util.LinkedList;
import java.util.List;

public class Journal extends LinkedList<String>{
	
	private List<String> journalVeille; //liste stockant les donn�es de la veille
	private int jourCourant;

	public Journal() {
		super();
		this.journalVeille = new LinkedList<String>();
		this.jourCourant = 1;
		this.setEntete();
	}
	
	/**
	 * Ecriture de l'ent�te du jour courant
	 */
	private void setEntete() {
		this.add("---R�sum� du jour " + this.jourCourant+"---");
	}
	
	public List<String> getJournal() {
		return this.journalVeille;
	}
	
	/**
	 * Passage du prochain jour.
	 * On vide le journal d'hier pour mettre celui d'aujourd'hui, puis on revide celui d'aujourd'hui
	 */
	public void setJournal() {
		this.journalVeille.clear();
		this.journalVeille.addAll(this);
		this.jourCourant++;
		this.clear();
		this.setEntete();
	}
	
	/**
	 * Ajoute au journal d'aujourd'hui une information dans le journal
	 * @param ligne, contenu � �crire
	 */
	public void addLigne(String ligne) {
		this.add(ligne);
	}
	
	public int getJourCourant() {
		return this.jourCourant;
	}
}
