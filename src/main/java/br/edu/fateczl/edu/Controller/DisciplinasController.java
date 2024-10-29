package br.edu.fateczl.edu.Controller;

import br.edu.fateczl.edu.Models.Disciplina;
import model.Lista;

public class DisciplinasController {

	public DisciplinasController() {
		super();
	}
	
	Lista<Disciplina> listaDisciplinas = new Lista<>();

	public void insereDisciplina(Disciplina disciplina, Lista<Disciplina> listDisc) {
		listDisc.addLast(disciplina);
	}
	
	public void removeProfessor(Lista<Disciplina> listProf, int posicao) {
		try {
			listProf.remove(posicao);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void consultaProfessor(Lista<Disciplina> listDisc) {
		int tamanho = listDisc.size();
		for (int i = 0; i < tamanho; i++) {
			try {
				System.out.println(listDisc.get(i).toString());
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
	}
	
	public void atualizaArquivoProf(Lista<Disciplina> listProf) {
		
	}
	
}
