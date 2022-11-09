package main.java.model.objet.factory;

import main.java.model.objet.Objet;

public interface FabriqueObjet {

	public Objet creerObjet(int qte);
}
