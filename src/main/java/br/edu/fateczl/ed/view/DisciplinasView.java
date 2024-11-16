package br.edu.fateczl.ed.view;

import br.edu.fateczl.ed.Controller.CursosController;
import br.edu.fateczl.ed.Controller.DisciplinasController;
import br.edu.fateczl.ed.Controller.InscricoesController;
import br.edu.fateczl.ed.Enums.EDiaSemana;
import br.edu.fateczl.ed.Models.Curso;
import br.edu.fateczl.ed.Models.Disciplina;
import br.edu.fateczl.ed.Models.Inscricao;
import br.edu.fateczl.ed.Utils.Utilities;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import br.edu.fateczl.lista.Lista;
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
import java.util.Objects;

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
    private JTable TabelaDisciplinas;
    private JButton SalvarButton;
    private JTextField InputCodDisciplina;
    private JTextField InputNomeDisciplina;
    private JComboBox CodCursoDropdown;
    private JTextField CargaHorariaDisciplina;
    private JComboBox DiaSemanaDisciplina;
    private JComboBox InputCurso;
    private JTextField InputHoraInicio;
    private JComboBox InputDiaSemana;
    private JTextField InputHorasDiarias;
    private JCheckBox exibirApenasDisciplinasComCheckBox;
    private JButton atualizarButton;
    private JButton LimparDisciplinaButton;
    private JButton InserirDisciplinaButton;

    public static String SelectedDisciplina = null;

    Lista<Disciplina> disciplinaLista = new Lista<>();
    private DisciplinasController disciplinasController = new DisciplinasController(disciplinaLista);

    public DisciplinasView(JFrame frame) {
        addActionListeners(frame);
        setupTable();
        setupDropdowns();
    }

    public JPanel getMainPanel() {
        return Disciplinas;
    }

    private void setupTable() {
        try {
            DefaultTableModel model = new DefaultTableModel(new String[]{"Código", "Nome", "Horário", "Horas/Dia", "Dia Da Semana", "Curso"}, 0);
            disciplinasController.populaLista();

            TabelaDisciplinas.setColumnSelectionAllowed(false);
            TabelaDisciplinas.setRowSelectionAllowed(false);

            int tamanho = disciplinaLista.size();
            for (int i = 0; i < tamanho; i++) {
                model.addRow(new Object[]{disciplinaLista.get(i).getCodigo(), disciplinaLista.get(i).getNome(), disciplinaLista.get(i).getHorario(),
                        disciplinaLista.get(i).getHorasDiarias(), disciplinaLista.get(i).getDiaSemana(), disciplinaLista.get(i).getCodigoCurso()});
            }
            TabelaDisciplinas.setModel(model);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setupDropdowns() {
        Lista<Curso> listaCurso = new Lista<>();
        CursosController cursosController = new CursosController(listaCurso);

        try {
            cursosController.populaLista();

            DefaultComboBoxModel model = new DefaultComboBoxModel();

            int tamanho = listaCurso.size();
            for (int i = 0; i < tamanho; i++) {
                model.addElement(listaCurso.get(i).getCodigo() + " - " + listaCurso.get(i).getNome());
            }

            InputCurso.setModel(model);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
            String cod = InputConsultaDisciplina.getText();
            if (cod.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Digite um código.");
                return;
            }

            Disciplina disciplina = disciplinasController.consultaPorCodigo(cod);
            if (disciplina != null) {
                CodDisciplina.setText(cod);
                NomeDisciplina.setText(disciplina.getNome());
                CodCursoDropdown.setSelectedItem(disciplina.getCodigoCurso());
                DiaSemanaDisciplina.setSelectedItem(disciplina.getDiaSemana());
                HorarioDisciplina.setText(disciplina.getHorario());
                CargaHorariaDisciplina.setText(disciplina.getHorasDiarias());
                SelectedDisciplina = cod;
                return;
            }
            JOptionPane.showMessageDialog(frame, "Disciplina não encontrada.");
        });

        ExcluirButton.addActionListener(e -> {
            // Perguntar se deseja excluir
            if (SelectedDisciplina == null) {
                JOptionPane.showMessageDialog(frame, "Selecione uma disciplina para excluir.");
                return;
            }

            int dialogResult = JOptionPane.showConfirmDialog(null, "Deseja excluir a disciplina?", "Excluir", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.NO_OPTION) {
                return;
            }

            try {
                disciplinasController.removePorCodigo(SelectedDisciplina);

                Lista<Inscricao> inscricaoLista = new Lista<>();
                InscricoesController inscricoesController = new InscricoesController(inscricaoLista);
                inscricoesController.populaLista();
                inscricoesController.removePorDisciplina(SelectedDisciplina);
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
                return;
            }

            JOptionPane.showMessageDialog(frame, "Disciplina excluída com sucesso.");
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
            JOptionPane.showMessageDialog(frame, "Disciplina salva com sucesso.");
        });

        InserirDisciplinaButton.addActionListener(e -> {
            String codDisciplina = InputCodDisciplina.getText();
            String nomeDisciplina = InputNomeDisciplina.getText();
            Object codCurso = InputCurso.getSelectedItem();
            String horasDiarias = InputHorasDiarias.getText();
            String horaInicio = InputHoraInicio.getText();
            EDiaSemana diaSemana = EDiaSemana.valueOf((String) InputDiaSemana.getSelectedItem());

            if (codDisciplina.isEmpty() || nomeDisciplina.isEmpty() || codCurso == null || horasDiarias.isEmpty() || horaInicio.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Preencha todos os campos.");
                return;
            }

            String codigoCurso = codCurso.toString().split(" ")[0];
            Disciplina disciplina = new Disciplina(codDisciplina, nomeDisciplina, diaSemana, horaInicio, horasDiarias, Integer.parseInt(codigoCurso));
            boolean sucesso = disciplinasController.insere(disciplina);
            if (!sucesso) {
                JOptionPane.showMessageDialog(frame, "Essa disciplina já está cadastrada.");
                return;
            }
            JOptionPane.showMessageDialog(frame, "Disciplina adicionada com sucesso.");
        });

        LimparDisciplinaButton.addActionListener(e -> {
            InputNomeDisciplina.setText("");
            InputCodDisciplina.setText("");
            InputHoraInicio.setText("");
            InputHorasDiarias.setText("");
            InputDiaSemana.setSelectedIndex(0);
            InputCurso.setSelectedIndex(0);
        });

        TabelaDisciplinas.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                // o ideal seria usar um evento de double click, mas devido a seleção de linha/coluna, foi necessário usar 1 click
                if (e.getClickCount() == 1 && SwingUtilities.isLeftMouseButton(e)) {
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    PainelDisciplinas.setSelectedIndex(0);

                    InputConsultaDisciplina.setText((String) TabelaDisciplinas.getValueAt(row, 0));
                    ButtonConsultaDisciplina.doClick();
                }
            }
        });

        atualizarButton.addActionListener(e -> {
            setupTable();
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
        ButtonConsultaDisciplina.setIcon(new ImageIcon(getClass().getResource("/search.png")));
        ButtonConsultaDisciplina.setText("Pesquisar");
        panel2.add(ButtonConsultaDisciplina, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        voltarButton = new JButton();
        voltarButton.setContentAreaFilled(true);
        voltarButton.setIcon(new ImageIcon(getClass().getResource("/home.png")));
        voltarButton.setIconTextGap(4);
        voltarButton.setInheritsPopupMenu(false);
        voltarButton.setText("MENU");
        voltarButton.setVerticalAlignment(0);
        voltarButton.putClientProperty("hideActionText", Boolean.TRUE);
        panel2.add(voltarButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(6, 1, new Insets(0, 70, 0, 70), -1, -1));
        panel1.add(panel3, new GridConstraints(1, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        CodDisciplina = new JTextField();
        CodDisciplina.setEditable(true);
        CodDisciplina.setEnabled(false);
        Font CodDisciplinaFont = this.$$$getFont$$$(null, -1, -1, CodDisciplina.getFont());
        if (CodDisciplinaFont != null) CodDisciplina.setFont(CodDisciplinaFont);
        CodDisciplina.setHorizontalAlignment(0);
        CodDisciplina.setText("Código da Disciplina");
        CodDisciplina.setToolTipText("");
        panel3.add(CodDisciplina, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        NomeDisciplina = new JTextField();
        NomeDisciplina.setEnabled(false);
        Font NomeDisciplinaFont = this.$$$getFont$$$(null, -1, -1, NomeDisciplina.getFont());
        if (NomeDisciplinaFont != null) NomeDisciplina.setFont(NomeDisciplinaFont);
        NomeDisciplina.setHorizontalAlignment(0);
        NomeDisciplina.setText("Nome");
        NomeDisciplina.putClientProperty("html.disable", Boolean.TRUE);
        panel3.add(NomeDisciplina, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        HorarioDisciplina = new JTextField();
        HorarioDisciplina.setEditable(true);
        HorarioDisciplina.setEnabled(false);
        Font HorarioDisciplinaFont = this.$$$getFont$$$(null, -1, -1, HorarioDisciplina.getFont());
        if (HorarioDisciplinaFont != null) HorarioDisciplina.setFont(HorarioDisciplinaFont);
        HorarioDisciplina.setHorizontalAlignment(0);
        HorarioDisciplina.setText("Horário de início");
        HorarioDisciplina.putClientProperty("html.disable", Boolean.TRUE);
        panel3.add(HorarioDisciplina, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        CodCursoDropdown = new JComboBox();
        CodCursoDropdown.setEnabled(false);
        Font CodCursoDropdownFont = this.$$$getFont$$$(null, -1, -1, CodCursoDropdown.getFont());
        if (CodCursoDropdownFont != null) CodCursoDropdown.setFont(CodCursoDropdownFont);
        CodCursoDropdown.setMaximumRowCount(100);
        CodCursoDropdown.setName("Curso");
        panel3.add(CodCursoDropdown, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        CargaHorariaDisciplina = new JTextField();
        CargaHorariaDisciplina.setEditable(true);
        CargaHorariaDisciplina.setEnabled(false);
        Font CargaHorariaDisciplinaFont = this.$$$getFont$$$(null, -1, -1, CargaHorariaDisciplina.getFont());
        if (CargaHorariaDisciplinaFont != null) CargaHorariaDisciplina.setFont(CargaHorariaDisciplinaFont);
        CargaHorariaDisciplina.setHorizontalAlignment(0);
        CargaHorariaDisciplina.setText("Horas por Dia");
        CargaHorariaDisciplina.putClientProperty("html.disable", Boolean.TRUE);
        panel3.add(CargaHorariaDisciplina, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        DiaSemanaDisciplina = new JComboBox();
        DiaSemanaDisciplina.setEnabled(false);
        Font DiaSemanaDisciplinaFont = this.$$$getFont$$$(null, -1, -1, DiaSemanaDisciplina.getFont());
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
        InserirDisciplinaButton = new JButton();
        InserirDisciplinaButton.setText("Inserir");
        panel5.add(InserirDisciplinaButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        LimparDisciplinaButton = new JButton();
        LimparDisciplinaButton.setContentAreaFilled(true);
        LimparDisciplinaButton.setIconTextGap(4);
        LimparDisciplinaButton.setInheritsPopupMenu(false);
        LimparDisciplinaButton.setText("Limpar");
        LimparDisciplinaButton.setVerticalAlignment(0);
        LimparDisciplinaButton.putClientProperty("hideActionText", Boolean.TRUE);
        panel5.add(LimparDisciplinaButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(1, 1, new Insets(0, 30, 0, 30), -1, -1));
        panel6.setEnabled(true);
        panel4.add(panel6, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayoutManager(6, 2, new Insets(0, 70, 0, 70), -1, -1));
        panel6.add(panel7, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        InputCodDisciplina = new JTextField();
        InputCodDisciplina.setEditable(true);
        InputCodDisciplina.setEnabled(true);
        Font InputCodDisciplinaFont = this.$$$getFont$$$(null, -1, -1, InputCodDisciplina.getFont());
        if (InputCodDisciplinaFont != null) InputCodDisciplina.setFont(InputCodDisciplinaFont);
        InputCodDisciplina.setHorizontalAlignment(0);
        InputCodDisciplina.setText("");
        InputCodDisciplina.setToolTipText("CPF do Professor");
        panel7.add(InputCodDisciplina, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        InputNomeDisciplina = new JTextField();
        InputNomeDisciplina.setEnabled(true);
        Font InputNomeDisciplinaFont = this.$$$getFont$$$(null, -1, -1, InputNomeDisciplina.getFont());
        if (InputNomeDisciplinaFont != null) InputNomeDisciplina.setFont(InputNomeDisciplinaFont);
        InputNomeDisciplina.setHorizontalAlignment(0);
        InputNomeDisciplina.setText("");
        InputNomeDisciplina.setToolTipText("Nome do Professor");
        InputNomeDisciplina.putClientProperty("html.disable", Boolean.TRUE);
        panel7.add(InputNomeDisciplina, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
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
        Font InputDiaSemanaFont = this.$$$getFont$$$(null, -1, -1, InputDiaSemana.getFont());
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
        Font InputHoraInicioFont = this.$$$getFont$$$(null, -1, -1, InputHoraInicio.getFont());
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
        Font InputHorasDiariasFont = this.$$$getFont$$$(null, -1, -1, InputHorasDiarias.getFont());
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
        CadastroVoltarButton.setIcon(new ImageIcon(getClass().getResource("/home.png")));
        CadastroVoltarButton.setIconTextGap(4);
        CadastroVoltarButton.setInheritsPopupMenu(false);
        CadastroVoltarButton.setText("MENU");
        CadastroVoltarButton.setVerticalAlignment(0);
        CadastroVoltarButton.putClientProperty("hideActionText", Boolean.TRUE);
        panel8.add(CadastroVoltarButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel9 = new JPanel();
        panel9.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        PainelDisciplinas.addTab("Lista de Disciplinas", panel9);
        final JScrollPane scrollPane1 = new JScrollPane();
        panel9.add(scrollPane1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        TabelaDisciplinas = new JTable();
        scrollPane1.setViewportView(TabelaDisciplinas);
        final Spacer spacer2 = new Spacer();
        panel9.add(spacer2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel10 = new JPanel();
        panel10.setLayout(new GridLayoutManager(1, 3, new Insets(3, 15, 0, 15), -1, -1));
        panel9.add(panel10, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        atualizarButton = new JButton();
        atualizarButton.setIcon(new ImageIcon(getClass().getResource("/reload.png")));
        atualizarButton.setMargin(new Insets(0, 0, 0, 0));
        atualizarButton.setText("Atualizar");
        panel10.add(atualizarButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(80, 28), null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel10.add(spacer3, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        exibirApenasDisciplinasComCheckBox = new JCheckBox();
        exibirApenasDisciplinasComCheckBox.setText("Exibir apenas disciplinas com processos abertos");
        panel10.add(exibirApenasDisciplinasComCheckBox, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel11 = new JPanel();
        panel11.setLayout(new GridLayoutManager(1, 2, new Insets(0, 30, 0, 30), -1, -1));
        panel11.setAlignmentY(0.5f);
        panel11.setBackground(new Color(-11973552));
        Disciplinas.add(panel11, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(-1, 30), new Dimension(-1, 50), 0, true));
        TimeLabel = new JLabel();
        TimeLabel.setText("Hora");
        TimeLabel.setToolTipText("Hora Atual");
        panel11.add(TimeLabel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("v1.0.0");
        panel11.add(label7, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
