package br.edu.fateczl.ed.Models;

import br.edu.fateczl.ed.Enums.EDiaSemana;

public class Disciplina {
    private String codigo;
    private String nome;
    private String horario;
    private String horasDiarias;
    private EDiaSemana diaSemana;
    private int codigoCurso;

    public Disciplina(String codigo, String nome, EDiaSemana diaSemana, String horario, String horasDiarias, int codigoCurso) {
        this.codigo = codigo;
        this.nome = nome;
        this.diaSemana = diaSemana;
        this.horario = horario;
        this.horasDiarias = horasDiarias;
        this.codigoCurso = codigoCurso;
    }
    
    public Disciplina() {
    	this("","", EDiaSemana.Segunda,"","",0);
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public EDiaSemana getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(EDiaSemana diaSemana) {
        this.diaSemana = diaSemana;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getHorasDiarias() {
        return horasDiarias;
    }

    public void setHorasDiarias(String horasDiarias) {
        this.horasDiarias = horasDiarias;
    }

    public int getCodigoCurso() {
        return codigoCurso;
    }

    public void setCodigoCurso(int codigoCurso) {
        this.codigoCurso = codigoCurso;
    }

    @Override
    public String toString() {
        return codigo+";"+nome+";"+horario+";"+horasDiarias+";"+diaSemana.toString()+";"+codigoCurso;
    }
    
}
