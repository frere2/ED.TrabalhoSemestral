package br.edu.fateczl.ed.Controller;

import java.io.File;
import java.io.FileWriter;

import br.edu.fateczl.ed.Interface.ICursosController;
import br.edu.fateczl.ed.Models.Curso;
import model.Lista;

public class CursosController implements ICursosController {

	Lista<Curso> listaCursos = new Lista<>();
	
	public CursosController() {
		super();
	}
	
	//Adiciona um Curso à lista
	//Abrir uma nova classe Curso e inserir os dados dela antes de chamar esse método
	public void insereCurso(Curso curso) {
		listaCursos.addLast(curso);
	}
	
	//Remove um Curso e seus dados da Lista. É necessário inserir a posicão;
	public void removeCurso(int posicao) {
		try {
			listaCursos.remove(posicao);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	//Escreve a lista de Cursos e seus dados.
	public void consultaCursos() {
		int tamanho = listaCursos.size();
		for (int i = 0; i < tamanho; i++) {
			try {
				System.out.println((i) + " - " + listaCursos.get(i).toString());
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
	}
	
	public void atualizaArquivoCurso() {
		//path	/ED.TrabalhoSemestral/src/main/java/br/edu/fateczl/ed/Repository/cursos.csv
		//Corrigir o caminho do arquivo. Lançar no computador dá certo, mas dentro do repositório não deu.
		try {
			File sobreescreve = new File ("C:\\TEMP\\cursos.csv");
			int tamanho = listaCursos.size();
			FileWriter writer = new FileWriter(sobreescreve);
			for (int i = 0; i < tamanho; i++) {
				writer.write(listaCursos.get(i).toString()+"\n");
			}
			writer.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	
}
