package br.edu.fateczl.ed.Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import br.edu.fateczl.ed.Infrastructure.CSVReader;
import br.edu.fateczl.ed.Infrastructure.ConfigReader;
import br.edu.fateczl.ed.Interface.ICursosController;
import br.edu.fateczl.ed.Models.Curso;
import br.edu.fateczl.ed.Utils.Utilities;
import br.edu.fateczl.fila.Fila;
import br.edu.fateczl.lista.Lista;

public class CursosController implements ICursosController {
	private  CSVReader<Curso> readerCont = new CSVReader<>(Curso.class);
	private Lista<Curso> listaCursos;
	private ConfigReader configReader = new ConfigReader();
	private final String Caminho = configReader.getFullPath("cursos.csv");

	public CursosController(Lista<Curso> listaCursos) {
		this.listaCursos = listaCursos;
	}

	public boolean insere(Curso curso) {
		int tamanho = listaCursos.size();
		try {
			for (int i = 0; i < tamanho; i++) {
				if (listaCursos.get(i).getCodigo() == curso.getCodigo())
					return false;
				}
			} catch (Exception e) {
				System.err.println("Erro ao inserir curso");
        }
		listaCursos.addLast(curso);
		atualizaArquivo();
		return true;
	}

	public void removePorCodigo(int codigo) {
		int tamanho = listaCursos.size();
		try {
			for (int i = 0; i < tamanho; i++) {
				Curso curso = listaCursos.get(i);
				if (curso.getCodigo() == codigo) {
					listaCursos.remove(i);
				}
			}
			atualizaArquivo();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public Curso consultaPorCodigo(int codigo) {
		int tamanho = listaCursos.size();
		for (int i = 0; i < tamanho; i++) {
			try {
				Curso curso = listaCursos.get(i);
				if (curso.getCodigo() == codigo) {
					return curso;
				}
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
		return null;
	}

	public void atualizaArquivo() {
		try {
			File arquivo = new File (Caminho);
			FileWriter writer = new FileWriter(arquivo);
			writer.write(Utilities.GetHeadersByClass(Curso.class) + "\n");
			int tamanho = listaCursos.size();
			for (int i = 0; i < tamanho; i++) {
				writer.write(listaCursos.get(i).toString()+"\n");
			}
			writer.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void populaLista() throws FileNotFoundException {
		File dir = new File(Caminho);
		if (dir.exists()) {
			try {
				if (!listaCursos.isEmpty()) { listaCursos.clean(); }
				Fila<Curso> fila = readerCont.mapFromCSV(Caminho, ";");
				while (!fila.isEmpty()) {
					listaCursos.addLast(fila.remove());
				}
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		} else {
			throw new FileNotFoundException("Arquivo Inexistente");
		}
	}
	
	public void alteraDados(Curso curso) throws Exception {
		int tamanho = listaCursos.size();
		for (int i = 0; i < tamanho; i++) {
			if (curso.getCodigo() == listaCursos.get(i).getCodigo()) {
				listaCursos.get(i).setNome(curso.getNome());
				listaCursos.get(i).setArea(curso.getArea());
				break;
			}
		}
	}
	
	
}
