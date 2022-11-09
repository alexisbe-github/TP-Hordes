package main.java.model.objet.factory;

import main.java.model.objet.BoissonEnergisante;
import main.java.model.objet.Objet;

public class FabriqueObjetBoissonEnergisante implements FabriqueObjet{

	@Override
	public Objet creerObjet(int qte) {
		return new BoissonEnergisante(qte);
	}

}
