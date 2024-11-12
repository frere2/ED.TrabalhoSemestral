package br.edu.fateczl.ed.Controller;

import br.edu.fateczl.ed.Interface.IEntidadesController;
import br.edu.fateczl.ed.Models.Professor;
import model.Lista;

public class ProfessorController implements IEntidadesController<Professor> {

	Lista<Professor> listaProfessores = new Lista<>();
	
	public ProfessorController(Lista<Professor> listaProfessores) {
		this.listaProfessores = listaProfessores;
	}

	@Override
	public void insere(Professor professor) {
		listaProfessores.addLast(professor);
	}
	
	@Override
	public void remove(int posicao) {
		try {
			listaProfessores.remove(posicao);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	@Override
	public void consulta() {
		int tamanho = listaProfessores.size();
		for (int i = 0; i < tamanho; i++) {
			try {
				System.out.println((i) + " - " + listaProfessores.get(i).toString());
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
	}
	
	@Override
	public void atualizaArquivo(String caminho) {
		
	}
	
}
