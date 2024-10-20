package br.edu.fateczl.ed.Controller;

import java.io.IOException;

import br.edu.fateczl.ed.Models.Inscricao;
import model.Lista;

public class InscricoesController {
	
	String fileName = "inscricao.csv";

	public InscricoesController() {
		super();
	}
	
	public void insereInscricao(Inscricao inscricao, Lista<Inscricao> listInsc) {
		listInsc.addLast(inscricao);
	}
	
	public void removeProfessor(Lista<Inscricao> listInsc, int posicao) {
		try {
			listInsc.remove(posicao);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void consultaProfessor(Lista<Inscricao> listInsc) {
		int tamanho = listInsc.size();
		for (int i = 0; i < tamanho; i++) {
			try {
				System.out.println(listInsc.get(i).toString());
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
	}
	
	public void atualizaArquivoProf(Lista<Inscricao> listProf) throws IOException {
		
	}

}
