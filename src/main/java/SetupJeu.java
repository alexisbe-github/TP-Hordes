package main.java;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import main.java.controller.ControleurBoutons;
import main.java.controller.ControleurClic;
import main.java.controller.ControleurInventaire;
import main.java.controller.ControleurMouvementSouris;
import main.java.controller.ControleurMouvementSourisInventaire;
import main.java.model.Jeu;
import main.java.model.Joueur;
import main.java.view.VueInfoJeu;
import main.java.view.VueInventaire;
import main.java.view.VueJeu;
import main.java.view.VueMenu;

public class SetupJeu {

	public SetupJeu(List<Joueur> joueurs) {
		this.init(joueurs);
	}

	/**
	 * Initialisation du MVC
	 */
	private void init(List<Joueur> joueurs) {
		//Creation modeles
		Jeu j = new Jeu(joueurs);
		
		// Gestion de la fenetre
		JFrame fenetre = new JFrame("Hordes de Twinoid");
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension tailleEcran = Toolkit.getDefaultToolkit().getScreenSize();
		fenetre.setBounds(100, 100, tailleEcran.width - tailleEcran.width / 4,
				tailleEcran.height - tailleEcran.height / 4);


		// Initialisation des panel
		VueInfoJeu panelTop = new VueInfoJeu();
		JPanel panelCenter = new JPanel();
		VueInventaire panelBot = new VueInventaire();
		VueMenu panelCenterRight = new VueMenu(new ControleurBoutons(j));
		VueJeu panelCenterLeft = new VueJeu();
		panelCenter.setLayout(new BorderLayout());
		panelTop.setPreferredSize(new Dimension(fenetre.getWidth(),fenetre.getHeight()/10));
		panelCenter.setPreferredSize(new Dimension(fenetre.getWidth(),fenetre.getHeight()/10*8));
		panelBot.setPreferredSize(new Dimension(fenetre.getWidth(),fenetre.getHeight()/10));


		// Ajout des panel centraux
		panelCenter.add(panelCenterLeft,BorderLayout.CENTER);
		panelCenter.add(panelCenterRight,BorderLayout.EAST);

		// Ajout sur la fenetre
		fenetre.add(panelTop, BorderLayout.NORTH);
		fenetre.add(panelCenter, BorderLayout.CENTER);
		fenetre.add(panelBot, BorderLayout.SOUTH);
		
		//Création des controleurs
		ControleurClic cc = new ControleurClic(panelCenterLeft);
		ControleurInventaire ci = new ControleurInventaire(panelBot);
		ControleurMouvementSouris cms = new ControleurMouvementSouris(panelCenterLeft);
		ControleurMouvementSourisInventaire cmsi = new ControleurMouvementSourisInventaire(panelBot);
		
		//Ajout des listeners aux vues
		panelCenterLeft.addMouseListener(cc);
		panelCenterLeft.addMouseMotionListener(cms);
		panelBot.addMouseListener(ci);
		panelBot.addMouseMotionListener(cmsi);
		
		//Ajout des observers
		j.addObserver(panelTop);
		j.addObserver(panelCenterLeft);
		j.addObserver(panelBot);
		j.addObserver(panelCenterRight);
		
		j.commencerJeu();
		fenetre.setResizable(false);
		fenetre.setVisible(true);
	}
}
