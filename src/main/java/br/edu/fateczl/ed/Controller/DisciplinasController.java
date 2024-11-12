package br.edu.fateczl.ed.Controller;

import java.io.IOException;

import br.edu.fateczl.ed.Interface.IEntidadesController;
import br.edu.fateczl.ed.Models.Disciplina;
import model.Lista;

public class DisciplinasController implements IEntidadesController<Disciplina>{
	
	Lista<Disciplina> listaDisciplinas = new Lista<>();

	public DisciplinasController(Lista<Disciplina> listaDisciplinas) {
		this.listaDisciplinas = listaDisciplinas;
	}

	@Override
	public void insere(Disciplina disciplina) {
		listaDisciplinas.addLast(disciplina);		
	}

	@Override
	public void remove(int posicao) {
		try {
			listaDisciplinas.remove(posicao);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	@Override
	public void consulta() {
		int tamanho = listaDisciplinas.size();
		for (int i = 0; i < tamanho; i++) {
			try {
				System.out.println((i) + " - " + listaDisciplinas.get(i).toString());
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
	}


	@Override
	public void atualizaArquivo(String caminho) {
		// TODO Auto-generated method stub
		
	}
}
