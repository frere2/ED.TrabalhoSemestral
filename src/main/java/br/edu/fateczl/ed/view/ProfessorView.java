package br.edu.fateczl.ed.view;

import br.edu.fateczl.ed.Controller.InscricoesController;
import br.edu.fateczl.ed.Controller.ProfessorController;
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
import java.io.FileNotFoundException;
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
    private JTable TabelaProfessores;
    private JButton SalvarButton;
    private JButton atualizarButton;
    private JButton InserirProfButton;
    private JButton LimparProfButton;
    private JTextField InputCPFProf;
    private JTextField InputNomeProf;
    private JTextField InputAreaProf;
    private JTextField InputPontuacaoProf;
    private JButton EditarButton;

    public static String SelectedCPF = null;

    Lista<Professor> professorLista = new Lista<>();
    private ProfessorController professorController = new ProfessorController(professorLista);

    public ProfessorView(JFrame frame) {
        addActionListeners(frame);
        setupTable();
    }

    public JPanel getMainPanel() {
        return Professor;
    }

    private void setupTable() {
        try {
            DefaultTableModel model = new DefaultTableModel(new String[]{"CPF", "Nome", "Área", "Pontuação"}, 0);
            professorController.populaLista();

            TabelaProfessores.setColumnSelectionAllowed(false);
            TabelaProfessores.setRowSelectionAllowed(false);

            int tamanho = professorLista.size();
            for (int i = 0; i < tamanho; i++) {
                model.addRow(new Object[]{professorLista.get(i).getCPF(), professorLista.get(i).getNome(),
                        professorLista.get(i).getArea(), professorLista.get(i).getPontuacao()});
            }
            TabelaProfessores.setModel(model);
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

            Professor prof = professorController.consultaPorCPF(cpf);
            if (prof != null) {
                CPFProf.setText(Utilities.FormatCPF(cpf));
                NomeProf.setText(prof.getNome());
                AreaProf.setText(prof.getArea());
                PontuacaoProf.setText(prof.getPontuacao().toString());
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

            professorController.removePorCPF(SelectedCPF);

            try {
                Lista<Inscricao> inscricaoLista = new Lista<>();
                InscricoesController inscricoesController = new InscricoesController(inscricaoLista);
                inscricoesController.populaLista();
                inscricoesController.removePorCPF(SelectedCPF);
            } catch (FileNotFoundException ex) {
                System.err.println(ex.getMessage());
            }

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

            String cpf = CPFProf.getText().replaceAll("\\D", "");

            Professor professor1 = new Professor(cpf, NomeProf.getText(), AreaProf.getText(), Double.parseDouble(PontuacaoProf.getText()));
            try {
                professorController.alteraDados(professor1);
                JOptionPane.showMessageDialog(frame, "Professor salvo com sucesso.");
                NomeProf.setEnabled(false);
                AreaProf.setEnabled(false);
                PontuacaoProf.setEnabled(false);
                return;

            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        InserirProfButton.addActionListener(e -> {
            String cpf = InputCPFProf.getText().replaceAll("[^0-9]", "");

            if (cpf.isEmpty() || InputNomeProf.getText().isEmpty() || InputAreaProf.getText().isEmpty() || InputPontuacaoProf.getText().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Preencha todos os campos.");
                return;
            }

            if (cpf.length() != 11) {
                JOptionPane.showMessageDialog(frame, "CPF inválido.");
                return;
            }

            Professor professor = new Professor(InputCPFProf.getText(), InputNomeProf.getText(), InputAreaProf.getText(), Double.parseDouble(InputPontuacaoProf.getText()));
            boolean sucesso = professorController.insere(professor);
            InputAreaProf.setText("");
            InputNomeProf.setText("");
            InputCPFProf.setText("");
            InputPontuacaoProf.setText("");
            if (!sucesso) {
                JOptionPane.showMessageDialog(frame, "Este cpf já está cadastrado.");
                return;
            }
            JOptionPane.showMessageDialog(frame, "Professor adicionado com sucesso.");
        });

        LimparProfButton.addActionListener(e -> {
            InputAreaProf.setText("");
            InputNomeProf.setText("");
            InputCPFProf.setText("");
            InputPontuacaoProf.setText("");
        });

        EditarButton.addActionListener(e -> {
            if (NomeProf.getText().equals("Nome") && AreaProf.getText().equals("Área de Atuação") && PontuacaoProf.getText().equals("Pontuação")) {
                NomeProf.setEnabled(false);
                AreaProf.setEnabled(false);
                PontuacaoProf.setEnabled(false);
                return;
            }

            if (NomeProf.isEnabled() == false && AreaProf.isEnabled() == false && PontuacaoProf.isEnabled() == false) {
                NomeProf.setEnabled(true);
                AreaProf.setEnabled(true);
                PontuacaoProf.setEnabled(true);
            } else {
                NomeProf.setEnabled(false);
                AreaProf.setEnabled(false);
                PontuacaoProf.setEnabled(false);
            }

        });

        TabelaProfessores.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                // o ideal seria usar um evento de double click, mas devido a seleção de linha/coluna, foi necessário usar 1 click
                if (e.getClickCount() == 1 && SwingUtilities.isLeftMouseButton(e)) {
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    PainelProfessor.setSelectedIndex(0);

                    InputConsultaProf.setText((String) TabelaProfessores.getValueAt(row, 0));
                    ButtonConsultaProf.doClick();
                }
            }
        });

        atualizarButton.addActionListener(e -> {
            setupTable();
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
        panel1.setLayout(new GridLayoutManager(5, 4, new Insets(0, 30, 0, 30), -1, -1));
        PainelProfessor.addTab("Consulta", panel1);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 3, new Insets(0, 30, 0, 30), -1, -1));
        panel1.add(panel2, new GridConstraints(0, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        InputConsultaProf = new JTextField();
        InputConsultaProf.setHorizontalAlignment(0);
        InputConsultaProf.setText("");
        InputConsultaProf.setToolTipText("Digite o CPF do Professor");
        panel2.add(InputConsultaProf, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        ButtonConsultaProf = new JButton();
        ButtonConsultaProf.setIcon(new ImageIcon(getClass().getResource("/search.png")));
        ButtonConsultaProf.setText("Pesquisar");
        panel2.add(ButtonConsultaProf, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
        panel3.setLayout(new GridLayoutManager(4, 1, new Insets(0, 70, 0, 70), -1, -1));
        panel1.add(panel3, new GridConstraints(1, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        CPFProf = new JTextField();
        CPFProf.setEditable(true);
        CPFProf.setEnabled(false);
        Font CPFProfFont = this.$$$getFont$$$(null, -1, -1, CPFProf.getFont());
        if (CPFProfFont != null) CPFProf.setFont(CPFProfFont);
        CPFProf.setHorizontalAlignment(0);
        CPFProf.setText("CPF");
        CPFProf.setToolTipText("CPF");
        panel3.add(CPFProf, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        NomeProf = new JTextField();
        NomeProf.setEnabled(false);
        Font NomeProfFont = this.$$$getFont$$$(null, -1, -1, NomeProf.getFont());
        if (NomeProfFont != null) NomeProf.setFont(NomeProfFont);
        NomeProf.setHorizontalAlignment(0);
        NomeProf.setText("Nome");
        NomeProf.setToolTipText("Nome");
        NomeProf.putClientProperty("html.disable", Boolean.TRUE);
        panel3.add(NomeProf, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        AreaProf = new JTextField();
        AreaProf.setEditable(true);
        AreaProf.setEnabled(false);
        Font AreaProfFont = this.$$$getFont$$$(null, -1, -1, AreaProf.getFont());
        if (AreaProfFont != null) AreaProf.setFont(AreaProfFont);
        AreaProf.setHorizontalAlignment(0);
        AreaProf.setText("Área de Atuação");
        AreaProf.setToolTipText("Área de Atuação");
        AreaProf.putClientProperty("html.disable", Boolean.TRUE);
        panel3.add(AreaProf, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        PontuacaoProf = new JTextField();
        PontuacaoProf.setEnabled(false);
        Font PontuacaoProfFont = this.$$$getFont$$$(null, -1, -1, PontuacaoProf.getFont());
        if (PontuacaoProfFont != null) PontuacaoProf.setFont(PontuacaoProfFont);
        PontuacaoProf.setHorizontalAlignment(0);
        PontuacaoProf.setText("Pontuação");
        PontuacaoProf.setToolTipText("Pontuação");
        PontuacaoProf.putClientProperty("html.disable", Boolean.TRUE);
        panel3.add(PontuacaoProf, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        ExcluirButton = new JButton();
        ExcluirButton.setBackground(new Color(-8060413));
        ExcluirButton.setEnabled(true);
        ExcluirButton.setMargin(new Insets(0, 0, 0, 0));
        ExcluirButton.setText("Excluir");
        panel1.add(ExcluirButton, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(100, 30), new Dimension(140, 30), new Dimension(140, 30), 0, false));
        SalvarButton = new JButton();
        SalvarButton.setBackground(new Color(-16022238));
        SalvarButton.setEnabled(true);
        SalvarButton.setMargin(new Insets(0, 0, 0, 0));
        SalvarButton.setText("Salvar");
        panel1.add(SalvarButton, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(100, 30), new Dimension(140, 30), new Dimension(140, 30), 0, false));
        EditarButton = new JButton();
        EditarButton.setText("Editar");
        panel1.add(EditarButton, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(100, 30), new Dimension(140, 30), new Dimension(140, 30), 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        PainelProfessor.addTab("Cadastro", panel4);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(4, 2, new Insets(0, 70, 0, 70), -1, -1));
        panel4.add(panel5, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        InputCPFProf = new JTextField();
        InputCPFProf.setEditable(true);
        InputCPFProf.setEnabled(true);
        Font InputCPFProfFont = this.$$$getFont$$$(null, -1, -1, InputCPFProf.getFont());
        if (InputCPFProfFont != null) InputCPFProf.setFont(InputCPFProfFont);
        InputCPFProf.setHorizontalAlignment(0);
        InputCPFProf.setText("");
        InputCPFProf.setToolTipText("CPF do Professor");
        panel5.add(InputCPFProf, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        InputNomeProf = new JTextField();
        InputNomeProf.setEnabled(true);
        Font InputNomeProfFont = this.$$$getFont$$$(null, -1, -1, InputNomeProf.getFont());
        if (InputNomeProfFont != null) InputNomeProf.setFont(InputNomeProfFont);
        InputNomeProf.setHorizontalAlignment(0);
        InputNomeProf.setText("");
        InputNomeProf.setToolTipText("Nome do Professor");
        InputNomeProf.putClientProperty("html.disable", Boolean.TRUE);
        panel5.add(InputNomeProf, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        InputAreaProf = new JTextField();
        InputAreaProf.setEditable(true);
        InputAreaProf.setEnabled(true);
        Font InputAreaProfFont = this.$$$getFont$$$(null, -1, -1, InputAreaProf.getFont());
        if (InputAreaProfFont != null) InputAreaProf.setFont(InputAreaProfFont);
        InputAreaProf.setHorizontalAlignment(0);
        InputAreaProf.setText("");
        InputAreaProf.setToolTipText("Área de Atuação do Professor");
        InputAreaProf.putClientProperty("html.disable", Boolean.TRUE);
        panel5.add(InputAreaProf, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        InputPontuacaoProf = new JTextField();
        InputPontuacaoProf.setEnabled(true);
        Font InputPontuacaoProfFont = this.$$$getFont$$$(null, -1, -1, InputPontuacaoProf.getFont());
        if (InputPontuacaoProfFont != null) InputPontuacaoProf.setFont(InputPontuacaoProfFont);
        InputPontuacaoProf.setHorizontalAlignment(0);
        InputPontuacaoProf.setText("");
        InputPontuacaoProf.setToolTipText("Pontuação do Professor");
        InputPontuacaoProf.putClientProperty("html.disable", Boolean.TRUE);
        panel5.add(InputPontuacaoProf, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
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
        InserirProfButton = new JButton();
        InserirProfButton.setText("Inserir");
        panel6.add(InserirProfButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        LimparProfButton = new JButton();
        LimparProfButton.setContentAreaFilled(true);
        LimparProfButton.setIconTextGap(4);
        LimparProfButton.setInheritsPopupMenu(false);
        LimparProfButton.setText("Limpar");
        LimparProfButton.setVerticalAlignment(0);
        LimparProfButton.putClientProperty("hideActionText", Boolean.TRUE);
        panel6.add(LimparProfButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayoutManager(1, 2, new Insets(0, 30, 0, 30), -1, -1));
        panel7.setEnabled(true);
        panel4.add(panel7, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel7.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        CadastroVoltarButton = new JButton();
        CadastroVoltarButton.setContentAreaFilled(true);
        CadastroVoltarButton.setIcon(new ImageIcon(getClass().getResource("/home.png")));
        CadastroVoltarButton.setIconTextGap(4);
        CadastroVoltarButton.setInheritsPopupMenu(false);
        CadastroVoltarButton.setText("MENU");
        CadastroVoltarButton.setVerticalAlignment(0);
        CadastroVoltarButton.putClientProperty("hideActionText", Boolean.TRUE);
        panel7.add(CadastroVoltarButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        PainelProfessor.addTab("Lista de Professores", panel8);
        final JScrollPane scrollPane1 = new JScrollPane();
        panel8.add(scrollPane1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        TabelaProfessores = new JTable();
        scrollPane1.setViewportView(TabelaProfessores);
        final Spacer spacer2 = new Spacer();
        panel8.add(spacer2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel9 = new JPanel();
        panel9.setLayout(new GridLayoutManager(1, 2, new Insets(3, 15, 0, 15), -1, -1));
        panel8.add(panel9, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        atualizarButton = new JButton();
        atualizarButton.setIcon(new ImageIcon(getClass().getResource("/reload.png")));
        atualizarButton.setMargin(new Insets(0, 0, 0, 0));
        atualizarButton.setText("Atualizar");
        panel9.add(atualizarButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(80, 28), null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel9.add(spacer3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel10 = new JPanel();
        panel10.setLayout(new GridLayoutManager(1, 2, new Insets(0, 30, 0, 30), -1, -1));
        panel10.setAlignmentY(0.5f);
        panel10.setBackground(new Color(-11973552));
        Professor.add(panel10, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(-1, 30), new Dimension(-1, 50), 0, true));
        TimeLabel = new JLabel();
        TimeLabel.setText("Hora");
        TimeLabel.setToolTipText("Hora Atual");
        panel10.add(TimeLabel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("v1.0.0");
        panel10.add(label5, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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