package br.edu.fateczl.edu.Interface;

import br.edu.fateczl.edu.Models.Curso;
import model.Lista;

public interface ICursosController {
    void insereCursos(Curso curso, Lista<Curso> listCurso);
    void removeProfessor(Lista<Curso> listCurso, int posicao);
}
