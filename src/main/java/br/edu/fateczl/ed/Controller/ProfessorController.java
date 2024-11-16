package br.edu.fateczl.ed.Controller;

import java.io.File;
import java.io.FileWriter;

import br.edu.fateczl.ed.Infrastructure.CSVReader;
import br.edu.fateczl.ed.Interface.IEntidadesController;
import br.edu.fateczl.ed.Models.Professor;
import model.Lista;

public class ProfessorController implements IEntidadesController<Professor> {

	CSVReader<Professor> readerCont = new CSVReader<>(Professor.class);
	
	Lista<Professor> listaProfessores = new Lista<>();
	
	public ProfessorController(Lista<Professor> listaProfessores) {
		this.listaProfessores = listaProfessores;
	}

	@Override
	public void insere(Professor professor) {
		listaProfessores.addLast(professor);
	}
	
	@Override
	public void remove(int posicao) {
		try {
			listaProfessores.remove(posicao);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	@Override
	public void consulta() {
		int tamanho = listaProfessores.size();
		for (int i = 0; i < tamanho; i++) {
			try {
				System.out.println("CPF: " +listaProfessores.get(i).getCPF()+ " Nome: " +listaProfessores.get(i).getNome()+ " Área: " +listaProfessores.get(i).getArea()+ " Pontuação: " +listaProfessores.get(i).getPontuacao());
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
	}
	
	@Override
	public void atualizaArquivo(String caminho) {
		//Caminho de teste de arquivo: "C:" + File.separator + "TEMP" + File.separator + "disciplinas.csv"
		//path	/ED.TrabalhoSemestral/src/main/java/br/edu/fateczl/ed/Repository/disciplinas.csv
		try {
			File dir = new File(caminho);
			if (dir.exists() && dir.isDirectory()) {
				boolean existe = false;
				File arquivo = new File (caminho, "professores.csv"); //new File (caminho, nome);
				if (arquivo.exists()) {existe = true;}
				FileWriter writer = new FileWriter(arquivo, existe);
				if (existe == false) {writer.write("CPF;nome;area;pontuacao\n");}
				int tamanho = listaProfessores.size();
				for (int i = 0; i < tamanho; i++) {
					writer.write(listaProfessores.get(i).toString()+"\n");
				}
				writer.close();
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void populaLista() throws Exception {
		String caminho = "C:\\\\TEMP\\\\professores.csv";
		File dir = new File(caminho);
		if (dir.exists()) {
			try {
				listaProfessores = readerCont.mapFromCSV(caminho, ";");
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		} else {
			throw new Exception ("Arquivo Inexistente");
		}
	}
}
