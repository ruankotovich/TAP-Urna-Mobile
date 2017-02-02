/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.exlege.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import ru.exlege.bean.AveragePair;
import ru.exlege.connection.Database;
import ru.exlege.pojo.Analytics;

/**
 *
 * @author dmitry
 */
public class VotoDao {

    private SQLiteDatabase database;

    public VotoDao(Context context) {
        this.database = new Database(context).getWritableDatabase();
    }


    public  boolean verifyAble(long titulo) {
        String query = "select * from votos where vot_ele_titulo = "+titulo;
        Cursor c = database.rawQuery(query, null);
        return !c.moveToNext();
    }

    public boolean commitVote(long titulo, int pid, boolean branco) {
        try{
            String query = "insert into votos (vot_ele_titulo, vot_can_pid,vot_branco) values ("+titulo+","+pid+","+branco+")";
            database.execSQL(query);
            return true;
        }catch(Exception ex){
            return false;
        }
    }

    public Analytics getAnalytcs() {
        Analytics analytics = null;
        ArrayList<AveragePair> arraylist = new ArrayList<>();
        AveragePair current = null;
        int count = 0;

        String query = "select can_nome, count(vot_can_pid) as c from candidatos left join votos on can_pid = vot_can_pid group by can_pid order by c desc, can_nome;";
        Cursor c = database.rawQuery(query, null);

            while (c.moveToNext()) {
                current = new AveragePair();
                current.setName(c.getString(0));
                current.setVotes(c.getInt(1));
                arraylist.add(current);
                if (current.getVotes() != 0) {
                    count += current.getVotes();
                }
            }

        analytics = new Analytics(arraylist, count);

        return analytics;
    }
}
