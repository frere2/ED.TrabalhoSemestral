package br.edu.fateczl.ed.view;

import br.edu.fateczl.ed.Controller.CursosController;
import br.edu.fateczl.ed.Controller.DisciplinasController;
import br.edu.fateczl.ed.Controller.InscricoesController;
import br.edu.fateczl.ed.Models.Curso;
import br.edu.fateczl.ed.Models.Disciplina;
import br.edu.fateczl.ed.Models.Inscricao;
import br.edu.fateczl.ed.Models.Professor;
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

public class CursosView {
    private JTabbedPane PainelCursos;
    public JPanel Cursos;
    private JTextField InputConsultaCurso;
    private JButton ButtonConsultaCurso;
    private JTextField NomeCurso;
    private JTextField AreaCurso;
    private JTextField CodCurso;
    private JButton voltarButton;
    private JLabel TimeLabel;
    private JButton CadastroVoltarButton;
    private JButton ExcluirButton;
    private JTable TabelaCursos;
    private JButton SalvarButton;
    private JTextField InputCodCurso;
    private JTextField InputNomeCurso;
    private JTextField InputAreaCurso;
    private JButton atualizarButton;
    private JButton InserirCursoButton;
    private JButton LimparCursoButton;

    public static Integer SelectedCurso;

    Lista<Curso> cursoLista = new Lista<>();
    private CursosController cursoController = new CursosController(cursoLista);

    public CursosView(JFrame frame) {
        addActionListeners(frame);
        setupTable();
    }

    public JPanel getMainPanel() {
        return Cursos;
    }

    private void setupTable() {
        try {
            DefaultTableModel model = new DefaultTableModel(new String[]{"Código", "Nome", "Área"}, 0);
            cursoController.populaLista();

            TabelaCursos.setColumnSelectionAllowed(false);
            TabelaCursos.setRowSelectionAllowed(false);

            int tamanho = cursoLista.size();
            for (int i = 0; i < tamanho; i++) {
                model.addRow(new Object[]{Integer.toString(cursoLista.get(i).getCodigo()), cursoLista.get(i).getNome(), cursoLista.get(i).getArea()});
            }
            TabelaCursos.setModel(model);
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

        ButtonConsultaCurso.addActionListener(e -> {
            String cod = InputConsultaCurso.getText();
            if (cod.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Digite um código.");
                return;
            }

            Curso curso = cursoController.consultaPorCodigo(Integer.parseInt(cod));
            if (curso != null) {
                CodCurso.setText(cod);
                NomeCurso.setText(curso.getNome());
                AreaCurso.setText(curso.getArea());
                SelectedCurso = Integer.valueOf(cod);
                return;
            }

            JOptionPane.showMessageDialog(frame, "Curso não encontrado.");
        });

        ExcluirButton.addActionListener(e -> {
            // Perguntar se deseja excluir
            if (SelectedCurso == null) {
                JOptionPane.showMessageDialog(frame, "Selecione um curso para excluir.");
                return;
            }

            int dialogResult = JOptionPane.showConfirmDialog(null, "Deseja excluir o curso?", "Excluir", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.NO_OPTION) {
                return;
            }

            try {
                Lista<Disciplina> disciplinaLista = new Lista<>();
                DisciplinasController disciplinasController = new DisciplinasController(disciplinaLista);
                disciplinasController.populaLista();
                Lista<String> disciplinasRemovidas = disciplinasController.removePorCodigoCurso(SelectedCurso);

                Lista<Inscricao> inscricaoLista = new Lista<>();
                InscricoesController inscricoesController = new InscricoesController(inscricaoLista);
                inscricoesController.populaLista();

                int tamanho = disciplinasRemovidas.size();
                for (int i = 0; i < tamanho; i++) {
                    inscricoesController.removePorDisciplina(disciplinasRemovidas.get(i));
                }

                cursoController.removePorCodigo(SelectedCurso);
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
                return;
            }

            JOptionPane.showMessageDialog(frame, "Curso excluído com sucesso.");
            ResetFields();
        });

        SalvarButton.addActionListener(e -> {
            if (SelectedCurso == null) {
                JOptionPane.showMessageDialog(frame, "Selecione um curso para salvar.");
                return;
            }

            if (CodCurso.getText().isEmpty() || NomeCurso.getText().isEmpty() || AreaCurso.getText().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Preencha todos os campos.");
                return;
            }

            // temporário, a att vai ocorrer aqui
            JOptionPane.showMessageDialog(frame, "Curso salvo com sucesso.");
        });

        InserirCursoButton.addActionListener(e -> {
            if (InputCodCurso.getText().isEmpty() || InputAreaCurso.getText().isEmpty() || InputNomeCurso.getText().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Preencha todos os campos.");
                return;
            }

            Curso curso = new Curso(Integer.parseInt(InputCodCurso.getText()), InputNomeCurso.getText(), InputAreaCurso.getText());
            boolean sucesso = cursoController.insere(curso);
            if (!sucesso) {
                JOptionPane.showMessageDialog(frame, "Esse curso já está cadastrado.");
                return;
            }
            JOptionPane.showMessageDialog(frame, "Curso adicionado com sucesso.");
        });

        LimparCursoButton.addActionListener(e -> {
            InputNomeCurso.setText("");
            InputCodCurso.setText("");
            InputAreaCurso.setText("");
        });

        TabelaCursos.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                // o ideal seria usar um evento de double click, mas devido a seleção de linha/coluna, foi necessário usar 1 click
                if (e.getClickCount() == 1 && SwingUtilities.isLeftMouseButton(e)) {
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    PainelCursos.setSelectedIndex(0);

                    InputConsultaCurso.setText((String) TabelaCursos.getValueAt(row, 0));
                    ButtonConsultaCurso.doClick();
                }
            }
        });

        atualizarButton.addActionListener(e -> {
            setupTable();
        });
    }

    private void ResetFields() {
        InputConsultaCurso.setText("");
        NomeCurso.setText("");
        AreaCurso.setText("");
        CodCurso.setText("");
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
        Cursos = new JPanel();
        Cursos.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        PainelCursos = new JTabbedPane();
        Cursos.add(PainelCursos, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(5, 3, new Insets(0, 30, 0, 30), -1, -1));
        PainelCursos.addTab("Consulta", panel1);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 3, new Insets(0, 30, 0, 30), -1, -1));
        panel1.add(panel2, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        InputConsultaCurso = new JTextField();
        InputConsultaCurso.setText("");
        panel2.add(InputConsultaCurso, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        ButtonConsultaCurso = new JButton();
        ButtonConsultaCurso.setIcon(new ImageIcon(getClass().getResource("/search.png")));
        ButtonConsultaCurso.setText("Pesquisar");
        panel2.add(ButtonConsultaCurso, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
        panel3.setLayout(new GridLayoutManager(3, 1, new Insets(0, 70, 0, 70), -1, -1));
        panel1.add(panel3, new GridConstraints(1, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        CodCurso = new JTextField();
        CodCurso.setEditable(true);
        CodCurso.setEnabled(false);
        Font CodCursoFont = this.$$$getFont$$$(null, -1, -1, CodCurso.getFont());
        if (CodCursoFont != null) CodCurso.setFont(CodCursoFont);
        CodCurso.setHorizontalAlignment(0);
        CodCurso.setText("Código do Curso");
        CodCurso.setToolTipText("");
        panel3.add(CodCurso, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        NomeCurso = new JTextField();
        NomeCurso.setEnabled(false);
        Font NomeCursoFont = this.$$$getFont$$$(null, -1, -1, NomeCurso.getFont());
        if (NomeCursoFont != null) NomeCurso.setFont(NomeCursoFont);
        NomeCurso.setHorizontalAlignment(0);
        NomeCurso.setText("Nome");
        NomeCurso.putClientProperty("html.disable", Boolean.TRUE);
        panel3.add(NomeCurso, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        AreaCurso = new JTextField();
        AreaCurso.setEditable(true);
        AreaCurso.setEnabled(false);
        Font AreaCursoFont = this.$$$getFont$$$(null, -1, -1, AreaCurso.getFont());
        if (AreaCursoFont != null) AreaCurso.setFont(AreaCursoFont);
        AreaCurso.setHorizontalAlignment(0);
        AreaCurso.setText("Área de Conhecimento");
        AreaCurso.putClientProperty("html.disable", Boolean.TRUE);
        panel3.add(AreaCurso, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
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
        PainelCursos.addTab("Cadastro", panel4);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 2, new Insets(0, 30, 0, 30), -1, -1));
        panel4.add(panel5, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        InserirCursoButton = new JButton();
        InserirCursoButton.setText("Inserir");
        panel5.add(InserirCursoButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        LimparCursoButton = new JButton();
        LimparCursoButton.setContentAreaFilled(true);
        LimparCursoButton.setIconTextGap(4);
        LimparCursoButton.setInheritsPopupMenu(false);
        LimparCursoButton.setText("Limpar");
        LimparCursoButton.setVerticalAlignment(0);
        LimparCursoButton.putClientProperty("hideActionText", Boolean.TRUE);
        panel5.add(LimparCursoButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(1, 1, new Insets(0, 30, 0, 30), -1, -1));
        panel6.setEnabled(true);
        panel4.add(panel6, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayoutManager(3, 2, new Insets(0, 70, 0, 70), -1, -1));
        panel6.add(panel7, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        InputCodCurso = new JTextField();
        InputCodCurso.setEditable(true);
        InputCodCurso.setEnabled(true);
        Font InputCodCursoFont = this.$$$getFont$$$(null, -1, -1, InputCodCurso.getFont());
        if (InputCodCursoFont != null) InputCodCurso.setFont(InputCodCursoFont);
        InputCodCurso.setHorizontalAlignment(0);
        InputCodCurso.setText("");
        InputCodCurso.setToolTipText("CPF do Professor");
        panel7.add(InputCodCurso, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        InputNomeCurso = new JTextField();
        InputNomeCurso.setEnabled(true);
        Font InputNomeCursoFont = this.$$$getFont$$$(null, -1, -1, InputNomeCurso.getFont());
        if (InputNomeCursoFont != null) InputNomeCurso.setFont(InputNomeCursoFont);
        InputNomeCurso.setHorizontalAlignment(0);
        InputNomeCurso.setText("");
        InputNomeCurso.setToolTipText("Nome do Professor");
        InputNomeCurso.putClientProperty("html.disable", Boolean.TRUE);
        panel7.add(InputNomeCurso, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        InputAreaCurso = new JTextField();
        InputAreaCurso.setEditable(true);
        InputAreaCurso.setEnabled(true);
        Font InputAreaCursoFont = this.$$$getFont$$$(null, -1, -1, InputAreaCurso.getFont());
        if (InputAreaCursoFont != null) InputAreaCurso.setFont(InputAreaCursoFont);
        InputAreaCurso.setHorizontalAlignment(0);
        InputAreaCurso.setText("");
        InputAreaCurso.setToolTipText("Área de Atuação do Professor");
        InputAreaCurso.putClientProperty("html.disable", Boolean.TRUE);
        panel7.add(InputAreaCurso, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Área de Conhecimento");
        panel7.add(label1, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Nome");
        panel7.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Código");
        panel7.add(label3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
        PainelCursos.addTab("Lista de Cursos", panel9);
        final JScrollPane scrollPane1 = new JScrollPane();
        panel9.add(scrollPane1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        TabelaCursos = new JTable();
        scrollPane1.setViewportView(TabelaCursos);
        final Spacer spacer2 = new Spacer();
        panel9.add(spacer2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel10 = new JPanel();
        panel10.setLayout(new GridLayoutManager(1, 2, new Insets(3, 15, 0, 15), -1, -1));
        panel9.add(panel10, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        atualizarButton = new JButton();
        atualizarButton.setIcon(new ImageIcon(getClass().getResource("/reload.png")));
        atualizarButton.setMargin(new Insets(0, 0, 0, 0));
        atualizarButton.setText("Atualizar");
        panel10.add(atualizarButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(80, 28), null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel10.add(spacer3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel11 = new JPanel();
        panel11.setLayout(new GridLayoutManager(1, 2, new Insets(0, 30, 0, 30), -1, -1));
        panel11.setAlignmentY(0.5f);
        panel11.setBackground(new Color(-11973552));
        Cursos.add(panel11, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(-1, 30), new Dimension(-1, 50), 0, true));
        TimeLabel = new JLabel();
        TimeLabel.setText("Hora");
        TimeLabel.setToolTipText("Hora Atual");
        panel11.add(TimeLabel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("v1.0.0");
        panel11.add(label4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
        return Cursos;
    }
}
