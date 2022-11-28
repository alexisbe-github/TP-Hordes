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
		if (o instanceof Gourde || o instanceof BoissonEnergisante) {
			JMenuItem interagir = new JMenuItem("Boire");
			interagir.addActionListener(e -> {
				if (aClicSurSac) {
					sac.remove(o);
					j.getJoueurCourant().ajouterPa(6);
				} else {
					li.remove(o);
				}
				j.updateObservers();
			});
			this.add(interagir);
		}
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
	}

}
