package br.edu.fateczl.ed.Controller;

import java.io.File;
import java.io.FileWriter;
import br.edu.fateczl.ed.Infrastructure.CSVReader;

import br.edu.fateczl.ed.Interface.IEntidadesController;
import br.edu.fateczl.ed.Models.Curso;
import model.Lista;

public class CursosController implements IEntidadesController<Curso> {

	CSVReader<Curso> readerCont = new CSVReader<>(Curso.class);

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
				System.out.println("Código: " +listaCursos.get(i).getCodigo()+ " Curso: " +listaCursos.get(i).getNome()+ " Área: " +listaCursos.get(i).getArea());
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
				File arquivo = new File (caminho, "cursos.csv"); //new File (caminho, nome);
				if (arquivo.exists()) {existe = true;}
				FileWriter writer = new FileWriter(arquivo, existe);
				if (existe == false) {writer.write("codigo;nome;area\n");}
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
	
	public void populaLista() throws Exception {
		String caminho = "C:\\TEMP\\cursos.csv";
		File dir = new File(caminho);
		if (dir.exists()) {
			try {
				listaCursos = readerCont.mapFromCSV("C:\\TEMP\\cursos.csv", ";");
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		} else {
			throw new Exception ("Arquivo Inexistente");
		}
	}
}
