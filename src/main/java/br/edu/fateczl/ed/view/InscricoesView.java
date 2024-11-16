package br.edu.fateczl.ed.view;

import br.edu.fateczl.ed.Controller.DisciplinasController;
import br.edu.fateczl.ed.Controller.InscricoesController;
import br.edu.fateczl.ed.Controller.ProfessorController;
import br.edu.fateczl.ed.Models.Disciplina;
import br.edu.fateczl.ed.Models.Inscricao;
import br.edu.fateczl.ed.Models.Professor;
import br.edu.fateczl.ed.Utils.Utilities;
import br.edu.fateczl.lista.Lista;
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
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class InscricoesView {
    private JTabbedPane PainelInscricoes;
    public JPanel Inscricoes;
    private JTextField InputConsultaDisciplinaCPF;
    private JButton ButtonConsultaInscricao;
    private JTextField CodDisciplinaInscricao;
    private JTextField CodProcessoInscricao;
    private JTextField CPFProfInscricao;
    private JButton voltarButton;
    private JLabel TimeLabel;
    private JButton CadastroVoltarButton;
    private JButton ExcluirButton;
    private JTable TabelaInscricoes;
    private JButton SalvarButton;
    private JTextField InputCPFInscricao;
    private JTextField InputProcessoInscricao;
    private JComboBox EscolhaDisciplinaLista;
    private JButton pesquisarButton;
    private JComboBox InputDisciplinaInscricao;
    private JButton atualizarButton;
    private JButton InserirInscricaoButton;
    private JButton LimparInscricaoButton;

    public static String SelectedInscricao = null;

    Lista<Inscricao> inscricaoLista = new Lista<>();
    Lista<Professor> professorLista = new Lista<>();
    private InscricoesController inscricoesController = new InscricoesController(inscricaoLista);
    private ProfessorController professorController = new ProfessorController(professorLista);

    public InscricoesView(JFrame frame) {
        addActionListeners(frame);
        setupTable(null);
        setupDropdowns();
    }

    public JPanel getMainPanel() {
        return Inscricoes;
    }

    private void setupTable(Lista<Inscricao> lista) {
        if (lista == null) lista = inscricaoLista;
        try {
            ordenaPorPontuacao(lista);

            DefaultTableModel model = new DefaultTableModel(new String[]{"CPF", "Nome", "Área", "Processo", "Disciplina", "Pontuação"}, 0);
            inscricoesController.populaLista();

            TabelaInscricoes.setColumnSelectionAllowed(false);
            TabelaInscricoes.setRowSelectionAllowed(false);

            professorController.populaLista();

            int tamanho = lista.size();
            for (int i = 0; i < tamanho; i++) {
                Professor professor = professorController.consultaPorCPF(lista.get(i).getCPF());
                model.addRow(new Object[]{professor.getCPF(), professor.getNome(), professor.getArea(),
                        lista.get(i).getCodigoProcesso(), lista.get(i).getCodigoDisciplina(), professor.getPontuacao()});
            }
            TabelaInscricoes.setModel(model);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void ordenaPorPontuacao(Lista<Inscricao> lista) throws Exception {
        int tamanho = lista.size();
        for (int i = 0; i < tamanho - 1; i++) {
            for (int j = 0; j < tamanho - i - 1; j++) {
                Inscricao inscricaoJ = lista.get(j);
                Inscricao inscricaoJ1 = lista.get(j + 1);

                Professor professorJ = professorController.consultaPorCPF(inscricaoJ.getCPF());
                Professor professorJ1 = professorController.consultaPorCPF(inscricaoJ1.getCPF());

                double pontuacaoJ = professorJ.getPontuacao();
                double pontuacaoJ1 = professorJ1.getPontuacao();

                if (pontuacaoJ < pontuacaoJ1) {
                    troca(lista, j, j + 1);
                }
            }
        }
    }

    private void troca(Lista<Inscricao> lista, int i, int j) throws Exception {
        Inscricao temp = lista.get(i);
        set(lista, i, lista.get(j));
        set(lista, j, temp);
    }

    private void set(Lista<Inscricao> lista, int posicao, Inscricao valor) throws Exception {
        lista.remove(posicao);
        lista.add(posicao, valor);
    }

    private void setupDropdowns() {
        Lista<Disciplina> listaDisciplina = new Lista<>();
        DisciplinasController disciplinasController = new DisciplinasController(listaDisciplina);

        try {
            disciplinasController.populaLista();

            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

            int tamanho = listaDisciplina.size();
            for (int i = 0; i < tamanho; i++) {
                model.addElement(listaDisciplina.get(i).getCodigo() + " - " + listaDisciplina.get(i).getNome());
            }

            InputDisciplinaInscricao.setModel(model);

            DefaultComboBoxModel<String> model1 = new DefaultComboBoxModel<>();
            model1.addElement("Todas");
            for (int i = 0; i < tamanho; i++) {
                model1.addElement(listaDisciplina.get(i).getCodigo() + " - " + listaDisciplina.get(i).getNome());
            }

            EscolhaDisciplinaLista.setModel(model1);
            EscolhaDisciplinaLista.setSelectedIndex(0);

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

        ButtonConsultaInscricao.addActionListener(e -> {
            String cpf = InputConsultaDisciplinaCPF.getText().replaceAll("[^0-9]", "");
            if (cpf.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Digite um CPF.");
                return;
            }

            if (cpf.length() != 11) {
                JOptionPane.showMessageDialog(frame, "CPF inválido.");
                return;
            }

            Inscricao inscricao = inscricoesController.consultaPorCPF(cpf);
            if (inscricao != null) {
                CPFProfInscricao.setText(Utilities.FormatCPF(cpf));
                CodDisciplinaInscricao.setText(inscricao.getCodigoDisciplina());
                CodProcessoInscricao.setText(String.valueOf(inscricao.getCodigoProcesso()));
                SelectedInscricao = cpf;
                return;
            }

            JOptionPane.showMessageDialog(frame, "Inscrição não encontrada.");
        });

        ExcluirButton.addActionListener(e -> {
            // Perguntar se deseja excluir
            if (SelectedInscricao == null) {
                JOptionPane.showMessageDialog(frame, "Selecione uma inscrição para excluir.");
                return;
            }

            int dialogResult = JOptionPane.showConfirmDialog(null, "Deseja excluir a inscrição?", "Excluir", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.NO_OPTION) {
                return;
            }

            inscricoesController.removePorCPF(SelectedInscricao);
            JOptionPane.showMessageDialog(frame, "Inscrição excluída com sucesso.");
            ResetFields();
        });

        SalvarButton.addActionListener(e -> {
            if (SelectedInscricao == null) {
                JOptionPane.showMessageDialog(frame, "Selecione uma inscrição para salvar.");
                return;
            }


            if (CPFProfInscricao.getText().isEmpty() || CodDisciplinaInscricao.getText().isEmpty() || CodProcessoInscricao.getText().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Preencha todos os campos.");
                return;
            }

            // temporário, a att vai ocorrer aqui
            JOptionPane.showMessageDialog(frame, "Inscrição salva com sucesso.");
        });

        InserirInscricaoButton.addActionListener(e -> {
            if (InputCPFInscricao.getText().isEmpty() || InputProcessoInscricao.getText().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Preencha todos os campos.");
                return;
            }

            try {
                professorController.populaLista();
            } catch (FileNotFoundException ex) {
                System.err.println(ex.getMessage());
            }

            Professor prof = professorController.consultaPorCPF(InputCPFInscricao.getText());
            if (prof == null) {
                JOptionPane.showMessageDialog(frame, "Professor não encontrado");
                return;
            }

            String disciplina = InputDisciplinaInscricao.getSelectedItem().toString().split(" ")[0];
            Inscricao inscricao = new Inscricao(InputCPFInscricao.getText(), disciplina, Integer.parseInt(InputProcessoInscricao.getText()));
            boolean sucesso = inscricoesController.insere(inscricao);
            if (!sucesso) {
                JOptionPane.showMessageDialog(frame, "Esse CPF já esta inscrito em um processo.");
                return;
            }
            JOptionPane.showMessageDialog(frame, "Inscrição adicionada com sucesso.");
        });

        LimparInscricaoButton.addActionListener(e -> {
            InputCPFInscricao.setText("");
            InputDisciplinaInscricao.setSelectedIndex(0);
            InputProcessoInscricao.setText("");
        });

        TabelaInscricoes.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                // o ideal seria usar um evento de double click, mas devido a seleção de linha/coluna, foi necessário usar 1 click
                if (e.getClickCount() == 1 && SwingUtilities.isLeftMouseButton(e)) {
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    PainelInscricoes.setSelectedIndex(0);

                    InputConsultaDisciplinaCPF.setText((String) TabelaInscricoes.getValueAt(row, 0));
                    ButtonConsultaInscricao.doClick();
                }
            }
        });

        pesquisarButton.addActionListener(e -> {
            String disciplina = EscolhaDisciplinaLista.getSelectedItem().toString().split(" ")[0];
            Lista<Inscricao> InscricaoDisciplinaLista = new Lista<>();

            if (disciplina.equals("Todas")) {
                setupTable(null);
                return;
            }

            int tamanho = inscricaoLista.size();
            try {
                for (int i = 0; i < tamanho; i++) {
                    if (inscricaoLista.get(i).getCodigoDisciplina().equals(disciplina)) {
                        InscricaoDisciplinaLista.addLast(inscricaoLista.get(i));
                    }
                }
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }

            setupTable(InscricaoDisciplinaLista);
        });

        atualizarButton.addActionListener(e -> {
            setupTable(null);
            EscolhaDisciplinaLista.setSelectedIndex(0);
        });
    }

    private void ResetFields() {
        InputConsultaDisciplinaCPF.setText("");
        CodDisciplinaInscricao.setText("");
        CodProcessoInscricao.setText("");
        CPFProfInscricao.setText("");
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
        Inscricoes = new JPanel();
        Inscricoes.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        PainelInscricoes = new JTabbedPane();
        Inscricoes.add(PainelInscricoes, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(5, 3, new Insets(0, 30, 0, 30), -1, -1));
        PainelInscricoes.addTab("Consulta", panel1);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 3, new Insets(0, 30, 0, 30), -1, -1));
        panel1.add(panel2, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        InputConsultaDisciplinaCPF = new JTextField();
        InputConsultaDisciplinaCPF.setText("");
        panel2.add(InputConsultaDisciplinaCPF, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        ButtonConsultaInscricao = new JButton();
        ButtonConsultaInscricao.setIcon(new ImageIcon(getClass().getResource("/search.png")));
        ButtonConsultaInscricao.setText("Pesquisar");
        panel2.add(ButtonConsultaInscricao, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
        CPFProfInscricao = new JTextField();
        CPFProfInscricao.setEditable(true);
        CPFProfInscricao.setEnabled(false);
        Font CPFProfInscricaoFont = this.$$$getFont$$$(null, -1, -1, CPFProfInscricao.getFont());
        if (CPFProfInscricaoFont != null) CPFProfInscricao.setFont(CPFProfInscricaoFont);
        CPFProfInscricao.setHorizontalAlignment(0);
        CPFProfInscricao.setText("CPF do Professor");
        CPFProfInscricao.setToolTipText("");
        panel3.add(CPFProfInscricao, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        CodDisciplinaInscricao = new JTextField();
        CodDisciplinaInscricao.setEnabled(false);
        Font CodDisciplinaInscricaoFont = this.$$$getFont$$$(null, -1, -1, CodDisciplinaInscricao.getFont());
        if (CodDisciplinaInscricaoFont != null) CodDisciplinaInscricao.setFont(CodDisciplinaInscricaoFont);
        CodDisciplinaInscricao.setHorizontalAlignment(0);
        CodDisciplinaInscricao.setText("Código da Disciplina");
        CodDisciplinaInscricao.putClientProperty("html.disable", Boolean.TRUE);
        panel3.add(CodDisciplinaInscricao, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        CodProcessoInscricao = new JTextField();
        CodProcessoInscricao.setEditable(true);
        CodProcessoInscricao.setEnabled(false);
        Font CodProcessoInscricaoFont = this.$$$getFont$$$(null, -1, -1, CodProcessoInscricao.getFont());
        if (CodProcessoInscricaoFont != null) CodProcessoInscricao.setFont(CodProcessoInscricaoFont);
        CodProcessoInscricao.setHorizontalAlignment(0);
        CodProcessoInscricao.setText("Código do Processo");
        CodProcessoInscricao.putClientProperty("html.disable", Boolean.TRUE);
        panel3.add(CodProcessoInscricao, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
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
        PainelInscricoes.addTab("Cadastro", panel4);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 2, new Insets(0, 30, 0, 30), -1, -1));
        panel4.add(panel5, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        InserirInscricaoButton = new JButton();
        InserirInscricaoButton.setText("Inserir");
        panel5.add(InserirInscricaoButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        LimparInscricaoButton = new JButton();
        LimparInscricaoButton.setContentAreaFilled(true);
        LimparInscricaoButton.setIconTextGap(4);
        LimparInscricaoButton.setInheritsPopupMenu(false);
        LimparInscricaoButton.setText("Limpar");
        LimparInscricaoButton.setVerticalAlignment(0);
        LimparInscricaoButton.putClientProperty("hideActionText", Boolean.TRUE);
        panel5.add(LimparInscricaoButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(1, 1, new Insets(0, 30, 0, 30), -1, -1));
        panel6.setEnabled(true);
        panel4.add(panel6, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayoutManager(3, 2, new Insets(0, 70, 0, 70), -1, -1));
        panel6.add(panel7, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        InputCPFInscricao = new JTextField();
        InputCPFInscricao.setEditable(true);
        InputCPFInscricao.setEnabled(true);
        Font InputCPFInscricaoFont = this.$$$getFont$$$(null, -1, -1, InputCPFInscricao.getFont());
        if (InputCPFInscricaoFont != null) InputCPFInscricao.setFont(InputCPFInscricaoFont);
        InputCPFInscricao.setHorizontalAlignment(0);
        InputCPFInscricao.setText("");
        InputCPFInscricao.setToolTipText("CPF do Professor");
        panel7.add(InputCPFInscricao, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        InputProcessoInscricao = new JTextField();
        InputProcessoInscricao.setEditable(true);
        InputProcessoInscricao.setEnabled(true);
        Font InputProcessoInscricaoFont = this.$$$getFont$$$(null, -1, -1, InputProcessoInscricao.getFont());
        if (InputProcessoInscricaoFont != null) InputProcessoInscricao.setFont(InputProcessoInscricaoFont);
        InputProcessoInscricao.setHorizontalAlignment(0);
        InputProcessoInscricao.setText("");
        InputProcessoInscricao.setToolTipText("Área de Atuação do Professor");
        InputProcessoInscricao.putClientProperty("html.disable", Boolean.TRUE);
        panel7.add(InputProcessoInscricao, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Código do Processo");
        panel7.add(label1, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Código da Disciplina");
        panel7.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("CPF do Professor");
        panel7.add(label3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        InputDisciplinaInscricao = new JComboBox();
        panel7.add(InputDisciplinaInscricao, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
        panel9.setLayout(new GridLayoutManager(3, 5, new Insets(0, 0, 0, 0), -1, -1));
        PainelInscricoes.addTab("Lista de Inscrições por Disciplina", panel9);
        final JScrollPane scrollPane1 = new JScrollPane();
        panel9.add(scrollPane1, new GridConstraints(1, 0, 1, 5, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        TabelaInscricoes = new JTable();
        scrollPane1.setViewportView(TabelaInscricoes);
        final Spacer spacer2 = new Spacer();
        panel9.add(spacer2, new GridConstraints(2, 0, 1, 5, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel10 = new JPanel();
        panel10.setLayout(new GridLayoutManager(1, 5, new Insets(3, 15, 0, 15), -1, -1));
        panel9.add(panel10, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        atualizarButton = new JButton();
        atualizarButton.setIcon(new ImageIcon(getClass().getResource("/reload.png")));
        atualizarButton.setMargin(new Insets(0, 0, 0, 0));
        atualizarButton.setText("Atualizar");
        panel10.add(atualizarButton, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(80, 28), null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel10.add(spacer3, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Disciplina");
        panel10.add(label4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        EscolhaDisciplinaLista = new JComboBox();
        panel10.add(EscolhaDisciplinaLista, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        pesquisarButton = new JButton();
        pesquisarButton.setHorizontalAlignment(0);
        pesquisarButton.setIcon(new ImageIcon(getClass().getResource("/search.png")));
        pesquisarButton.setText("Pesquisar");
        panel10.add(pesquisarButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel11 = new JPanel();
        panel11.setLayout(new GridLayoutManager(1, 2, new Insets(0, 30, 0, 30), -1, -1));
        panel11.setAlignmentY(0.5f);
        panel11.setBackground(new Color(-11973552));
        Inscricoes.add(panel11, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(-1, 30), new Dimension(-1, 50), 0, true));
        TimeLabel = new JLabel();
        TimeLabel.setText("Hora");
        TimeLabel.setToolTipText("Hora Atual");
        panel11.add(TimeLabel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("v1.0.0");
        panel11.add(label5, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
        return Inscricoes;
    }
}
