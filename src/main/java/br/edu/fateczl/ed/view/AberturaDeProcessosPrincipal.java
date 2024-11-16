package br.edu.fateczl.ed.view;

import com.formdev.flatlaf.FlatDarculaLaf;
import javax.swing.*;
import br.edu.fateczl.ed.Controller.CursosController;
import br.edu.fateczl.ed.Controller.DisciplinasController;
import br.edu.fateczl.ed.Controller.HashController;
import br.edu.fateczl.ed.Controller.InscricoesController;
import br.edu.fateczl.ed.Controller.ProfessorController;
import br.edu.fateczl.ed.Models.*;
import model.Lista;

public class AberturaDeProcessosPrincipal {

	public static void main(String[] args){
		
		//HashController
		
		Lista<Curso> listaCursos = new Lista<>();
		Lista<Disciplina> listaDisciplinas = new Lista<>();
		Lista<Inscricao> listaInscricoes = new Lista<>();
		Lista<Professor> listaProfessores = new Lista<>();
		
		CursosController cursCont = new CursosController(listaCursos);
		DisciplinasController discCont = new DisciplinasController(listaDisciplinas);
		InscricoesController inscCont = new InscricoesController(listaInscricoes);
		ProfessorController profCont = new ProfessorController(listaProfessores);
		
		//Hash Keys: Curso[0]; Disciplina[1]; Inscrição[2]; Professor[3]
		HashController hashCont = new HashController(cursCont, discCont, inscCont, profCont);
		
		//Curso: int codigo, String nome, String area
		Curso curso1 = new Curso(001, "batata", "legumes");
		//Disciplina: int codigo, String nome, int diaSemana, String horario, String horasDiarias, int codigoCurso
		Disciplina disp1 = new Disciplina(1, "CozinhaGourmet", 2, "12h00", "3h", 1);
		//Inscrição: String cpf, int codigoDisciplina, int codigoProcesso
		Inscricao insc1 = new Inscricao("11111111111", 1, 1);
		//Professor: String cpf, String nome, String area, Double pontuacao
		Professor prof1 = new Professor("11111111111", "Fabiano", "legumes", 100.00 );
    
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
