package main.java.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import main.java.model.Jeu;
import main.java.model.Joueur;
import main.java.model.objet.Gourde;
import main.java.view.VueJournal;

public class ControleurBoutons implements ActionListener {

	private Jeu jeu;

	public ControleurBoutons(Jeu jeu) {
		this.jeu = jeu;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton bouton = (JButton) e.getSource();
		String text = bouton.getText();
		Joueur j = this.jeu.getJoueurCourant();
		switch (text) {
		case "Boire au puits":
			j.boireAuPuitDeLaVille();
			this.jeu.updateObservers();
			break;
		case "Remplir une gourde":
			j.getInventaire().ajouter(new Gourde(1));
			this.jeu.updateObservers();
			break;
		case "Construire une défense":

			break;
		case "Passer le tour du joueur":
			this.jeu.prochainJoueur();
			break;
		case "Ouvrir portes de la ville":
			j.ouvrirPortesVille();
			this.jeu.updateObservers();
			break;
		case "Fermer portes de la ville":
			j.fermerPortesVille();
			this.jeu.updateObservers();
			break;
		case "Fouiller la zone (1PA)":
			this.jeu.fouiller(this.jeu.getCaseDuJoueur(j),j);
			break;
		case "MàJ case (1PA)":
			this.jeu.majCarte(j,this.jeu.getCaseDuJoueur(j), j.getPosX(), j.getPosY());
			break;
		case "Tuer un zombie (1PA)":
			this.jeu.tuerZombie(this.jeu.getCaseDuJoueur(j));
			break;
		case "Lire le journal":
			new VueJournal(this.jeu.getJournal());
			break;
		}
	}

}
