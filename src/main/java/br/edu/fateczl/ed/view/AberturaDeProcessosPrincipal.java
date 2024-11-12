package br.edu.fateczl.ed.view;

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
		Curso curso2 = new Curso(002, "banana", "frutas");
		Curso curso3 = new Curso(003, "alface", "verduras");
		Curso curso4 = new Curso(004, "feijão", "grãos");
		Curso curso5 = new Curso(005, "beringela", "legumes");
		//Disciplina: int codigo, String nome, int diaSemana, String horario, String horasDiarias, int codigoCurso
		Disciplina disp1 = new Disciplina(1, "CozinhaGourmet", 2, "12h00", "3h", 1);
		//Inscrição: String cpf, int codigoDisciplina, int codigoProcesso
		Inscricao insc1 = new Inscricao("11111111111", 1, 1);
		//Professor: String cpf, String nome, String area, Double pontuacao
		Professor prof1 = new Professor("11111111111", "Fabiano", "", 100.00 );
		
		hashCont.inserir(0, curso1);
		hashCont.inserir(0, curso2);
		hashCont.inserir(0, curso3);
		hashCont.inserir(0, curso4);
		hashCont.inserir(0, curso5);
		hashCont.inserir(1, disp1);
		hashCont.inserir(2, insc1);
		hashCont.inserir(3, prof1);
		
//		hashCont.populaLista(0);
		hashCont.consulta(0);
		System.out.println();
		hashCont.consulta(1);
		System.out.println();
		hashCont.consulta(2);
		System.out.println();
		hashCont.consulta(3);
		hashCont.atualizaArquivo(0);
		hashCont.atualizaArquivo(1);
		hashCont.atualizaArquivo(2);
		hashCont.atualizaArquivo(3);
//		hashCont.remove(0, 4);
//		hashCont.remove(0, 3);
//		hashCont.remove(0, 2);
//		hashCont.remove(0, 1);
//		hashCont.remove(0, 0);
//		System.out.println();
//		hashCont.consulta(0);
//		hashCont.inserir(0, curso5);
//		System.out.println();
//		hashCont.consulta(0);
//		hashCont.atualizaArquivo(0);
	}
}
