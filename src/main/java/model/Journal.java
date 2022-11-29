package main.java.model;

import java.util.LinkedList;
import java.util.List;

public class Journal extends LinkedList<String>{
	
	private List<String> journalVeille;
	private int jourCourant;

	public Journal() {
		super();
		this.journalVeille = new LinkedList<String>();
		this.jourCourant = 1;
		this.setEntete();
	}
	
	private void setEntete() {
		this.add("---Résumé du jour " + this.jourCourant+"---");
	}
	
	public List<String> getJournal() {
		return this.journalVeille;
	}
	
	public void setJournal() {
		this.journalVeille.clear();
		this.journalVeille.addAll(this);
		this.jourCourant++;
		this.clear();
		this.setEntete();
	}
	
	public void addLigne(String ligne) {
		this.add(ligne);
	}
}
