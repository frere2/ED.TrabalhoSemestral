package br.edu.fateczl.ed.Interface;

import br.edu.fateczl.ed.Models.Curso;
import model.Lista;

public interface ICursosController {
    void insereCursos(Curso curso, Lista<Curso> listCurso);
    void removeProfessor(Lista<Curso> listCurso, int posicao);
}
