package main.java.view;

import java.awt.Polygon;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import main.java.model.Jeu;
import main.java.model.objet.Objet;
import main.java.model.stockage.Inventaire;
import main.java.model.stockage.Sac;

public class VueClicItem extends JPopupMenu implements Observer{

	private Inventaire inventaire;
	private Objet objetCourant;
	
	public VueClicItem(Inventaire inv) {
		this.inventaire=inv;
		if(inventaire instanceof Sac) {
			JMenuItem interagir = new JMenuItem("Interagir");
			this.add(interagir);
		}
		JMenuItem deposer = new JMenuItem("Déposer");
		this.add(deposer);
	}

	@Override
	public void update(Observable o, Object arg) {
		Jeu j = (Jeu) o;
		
	}
	
}
