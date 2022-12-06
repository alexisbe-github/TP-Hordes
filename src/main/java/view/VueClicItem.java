package main.java.view;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import main.java.model.Jeu;
import main.java.model.carte.Ville;
import main.java.model.objet.BoissonEnergisante;
import main.java.model.objet.Gourde;
import main.java.model.objet.Objet;
import main.java.model.objet.Ration;
import main.java.model.stockage.Entrepot;
import main.java.model.stockage.Inventaire;
import main.java.model.stockage.Sac;

public class VueClicItem extends JPopupMenu {

	public VueClicItem(Objet o, Sac sac, Inventaire li, Jeu j, boolean aClicSurSac) {
		if (aClicSurSac) {
			JMenuItem deposer = new JMenuItem("Déposer");
			deposer.addActionListener(e -> {
				if (o instanceof Gourde) {
					sac.remove(new Gourde(1));
					li.ajouter(new Gourde(1));
				} else {
					if (o instanceof BoissonEnergisante) {
						sac.remove(new BoissonEnergisante(1));
						li.ajouter(new BoissonEnergisante(1));
					} else {
						if (o instanceof Ration) {
							sac.remove(new Ration(1));
							li.ajouter(new Ration(1));
						} else {
							li.ajouter(o);
							sac.remove(o);
						}
					}
				}
				j.updateObservers();
			});
			this.add(deposer);

			if (o.getNom().equals("Gourde") && !j.getJoueurCourant().aBu()) {
				JMenuItem interagir = new JMenuItem("Boire");
				interagir.addActionListener(e -> {
					j.getJoueurCourant().boire();
					j.updateObservers();
				});
				this.add(interagir);
			}

			if (o.getNom().equals("Boisson énergisante")) {
				JMenuItem interagir = new JMenuItem("Boire");
				interagir.addActionListener(e -> {
					if (aClicSurSac) {
						j.getJoueurCourant().boireBoissonEnergisante();
						j.updateObservers();
					}
				});
				this.add(interagir);
			}

			if (o.getNom().equals("Ration") && aClicSurSac) {
				JMenuItem interagir = new JMenuItem("Manger");
				interagir.addActionListener(e -> {
					if (aClicSurSac) {
						j.getJoueurCourant().mangerRation();
						j.updateObservers();
					}
				});
				this.add(interagir);
			}

		} else {
			JMenuItem recuperer = new JMenuItem("Mettre dans le sac");
			recuperer.addActionListener(e -> {
				if (sac.size() < 10) {
					if (o instanceof Ration) {
						Ville.getVille().getEntrepot().retirerEnQte(new Ration(1));
						sac.ajouter(new Ration(1));
					} else {
						if (o instanceof Gourde) {
							Ville.getVille().getEntrepot().retirerEnQte(new Gourde(1));
							sac.ajouter(new Gourde(1));
						} else {
							if (o instanceof BoissonEnergisante) {
								Ville.getVille().getEntrepot().retirerEnQte(new BoissonEnergisante(1));
								sac.ajouter(new BoissonEnergisante(1));
							} else {
								sac.ajouter(o);
								li.remove(o);
							}
						}

					}
					j.updateObservers();
				}
			});
			this.add(recuperer);
		}

	}

}
