package br.edu.fateczl.ed.Controller;

import java.io.IOException;

import br.edu.fateczl.ed.Models.Professor;
import model.Lista;

public class ProfessorController {
	
	String fileName = "professor.csv";

	public ProfessorController() {
		super();
	}

	public void insereProfessor(Professor professor, Lista<Professor> listProf) {
		listProf.addLast(professor);
	}
	
	public void removeProfessor(Lista<Professor> listProf, int posicao) {
		try {
			listProf.remove(posicao);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void consultaProfessor(Lista<Professor> listProf) {
		int tamanho = listProf.size();
		for (int i = 0; i < tamanho; i++) {
			try {
				System.out.println(listProf.get(i).toString());
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
	}
	
	public void atualizaArquivoProf(Lista<Professor> listProf) throws IOException{
		
	}
	
}
