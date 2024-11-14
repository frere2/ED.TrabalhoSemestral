package br.edu.fateczl.ed.view;

import br.edu.fateczl.ed.Utils.Utilities;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProfessorView {
    private JTabbedPane PainelProfessor;
    public JPanel Professor;
    private JTextField InputConsultaProf;
    private JButton ButtonConsultaProf;
    private JTextField NomeProf;
    private JTextField AreaProf;
    private JTextField PontuacaoProf;
    private JTextField CPFProf;
    private JButton voltarButton;
    private JLabel TimeLabel;
    private JButton CadastroVoltarButton;
    private JButton ExcluirButton;
    private JTable table1;
    private JButton SalvarButton;

    public static String SelectedCPF = null;

    public ProfessorView(JFrame frame) {
        addActionListeners(frame);

        table1.setEnabled(true);
        table1.setColumnSelectionAllowed(false);
        table1.setRowSelectionAllowed(false);
        table1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                    {"123.456.789-00", "Professor Teste", "Computação", "10.0"},
                    {"987.654.321-00", "Professor Teste 2", "Recursos Humanos", "8.0"},
                    {"123.456.789-00", "Professor Teste 3", "Computação", "9.0"},
                    {"987.654.321-00", "Professor Teste 4", "Comércio Exterior", "7.0"},
                    {"987.654.321-00", "Professor Teste 2", "Comércio Exterior", "8.0"},
                    {"123.456.789-00", "Professor Teste 3", "Recursos Humanos", "9.0"},
                    {"987.654.321-00", "Professor Teste 2", "Computação", "8.0"},
                    {"123.456.789-00", "Professor Teste 3", "Recursos Humanos", "9.0"},
                    {"987.654.321-00", "Professor Teste 2", "Computação", "8.0"},
                    {"123.456.789-00", "Professor Teste 3", "Computação", "9.0"},
                    {"987.654.321-00", "Professor Teste 2", "Computação", "8.0"},
                    {"123.456.789-00", "Professor Teste 3", "Comércio Exterior", "9.0"},
                    {"987.654.321-00", "Professor Teste 2", "Computação", "8.0"},
                    {"123.456.789-00", "Professor Teste 3", "Recursos Humanos", "9.0"},
                    {"987.654.321-00", "Professor Teste 2", "Computação", "8.0"},
                    {"123.456.789-00", "Professor Teste 3", "Comércio Exterior", "9.0"},
                    {"987.654.321-00", "Professor Teste 2", "Computação", "8.0"},
                    {"123.456.789-00", "Professor Teste 3", "Recursos Humanos", "9.0"},
            },
            new String [] {
                "CPF", "Nome", "Área", "Pontuação"
            }
        ));
    }

    public JPanel getMainPanel() {
        return Professor;
    }

    private void addActionListeners(JFrame frame) {
        ActionListener updateClockAction = e -> {
            SimpleDateFormat sourceDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String formattedDate = sourceDateFormat.format(new Date());
            TimeLabel.setText(formattedDate);
        };

        Timer t = new Timer(100, updateClockAction);
        t.start();

        voltarButton.addActionListener(e -> {
            Menu menu = new Menu(frame);
            frame.setContentPane(menu.getMainPanel());
            frame.setTitle("Sistema de Contratação");
            frame.revalidate();
            frame.repaint();
        });

        CadastroVoltarButton.addActionListener(e -> {
            Menu menu = new Menu(frame);
            frame.setContentPane(menu.getMainPanel());
            frame.setTitle("Sistema de Contratação");
            frame.revalidate();
            frame.repaint();
        });

        ButtonConsultaProf.addActionListener(e -> {
            String cpf = InputConsultaProf.getText().replaceAll("[^0-9]", "");
            if (cpf.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Digite um CPF.");
                return;
            }

            if (cpf.length() != 11) {
                JOptionPane.showMessageDialog(frame, "CPF inválido.");
                return;
            }

            // temporário, a consulta vai ocorrer aqui
            if (true) {
                CPFProf.setText(Utilities.FormatCPF(cpf));
                NomeProf.setText("Professor Teste");
                AreaProf.setText("Computação");
                PontuacaoProf.setText("10.0");
                SelectedCPF = cpf;
                return;
            }

            JOptionPane.showMessageDialog(frame, "Professor não encontrado.");
        });

        ExcluirButton.addActionListener(e -> {
            // Perguntar se deseja excluir
            if (SelectedCPF == null) {
                JOptionPane.showMessageDialog(frame, "Selecione um professor para excluir.");
                return;
            }

            int dialogResult = JOptionPane.showConfirmDialog(null, "Deseja excluir o professor?", "Excluir", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.NO_OPTION) {
                return;
            }

            // temporário, a exclusão vai ocorrer aqui
            JOptionPane.showMessageDialog(frame, "Professor excluído com sucesso.");
            ResetFields();
        });

        SalvarButton.addActionListener(e -> {
            if (SelectedCPF == null) {
                JOptionPane.showMessageDialog(frame, "Selecione um professor para salvar.");
                return;
            }

            if (CPFProf.getText().isEmpty() || NomeProf.getText().isEmpty() || AreaProf.getText().isEmpty() || PontuacaoProf.getText().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Preencha todos os campos.");
                return;
            }

            // temporário, a att vai ocorrer aqui
            JOptionPane.showMessageDialog(frame, "Professor salvo com sucesso.");
        });

        table1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                // o ideal seria usar um evento de double click, mas devido a seleção de linha/coluna, foi necessário usar 1 click
                if (e.getClickCount() == 1 && SwingUtilities.isLeftMouseButton(e)) {
                    JTable target = (JTable)e.getSource();
                    int row = target.getSelectedRow();
                    PainelProfessor.setSelectedIndex(0);

                    InputConsultaProf.setText((String) table1.getValueAt(row, 0));
                    ButtonConsultaProf.doClick();
                }
            }
        });
    }

    private void ResetFields() {
        CPFProf.setText("");
        NomeProf.setText("");
        AreaProf.setText("");
        PontuacaoProf.setText("");
        InputConsultaProf.setText("");
        SelectedCPF = null;
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        Professor = new JPanel();
        Professor.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        PainelProfessor = new JTabbedPane();
        Professor.add(PainelProfessor, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(5, 2, new Insets(0, 0, 0, 0), -1, -1));
        PainelProfessor.addTab("Consulta", panel1);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 3, new Insets(0, 30, 0, 30), -1, -1));
        panel1.add(panel2, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        InputConsultaProf = new JTextField();
        InputConsultaProf.setText("");
        panel2.add(InputConsultaProf, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        ButtonConsultaProf = new JButton();
        ButtonConsultaProf.setText("Pesquisar");
        panel2.add(ButtonConsultaProf, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        voltarButton = new JButton();
        voltarButton.setContentAreaFilled(true);
        voltarButton.setIconTextGap(4);
        voltarButton.setInheritsPopupMenu(false);
        voltarButton.setText("Voltar");
        voltarButton.setVerticalAlignment(0);
        voltarButton.putClientProperty("hideActionText", Boolean.TRUE);
        panel2.add(voltarButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(5, 1, new Insets(0, 70, 0, 70), -1, -1));
        panel1.add(panel3, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        NomeProf = new JTextField();
        NomeProf.setEnabled(false);
        NomeProf.setHorizontalAlignment(0);
        NomeProf.setText("CPF");
        NomeProf.putClientProperty("html.disable", Boolean.TRUE);
        panel3.add(NomeProf, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        AreaProf = new JTextField();
        AreaProf.setHorizontalAlignment(0);
        AreaProf.putClientProperty("html.disable", Boolean.TRUE);
        panel3.add(AreaProf, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        PontuacaoProf = new JTextField();
        PontuacaoProf.setHorizontalAlignment(0);
        PontuacaoProf.putClientProperty("html.disable", Boolean.TRUE);
        panel3.add(PontuacaoProf, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        PainelProfessor.addTab("Cadastro", panel4);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        Professor.add(panel5, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("V0.0.1 ");
        panel5.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return Professor;
    }

}