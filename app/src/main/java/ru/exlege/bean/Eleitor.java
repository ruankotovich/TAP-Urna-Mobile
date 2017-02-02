/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.exlege.bean;


import java.io.Serializable;

import ru.exlege.pojo.Validable;

/**
 *
 * @author dmitry
 */
public class Eleitor implements Validable<Eleitor>, Serializable {

    private long titulo;
    private String nome;

    public Eleitor(long titulo) {
        this.titulo = titulo;
    }

    public Eleitor(long titulo, String nome) {
        this.titulo = titulo;
        this.nome = nome;
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

    @Override
    public Eleitor validate() {
        if (nome.isEmpty() || titulo <= 0) {
            return null;
        } else {
            return this;
        }
    }

}
