package main.java.model.stockage;

import main.java.model.objet.Objet;

public class Entrepot extends Inventaire{

	public Entrepot() {
		super();
	}
	
	@Override
	public void ajouter(Objet o) {
		if (this.contains(o)) {
			int indexObjet = this.indexOf(o);
			this.get(indexObjet).ajouterQte(o.getQuantite()); //ajout en quantité si l'objet existe déjà
		} else {
			this.add(o); //ajout de l'objet si il n'existe pas
		}
	}
	
	public void retirerEnQte(Objet o) {
		if(this.contains(o)) {
			int indexObjet = this.indexOf(o);
			this.get(indexObjet).retirerQte(o.getQuantite()); //On retire en quantité l'objet dans la liste
			if(this.get(indexObjet).getQuantite() == 0) {
				this.remove(o); //si la quantité tombe à 0 on le supprime de la liste
			}
		}
	}

}
