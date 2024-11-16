package br.edu.fateczl.ed.Controller;

import java.io.File;
import java.io.FileWriter;
import br.edu.fateczl.ed.Infrastructure.CSVReader;

import br.edu.fateczl.ed.Interface.IEntidadesController;
import br.edu.fateczl.ed.Models.Inscricao;
import model.Lista;

public class InscricoesController implements IEntidadesController<Inscricao> {
	
	CSVReader<Inscricao> readerCont = new CSVReader<>(Inscricao.class);

	Lista<Inscricao> listaInscricoes = new Lista<>();
	
	public InscricoesController(Lista<Inscricao> listaInscricoes) {
		this.listaInscricoes = listaInscricoes;
	}
	
	@Override
	public void insere(Inscricao inscricao) {
		listaInscricoes.addLast(inscricao);
	}	
	
	@Override
	public void remove(int posicao) {
		try {
			listaInscricoes.remove(posicao);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	@Override
	public void consulta() {
		int tamanho = listaInscricoes.size();
		for (int i = 0; i < tamanho; i++) {
			try {
				System.out.println("CPF: " +listaInscricoes.get(i).getCPF()+ " Código Disciplina: " +listaInscricoes.get(i).getCodigoDisciplina() + " Código Processo: " +listaInscricoes.get(i).getCodigoProcesso());
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
				File arquivo = new File (caminho, "inscricoes.csv"); //new File (caminho, nome);
				if (arquivo.exists()) {existe = true;}
				FileWriter writer = new FileWriter(arquivo, existe);
				if (existe == false) {writer.write("CPF;codigoDisciplina;codigoProcesso\n");}
				int tamanho = listaInscricoes.size();
				for (int i = 0; i < tamanho; i++) {
					writer.write(listaInscricoes.get(i).toString()+"\n");
				}
				writer.close();
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public void populaLista() throws Exception {
		String caminho = "C:\\TEMP\\inscricoes.csv";
		File dir = new File(caminho);
		if (dir.exists()) {
			try {
				listaInscricoes = readerCont.mapFromCSV("C:\\TEMP\\inscricoes.csv", ";");
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		} else {
			throw new Exception ("Arquivo Inexistente");
		}
	}
}
