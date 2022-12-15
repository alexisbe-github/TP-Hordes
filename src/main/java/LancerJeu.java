package main.java;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.java.model.Joueur;

/**
 * 
 * @author Alexis BEER, Baptiste BILLARD, Emile ALEXANDRE
 *
 */
public class LancerJeu {

	private static JFrame fenetre;
	private static List<Joueur> joueurs;

	public static void main(String[] args) {
		joueurs = new ArrayList<Joueur>();
		// Fenetre de demande du nombre de joueurs
		fenetre = new JFrame("Lancement de Hordes");
		Dimension tailleEcran = Toolkit.getDefaultToolkit().getScreenSize();
		fenetre.setBounds(100, 100, tailleEcran.width / 4, tailleEcran.height / 8);

		// Elements de la fenetre
		JLabel prompt = new JLabel("Rentrez le nombre de joueurs qui vont jouer:");
		JTextField jtf = new JTextField();
		JPanel pane = new JPanel();
		JButton confirmer = new JButton("OK");
		jtf.setColumns(2);

		// Controller
		jtf.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
					e.consume();
				}
			}
		});

		// Listener sur la confirmation du nombre de joueur
		confirmer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int nbJoueurs = Integer.parseInt(jtf.getText());
					if (nbJoueurs < 2 || nbJoueurs > 20) {
						JOptionPane.showMessageDialog(new JFrame(),
								"Veuillez rentrez un nombre de joueur entre 2 et 20", "Erreur",
								JOptionPane.ERROR_MESSAGE);
					} else {
						pane.removeAll();
						pane.revalidate();
						pane.repaint();
						inputPlayers(nbJoueurs, 1);
					}
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(new JFrame(),
							"Veuillez rentrez un nombre de joueur valide! ' " + jtf.getText() + " ' n'est pas valide!",
							"Erreur", JOptionPane.ERROR_MESSAGE);
				}
			}

		});

		// Ajout des éléments
		fenetre.setContentPane(pane);
		fenetre.setLayout(new FlowLayout());
		fenetre.add(prompt);
		fenetre.add(jtf);
		fenetre.add(confirmer);

		// Ajustements sur la fenetre
		fenetre.pack();
		fenetre.setResizable(false);
		fenetre.setVisible(true);
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Appel récursif de l'entrée utilisateur des noms des joueurs
	 * 
	 * @param nbJoueurs
	 * @param compteur
	 */
	private static void inputPlayers(int nbJoueurs, int compteur) {
		JLabel labelInput = new JLabel("Rentrez le nom du joueur " + compteur);
		JTextField input = new JTextField();
		input.setColumns(7);
		JButton confirmer = new JButton("OK");
		confirmer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				joueurs.add(new Joueur(input.getText()));
				if (compteur == nbJoueurs) {
					fenetre.setVisible(false);
					new SetupJeu(joueurs);
					fenetre = null;
				} else {
					fenetre.getContentPane().removeAll();
					fenetre.getContentPane().revalidate();
					fenetre.getContentPane().repaint();
					inputPlayers(nbJoueurs, compteur + 1);
				}
			}

		});
		fenetre.add(labelInput);
		fenetre.add(input);
		fenetre.add(confirmer);
	}
}
