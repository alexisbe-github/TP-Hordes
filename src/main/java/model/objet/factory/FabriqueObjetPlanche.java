package main.java.model.objet.factory;

import main.java.model.objet.Objet;
import main.java.model.objet.Planche;

public class FabriqueObjetPlanche implements FabriqueObjet {

	@Override
	public Objet creerObjet(int qte) {
		return new Planche(qte);
	}

}
