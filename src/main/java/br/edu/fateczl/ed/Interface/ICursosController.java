package br.edu.fateczl.ed.Interface;

import br.edu.fateczl.ed.Models.Curso;

import java.io.FileNotFoundException;

public interface ICursosController {
    boolean insere(Curso curso);
    void removePorCodigo(int codigo);
    Curso consultaPorCodigo(int codigo);
    void atualizaArquivo();
    void populaLista() throws FileNotFoundException;
}
