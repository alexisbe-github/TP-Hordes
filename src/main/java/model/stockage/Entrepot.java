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
			this.get(indexObjet).ajouterQte(o.getQuantite());
		} else {
			this.add(o);
		}
	}

}