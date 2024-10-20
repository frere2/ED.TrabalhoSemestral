package br.edu.fateczl.ed.Controller;

import java.io.IOException;

import br.edu.fateczl.ed.Models.Disciplina;
import model.Lista;

public class DisciplinasController {

	String fileName = "disciplinas.csv";
	
	public DisciplinasController() {
		super();
	}

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
	
	public void atualizaArquivoProf(Lista<Disciplina> listProf) throws IOException {
		
	}
	
}
