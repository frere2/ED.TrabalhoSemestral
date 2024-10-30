package br.edu.fateczl.ed.Controller;

import java.io.File;
import java.io.FileWriter;

import br.edu.fateczl.ed.Interface.IEntidadesController;
import br.edu.fateczl.ed.Models.Curso;
import model.Lista;

public class CursosController implements IEntidadesController<Curso> {

	Lista<Curso> listaCursos = new Lista<>();
	
	public CursosController(Lista<Curso> listaCursos) {
		this.listaCursos = listaCursos;
	}

	@Override
	public void insere(Curso curso) {
		listaCursos.addLast(curso);
	}

	@Override
	public void remove(int posicao) {
		try {
			listaCursos.remove(posicao);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	@Override
	public void consulta() {
		int tamanho = listaCursos.size();
		for (int i = 0; i < tamanho; i++) {
			try {
				System.out.println((i) + " - " + listaCursos.get(i).toString());
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
		
	}

	@Override
	public void atualizaArquivo(String caminho) {
		//Caminho de teste de arquivo: "C:" + File.separator + "TEMP" + File.separator + "cursos.csv"
		//path	/ED.TrabalhoSemestral/src/main/java/br/edu/fateczl/ed/Repository/cursos.csv
		try {
			File dir = new File(caminho);
			if (dir.exists() && dir.isDirectory()) {
				boolean existe = false;
				File arquivo = new File ("C:\\TEMP\\", "cursos.csv"); //new File (caminho, nome);
				if (arquivo.exists()) {existe = true;}
				FileWriter writer = new FileWriter(arquivo, existe);
				int tamanho = listaCursos.size();
				for (int i = 0; i < tamanho; i++) {
					writer.write(listaCursos.get(i).toString()+"\n");
				}
				writer.close();
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	/*
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
	}*/

	
}
