package main.java.model;

import java.util.LinkedList;

public class Journal extends LinkedList<String>{
	
	private Journal journalVeille;

	public Journal() {
		super();
		this.journalVeille = null;
	}
	
	public Journal getJournal() {
		return this.journalVeille;
	}
	
	public void setJournal() {
		this.journalVeille = this;
		this.clear();
	}
	
	public void addLigne(String ligne) {
		this.journalVeille.add(ligne);
	}
}
