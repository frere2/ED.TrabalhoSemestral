package br.edu.fateczl.ed.view;

import java.io.IOException;

import br.edu.fateczl.ed.Models.*;
import br.edu.fateczl.ed.Controller.*;

import model.Lista;

public class AberturaDeProcessosPrincipal {

	public static void main(String[] args) throws IOException{
		Lista<Disciplina> listaDisciplinas = new Lista<>();
		Lista<Curso> listaCursos = new Lista<>();
		Lista<Professor> listaProfessores = new Lista<>();
		Lista<Inscricao> listaInscricoes = new Lista<>();
		
		DisciplinasController discCont = new DisciplinasController();
		CursosController cursoCont = new CursosController();
		ProfessorController profCont = new ProfessorController();
		InscricoesController inscCont = new InscricoesController();
	}

}
