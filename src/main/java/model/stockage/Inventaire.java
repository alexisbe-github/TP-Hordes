package main.java.model.stockage;

import java.util.LinkedList;
import java.util.List;

import main.java.model.objet.Objet;

public abstract class Inventaire extends LinkedList<Objet> {

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
		for (Objet o : this) {
			if (!this.contains(o)) {
				equals = false;
			}
		}
		return equals;
	}

	@Override
	public String toString() {
		String res = "[";
		for (Objet o : this) {
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
		if (o.stackable() && this.contains(o)) {
			int indexObjet = this.indexOf(o);
			this.get(indexObjet).ajouterQte(o.getQuantite());
		} else {
			this.add(o);
		}
	}

	/**
	 * Suppresion d'un objet en quantité dans l'inventaire, le supprime si la
	 * quantité baisse à 0
	 * 
	 * @param o
	 */
	public void retirer(Objet o) {
		if (o.stackable() && this.contains(o)) {
			int indexObjet = this.indexOf(o);
			int qteActuelle = this.get(indexObjet).getQuantite();
			o.retirerQte(qteActuelle);
			if (o.getQuantite() == 0) {
				this.remove(indexObjet);
			} else {
				this.set(indexObjet, o);
			}
		} else {
			for (int i = 0; i < this.size(); i++) {
				Objet obj = this.get(0);
				if (obj.getNom().equals(o.getNom()))
					this.remove(i);
			}
		}
	}
}
