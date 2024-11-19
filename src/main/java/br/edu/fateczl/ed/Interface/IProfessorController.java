package br.edu.fateczl.ed.Interface;

import br.edu.fateczl.ed.Models.Professor;

import java.io.FileNotFoundException;

public interface IProfessorController {
    boolean insere(Professor professor);
    void removePorCPF(String cpf);
    Professor consultaPorCPF(String cpf);
    void atualizaArquivo();
    void populaLista() throws FileNotFoundException;
}
