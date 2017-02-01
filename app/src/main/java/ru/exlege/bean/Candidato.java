/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.exlege.bean;

import java.sql.Blob;

import ru.exlege.pojo.Validable;


/**
 *
 * @author dmitry
 */
public class Candidato implements Validable<Candidato> {

    private int pid;
    private String nome;
    private String partido;

    public Candidato(int pid) {
        this.pid = pid;
    }

    public Candidato(int pid, String nome, String partido) {
        this.pid = pid;
        this.nome = nome;
        this.partido = partido;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPartido() {
        return partido;
    }

    public void setPartido(String partido) {
        this.partido = partido;
    }

    @Override
    public Candidato validate() {
        if (pid <= 0 || nome.isEmpty() || partido.isEmpty()) {
            return null;
        } else {
            return this;
        }
    }

    @Override
    public String toString() {
        return nome + "(" + pid + ") do " + partido;
    }

}
