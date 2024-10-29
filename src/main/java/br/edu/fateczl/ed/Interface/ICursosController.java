package br.edu.fateczl.ed.Interface;

import br.edu.fateczl.ed.Models.Curso;
import model.Lista;

public interface ICursosController {
    void insereCurso(Curso curso);
    void removeCurso(int posicao);
    void consultaCursos();
    void atualizaArquivoCurso();
}
