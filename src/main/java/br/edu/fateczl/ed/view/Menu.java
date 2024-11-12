package br.edu.fateczl.ed.view;

import javax.swing.*;

public class Menu {
    private JButton CURSOSButton;
    private JPanel Painel;
    private JButton DISCIPLINASButton;
    private JButton PROFESSORESButton;
    private JButton INSCRIÇÕESButton;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Painel - Menu");

        Menu menu = new Menu();

        frame.setContentPane(menu.Painel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}
