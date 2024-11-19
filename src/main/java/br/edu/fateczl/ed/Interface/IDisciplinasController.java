package br.edu.fateczl.ed.Interface;

import br.edu.fateczl.ed.Models.Disciplina;
import br.edu.fateczl.lista.Lista;

import java.io.FileNotFoundException;

public interface IDisciplinasController {
    boolean insere(Disciplina disciplina);
    void removePorCodigo(String codigo);
    Lista<String> removePorCodigoCurso(int codigoCurso);
    Disciplina consultaPorCodigo(String codigo);
    void atualizaArquivo();
    void populaLista() throws FileNotFoundException;
}
