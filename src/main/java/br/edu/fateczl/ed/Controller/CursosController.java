package br.edu.fateczl.ed.Controller;

import java.io.IOException;

import br.edu.fateczl.ed.Models.Curso;
import model.Lista;

public class CursosController {
	
	String fileName = "cursos.csv";

	public CursosController() {
		super();
	}

	public void insereCursos(Curso curso, Lista<Curso> listCurso) {
		listCurso.addLast(curso);
	}
	
	public void removeProfessor(Lista<Curso> listCurso, int posicao) {
		try {
			listCurso.remove(posicao);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void consultaProfessor(Lista<Curso> listCurso) {
		int tamanho = listCurso.size();
		for (int i = 0; i < tamanho; i++) {
			try {
				System.out.println(listCurso.get(i).toString());
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
	}
	
	public void atualizaArquivoProf(Lista<Curso> listProf) throws IOException{
		
	}
	
}
