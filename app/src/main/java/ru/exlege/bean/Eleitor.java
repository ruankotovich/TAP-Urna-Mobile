/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.exlege.bean;


import ru.exlege.pojo.Validable;

/**
 *
 * @author dmitry
 */
public class Eleitor implements Validable<Eleitor> {

    private long titulo;
    private String nome;
    private String nascimento;

    public Eleitor(long titulo) {
        this.titulo = titulo;
    }

    public Eleitor(long titulo, String nome, String nascimento) {
        this.titulo = titulo;
        this.nome = nome;
        this.nascimento = nascimento;
    }

    public long getTitulo() {
        return titulo;
    }

    public void setTitulo(long titulo) {
        this.titulo = titulo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNascimento() {
        return nascimento;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }

    @Override
    public Eleitor validate() {
        if (nome.isEmpty() || nascimento.isEmpty() || titulo <= 0) {
            return null;
        } else {
            return this;
        }
    }

}
