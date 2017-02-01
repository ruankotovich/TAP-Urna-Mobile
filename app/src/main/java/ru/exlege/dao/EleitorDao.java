/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.exlege.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import ru.exlege.bean.Eleitor;
import ru.exlege.connection.Database;
import ru.exlege.pojo.Validable;

/**
 * @author dmitry
 */
public class EleitorDao {

    private SQLiteDatabase database;

    public EleitorDao(Context context) {
        database = new Database(context).getWritableDatabase();
    }

    public boolean cadastrar(Validable<Eleitor> eleitorVal) {
        Eleitor eleitor = eleitorVal.validate();
        try {
            String query = "INSERT INTO `eleitores`(`ele_titulo`,`ele_nome`)VALUES(" + eleitor.getTitulo() + ",'" + eleitor.getNome() + "');";
            database.execSQL(query);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public Eleitor consultar(long titulo) {
        Eleitor eleitor;

        String query = "SELECT ele_nome, ele_titulo FROM `eleitores` where `ele_titulo`=" + titulo;
        Cursor c = database.rawQuery(query, null);
        if (c.moveToNext()) {
            eleitor = new Eleitor(c.getLong(1), c.getString(0));
            return eleitor;
        } else {
            return null;
        }
    }

    public boolean deletar(long titulo) {
        try {
            String query = "DELETE FROM `eleitores` where `ele_titulo`=" + titulo;
            database.execSQL(query);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean alterar(long titulo, Eleitor ele) {
        ele = ele.validate();
        if (ele != null) {
            try {
                String query = "update eleitores set ele_nome="+ele.getNome()+",ele_titulo="+ele.getTitulo()+" where ele_titulo="+titulo;
                database.execSQL(query);
                return true;
            } catch (Exception ex) {
                return false;
            }
        } else {
            return false;
        }
    }
}
