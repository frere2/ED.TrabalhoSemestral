package br.edu.fateczl.ed.Controller;

import br.edu.fateczl.lista.Lista;

import java.io.FileNotFoundException;

import br.edu.fateczl.ed.Models.*;

public class HashController {
	
	private Lista<Disciplina> listaDisciplina = new Lista<>();
	private Lista<Curso> listaCurso = new Lista<>();

	private DisciplinasController discCont = new DisciplinasController(listaDisciplina);
	private CursosController cursoCont = new CursosController(listaCurso);

	int sizeCurso = tamanhoListaCurso();
	
	@SuppressWarnings("unchecked")
	private Lista<Disciplina>[] hashTable = new Lista[sizeCurso];
	
	public HashController(CursosController cursoCont, DisciplinasController discCont) {
		this.cursoCont = cursoCont;
		this.discCont = discCont;
	}

	public HashController() {}
	
	private void insereDisciplina() throws Exception {
		discCont.populaLista();
		for(int i = 0; i < sizeCurso; i++) {
			hashTable[i] = new Lista<>();
		}

		int sizeDisc = listaDisciplina.size();
		for (int i = 0; i < sizeDisc; i++) {
			for (int j = 0; j < sizeCurso; j++) {
				if (listaDisciplina.get(i).getCodigoCurso() == listaCurso.get(j).getCodigo()) {
					hashTable[j].addLast(listaDisciplina.get(i));
				}
			}
		}
	}
	
	public Lista<Disciplina> hashFunction(int key) {
		try {
			insereDisciplina();
			for (int i = 0; i < sizeCurso; i++) {
				if (!hashTable[i].isEmpty() && key == hashTable[i].get(0).getCodigoCurso()) {
					return hashTable[i];
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
	}
	
	private int tamanhoListaCurso() {
		try {
			cursoCont.populaLista();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return listaCurso.size();
	}
}