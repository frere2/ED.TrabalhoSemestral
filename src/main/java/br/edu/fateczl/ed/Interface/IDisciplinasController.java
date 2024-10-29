package br.edu.fateczl.ed.Interface;

import br.edu.fateczl.ed.Models.Disciplina;

public interface IDisciplinasController {
	void insereDisciplina(Disciplina disciplina);
    void removeDisciplina(int posicao);
    void consultaDisciplinas();
    void atualizaArquivoDisc();
}
