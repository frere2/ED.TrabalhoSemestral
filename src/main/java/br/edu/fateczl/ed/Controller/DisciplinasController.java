package br.edu.fateczl.ed.Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Objects;

import br.edu.fateczl.ed.Enums.EDiaSemana;
import br.edu.fateczl.ed.Infrastructure.CSVReader;
import br.edu.fateczl.ed.Infrastructure.ConfigReader;
import br.edu.fateczl.ed.Interface.IDisciplinasController;
import br.edu.fateczl.ed.Models.Curso;
import br.edu.fateczl.ed.Models.Disciplina;
import br.edu.fateczl.ed.Utils.Utilities;
import br.edu.fateczl.fila.Fila;
import br.edu.fateczl.lista.Lista;

public class DisciplinasController implements IDisciplinasController {

	private CSVReader<Disciplina> readerCont = new CSVReader<>(Disciplina.class);
	private Lista<Disciplina> listaDisciplinas;
	private ConfigReader configReader = new ConfigReader();
	private final String Caminho = configReader.getFullPath("disciplinas.csv");

	public DisciplinasController(Lista<Disciplina> listaDisciplinas) {
		this.listaDisciplinas = listaDisciplinas;
	}

	public boolean insere(Disciplina disciplina) {
		int tamanho = listaDisciplinas.size();
		try {
			for (int i = 0; i < tamanho; i++) {
				if (listaDisciplinas.get(i).getCodigo().equals(disciplina.getCodigo()))
					return false;
				}
			} catch (Exception e) {
			System.err.println("Erro ao inserir disciplina");
        }
		listaDisciplinas.addLast(disciplina);
		atualizaArquivo();
		return true;
	}

	public void removePorCodigo(String codigo) {
		int tamanho = listaDisciplinas.size();
		try {
			for (int i = 0; i < tamanho; i++) {
				Disciplina disciplina = listaDisciplinas.get(i);
				if (disciplina.getCodigo().equals(codigo)) {
					listaDisciplinas.remove(i);
				}
			}
			atualizaArquivo();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public Lista<String> removePorCodigoCurso(int codigo) {
		int tamanho = listaDisciplinas.size();
		Lista<String> disciplinasRemovidas = new Lista<>();
		try {
			for (int i = 0; i < tamanho; i++) {
				Disciplina disciplina = listaDisciplinas.get(i);
				if (disciplina.getCodigoCurso() == codigo) {
					disciplinasRemovidas.addLast(disciplina.getCodigo());
					listaDisciplinas.remove(i);
				}
			}
			atualizaArquivo();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		return disciplinasRemovidas;
	}

	public Disciplina consultaPorCodigo(String codigo) {
		int tamanho = listaDisciplinas.size();
		for (int i = 0; i < tamanho; i++) {
			try {
				Disciplina disciplina = listaDisciplinas.get(i);
				if (disciplina.getCodigo().equals(codigo)) {
					return disciplina;
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
			writer.write(Utilities.GetHeadersByClass(Disciplina.class) + "\n");
			int tamanho = listaDisciplinas.size();
			for (int i = 0; i < tamanho; i++) {
				writer.write(listaDisciplinas.get(i).toString()+"\n");
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
				if (!listaDisciplinas.isEmpty()) { listaDisciplinas.clean(); }
				Fila<Disciplina> fila = readerCont.mapFromCSV(Caminho, ";");
				while (!fila.isEmpty()) {
					listaDisciplinas.addLast(fila.remove());
				}
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		} else {
			throw new FileNotFoundException("Arquivo Inexistente");
		}
	}
	
	public void alteraDados(Disciplina disciplina) throws Exception {
		int tamanho = listaDisciplinas.size();
		for (int i = 0; i < tamanho; i++) {
			if (Objects.equals(disciplina.getCodigo(), listaDisciplinas.get(i).getCodigo())) {
				listaDisciplinas.get(i).setNome(disciplina.getNome());
				listaDisciplinas.get(i).setDiaSemana(disciplina.getDiaSemana());
				listaDisciplinas.get(i).setHorario(disciplina.getHorario());
				listaDisciplinas.get(i).setHorasDiarias(disciplina.getHorasDiarias());
				listaDisciplinas.get(i).setCodigoCurso(disciplina.getCodigoCurso());
				atualizaArquivo();
				break;
			}
		}
	}
	
}
