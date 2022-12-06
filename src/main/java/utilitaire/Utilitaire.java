package main.java.utilitaire;

import java.util.Random;

public class Utilitaire {

	/**
	 * Permet de g�n�rer un entier entre a et b strictement inf�rieur
	 * 
	 * @param a
	 * @param b
	 * @return entier g�n�r�
	 */
	public static int genererEntier(int a, int b) {
		Random random = new Random();
		int res;
		res = a + random.nextInt(b - a);
		return res;
	}
}
