package main.java.view;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import main.java.model.Journal;

public class VueJournal extends JFrame{

	public VueJournal(Journal journal) {
		Dimension tailleEcran = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(100,100,tailleEcran.width/6, tailleEcran.height/4);
		JTextArea textArea = new JTextArea();
		for(String s:journal) {
			textArea.setText(textArea.getText()+"\n"+s);
		}
		textArea.setEditable(false);
		this.add(textArea);
		this.setVisible(true);
	}
}
