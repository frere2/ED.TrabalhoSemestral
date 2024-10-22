package br.edu.fateczl.ed.Models;

public class Inscricao {
    private String CPF;
    private int codigoDisciplina;
    private int codigoProcesso;

    public Inscricao(String cpf, int codigoDisciplina, int codigoProcesso) {
        this.CPF = cpf;
        this.codigoDisciplina = codigoDisciplina;
        this.codigoProcesso = codigoProcesso;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String cpf) {
        this.CPF = cpf;
    }

    public int getCodigoDisciplina() {
        return codigoDisciplina;
    }

    public void setCodigoDisciplina(int codigoDisciplina) {
        this.codigoDisciplina = codigoDisciplina;
    }

    public int getCodigoProcesso() {
        return codigoProcesso;
    }

    public void setCodigoProcesso(int codigoProcesso) {
        this.codigoProcesso = codigoProcesso;
    }

    @Override
    public String toString() {
        return CPF+";"+codigoDisciplina+";"+codigoProcesso;
    }
}
