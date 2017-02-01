/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.exlege.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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

    public boolean verifyDeadlock() {
        String query = ("SELECT * FROM administratorPrivileges WHERE adm_deadlock=b'1';");
        return false;
    }

    public boolean revokeDeadlock() {
        String query = ("UPDATE administratorPrivileges set adm_deadlock=b'0';");
        return false;
    }

    public boolean doDeadlock(String password) {
        String query = "UPDATE administratorPrivileges set adm_deadlock=b'1' where adm_key = md5(?);";
        return false;
    }
}
