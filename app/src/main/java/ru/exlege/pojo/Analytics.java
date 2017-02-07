/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.exlege.pojo;

import java.util.ArrayList;

import ru.exlege.bean.AveragePair;

/**
 *
 * @author dmitry
 */
public class Analytics {

    private final ArrayList<AveragePair> pair;
    private final int total;

    public Analytics(ArrayList<AveragePair> pair, int total) {
        this.pair = pair;
        this.total = total;
    }

    public ArrayList<AveragePair> getPair() {
        return pair;
    }

    public int getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return total+"";
    }
}
