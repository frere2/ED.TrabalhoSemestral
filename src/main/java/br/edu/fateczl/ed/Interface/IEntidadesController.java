package br.edu.fateczl.ed.Interface;

public interface IEntidadesController<T> {
	void insere(T objeto);
	void remove(int posicao);
	void consulta();
	void atualizaArquivo(String caminho);
}
