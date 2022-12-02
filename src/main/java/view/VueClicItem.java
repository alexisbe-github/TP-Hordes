package main.java.view;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import main.java.model.Jeu;
import main.java.model.objet.Objet;
import main.java.model.objet.Ration;
import main.java.model.stockage.Inventaire;
import main.java.model.stockage.Sac;

public class VueClicItem extends JPopupMenu {

	public VueClicItem(Objet o, Sac sac, Inventaire li, Jeu j, boolean aClicSurSac) {
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
					if (!(o instanceof Ration)) {
						sac.ajouter(o);
						li.remove(o);
					} else {
						sac.ajouter(new Ration(1));
						o.retirerQte(1);
					}
					j.updateObservers();
				}
			});
			this.add(recuperer);
		}
		if (o.getNom().equals("Gourde") && !j.getJoueurCourant().aBu() && aClicSurSac) {
			JMenuItem interagir = new JMenuItem("Boire");
			interagir.addActionListener(e -> {
				if (aClicSurSac) {
					j.getJoueurCourant().boire();
					j.updateObservers();
				}
			});
			this.add(interagir);
		}
		if (o.getNom().equals("Boisson énergisante")&& aClicSurSac) {
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
	}

}
