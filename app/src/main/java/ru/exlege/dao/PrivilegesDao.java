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

import ru.exlege.connection.Database;

/**
 * @author dmitry
 */
public class PrivilegesDao {

    private SQLiteDatabase database;

    public PrivilegesDao(Context context) {
        this.database = new Database(context).getWritableDatabase();
    }

    public boolean verifyPrivileges(String password) {

        String query = "SELECT * FROM administratorPrivileges WHERE adm_key = '"+password+"'";
        Cursor cursor = database.rawQuery(query, null);

        boolean ret = cursor.moveToNext();

        cursor.close();
        return ret;
    }

    public void listSQLiteTables(){
        String query = "SELECT * FROM sqlite_master where type='table'";

        Cursor c = database.rawQuery(query, null);

        while(c.moveToNext()){
            Log.e("SQLite Tables", c.getString(0) + " | " + c.getString(1));
        }
    }

    public boolean verifyDeadlock() {
        String query = ("SELECT * FROM administratorPrivileges WHERE adm_deadlock=1;");
        if(database.rawQuery(query, null).moveToNext()){
            return true;
        }else {
            return false;
        }
    }

    public boolean revokeDeadlock() {
        String query = ("UPDATE administratorPrivileges set adm_deadlock=0;");
        database.execSQL(query);
        return false;
    }

    public boolean doDeadlock(String password) {
        String query = "UPDATE administratorPrivileges set adm_deadlock=1 where adm_key = '"+password+"'";
        database.execSQL(query);
        return false;
    }
}
