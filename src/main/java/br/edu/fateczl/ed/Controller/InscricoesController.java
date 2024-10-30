package br.edu.fateczl.ed.Controller;

import br.edu.fateczl.ed.Interface.IEntidadesController;
import br.edu.fateczl.ed.Models.Inscricao;
import model.Lista;

public class InscricoesController implements IEntidadesController<Inscricao> {

	Lista<Inscricao> listaInscricoes = new Lista<>();
	
	public InscricoesController(Lista<Inscricao> listaInscricoes) {
		this.listaInscricoes = listaInscricoes;
	}
	
	@Override
	public void insere(Inscricao inscricao) {
		listaInscricoes.addLast(inscricao);
	}	
	
	@Override
	public void remove(int posicao) {
		try {
			listaInscricoes.remove(posicao);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	@Override
	public void consulta() {
		int tamanho = listaInscricoes.size();
		for (int i = 0; i < tamanho; i++) {
			try {
				System.out.println((i) + " - " + listaInscricoes.get(i).toString());
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
	}
	
	@Override
	public void atualizaArquivo(String caminho) {
		
	}

}
