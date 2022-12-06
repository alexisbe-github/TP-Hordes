package main.java.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import main.java.model.Jeu;
import main.java.model.Joueur;
import main.java.model.carte.Case;
import main.java.model.carte.Ville;
import main.java.model.objet.Objet;
import main.java.model.stockage.Sac;

public class VueInventaire extends JPanel implements Observer {

	private Joueur joueurCourant;
	private int padding;
	private List<Rectangle> slots;
	private int cote;
	private Jeu j;
	private int hover;

	public VueInventaire() {
		this.padding = 0;
		this.hover = -1;
	}

	public Jeu getJeu() {
		return this.j;
	}

	public Joueur getJoueurCourant() {
		return this.joueurCourant;
	}

	public List<Rectangle> initSlots() {
		this.slots = new ArrayList<Rectangle>();
		for (int i = 0; i < 10; i++) {
			this.slots.add(new Rectangle(this.padding + cote * i + (this.padding * i), 5, cote, cote));
		}
		return this.slots;
	}

	public boolean isOnSlot(Point p) {
		Sac sac = this.joueurCourant.getInventaire();
		int indice = this.getIndiceSlot(p);
		return indice != -1 && indice < sac.size();
	}

	public void setHover(int indice) {
		this.hover = indice;
	}

	public int getIndiceSlot(Point p) {
		for (int i = 0; i < this.slots.size(); i++) {
			if (this.slots.get(i).contains(new Point(p.x, p.y)))
				return i;
		}
		return -1;
	}

	public Objet getClicObjet(Point p) {
		Sac sac = this.joueurCourant.getInventaire();
		return sac.get(this.getIndiceSlot(p));
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(new Color(102, 51, 0));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		Graphics2D g2 = (Graphics2D) g;

		this.padding = this.getWidth() / 30;
		this.cote = this.getWidth() / 20;
		this.initSlots();
		g.setColor(Color.DARK_GRAY);
		for (int i = 0; i < 10; i++) {
			g.fillRect(this.padding + cote * i + (this.padding * i), 5, cote, cote);
		}

		g.setColor(Color.black);
		g.fillRect(this.padding + cote * 10 + (this.padding * 10), 0,
				this.getWidth() - this.padding + cote * 10 + (this.padding * 10), this.getHeight());
		if (this.joueurCourant != null) {
			List<Objet> inventaire = this.joueurCourant.getInventaire();
			int i = 0;
			for (Objet o : inventaire) {

				BufferedImage icon;
				try {
					icon = o.getSpritePath();
					g.drawImage(icon, this.padding + cote * i + (this.padding * i), 5, cote, cote, this);
					g.setColor(Color.white);
					if (o.getQuantite() > 1) {
						g.drawString(String.valueOf(o.getQuantite()),
								this.padding + cote + cote * i + (this.padding * i) - 20, cote);
					}
					if (i == this.hover) {
						g2.setColor(new Color(200, 200, 200, 100));
						g2.fill(new Rectangle2D.Double(this.padding + cote * i + (this.padding * i), 5, cote, cote));
					}
					i++;
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		this.j = (Jeu) o;
		this.joueurCourant = this.j.getJoueurCourant();
		this.repaint();
	}

}
