package br.edu.fateczl.ed.Interface;

import br.edu.fateczl.ed.Models.Professor;
import model.Lista;

public interface IProfessorController {
    void insereProfessor(Professor professor, Lista<Professor> listProf);
    void removeProfessor(Lista<Professor> listProf, int posicao);
    void consultaProfessor(Lista<Professor> listProf);
    void atualizaArquivoProf(Lista<Professor> listProf);
}
