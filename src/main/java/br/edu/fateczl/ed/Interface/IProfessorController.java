package br.edu.fateczl.ed.Interface;

import br.edu.fateczl.ed.Models.Professor;
import model.Lista;

public interface IProfessorController {
    void insereProfessor(Professor professor);
    void removeProfessor(int posicao);
    void consultaProfessores();
    void atualizaArquivoProf();
}
