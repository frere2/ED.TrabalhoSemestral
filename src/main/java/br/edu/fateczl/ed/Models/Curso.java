package br.edu.fateczl.ed.Models;

public class Curso {
    private int codigo;
    private String nome;
    private String area;

    public Curso(int codigo, String nome, String area) {
        this.codigo = codigo;
        this.nome = nome;
        this.area = area;
    }
    
    public Curso() {
    	this(0,"","");
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
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

    @Override
    public String toString() {
        return "Curso{" + "codigo=" + codigo + ", nome=" + nome + ", area=" + area + '}';
    }
}
