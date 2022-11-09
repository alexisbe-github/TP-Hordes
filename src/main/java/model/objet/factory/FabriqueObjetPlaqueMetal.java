package main.java.model.objet.factory;

import main.java.model.objet.Objet;
import main.java.model.objet.PlaqueMetal;

public class FabriqueObjetPlaqueMetal implements FabriqueObjet{

	@Override
	public Objet creerObjet(int qte) {
		return new PlaqueMetal(qte);
	}

}
