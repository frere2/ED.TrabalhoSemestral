package br.edu.fateczl.ed.Controller;

import br.edu.fateczl.ed.Interface.IDisciplinasController;
import br.edu.fateczl.ed.Models.Disciplina;
import model.Lista;

public class DisciplinasController implements IDisciplinasController{
	
	Lista<Disciplina> listaDisciplinas = new Lista<>();

	public DisciplinasController() {
		super();
	}

	public void insereDisciplina(Disciplina disciplina) {
		listaDisciplinas.addLast(disciplina);
	}
	
	public void removeDisciplina(int posicao) {
		try {
			listaDisciplinas.remove(posicao);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void consultaDisciplinas() {
		int tamanho = listaDisciplinas.size();
		for (int i = 0; i < tamanho; i++) {
			try {
				System.out.println((i) + " - " + listaDisciplinas.get(i).toString());
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
	}
	
	public void atualizaArquivoDisc() {
		
	}
	
}
