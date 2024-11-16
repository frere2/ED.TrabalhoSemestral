package br.edu.fateczl.ed.view;

import br.edu.fateczl.ed.Utils.Utilities;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
        table1.setModel(new DefaultTableModel(
                new Object[][]{
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
                new String[]{
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
                    JTable target = (JTable) e.getSource();
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
        panel1.setLayout(new GridLayoutManager(5, 3, new Insets(0, 30, 0, 30), -1, -1));
        PainelProfessor.addTab("Consulta", panel1);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 3, new Insets(0, 30, 0, 30), -1, -1));
        panel1.add(panel2, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
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
        voltarButton.setText("<-- Voltar");
        voltarButton.setVerticalAlignment(0);
        voltarButton.putClientProperty("hideActionText", Boolean.TRUE);
        panel2.add(voltarButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(4, 1, new Insets(0, 70, 0, 70), -1, -1));
        panel1.add(panel3, new GridConstraints(1, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        CPFProf = new JTextField();
        CPFProf.setEditable(true);
        CPFProf.setEnabled(false);
        Font CPFProfFont = this.$$$getFont$$$("Consolas", -1, 14, CPFProf.getFont());
        if (CPFProfFont != null) CPFProf.setFont(CPFProfFont);
        CPFProf.setHorizontalAlignment(0);
        CPFProf.setText("CPF");
        CPFProf.setToolTipText("");
        panel3.add(CPFProf, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        NomeProf = new JTextField();
        NomeProf.setEnabled(false);
        Font NomeProfFont = this.$$$getFont$$$("Consolas", -1, 14, NomeProf.getFont());
        if (NomeProfFont != null) NomeProf.setFont(NomeProfFont);
        NomeProf.setHorizontalAlignment(0);
        NomeProf.setText("Nome");
        NomeProf.putClientProperty("html.disable", Boolean.TRUE);
        panel3.add(NomeProf, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        AreaProf = new JTextField();
        AreaProf.setEditable(true);
        AreaProf.setEnabled(false);
        Font AreaProfFont = this.$$$getFont$$$("Consolas", -1, 14, AreaProf.getFont());
        if (AreaProfFont != null) AreaProf.setFont(AreaProfFont);
        AreaProf.setHorizontalAlignment(0);
        AreaProf.setText("Área de Atuação");
        AreaProf.putClientProperty("html.disable", Boolean.TRUE);
        panel3.add(AreaProf, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        PontuacaoProf = new JTextField();
        PontuacaoProf.setEnabled(false);
        Font PontuacaoProfFont = this.$$$getFont$$$("Consolas", -1, 14, PontuacaoProf.getFont());
        if (PontuacaoProfFont != null) PontuacaoProf.setFont(PontuacaoProfFont);
        PontuacaoProf.setHorizontalAlignment(0);
        PontuacaoProf.setText("Pontuação");
        PontuacaoProf.putClientProperty("html.disable", Boolean.TRUE);
        panel3.add(PontuacaoProf, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
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
        panel4.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        PainelProfessor.addTab("Cadastro", panel4);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(4, 2, new Insets(0, 70, 0, 70), -1, -1));
        panel4.add(panel5, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JTextField textField1 = new JTextField();
        textField1.setEditable(true);
        textField1.setEnabled(true);
        Font textField1Font = this.$$$getFont$$$("Consolas", -1, 14, textField1.getFont());
        if (textField1Font != null) textField1.setFont(textField1Font);
        textField1.setHorizontalAlignment(0);
        textField1.setText("");
        textField1.setToolTipText("CPF do Professor");
        panel5.add(textField1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JTextField textField2 = new JTextField();
        textField2.setEnabled(true);
        Font textField2Font = this.$$$getFont$$$("Consolas", -1, 14, textField2.getFont());
        if (textField2Font != null) textField2.setFont(textField2Font);
        textField2.setHorizontalAlignment(0);
        textField2.setText("");
        textField2.setToolTipText("Nome do Professor");
        textField2.putClientProperty("html.disable", Boolean.TRUE);
        panel5.add(textField2, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JTextField textField3 = new JTextField();
        textField3.setEditable(true);
        textField3.setEnabled(true);
        Font textField3Font = this.$$$getFont$$$("Consolas", -1, 14, textField3.getFont());
        if (textField3Font != null) textField3.setFont(textField3Font);
        textField3.setHorizontalAlignment(0);
        textField3.setText("");
        textField3.setToolTipText("Área de Atuação do Professor");
        textField3.putClientProperty("html.disable", Boolean.TRUE);
        panel5.add(textField3, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JTextField textField4 = new JTextField();
        textField4.setEnabled(true);
        Font textField4Font = this.$$$getFont$$$("Consolas", -1, 14, textField4.getFont());
        if (textField4Font != null) textField4.setFont(textField4Font);
        textField4.setHorizontalAlignment(0);
        textField4.setText("");
        textField4.setToolTipText("Pontuação do Professor");
        textField4.putClientProperty("html.disable", Boolean.TRUE);
        panel5.add(textField4, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Pontuação");
        panel5.add(label1, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Área de Atuação");
        panel5.add(label2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Nome");
        panel5.add(label3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("CPF");
        panel5.add(label4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(1, 2, new Insets(0, 30, 0, 30), -1, -1));
        panel4.add(panel6, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JButton button1 = new JButton();
        button1.setText("Inserir");
        panel6.add(button1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JButton button2 = new JButton();
        button2.setContentAreaFilled(true);
        button2.setIconTextGap(4);
        button2.setInheritsPopupMenu(false);
        button2.setText("Limpar");
        button2.setVerticalAlignment(0);
        button2.putClientProperty("hideActionText", Boolean.TRUE);
        panel6.add(button2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayoutManager(1, 2, new Insets(0, 30, 0, 30), -1, -1));
        panel7.setEnabled(true);
        panel4.add(panel7, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel7.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        CadastroVoltarButton = new JButton();
        CadastroVoltarButton.setContentAreaFilled(true);
        CadastroVoltarButton.setIconTextGap(4);
        CadastroVoltarButton.setInheritsPopupMenu(false);
        CadastroVoltarButton.setText("<-- Voltar");
        CadastroVoltarButton.setVerticalAlignment(0);
        CadastroVoltarButton.putClientProperty("hideActionText", Boolean.TRUE);
        panel7.add(CadastroVoltarButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        PainelProfessor.addTab("Lista de Professores", panel8);
        final JScrollPane scrollPane1 = new JScrollPane();
        panel8.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        table1 = new JTable();
        scrollPane1.setViewportView(table1);
        final Spacer spacer2 = new Spacer();
        panel8.add(spacer2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel9 = new JPanel();
        panel9.setLayout(new GridLayoutManager(1, 2, new Insets(0, 30, 0, 30), -1, -1));
        panel9.setAlignmentY(0.5f);
        panel9.setBackground(new Color(-11973552));
        Professor.add(panel9, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(-1, 30), new Dimension(-1, 50), 0, true));
        TimeLabel = new JLabel();
        TimeLabel.setText("Hora");
        TimeLabel.setToolTipText("Hora Atual");
        panel9.add(TimeLabel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("v0.0.1");
        panel9.add(label5, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
        return Professor;
    }

}