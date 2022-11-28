package main.java.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import main.java.model.Jeu;
import main.java.model.Joueur;
import main.java.model.objet.Objet;

public class VueInventaire extends JPanel implements Observer {

	private Joueur joueurCourant;
	private int padding;

	public VueInventaire() {
		this.padding = 0;
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(new Color(102, 51, 0));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		this.padding = this.getWidth() / 30;
		int cote = this.getWidth() / 20;
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
					i++;
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		Jeu j = (Jeu) o;
		this.joueurCourant = j.getJoueurCourant();
		this.repaint();
	}

}
