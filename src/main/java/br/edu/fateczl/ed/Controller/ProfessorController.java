package br.edu.fateczl.ed.Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;

import br.edu.fateczl.ed.Infrastructure.CSVReader;
import br.edu.fateczl.ed.Infrastructure.ConfigReader;
import br.edu.fateczl.ed.Interface.IProfessorController;
import br.edu.fateczl.ed.Models.Disciplina;
import br.edu.fateczl.ed.Models.Professor;
import br.edu.fateczl.ed.Utils.Utilities;
import br.edu.fateczl.fila.Fila;
import br.edu.fateczl.lista.Lista;

public class ProfessorController implements IProfessorController {
	private CSVReader<Professor> readerCont = new CSVReader<>(Professor.class);
	private Lista<Professor> listaProfessores;
	private ConfigReader configReader = new ConfigReader();
	private final String Caminho = configReader.getFullPath("professor.csv");
	
	public ProfessorController(Lista<Professor> listaProfessores) {
		this.listaProfessores = listaProfessores;
	}

	public boolean insere(Professor professor) {
		int tamanho = listaProfessores.size();
		try {
			for (int i = 0; i < tamanho; i++) {
				if (listaProfessores.get(i).getCPF().equals(professor.getCPF())) {
					return false;
				}
			}
		} catch (Exception e) {
			System.err.println("Erro ao inserir professor");
		}
		listaProfessores.addLast(professor);
		atualizaArquivo();
		return true;
	}
	
	public void removePorCPF(String cpf) {
		try {
			int tamanho = listaProfessores.size();
			for (int i = 0; i < tamanho; i++) {
				Professor professor = listaProfessores.get(i);
				if (professor.getCPF().equals(cpf)) {
					listaProfessores.remove(i);
				}
			}
			atualizaArquivo();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	public Professor consultaPorCPF(String cpf) {
		int tamanho = listaProfessores.size();
		for (int i = 0; i < tamanho; i++) {
			try {
				Professor professor = listaProfessores.get(i);
				if (professor.getCPF().equals(cpf)) {
					return professor;
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
			writer.write(Utilities.GetHeadersByClass(Professor.class) + "\n");
			int tamanho = listaProfessores.size();
			for (int i = 0; i < tamanho; i++) {
				writer.write(listaProfessores.get(i).toString()+"\n");
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
				if (!listaProfessores.isEmpty()) { listaProfessores.clean(); }
				Fila<Professor> fila = readerCont.mapFromCSV(Caminho, ";");
				while (!fila.isEmpty()) {
					listaProfessores.addLast(fila.remove());
				}
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		} else {
			throw new FileNotFoundException("Arquivo Inexistente");
		}
	}
	
	public void alteraDados(Professor professor) throws Exception {
		int tamanho = listaProfessores.size();
		for (int i = 0; i < tamanho; i++) {
			if (professor.getCPF() == listaProfessores.get(i).getCPF()) {
				listaProfessores.get(i).setNome(professor.getNome());
				listaProfessores.get(i).setArea(professor.getArea());
				listaProfessores.get(i).setPontuacao(professor.getPontuacao());
				atualizaArquivo();
				break;
			}
		}
	}
	
}
