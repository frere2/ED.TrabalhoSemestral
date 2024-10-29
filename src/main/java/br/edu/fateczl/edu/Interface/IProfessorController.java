package br.edu.fateczl.edu.Interface;

import br.edu.fateczl.edu.Models.Professor;
import model.Lista;

public interface IProfessorController {
    void insereProfessor(Professor professor, Lista<Professor> listProf);
    void removeProfessor(Lista<Professor> listProf, int posicao);
    void consultaProfessor(Lista<Professor> listProf);
    void atualizaArquivoProf(Lista<Professor> listProf);
}
