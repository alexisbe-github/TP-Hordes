package main.java.model.stockage;

import main.java.model.objet.Objet;

public class ListeItems extends Inventaire {

	public ListeItems() {
		super();
	}

	@Override
	public void ajouter(Objet o) {
		this.insertion(o);
	}

}
