package br.edu.fateczl.ed.Controller;

import java.io.File;
import java.io.FileWriter;
import br.edu.fateczl.ed.Infrastructure.CSVReader;

import br.edu.fateczl.ed.Interface.IEntidadesController;
import br.edu.fateczl.ed.Models.Disciplina;
import model.Lista;

public class DisciplinasController implements IEntidadesController<Disciplina>{
	
	CSVReader<Disciplina> readerCont = new CSVReader<>(Disciplina.class);
	
	Lista<Disciplina> listaDisciplinas = new Lista<>();

	public DisciplinasController(Lista<Disciplina> listaDisciplinas) {
		this.listaDisciplinas = listaDisciplinas;
	}

	@Override
	public void insere(Disciplina disciplina) {
		listaDisciplinas.addLast(disciplina);		
	}

	@Override
	public void remove(int posicao) {
		try {
			listaDisciplinas.remove(posicao);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	@Override
	public void consulta() {
		int tamanho = listaDisciplinas.size();
		for (int i = 0; i < tamanho; i++) {
			try {
				System.out.println("Código: " +listaDisciplinas.get(i).getCodigo()+ " Nome: " +listaDisciplinas.get(i).getNome()+ " Dia da Semana: " +listaDisciplinas.get(i).getDiaSemana()+ " Horário: " +listaDisciplinas.get(i).getHorario() + " Horas Diárias: " +listaDisciplinas.get(i).getHorasDiarias() + " Código do Curso: " +listaDisciplinas.get(i).getCodigoCurso());
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
				File arquivo = new File (caminho, "disciplinas.csv"); //new File (caminho, nome);
				if (arquivo.exists()) {existe = true;}
				FileWriter writer = new FileWriter(arquivo, existe);
				if (existe == false) {writer.write("codigo;nome;diaSemana;horario;horasDiarias;codigoCurso\n");}
				int tamanho = listaDisciplinas.size();
				for (int i = 0; i < tamanho; i++) {
					writer.write(listaDisciplinas.get(i).toString()+"\n");
				}
				writer.close();
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void populaLista() throws Exception {
		String caminho = "C:\\TEMP\\disciplinas.csv";
		File dir = new File(caminho);
		if (dir.exists()) {
			try {
				listaDisciplinas = readerCont.mapFromCSV("C:\\TEMP\\disciplinas.csv", ";");
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		} else {
			throw new Exception ("Arquivo Inexistente");
		}
	}
}
