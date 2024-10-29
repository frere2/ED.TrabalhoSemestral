package br.edu.fateczl.ed.Controller;

import br.edu.fateczl.ed.Interface.IProfessorController;
import br.edu.fateczl.ed.Models.Professor;
import model.Lista;

public class ProfessorController implements IProfessorController {

	Lista<Professor> listaProfessores = new Lista<>();
	
	public ProfessorController() {
		super();
	}

	public void insereProfessor(Professor professor) {
		listaProfessores.addLast(professor);
	}
	
	public void removeProfessor(int posicao) {
		try {
			listaProfessores.remove(posicao);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void consultaProfessores() {
		int tamanho = listaProfessores.size();
		for (int i = 0; i < tamanho; i++) {
			try {
				System.out.println((i) + " - " + listaProfessores.get(i).toString());
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
	}
	
	public void atualizaArquivoProf() {
		
	}
	
}
