package main.java.view;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import main.java.model.Jeu;
import main.java.model.Joueur;

public class VueInfoJeu extends JPanel implements Observer {

	private JLabel labelTexte;
	private JProgressBar barreVie;
	private JProgressBar barrePA;

	public VueInfoJeu() {
		JPanel top = new JPanel(new GridLayout(1,2));
		JPanel topRight = new JPanel();
		JLabel textePA = new JLabel("PA:");
		this.barrePA = new JProgressBar(0,10);
		this.barrePA.setStringPainted(true);
		this.barrePA.setForeground(Color.orange);
		this.barrePA.setValue(6);
		this.barrePA.setString("6/10");

		this.setLayout(new GridLayout(2,1));
		this.labelTexte = new JLabel("",SwingConstants.CENTER);
		this.barreVie = new JProgressBar(0,100);
		this.barreVie.setStringPainted(true);
		this.barreVie.setForeground(new Color(2,100,64));
		this.barreVie.setString("PV: 100/100");
		this.barreVie.setBackground(new Color(139,0,0));
		this.barreVie.setValue(100);

		topRight.add(textePA);
		topRight.add(this.barrePA);
		top.add(labelTexte);
		top.add(topRight);
		this.add(top);
		this.add(barreVie);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		Jeu j = (Jeu) o;
		Joueur jCourant = j.getJoueurCourant();
		String nomJoueur = jCourant.getNom();
		this.updateText(j.getJour(), j.getTour(), nomJoueur);
		this.updateHP(jCourant.getPv());
		this.updatePA(jCourant.getPa());
	}

	private void updateText(int jour, int tour, String nom) {
		this.labelTexte.setText("Jour " + jour + " - Heure " + tour + " - " + nom);
	}
	
	private void updatePA(int pa) {
		this.barrePA.setValue(pa);
		this.barrePA.setString(pa+"/10");
	}
	
	private void updateHP(int hp) {
		this.barreVie.setValue(hp);
		this.barreVie.setString("PV: "+hp+"/100");
	}

}
