package main.java.utilitaire;

import java.util.Random;

public class Utilitaire {

	/**
	 * Permet de générer un entier entre a et b strictement inférieur
	 * 
	 * @param a
	 * @param b
	 * @return entier généré
	 */
	public static int genererEntier(int a, int b) {
		Random random = new Random();
		int res;
		res = a + random.nextInt(b - a);
		return res;
	}
}
