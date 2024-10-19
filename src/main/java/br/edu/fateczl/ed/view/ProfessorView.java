package br.edu.fateczl.ed.view;

import javax.swing.*;
import java.awt.*;

public class ProfessorView {
    private JTabbedPane PainelProfessor;
    public JPanel Professor;
    private JTextField InputConsultaProf;
    private JButton ButtonConsultaProf;
    private JTextField CPFTextField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField códigoTextField;
    private JButton goBackButton;  // Go Back button

    public ProfessorView() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        Professor.setLayout(new BorderLayout());
        Professor.add(PainelProfessor, BorderLayout.CENTER);
        Professor.add(buttonPanel, BorderLayout.SOUTH);
    }
}