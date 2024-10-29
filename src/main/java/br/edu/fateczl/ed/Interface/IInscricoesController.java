package br.edu.fateczl.ed.Interface;

import br.edu.fateczl.ed.Models.Inscricao;

public interface IInscricoesController {
	void insereInscricao(Inscricao inscricao);
    void removeInscricao(int posicao);
    void consultaInscricoes();
    void atualizaArquivoInsc();
}
