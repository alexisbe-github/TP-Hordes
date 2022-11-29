package main.java.view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import main.java.model.Jeu;
import main.java.model.objet.BoissonEnergisante;
import main.java.model.objet.Gourde;
import main.java.model.objet.Objet;
import main.java.model.stockage.ListeItems;
import main.java.model.stockage.Sac;

public class VueClicItem extends JPopupMenu {

	public VueClicItem(Objet o, Sac sac, ListeItems li, Jeu j, boolean aClicSurSac) {
		if (aClicSurSac) {
			JMenuItem deposer = new JMenuItem("Déposer");
			deposer.addActionListener(e -> {
				sac.remove(o);
				li.ajouter(o);
				j.updateObservers();
			});
			this.add(deposer);
		} else {
			JMenuItem recuperer = new JMenuItem("Mettre dans le sac");
			recuperer.addActionListener(e -> {
				if (sac.size() < 10) {
					sac.ajouter(o);
					li.remove(o);
					j.updateObservers();
				}
			});
			this.add(recuperer);
		}
		if (o.getNom().equals("Gourde") && !j.getJoueurCourant().aBu()) {
			JMenuItem interagir = new JMenuItem("Boire");
			interagir.addActionListener(e -> {
				if (aClicSurSac) {
					j.getJoueurCourant().boire();
					j.updateObservers();
				}
			});
			this.add(interagir);
		}
		if(o.getNom().equals("Boisson énergisante")) {
			JMenuItem interagir = new JMenuItem("Boire");
			interagir.addActionListener(e -> {
				if (aClicSurSac) {
					j.getJoueurCourant().boireBoissonEnergisante();
					j.updateObservers();
				}
			});
			this.add(interagir);
		}
		if(o.getNom().equals("Ration")) {
			JMenuItem interagir = new JMenuItem("Manger");
			interagir.addActionListener(e -> {
				if (aClicSurSac) {
					j.getJoueurCourant().boireBoissonEnergisante();
					j.updateObservers();
				}
			});
			this.add(interagir);
		}
	}

}
