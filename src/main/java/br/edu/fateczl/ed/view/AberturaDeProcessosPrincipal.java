package br.edu.fateczl.ed.view;

import com.formdev.flatlaf.FlatDarculaLaf;
import javax.swing.*;

public class AberturaDeProcessosPrincipal {

	public static void main(String[] args){
		try {
			UIManager.setLookAndFeel(new FlatDarculaLaf());
		} catch (Exception e) {
			System.err.println("Ocorreu um erro ao tentar carregar o tema Darcula.");
		}

		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("Sistema de Contratação");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			Menu menu = new Menu(frame);
			frame.setContentPane(menu.getMainPanel());
			frame.setSize(650, 400);
			frame.setResizable(false);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});
	}
}
