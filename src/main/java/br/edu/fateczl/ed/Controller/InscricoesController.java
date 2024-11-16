package br.edu.fateczl.ed.Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import br.edu.fateczl.ed.Infrastructure.CSVReader;
import br.edu.fateczl.ed.Infrastructure.ConfigReader;
import br.edu.fateczl.ed.Interface.IInscricoesController;
import br.edu.fateczl.ed.Models.Inscricao;
import br.edu.fateczl.ed.Utils.Utilities;
import br.edu.fateczl.fila.Fila;
import br.edu.fateczl.lista.Lista;

public class InscricoesController implements IInscricoesController {
	private CSVReader<Inscricao> readerCont = new CSVReader<>(Inscricao.class);
	private Lista<Inscricao> listaInscricoes;
	private ConfigReader configReader = new ConfigReader();
	private final String Caminho = configReader.getFullPath("inscricoes.csv");
	
	public InscricoesController(Lista<Inscricao> listaInscricoes) {
		this.listaInscricoes = listaInscricoes;
	}
	
	public boolean insere(Inscricao inscricao) {
		int tamanho = listaInscricoes.size();
		try {
			for (int i = 0; i < tamanho; i++) {
				if (listaInscricoes.get(i).getCPF().equals(inscricao.getCPF()))
					return false;
			}
		} catch (Exception e) {
			System.err.println("Erro ao inserir inscrição");
		}
		listaInscricoes.addLast(inscricao);
		atualizaArquivo();
		return true;
	}
	
	public void removePorCPF(String cpf) {
		try {
			int tamanho = listaInscricoes.size();
			for (int i = 0; i < tamanho; i++) {
				Inscricao inscricao = listaInscricoes.get(i);
				if (inscricao.getCPF().equals(cpf)) {
					listaInscricoes.remove(i);
				}
			}
			atualizaArquivo();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public Inscricao consultaPorCPF(String cpf) {
		int tamanho = listaInscricoes.size();
		for (int i = 0; i < tamanho; i++) {
			try {
				Inscricao inscricao = listaInscricoes.get(i);
				if (inscricao.getCPF().equals(cpf)) {
					return inscricao;
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
			writer.write(Utilities.GetHeadersByClass(Inscricao.class) + "\n");
			int tamanho = listaInscricoes.size();
			for (int i = 0; i < tamanho; i++) {
				writer.write(listaInscricoes.get(i).toString()+"\n");
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
				if (!listaInscricoes.isEmpty()) { listaInscricoes.clean(); }
				Fila<Inscricao> fila = readerCont.mapFromCSV(Caminho, ";");
				while (!fila.isEmpty()) {
					listaInscricoes.addLast(fila.remove());
				}
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		} else {
			throw new FileNotFoundException("Arquivo Inexistente");
		}
	}
}
