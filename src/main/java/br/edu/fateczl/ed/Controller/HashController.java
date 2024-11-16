package br.edu.fateczl.ed.Controller;

import model.Lista;
import br.edu.fateczl.ed.Models.*;

public class HashController {
	
	private Lista<?>[] hashTable = new Lista[4];
	
	private int size = hashTable.length;
	
	public HashController() {
		hashTable[0] = new Lista<Curso>();
		hashTable[1] = new Lista<Disciplina>();
		hashTable[2] = new Lista<Inscricao>();
		hashTable[3] = new Lista<Professor>();
	
	}
	
	@SuppressWarnings("unchecked")
	CursosController cursoCont = new CursosController((Lista<Curso>) hashTable[0]);
	@SuppressWarnings("unchecked")
	DisciplinasController discCont = new DisciplinasController((Lista<Disciplina>) hashTable[1]);
	@SuppressWarnings("unchecked")
	InscricoesController inscCont = new InscricoesController((Lista<Inscricao>) hashTable[2]);
	@SuppressWarnings("unchecked")
	ProfessorController profCont = new ProfessorController((Lista<Professor>) hashTable[3]);
	
	public HashController(CursosController cursoCont, DisciplinasController discCont, InscricoesController inscCont, ProfessorController profCont) {
		this.cursoCont = cursoCont;
		this.discCont = discCont;
		this.inscCont = inscCont;
		this.profCont = profCont;
	}
	
	private int hashFunction(int key) {
		return (key % size);
	}
	
	public void inserir(int key, Object objeto) {
		int hIndex = hashFunction(key);
		if (hIndex == 0 && objeto.getClass() == Curso.class) {
			cursoCont.insere((Curso) objeto);
		} else if (hIndex == 1 && objeto.getClass() == Disciplina.class) {
			discCont.insere((Disciplina) objeto);
		} else if (hIndex == 2 && objeto.getClass() == Inscricao.class) {
			inscCont.insere((Inscricao) objeto);
		} else if (hIndex == 3 && objeto.getClass() == Professor.class) {
			profCont.insere((Professor) objeto);
		} else {
			System.out.println("Objeto inválido ou chave não encontrada.");
		}
	}
	
	public void remove(int key, int posicao) {
		int hIndex = hashFunction(key);
		if (hIndex == 0) {
			cursoCont.remove(posicao);
		} else if (hIndex == 1) {
			discCont.remove(posicao);
		} else if (hIndex == 2) {
			inscCont.remove(posicao);
		} else {
			profCont.remove(posicao);
		}
	}
	
	public void consulta(int key) {
		int hIndex = hashFunction(key);
		if (hIndex == 0) {
			cursoCont.consulta();
		} else if (hIndex == 1) {
			discCont.consulta();
		} else if (hIndex == 2) {
			inscCont.consulta();
		} else {
			profCont.consulta();
		}
	}
	
	public void populaLista(int key) throws Exception {
		int hIndex = hashFunction(key);
		if (hIndex == 0) {
			cursoCont.populaLista();
		} else if (hIndex == 1) {
			discCont.populaLista();
		} else if (hIndex == 2) {
			inscCont.populaLista();
		} else {
			profCont.populaLista();
		}
	}
	
	public void atualizaArquivo(int key) {
		int hIndex = hashFunction(key);
		String caminho = "C:\\TEMP\\";
		if (hIndex == 0) {
			cursoCont.atualizaArquivo(caminho);
		} else if (hIndex == 1) {
			discCont.atualizaArquivo(caminho);
		} else if (hIndex == 2) {
			inscCont.atualizaArquivo(caminho);
		} else {
			profCont.atualizaArquivo(caminho);
		}
	}
}
