package main.java.view;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import main.java.model.Journal;

public class VueJournal extends JFrame {

	public VueJournal(Journal journal) {
		this.setTitle("Journal du jour " + (journal.getJourCourant() - 1));
		Dimension tailleEcran = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(100, 100, tailleEcran.width / 4, tailleEcran.height / 2);
		JTextArea textArea = new JTextArea();
		for (String s : journal.getJournal()) {
			textArea.setText(textArea.getText() + "\n" + s);
		}
		textArea.setEditable(false);
		this.add(textArea);
		this.setVisible(true);
	}
}
