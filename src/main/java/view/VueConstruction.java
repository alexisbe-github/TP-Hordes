package main.java.view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.Collections;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import main.java.model.Jeu;
import main.java.model.carte.Ville;
import main.java.model.construction.Construction;
import main.java.model.objet.Planche;
import main.java.model.objet.PlaqueMetal;
import main.java.model.stockage.Entrepot;

public class VueConstruction extends JFrame {

	public VueConstruction(Jeu j) {
		this.init(j);
	}

	private void init(Jeu j) {
		this.setTitle("Menu des constructions");
		Ville v = Ville.getVille();
		Set<Construction> constructions = v.getConstructions();
		Dimension tailleEcran = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(100, 100, tailleEcran.width / 3 * 2, tailleEcran.height / 2);
		this.setLayout(new GridLayout(constructions.size(), 2));
		for (Construction c : constructions) {
			String contenu = "<html>";
			contenu += c.getNom() + "<br>";
			contenu += " - Besoin: " + c.getNbPlanches() + "planches et " + c.getNbPlaques() + "plaques de métal et "
					+ c.getNbPa() + "PA<br>";
			if (c.estEnConstruction()) {
				contenu += "- Est en construction et a encore besoin de " + (c.getNbPa() - c.getAvancement())
						+ "PA pour finir.";
			} else {
				contenu += "- Actuellement dans l'entrepôt: " + v.getNbPlanches() + "planche(s) et " + v.getNbPlaques()
						+ "plaque(s) de métal";
			}
			JLabel label = new JLabel(contenu);
			this.add(label);

			String contenuBouton = "";
			if (c.estEnConstruction() && c.getAvancement() == c.getNbPa()) {
				contenuBouton = "Construction finie";
			}
			if (c.estEnConstruction() && c.getAvancement() < c.getNbPa()) {
				contenuBouton = "Participer à la construction en donnant vos PA actuels ("
						+ j.getJoueurCourant().getPa() + ")";
			}
			if (!c.estEnConstruction()) {
				contenuBouton = "Commencer construction de " + c.getNom();
			}
			JButton button = new JButton(contenuBouton);
			if ((c.estEnConstruction() && c.getAvancement() == c.getNbPa()) || (!c.estEnConstruction()
					&& c.getNbPlanches() > v.getNbPlanches() && c.getNbPlaques() > v.getNbPlaques())
					|| j.getJoueurCourant().getPa() == 0) {
				button.setEnabled(false);
			}
			button.addActionListener(e -> {
				if (!c.estEnConstruction()) {
					c.commencerConstruction();
					Entrepot ent = (Entrepot) v.getLoot();
					ent.retirerEnQte(new Planche(c.getNbPlanches()));
					ent.retirerEnQte(new PlaqueMetal(c.getNbPlaques()));
					j.updateObservers();
					this.getContentPane().removeAll();
					this.init(j);
				} else {
					c.ajouterPa(j.getJoueurCourant().getPa());
					j.getJoueurCourant().ajouterPa(-j.getJoueurCourant().getPa());
					this.getContentPane().removeAll();
					this.init(j);
				}
			});
			this.add(button);
		}
		this.setVisible(true);
		this.repaint();
	}

}
