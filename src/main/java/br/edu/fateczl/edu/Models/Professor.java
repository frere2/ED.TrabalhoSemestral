package br.edu.fateczl.edu.Models;

public class Professor {
    private String CPF;
    private String nome;
    private String area;
    private Double pontuacao;

    public Professor() {}

    public Professor(String cpf, String nome, String area, Double pontuacao) {
        this.CPF = cpf;
        this.nome = nome;
        this.area = area;
        this.pontuacao = pontuacao;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String cpf) {
        this.CPF = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Double getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Double pontuacao) {
        this.pontuacao = pontuacao;
    }

    @Override
    public String toString() {
        return CPF+";"+nome+";"+area+";"+pontuacao;
    }
}
