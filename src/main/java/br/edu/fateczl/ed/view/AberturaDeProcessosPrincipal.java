package br.edu.fateczl.ed.view;

import br.edu.fateczl.ed.Controller.CursosController;
import br.edu.fateczl.ed.Controller.HashController;
import br.edu.fateczl.ed.Models.Curso;
import model.Lista;

public class AberturaDeProcessosPrincipal {

	public static void main(String[] args){
		
		//Chamar CursoController diretamente está funcionando. Chamar o CursoController pelo Hash não está funcionando.
		//Há a possibilidade de mexer em tudo pelo HashController, pois temos que usar o Hashtable nesse trabalho
		
		
		//CursosController
		/*Lista<Curso> listacurso = new Lista<>();
		CursosController cursCont = new CursosController(listacurso);
		
		Curso curso1 = new Curso(001, "batata", "legumes");
		Curso curso2 = new Curso(002, "banana", "frutas");
		Curso curso3 = new Curso(003, "alface", "verduras");
		Curso curso4 = new Curso(004, "feijão", "grãos");
		Curso curso5 = new Curso(005, "beringela", "legumes");
		
		cursCont.populaLista();
		
		cursCont.insere(curso5);
		cursCont.insere(curso1);
		cursCont.insere(curso2);
		cursCont.insere(curso3);
		cursCont.insere(curso4);
		
		cursCont.consulta();
		
		cursCont.atualizaArquivo("C:\\TEMP\\");*/
		
		//HashController
		/*HashController hashCont = new HashController();
		
		Curso curso1 = new Curso(001, "batata", "legumes");
		Curso curso2 = new Curso(002, "banana", "frutas");
		Curso curso3 = new Curso(003, "alface", "verduras");
		Curso curso4 = new Curso(004, "feijão", "grãos");
		Curso curso5 = new Curso(005, "beringela", "legumes");
		
		hashCont.inserir(0, curso1);
		hashCont.inserir(0, curso2);
		hashCont.inserir(0, curso3);
		hashCont.inserir(0, curso4);
		hashCont.inserir(0, curso5);
		
		hashCont.populaLista(0);
		hashCont.consulta(0);
		hashCont.remove(0, 4);
		hashCont.remove(0, 3);
		hashCont.remove(0, 2);
		hashCont.remove(0, 1);
		hashCont.remove(0, 0);
		System.out.println();
		hashCont.consulta(0);
		hashCont.inserir(0, curso5);
		System.out.println();
		hashCont.consulta(0);
		hashCont.atualizaArquivo(0);*/
	}
}
