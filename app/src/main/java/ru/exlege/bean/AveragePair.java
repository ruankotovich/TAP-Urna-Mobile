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
public class AveragePair {

    private String name;
    private Integer votes;

    public String getName() {
        return name;
    }

    public String getResumeName() {
        String nameC[] = name.split(" ");
        return nameC.length >= 2 ? nameC[0] + " " + nameC[1] : nameC[0];
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    @Override
    public String toString() {
        return name + " - " + votes + " votos";
    }

}
