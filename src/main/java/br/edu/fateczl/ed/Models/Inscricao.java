package br.edu.fateczl.ed.Models;

public class Inscricao {
    private String cpf;
    private int codigoDisciplina;
    private int codigoProcesso;

    public Inscricao(String cpf, int codigoDisciplina, int codigoProcesso) {
        this.cpf = cpf;
        this.codigoDisciplina = codigoDisciplina;
        this.codigoProcesso = codigoProcesso;
    }
    
    public Inscricao() {
    	this("",0,0);
    }

    public String getCPF() {
        return cpf;
    }

    public void setCPF(String cpf) {
        this.cpf = cpf;
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
        return cpf+";"+codigoDisciplina+";"+codigoProcesso;
    }
}
