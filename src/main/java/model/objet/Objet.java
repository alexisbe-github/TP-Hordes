package main.java.model.objet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;

public abstract class Objet {

	private String nom;
	private int quantite;
	private String path;

	public Objet(String nom, String path) {
		this.init(nom);
		this.path = path;
	}

	@Override
	public String toString() {
		return "Objet [nom=" + nom + ", quantite=" + quantite + "]";
	}

	public Objet(String nom, int quantite, String path) {
		this.init(nom);
		this.quantite = quantite;
		this.path = path;
	}

	private void init(String nom) {
		this.nom = nom;
	}

	public abstract boolean stackable();

	public BufferedImage getSpritePath() throws IOException {
		return ImageIO.read(new File(this.path));
	}

	public String getNom() {
		return this.nom;
	}

	public int getQuantite() {
		return this.quantite;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Objet other = (Objet) obj;
		return Objects.equals(nom, other.nom);
	}

	public void ajouterQte(int qte) {
		this.quantite += qte;
	}

	public void setQte(int qte) {
		this.quantite = qte;
	}

	public void retirerQte(int qte) {
		if (qte > this.quantite) {
			qte = this.quantite;
		}
		this.quantite -= qte;
	}

}
