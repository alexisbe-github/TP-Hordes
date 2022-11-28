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

public class VueClicItem extends JPopupMenu{
	
	public VueClicItem(Objet o,Sac sac, ListeItems li,Jeu j,boolean aClicSurSac) {
		
		if(o instanceof Gourde || o instanceof BoissonEnergisante) {
			JMenuItem interagir = new JMenuItem("Interagir");
			interagir.addActionListener(e->{
				
			});
			this.add(interagir);
		}
		if(aClicSurSac) {
			JMenuItem deposer = new JMenuItem("Déposer");
			deposer.addActionListener(e->{
				
			});
			this.add(deposer);
		}else {
			JMenuItem recuperer = new JMenuItem("Mettre dans le sac");
			recuperer.addActionListener(e->{
				sac.ajouter(o);
				li.retirer(o);
				j.updateObservers();
			});
			this.add(recuperer);
		}
	}
	
}
