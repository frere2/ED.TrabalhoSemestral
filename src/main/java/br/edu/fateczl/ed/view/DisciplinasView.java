package br.edu.fateczl.ed.view;

import br.edu.fateczl.ed.Utils.Utilities;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DisciplinasView {
    private JTabbedPane PainelDisciplinas;
    public JPanel Disciplinas;
    private JTextField InputConsultaDisciplina;
    private JButton ButtonConsultaDisciplina;
    private JTextField NomeDisciplina;
    private JTextField HorarioDisciplina;
    private JTextField CodDisciplina;
    private JButton voltarButton;
    private JLabel TimeLabel;
    private JButton CadastroVoltarButton;
    private JButton ExcluirButton;
    private JTable table1;
    private JButton SalvarButton;
    private JTextField InputCodCurso;
    private JTextField InputNomeCurso;
    private JComboBox CodCursoDropdown;
    private JTextField CargaHorariaDisciplina;
    private JComboBox DiaSemanaDisciplina;
    private JComboBox InputCurso;
    private JTextField InputHoraInicio;
    private JComboBox InputDiaSemana;
    private JTextField InputHorasDiarias;
    private JCheckBox exibirApenasDisciplinasComCheckBox;

    public static String SelectedDisciplina = null;

    public DisciplinasView(JFrame frame) {
        addActionListeners(frame);
    }

    public JPanel getMainPanel() {
        return Disciplinas;
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

        ButtonConsultaDisciplina.addActionListener(e -> {
            String cpf = InputConsultaDisciplina.getText().replaceAll("[^0-9]", "");
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
                CodDisciplina.setText(Utilities.FormatCPF(cpf));
                NomeDisciplina.setText("Professor Teste");
                HorarioDisciplina.setText("Computação");
                return;
            }

            JOptionPane.showMessageDialog(frame, "Professor não encontrado.");
        });

        ExcluirButton.addActionListener(e -> {
            // Perguntar se deseja excluir
            if (SelectedDisciplina == null) {
                JOptionPane.showMessageDialog(frame, "Selecione um professor para excluir.");
                return;
            }

            int dialogResult = JOptionPane.showConfirmDialog(null, "Deseja excluir o professor?", "Excluir", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.NO_OPTION) {
                return;
            }

            // temporário, a exclusão vai ocorrer aqui
            JOptionPane.showMessageDialog(frame, "Curso excluído com sucesso.");
            ResetFields();
        });

        SalvarButton.addActionListener(e -> {
            if (SelectedDisciplina == null) {
                JOptionPane.showMessageDialog(frame, "Selecione um curso para salvar.");
                return;
            }


            if (CodDisciplina.getText().isEmpty() || NomeDisciplina.getText().isEmpty() || HorarioDisciplina.getText().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Preencha todos os campos.");
                return;
            }

            // temporário, a att vai ocorrer aqui
            JOptionPane.showMessageDialog(frame, "Curso salvo com sucesso.");
        });

        table1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                // o ideal seria usar um evento de double click, mas devido a seleção de linha/coluna, foi necessário usar 1 click
                if (e.getClickCount() == 1 && SwingUtilities.isLeftMouseButton(e)) {
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    PainelDisciplinas.setSelectedIndex(0);

                    InputConsultaDisciplina.setText((String) table1.getValueAt(row, 0));
                    ButtonConsultaDisciplina.doClick();
                }
            }
        });
    }

    private void ResetFields() {
        InputConsultaDisciplina.setText("");
        NomeDisciplina.setText("");
        HorarioDisciplina.setText("");
        CodDisciplina.setText("");
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
        Disciplinas = new JPanel();
        Disciplinas.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        PainelDisciplinas = new JTabbedPane();
        Disciplinas.add(PainelDisciplinas, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(5, 3, new Insets(0, 30, 0, 30), -1, -1));
        PainelDisciplinas.addTab("Consulta", panel1);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 3, new Insets(0, 30, 0, 30), -1, -1));
        panel1.add(panel2, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        InputConsultaDisciplina = new JTextField();
        InputConsultaDisciplina.setText("");
        panel2.add(InputConsultaDisciplina, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        ButtonConsultaDisciplina = new JButton();
        ButtonConsultaDisciplina.setText("Pesquisar");
        panel2.add(ButtonConsultaDisciplina, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        voltarButton = new JButton();
        voltarButton.setContentAreaFilled(true);
        voltarButton.setIconTextGap(4);
        voltarButton.setInheritsPopupMenu(false);
        voltarButton.setText("<-- Voltar");
        voltarButton.setVerticalAlignment(0);
        voltarButton.putClientProperty("hideActionText", Boolean.TRUE);
        panel2.add(voltarButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(6, 1, new Insets(0, 70, 0, 70), -1, -1));
        panel1.add(panel3, new GridConstraints(1, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        CodDisciplina = new JTextField();
        CodDisciplina.setEditable(true);
        CodDisciplina.setEnabled(false);
        Font CodDisciplinaFont = this.$$$getFont$$$("Consolas", -1, 14, CodDisciplina.getFont());
        if (CodDisciplinaFont != null) CodDisciplina.setFont(CodDisciplinaFont);
        CodDisciplina.setHorizontalAlignment(0);
        CodDisciplina.setText("Código da Disciplina");
        CodDisciplina.setToolTipText("");
        panel3.add(CodDisciplina, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        NomeDisciplina = new JTextField();
        NomeDisciplina.setEnabled(false);
        Font NomeDisciplinaFont = this.$$$getFont$$$("Consolas", -1, 14, NomeDisciplina.getFont());
        if (NomeDisciplinaFont != null) NomeDisciplina.setFont(NomeDisciplinaFont);
        NomeDisciplina.setHorizontalAlignment(0);
        NomeDisciplina.setText("Nome");
        NomeDisciplina.putClientProperty("html.disable", Boolean.TRUE);
        panel3.add(NomeDisciplina, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        HorarioDisciplina = new JTextField();
        HorarioDisciplina.setEditable(true);
        HorarioDisciplina.setEnabled(false);
        Font HorarioDisciplinaFont = this.$$$getFont$$$("Consolas", -1, 14, HorarioDisciplina.getFont());
        if (HorarioDisciplinaFont != null) HorarioDisciplina.setFont(HorarioDisciplinaFont);
        HorarioDisciplina.setHorizontalAlignment(0);
        HorarioDisciplina.setText("Horário de início");
        HorarioDisciplina.putClientProperty("html.disable", Boolean.TRUE);
        panel3.add(HorarioDisciplina, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        CodCursoDropdown = new JComboBox();
        CodCursoDropdown.setEnabled(false);
        Font CodCursoDropdownFont = this.$$$getFont$$$("Consolas", -1, 14, CodCursoDropdown.getFont());
        if (CodCursoDropdownFont != null) CodCursoDropdown.setFont(CodCursoDropdownFont);
        CodCursoDropdown.setMaximumRowCount(100);
        CodCursoDropdown.setName("Curso");
        panel3.add(CodCursoDropdown, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        CargaHorariaDisciplina = new JTextField();
        CargaHorariaDisciplina.setEditable(true);
        CargaHorariaDisciplina.setEnabled(false);
        Font CargaHorariaDisciplinaFont = this.$$$getFont$$$("Consolas", -1, 14, CargaHorariaDisciplina.getFont());
        if (CargaHorariaDisciplinaFont != null) CargaHorariaDisciplina.setFont(CargaHorariaDisciplinaFont);
        CargaHorariaDisciplina.setHorizontalAlignment(0);
        CargaHorariaDisciplina.setText("Horas por Dia");
        CargaHorariaDisciplina.putClientProperty("html.disable", Boolean.TRUE);
        panel3.add(CargaHorariaDisciplina, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        DiaSemanaDisciplina = new JComboBox();
        DiaSemanaDisciplina.setEnabled(false);
        Font DiaSemanaDisciplinaFont = this.$$$getFont$$$("Consolas", -1, 14, DiaSemanaDisciplina.getFont());
        if (DiaSemanaDisciplinaFont != null) DiaSemanaDisciplina.setFont(DiaSemanaDisciplinaFont);
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Segunda");
        defaultComboBoxModel1.addElement("Terça");
        defaultComboBoxModel1.addElement("Quarta");
        defaultComboBoxModel1.addElement("Quinta");
        defaultComboBoxModel1.addElement("Sexta");
        defaultComboBoxModel1.addElement("Sábado");
        defaultComboBoxModel1.addElement("Domingo");
        DiaSemanaDisciplina.setModel(defaultComboBoxModel1);
        DiaSemanaDisciplina.setName("");
        panel3.add(DiaSemanaDisciplina, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ExcluirButton = new JButton();
        ExcluirButton.setBackground(new Color(-8060413));
        ExcluirButton.setEnabled(true);
        ExcluirButton.setMargin(new Insets(0, 0, 0, 0));
        ExcluirButton.setText("Excluir");
        panel1.add(ExcluirButton, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(100, 30), new Dimension(140, 30), new Dimension(140, 30), 0, false));
        SalvarButton = new JButton();
        SalvarButton.setBackground(new Color(-16022238));
        SalvarButton.setEnabled(true);
        SalvarButton.setMargin(new Insets(0, 0, 0, 0));
        SalvarButton.setText("Salvar");
        panel1.add(SalvarButton, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(100, 30), new Dimension(140, 30), new Dimension(140, 30), 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        PainelDisciplinas.addTab("Cadastro", panel4);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 2, new Insets(0, 30, 0, 30), -1, -1));
        panel4.add(panel5, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JButton button1 = new JButton();
        button1.setText("Inserir");
        panel5.add(button1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JButton button2 = new JButton();
        button2.setContentAreaFilled(true);
        button2.setIconTextGap(4);
        button2.setInheritsPopupMenu(false);
        button2.setText("Limpar");
        button2.setVerticalAlignment(0);
        button2.putClientProperty("hideActionText", Boolean.TRUE);
        panel5.add(button2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(1, 1, new Insets(0, 30, 0, 30), -1, -1));
        panel6.setEnabled(true);
        panel4.add(panel6, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayoutManager(6, 2, new Insets(0, 70, 0, 70), -1, -1));
        panel6.add(panel7, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        InputCodCurso = new JTextField();
        InputCodCurso.setEditable(true);
        InputCodCurso.setEnabled(true);
        Font InputCodCursoFont = this.$$$getFont$$$("Consolas", -1, 14, InputCodCurso.getFont());
        if (InputCodCursoFont != null) InputCodCurso.setFont(InputCodCursoFont);
        InputCodCurso.setHorizontalAlignment(0);
        InputCodCurso.setText("");
        InputCodCurso.setToolTipText("CPF do Professor");
        panel7.add(InputCodCurso, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        InputNomeCurso = new JTextField();
        InputNomeCurso.setEnabled(true);
        Font InputNomeCursoFont = this.$$$getFont$$$("Consolas", -1, 14, InputNomeCurso.getFont());
        if (InputNomeCursoFont != null) InputNomeCurso.setFont(InputNomeCursoFont);
        InputNomeCurso.setHorizontalAlignment(0);
        InputNomeCurso.setText("");
        InputNomeCurso.setToolTipText("Nome do Professor");
        InputNomeCurso.putClientProperty("html.disable", Boolean.TRUE);
        panel7.add(InputNomeCurso, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Curso");
        panel7.add(label1, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Nome");
        panel7.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Código");
        panel7.add(label3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        InputCurso = new JComboBox();
        panel7.add(InputCurso, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Dia da Semana");
        panel7.add(label4, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        InputDiaSemana = new JComboBox();
        InputDiaSemana.setEnabled(true);
        Font InputDiaSemanaFont = this.$$$getFont$$$("Consolas", -1, 14, InputDiaSemana.getFont());
        if (InputDiaSemanaFont != null) InputDiaSemana.setFont(InputDiaSemanaFont);
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("Segunda");
        defaultComboBoxModel2.addElement("Terça");
        defaultComboBoxModel2.addElement("Quarta");
        defaultComboBoxModel2.addElement("Quinta");
        defaultComboBoxModel2.addElement("Sexta");
        defaultComboBoxModel2.addElement("Sábado");
        defaultComboBoxModel2.addElement("Domingo");
        InputDiaSemana.setModel(defaultComboBoxModel2);
        InputDiaSemana.setName("");
        panel7.add(InputDiaSemana, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Horário de Início");
        panel7.add(label5, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        InputHoraInicio = new JTextField();
        InputHoraInicio.setEnabled(true);
        Font InputHoraInicioFont = this.$$$getFont$$$("Consolas", -1, 14, InputHoraInicio.getFont());
        if (InputHoraInicioFont != null) InputHoraInicio.setFont(InputHoraInicioFont);
        InputHoraInicio.setHorizontalAlignment(0);
        InputHoraInicio.setText("");
        InputHoraInicio.setToolTipText("Nome do Professor");
        InputHoraInicio.putClientProperty("html.disable", Boolean.TRUE);
        panel7.add(InputHoraInicio, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("Horas Diárias");
        panel7.add(label6, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        InputHorasDiarias = new JTextField();
        InputHorasDiarias.setEnabled(true);
        Font InputHorasDiariasFont = this.$$$getFont$$$("Consolas", -1, 14, InputHorasDiarias.getFont());
        if (InputHorasDiariasFont != null) InputHorasDiarias.setFont(InputHorasDiariasFont);
        InputHorasDiarias.setHorizontalAlignment(0);
        InputHorasDiarias.setText("");
        InputHorasDiarias.setToolTipText("Nome do Professor");
        InputHorasDiarias.putClientProperty("html.disable", Boolean.TRUE);
        panel7.add(InputHorasDiarias, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new GridLayoutManager(1, 2, new Insets(0, 30, 0, 30), -1, -1));
        panel8.setEnabled(true);
        panel4.add(panel8, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel8.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        CadastroVoltarButton = new JButton();
        CadastroVoltarButton.setContentAreaFilled(true);
        CadastroVoltarButton.setIconTextGap(4);
        CadastroVoltarButton.setInheritsPopupMenu(false);
        CadastroVoltarButton.setText("<-- Voltar");
        CadastroVoltarButton.setVerticalAlignment(0);
        CadastroVoltarButton.putClientProperty("hideActionText", Boolean.TRUE);
        panel8.add(CadastroVoltarButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel9 = new JPanel();
        panel9.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        PainelDisciplinas.addTab("Lista de Disciplinas", panel9);
        final JScrollPane scrollPane1 = new JScrollPane();
        panel9.add(scrollPane1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        table1 = new JTable();
        scrollPane1.setViewportView(table1);
        final Spacer spacer2 = new Spacer();
        panel9.add(spacer2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        exibirApenasDisciplinasComCheckBox = new JCheckBox();
        exibirApenasDisciplinasComCheckBox.setText("Exibir apenas disciplinas com processos abertos");
        panel9.add(exibirApenasDisciplinasComCheckBox, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel10 = new JPanel();
        panel10.setLayout(new GridLayoutManager(1, 2, new Insets(0, 30, 0, 30), -1, -1));
        panel10.setAlignmentY(0.5f);
        panel10.setBackground(new Color(-11973552));
        Disciplinas.add(panel10, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(-1, 30), new Dimension(-1, 50), 0, true));
        TimeLabel = new JLabel();
        TimeLabel.setText("Hora");
        TimeLabel.setToolTipText("Hora Atual");
        panel10.add(TimeLabel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("v0.0.1");
        panel10.add(label7, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return Disciplinas;
    }

}
