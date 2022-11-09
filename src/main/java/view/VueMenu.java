package main.java.view;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;

import main.java.controller.ControleurBoutons;
import main.java.model.Jeu;
import main.java.model.Joueur;
import main.java.model.carte.Ville;

public class VueMenu extends JPanel implements Observer {

	private boolean enVille;
	private List<JButton> boutonsVille;
	private List<JButton> boutonsDehors;
	private Jeu j;
	private Joueur joueur;

	public VueMenu(ControleurBoutons cb) {
		this.setBackground(Color.DARK_GRAY);
		this.boutonsVille = new ArrayList<JButton>();
		this.boutonsDehors = new ArrayList<JButton>();

		// Boutons de la ville
		this.enVille = true;
		JButton ouvrirVille = new JButton("Ouvrir portes de la ville");
		JButton gazette = new JButton("Lire le journal");
		JButton remplirGourde = new JButton("Remplir une gourde");
		JButton manger = new JButton("Manger");
		JButton construire = new JButton("Construire une défense");
		JButton viderSac = new JButton("Vider le sac");
		JButton passerTour = new JButton("Passer le tour du joueur");

		this.boutonsVille.add(ouvrirVille);
		this.boutonsVille.add(gazette);
		this.boutonsVille.add(remplirGourde);
		this.boutonsVille.add(manger);
		this.boutonsVille.add(construire);
		this.boutonsVille.add(viderSac);
		this.boutonsVille.add(passerTour);

		// Boutons en dehors de la vilel
		JButton fouiller = new JButton("Fouiller la zone (1PA)");
		JButton attaquer = new JButton("Tuer un zombie (1PA)");
		JButton communiquer = new JButton("MàJ case (1PA)");
		JButton remplirSac = new JButton("Remplir le sac");

		this.boutonsDehors.add(fouiller);
		this.boutonsDehors.add(attaquer);
		this.boutonsDehors.add(communiquer);
		this.boutonsDehors.add(remplirSac);

		for (JButton b : this.boutonsVille) {
			b.addActionListener(cb);
		}

		this.boutonsDehors.add(remplirGourde);
		this.boutonsDehors.add(passerTour);

		for (JButton b : this.boutonsDehors) {
			if (b.getActionListeners().length < 1) {
				b.addActionListener(cb);
			}
		}

		this.setLayout(new GridLayout(this.boutonsVille.size(), 1));
		for (JButton b : this.boutonsVille) {
			this.add(b);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		this.j = (Jeu) o;
		this.joueur = this.j.getJoueurCourant();
		this.enVille = this.joueur.estEnVille();
		this.updateMenu();
	}

	private void updateMenu() {
		this.removeAll();
		if (enVille) {
			this.setLayout(new GridLayout(this.boutonsVille.size(), 1));
			for (int i = 0; i < this.boutonsVille.size(); i++) {
				JButton b = this.boutonsVille.get(i);
				if (i == 0) {
					if (!Ville.getVille().getPortesOuvertes()) {
						b.setText("Ouvrir portes de la ville");
					} else {
						b.setText("Fermer portes de la ville");
					}
					if(this.joueur.getPa() < 1) {
						b.setEnabled(false);
					}else {
						b.setEnabled(true);
					}
				}
				if (b.getText().equals("Remplir une gourde")) {
					if (this.joueur.getABu() || (!this.joueur.aUneGourde() && !this.joueur.estEnVille())) {
						b.setEnabled(false);
					} else {
						b.setEnabled(true);
					}
				}
				this.add(b);
			}
		} else {
			this.setLayout(new GridLayout(this.boutonsDehors.size(), 1));
			for (JButton b : this.boutonsDehors) {
				switch (b.getText()) {
				case "Remplir une gourde":
					if (this.joueur.getABu() || !this.joueur.estEnVille()) {
						b.setEnabled(false);
					} else {
						b.setEnabled(true);
					}
					break;

				case "Fouiller la zone (1PA)":
					if (this.j.getCarte().getCarte()[this.joueur.getPosX()][this.joueur.getPosY()].estFouillee()
							|| this.joueur.getPa() < 1) {
						b.setEnabled(false);
					} else {
						b.setEnabled(true);
					}
					break;

				case "Tuer un zombie (1PA)":
					if (this.joueur.getPa() < 1) {
						b.setEnabled(false);
					} else {
						b.setEnabled(true);
					}
					break;
				case "MàJ case (1PA)":
					if (this.joueur.getPa() < 1) {
						b.setEnabled(false);
					} else {
						b.setEnabled(true);
					}
					break;
				}
				this.add(b);
			}
		}
		this.validate();
		this.repaint();
	}

}
