package br.edu.fateczl.ed.Interface;

import br.edu.fateczl.ed.Models.Inscricao;

import java.io.FileNotFoundException;

public interface IInscricoesController {
    boolean insere(Inscricao inscricao);
    void removePorCPF(String cpf);
    void removePorDisciplina(String disciplina);
    Inscricao consultaPorCPF(String cpf);
    void atualizaArquivo();
    void populaLista() throws FileNotFoundException;
}
