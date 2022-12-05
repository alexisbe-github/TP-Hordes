package main.java.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import main.java.model.Jeu;
import main.java.model.Joueur;
import main.java.model.carte.Case;
import main.java.model.carte.Ville;
import main.java.model.construction.Construction;
import main.java.model.objet.Objet;

public class VueJeu extends JPanel implements Observer {

	private int padding;
	private Case[][] map;
	private Joueur joueurCourant;
	private Jeu j;
	private Polygon top, left, right, bot, topEntrepot, botEntrepot;

	private Point vueCourante;
	private List<Rectangle> slots;
	private int cote;
	private int compteurEntrepot;

	public VueJeu() {
		this.vueCourante = new Point(12, 12);
		this.setLayout(null);
		this.compteurEntrepot = 0;
	}

	public void incrementCompteurEntrepot() {
		this.compteurEntrepot++;
		this.repaint();
	}

	public void decrementCompteurEntrepot() {
		if (this.compteurEntrepot > 0) {
			this.compteurEntrepot--;
			this.repaint();
		}
	}

	public Polygon getTopEntrepot() {
		return topEntrepot;
	}

	public Polygon getBotEntrepot() {
		return botEntrepot;
	}

	public List<Rectangle> initSlots() {
		this.slots = new ArrayList<Rectangle>();
		for (int i = 0; i < 7; i++) {
			this.slots.add(new Rectangle(this.getWidth() - this.padding - 5,
					this.padding * i + this.padding + 20 * i + 2, this.padding, this.padding));
		}
		return this.slots;
	}

	public boolean clicOnSlot(Point p) {
		List<Objet> loot = this.j.getCarte().getCarte()[this.joueurCourant.getPosX()][this.joueurCourant.getPosY()]
				.getLoot();
		int indice = this.getIndiceClicSlot(p);
		return indice != -1 && indice < loot.size() && this.joueurCourant.getPosX() == this.vueCourante.x
				&& this.joueurCourant.getPosY() == this.vueCourante.y;
	}

	private int getIndiceClicSlot(Point p) {
		for (int i = 0; i < this.slots.size(); i++) {
			if (this.slots.get(i).contains(new Point(p.x, p.y))) {
				if (this.vueCourante.x == 12 && this.vueCourante.y == 12) {
					return i + this.compteurEntrepot;
				} else {
					return i;
				}
			}
		}
		return -1;
	}

	public Case getCaseCourante() {
		return this.j.getCarte().getCarte()[this.joueurCourant.getPosX()][this.joueurCourant.getPosY()];
	}

	public Objet getClicObjet(Point p) {
		List<Objet> loot = this.j.getCarte().getCarte()[this.joueurCourant.getPosX()][this.joueurCourant.getPosY()]
				.getLoot();
		return loot.get(this.getIndiceClicSlot(p));
	}

	public Joueur getJoueurCourant() {
		return joueurCourant;
	}

	public void setVueCourant(int x, int y) {
		this.vueCourante.x = x;
		this.vueCourante.y = y;
		this.repaint();
	}

	public int getCote() {
		return this.cote;
	}

	public int getPadding() {
		return this.padding;
	}

	public Jeu getJeu() {
		return this.j;
	}

	public Polygon getTop() {
		return top;
	}

	public Polygon getLeft() {
		return left;
	}

	public Polygon getRight() {
		return right;
	}

	public Polygon getBot() {
		return bot;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (this.map != null) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(Color.gray);
			g2.fillRect(0, 0, this.getWidth(), this.getHeight());
			this.padding = this.getHeight() / 10;
			this.initSlots();
			g2.setColor(new Color(85, 107, 47));
			cote = this.getHeight() - 2 * this.padding;
			g2.fillRect(this.padding, this.padding, cote, cote);
			double longueurCase = (double) cote / map.length;
			double largeurCase = (double) cote / map[0].length;

			// Dessin des cases explorées
			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[0].length; j++) {
					if (this.map[i][j].estFouillee()) {
						g2.setColor(Color.green);
						g2.fill(new Rectangle2D.Double(this.padding + i * longueurCase, this.padding + j * largeurCase,
								longueurCase, largeurCase));
					}
				}
			}

			// Indication case courante
			if (this.joueurCourant != null) {
				int x = this.joueurCourant.getPosX();
				int y = this.joueurCourant.getPosY();
				g2.setColor(Color.yellow);
				g2.fill(new Rectangle2D.Double(this.padding + x * longueurCase, this.padding + y * largeurCase,
						longueurCase, largeurCase));
			}

			// Dessin de la map
			g2.setColor(Color.black);
			for (int i = 0; i <= map.length; i++) {
				g2.draw(new Line2D.Double(this.padding + i * longueurCase, this.padding,
						this.padding + i * longueurCase, this.padding + cote));
			}
			for (int i = 0; i <= map.length; i++) {
				g2.draw(new Line2D.Double(this.padding, this.padding + i * largeurCase, this.padding + cote,
						this.padding + i * largeurCase));
			}

			// Dessin des flèches
			g2.setColor(Color.YELLOW);
			this.top = new Polygon(new int[] { this.padding, this.padding + cote / 2, this.padding + cote },
					new int[] { this.padding / 4 * 3, this.padding / 4, this.padding / 4 * 3 }, 3);
			this.left = new Polygon(new int[] { this.padding / 4 * 3, this.padding / 4, this.padding / 4 * 3 },
					new int[] { this.padding, this.padding + cote / 2, this.padding + cote }, 3);
			this.bot = new Polygon(new int[] { this.padding, this.padding + cote / 2, this.padding + cote },
					new int[] { this.padding / 4 + cote + this.padding, this.padding / 4 * 3 + cote + this.padding,
							this.padding / 4 + cote + this.padding },
					3);
			this.right = new Polygon(
					new int[] { this.padding + this.padding / 4 + cote, this.padding + this.padding / 4 * 3 + cote,
							this.padding + this.padding / 4 + cote },
					new int[] { this.padding, this.padding + cote / 2, this.padding + cote }, 3);
			this.topEntrepot = new Polygon(
					new int[] { this.getWidth() - this.padding - 5,
							this.getWidth() - this.padding - 5 + this.padding / 2, this.getWidth() - 5 },
					new int[] { this.padding - 2, this.padding - 20, this.padding - 2 }, 3);
			this.botEntrepot = new Polygon(
					new int[] { this.getWidth() - this.padding - 5,
							this.getWidth() - this.padding - 5 + this.padding / 2, this.getWidth() - 5 },
					new int[] { this.padding + cote + 2, this.padding + cote + 20, this.padding + cote + 2 }, 3);
			if (this.getJeu().getCaseDuJoueur(this.joueurCourant).getNbZombie() == 0) {
				g2.fillPolygon(top);
				g2.fillPolygon(left);
				g2.fillPolygon(bot);
				g2.fillPolygon(right);
			}

			// Dessin de la case séléctionnée
			BufferedImage icon, zombie;
			// Ville
			if (this.vueCourante.x == 12 && this.vueCourante.y == 12) {
				try {
					icon = ImageIO.read(new File(this.map[vueCourante.x][vueCourante.y].getPath()));
					g.drawImage(icon, this.padding * 2 + cote, this.padding, cote, cote, this);
					g2.setColor(new Color(102, 51, 0));
					g2.fill(new Rectangle2D.Double(this.getWidth() - this.padding - 5, this.padding, this.padding,
							cote));
					g2.setColor(Color.green);

					g2.fillPolygon(this.topEntrepot);
					g2.fillPolygon(this.botEntrepot);

					List<Objet> loot = Ville.getVille().getLoot();
					for (int i = this.compteurEntrepot; i < loot.size(); i++) {
						Objet o = loot.get(i);
						if (i < loot.size()) {
							try {
								icon = o.getSpritePath();
								g.drawImage(icon, this.getWidth() - this.padding - 5,
										this.padding * (i - this.compteurEntrepot) + this.padding
												+ 5 * (i - this.compteurEntrepot) + 2,
										this.padding - 5, this.padding - 5, this);
								g.setColor(Color.white);
								if (o.getQuantite() > 1) {
									g.drawString(String.valueOf(o.getQuantite()), this.getWidth() - this.padding - 5,
											this.padding * (i - this.compteurEntrepot) + this.padding
													+ 5 * (i - this.compteurEntrepot) + 10);
								}
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				try {
					icon = ImageIO.read(new File(this.map[vueCourante.x][vueCourante.y].getPath()));
					zombie = ImageIO.read(new File("src/main/resources/zombie.png"));
					g.drawImage(icon, this.padding * 2 + cote, this.padding, cote, cote, this);
					for (int i = 0; i < this.map[vueCourante.x][vueCourante.y].getNbZombie(); i++) {
						g.drawImage(zombie, this.padding * 2 + cote + 20 + i * cote / 10,
								this.padding + cote - cote / 4, cote / 10, cote / 10, this);
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				// Dessin du loot sur la case
				int i = 0;
				if (this.map[vueCourante.x][vueCourante.y].estFouillee()) {
					g2.setColor(new Color(102, 51, 0));
					g2.fill(new Rectangle2D.Double(this.getWidth() - this.padding - 5, this.padding, this.padding,
							cote));
					List<Objet> loot = this.j.getCarte().getCarte()[vueCourante.x][vueCourante.y].getLoot();
					for (Objet o : loot) {

						try {
							icon = o.getSpritePath();
							g.drawImage(icon, this.getWidth() - this.padding - 5,
									this.padding * i + this.padding + 5 * i + 2, this.padding - 5, this.padding - 5,
									this);
							g.setColor(Color.white);
							if (o.getQuantite() > 1) {
								g.drawString(String.valueOf(o.getQuantite()), this.getWidth() - this.padding - 5,
										this.padding * i + this.padding + 5 * i + 10);
							}
							i++;
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
			// Dessin de la ville
			g2.setColor(Color.red);
			if (Ville.getVille().getPortesOuvertes()) {
				// Mur du haut
				g2.fill(new Rectangle2D.Double(this.padding + 12 * longueurCase + 1, this.padding + 12 * largeurCase,
						longueurCase / 3, largeurCase / 10));
				g2.fill(new Rectangle2D.Double(this.padding + 12 * longueurCase + ((longueurCase / 3) * 2),
						this.padding + 12 * largeurCase, longueurCase / 3, largeurCase / 10));

				// Mur du bas
				g2.fill(new Rectangle2D.Double(this.padding + 12 * longueurCase + 1,
						this.padding + 12 * largeurCase + largeurCase - 1, longueurCase / 3, largeurCase / 10));
				g2.fill(new Rectangle2D.Double(this.padding + 12 * longueurCase + ((longueurCase / 3) * 2),
						this.padding + 12 * largeurCase + largeurCase - 1, longueurCase / 3, largeurCase / 10));

				// Mur de gauche
				g2.fill(new Rectangle2D.Double(this.padding + 12 * longueurCase + 1, this.padding + 12 * largeurCase, 2,
						largeurCase / 3));
				g2.fill(new Rectangle2D.Double(this.padding + 12 * longueurCase + 1,
						this.padding + 12 * largeurCase + (largeurCase / 3) * 2, 2, largeurCase / 3));

				// Mur de droite
				g2.fill(new Rectangle2D.Double(this.padding + 13 * longueurCase - 2,
						this.padding + 12 * largeurCase + 1, 2, largeurCase / 3));
				g2.fill(new Rectangle2D.Double(this.padding + 13 * longueurCase - 2,
						this.padding + 12 * largeurCase + (largeurCase / 3) * 2, 2, largeurCase / 3));
			} else {
				g2.draw(new Rectangle2D.Double(this.padding + 12 * longueurCase, this.padding + 12 * largeurCase,
						longueurCase, largeurCase));
			}
		}

		// Dessin des constructions de la ville
		for (Construction c : Ville.getVille().getConstructions()) {
			if (c.constructionFinie() && this.vueCourante.x == 12 && this.vueCourante.y == 12) {
				BufferedImage icon;
				try {
					icon = ImageIO.read(new File(c.getPath()));
					switch (c.getNom()) {
					case "Abris anti-atomique":
						g.drawImage(icon, this.padding * 2 + cote + (cote * 28 / 100), this.padding + (cote * 66 / 100),
								cote * 10 / 100, cote * 5 / 100, this);
						break;
					case "Fils barbelés":
						g.drawImage(icon, this.padding * 2 + cote + (cote * 4 / 100), this.padding + (cote * 2 / 100),
								cote * 94 / 100, cote * 92 / 100, this);
						break;
					case "Fosses à zombies":
						g.drawImage(icon, this.padding * 2 + cote + (cote * 6 / 100), this.padding + (cote * 4 / 100),
								cote * 89 / 100, cote * 88 / 100, this);
						break;
					case "Mines autour de la ville":
						g.drawImage(icon, this.padding * 2 + cote + (cote * 1 / 100), this.padding + (cote * 1 / 100),
								cote * 4 / 100, cote * 4 / 100, this);
						g.drawImage(icon, this.padding * 2 + cote + (cote * 1 / 100), this.padding + (cote * 20 / 100),
								cote * 4 / 100, cote * 4 / 100, this);
						g.drawImage(icon, this.padding * 2 + cote + (cote * 1 / 100), this.padding + (cote * 40 / 100),
								cote * 4 / 100, cote * 4 / 100, this);
						g.drawImage(icon, this.padding * 2 + cote + (cote * 1 / 100), this.padding + (cote * 60 / 100),
								cote * 4 / 100, cote * 4 / 100, this);
						g.drawImage(icon, this.padding * 2 + cote + (cote * 1 / 100), this.padding + (cote * 80 / 100),
								cote * 4 / 100, cote * 4 / 100, this);
						g.drawImage(icon, this.padding * 2 + cote + (cote * 1 / 100), this.padding + (cote * 95 / 100),
								cote * 4 / 100, cote * 4 / 100, this);
						break;
					case "Miradors avec mitrailleuses automatiques":
						g.drawImage(icon, this.padding * 2 + cote + (cote * 7 / 100), this.padding + (cote * 92 / 100),
								cote * 8 / 100, cote * 8 / 100, this);
						g.drawImage(icon, this.padding * 2 + cote + (cote * 27 / 100), this.padding + (cote * 92 / 100),
								cote * 8 / 100, cote * 8 / 100, this);
						g.drawImage(icon, this.padding * 2 + cote + (cote * 57 / 100), this.padding + (cote * 92 / 100),
								cote * 8 / 100, cote * 8 / 100, this);
						g.drawImage(icon, this.padding * 2 + cote + (cote * 87 / 100), this.padding + (cote * 92 / 100),
								cote * 8 / 100, cote * 8 / 100, this);
						break;
					case "Mur d'enceinte":
						g.drawImage(icon, this.padding * 2 + cote + (cote * 11 / 100), this.padding + (cote * 9 / 100),
								cote * 79 / 100, cote * 78 / 100, this);
						break;
					case "Portes blindées":
						g.drawImage(icon, this.padding * 2 + cote + (cote * 11 / 100), this.padding + (cote * 9 / 100),
								cote * 79 / 100, cote * 78 / 100, this);
						break;
					}

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
		this.map = this.joueurCourant.getCarteVersionJoueur();
		this.vueCourante.x = this.joueurCourant.getPosX();
		this.vueCourante.y = this.joueurCourant.getPosY();
		this.repaint();
	}

}
