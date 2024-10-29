package br.edu.fateczl.ed.Controller;

import br.edu.fateczl.ed.Interface.IInscricoesController;
import br.edu.fateczl.ed.Models.Inscricao;
import model.Lista;

public class InscricoesController implements IInscricoesController {

	Lista<Inscricao> listaInscricoes = new Lista<>();
	
	public InscricoesController() {
		super();
	}
	
	public void insereInscricao(Inscricao inscricao) {
		listaInscricoes.addLast(inscricao);
	}
	
	public void removeInscricao(int posicao) {
		try {
			listaInscricoes.remove(posicao);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void consultaInscricoes() {
		int tamanho = listaInscricoes.size();
		for (int i = 0; i < tamanho; i++) {
			try {
				System.out.println((i) + " - " + listaInscricoes.get(i).toString());
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
	}
	
	public void atualizaArquivoInsc() {
		
	}

}
