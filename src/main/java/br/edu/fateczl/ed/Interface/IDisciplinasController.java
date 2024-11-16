package br.edu.fateczl.ed.Interface;

import br.edu.fateczl.ed.Models.Disciplina;

import java.io.FileNotFoundException;

public interface IDisciplinasController {
    boolean insere(Disciplina disciplina);
    void removePorCodigo(String codigo);
    void removePorCodigoCurso(int codigoCurso);
    Disciplina consultaPorCodigo(String codigo);
    void atualizaArquivo();
    void populaLista() throws FileNotFoundException;
}
