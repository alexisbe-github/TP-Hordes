package main.java.model.stockage;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import main.java.model.objet.Objet;

public abstract class Inventaire {

	private List<Objet> inventaire;

	public Inventaire() {
		this.inventaire = new LinkedList<Objet>();
	}

	public List<Objet> getInventaire() {
		return this.inventaire;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Inventaire other = (Inventaire) obj;
		boolean equals = true;
		for(Objet o: other.getInventaire()) {
			if(!this.getInventaire().contains(o)) {
				equals = false;
			}
		}
		return equals;
	}

	@Override
	public String toString() {
		String res = "[";
		for(Objet o: this.inventaire) {
			res += o + "\n";
		}
		return res + "]";
	}

	public abstract void ajouter(Objet o);

	/**
	 * Insertion d'un objet en quantité dans l'inventaire, méthode commune aux
	 * classes filles, appelée dans ajouter(Objet o)
	 * 
	 * @param o
	 */
	protected void insertion(Objet o) {
		if (o.stackable()) {
			if (this.inventaire.contains(o)) {
				int indexObjet = this.inventaire.indexOf(o);
				this.inventaire.get(indexObjet).ajouterQte(o.getQuantite());
			} else {
				this.inventaire.add(o);
			}
		} else {
			this.inventaire.add(o);
		}
	}

	/**
	 * Suppresion d'un objet en quantité dans l'inventaire, le supprime si la
	 * quantité baisse à 0
	 * 
	 * @param o
	 */
	public void retirer(Objet o) {
		if (o.stackable()) {
			if (this.inventaire.contains(o)) {
				int indexObjet = this.inventaire.indexOf(o);
				int qteActuelle = this.inventaire.get(indexObjet).getQuantite();
				o.retirerQte(qteActuelle);
				if (o.getQuantite() == 0) {
					this.inventaire.remove(indexObjet);
				} else {
					this.inventaire.set(indexObjet, o);
				}
			}
		} else {
			for (int i = 0; i < this.inventaire.size(); i++) {
				Objet obj = this.inventaire.get(0);
				if (obj.getNom().equals(o.getNom()))
					this.inventaire.remove(i);
			}
		}
	}
}
