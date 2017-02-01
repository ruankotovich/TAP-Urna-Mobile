/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.exlege.bean;

/**
 *
 * @author dmitry
 */
public class Voto {

    private Eleitor eleitor;
    private Candidato candidato;

    public Voto(long eleitor, int candidato) {
        this.eleitor = new Eleitor(eleitor);
        this.candidato = new Candidato(candidato);
    }

    public Eleitor getEleitor() {
        return eleitor;
    }

    public void setEleitor(Eleitor eleitor) {
        this.eleitor = eleitor;
    }

    public Candidato getCandidato() {
        return candidato;
    }

    public void setCandidato(Candidato candidato) {
        this.candidato = candidato;
    }

}
