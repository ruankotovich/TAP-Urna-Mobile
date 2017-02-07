/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.exlege.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import ru.exlege.bean.Candidato;
import ru.exlege.connection.Database;
import ru.exlege.pojo.Validable;

/**
 *
 * @author dmitry
 */
public class CandidatoDao {


    private SQLiteDatabase database;

    public CandidatoDao(Context context) {
        this.database = new Database(context).getWritableDatabase();
    }

    public boolean cadastrar(Validable<Candidato> candidatoVal) {
        Candidato can = candidatoVal.validate();
        if(can != null) {
            try {
                String query = "INSERT INTO candidatos (can_pid, can_nome, can_partido) " +
                        "VALUES (" + can.getPid() + ",'" + can.getNome() + "','" + can.getPartido() + "')";
                database.execSQL(query);
                return true;
            }catch(Exception ex){
                return false;
            }

        }else {
            return false;
        }
    }

    public Candidato consultar(int pid) {
        Candidato can = null;

        String query = "SELECT can_pid, can_nome, can_partido FROM candidatos where can_pid="+pid;
        Cursor c = database.rawQuery(query, null);

        if(c.moveToNext()){
            can = new Candidato(c.getInt(0), c.getString(1), c.getString(2));
        }

        return can;
    }

    public boolean deletar(int pid) {

        try {
            String query = "DELETE FROM candidatos WHERE can_pid = "+pid;
            database.execSQL(query);
            return true;
        }catch(Exception ex){
            return false;
        }
    }

    public boolean alterar(int pid, Candidato candidato) {
        try {
            String query = "UPDATE candidatos " +
                    "set can_pid="+candidato.getPid()+", " +
                    "can_nome='"+candidato.getNome()+"', " +
                    "can_partido='"+candidato.getPartido()+"'"+
                    " where can_pid="+pid;

            database.execSQL(query);
            return true;
        }catch(Exception ex){
            return false;
        }
    }

    public ArrayList<Candidato> candidatos() {
        ArrayList<Candidato> arrayList = new ArrayList<>();

            String query = "SELECT can_pid, can_nome, can_partido FROM candidatos WHERE can_pid > 0 order by can_nome asc,can_partido asc";
            Cursor cur = database.rawQuery(query, null);

            while(cur.moveToNext()){
                Candidato c = new Candidato(cur.getInt(0));
                c.setNome(cur.getString(1));
                c.setPartido(cur.getString(2));
                arrayList.add(c);
            }

        return arrayList;
    }

    public ArrayList<Candidato> candidatosQuery(String query) {

        ArrayList<Candidato> arrayList = new ArrayList<>();

        String quer = ("SELECT can_pid, can_nome, can_partido FROM candidatos WHERE can_pid > 0 AND (can_pid like ? OR can_nome like ? OR can_partido like ?) order by can_nome asc,can_partido asc");
        Cursor cur = database.rawQuery(quer, null);

        while(cur.moveToNext()){
            Candidato c = new Candidato(cur.getInt(0));
            c.setNome(cur.getString(1));
            c.setPartido(cur.getString(2));
            arrayList.add(c);
        }

        return arrayList;
    }

    public Candidato getWhite() {
        Candidato can = null;
        int last=-1;

        Cursor cur = database.rawQuery("select vot_can_pid, count(*) as c from votos group by vot_can_pid order by c desc;", null);

        while(cur.moveToNext()){
            if(last<0){
                last = cur.getInt(1);
                can = new Candidato(cur.getInt(0));
            }else{
                if(last > cur.getInt(1)){
                    return can;
                }else{
                    return null;
                }
            }
        }

        return can;
    }
}