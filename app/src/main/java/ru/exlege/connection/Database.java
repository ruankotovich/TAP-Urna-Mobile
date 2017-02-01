package ru.exlege.connection;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by dmitry on 1/31/17.
 */

public class Database extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 11;
    public static final String DATABASE_NAME = "SCPUrn.db";

    private static final String SQL_CREATE_AP = "CREATE TABLE administratorPrivileges(adm_key TEXT NOT NULL PRIMARY KEY, adm_deadlock bit(1) NOT NULL DEFAULT b0);";
    private static final String SQL_CREATE_CD = "CREATE TABLE candidatos(can_pid INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, can_nome TEXT NOT NULL, can_partido TEXT NOT NULL);";
    private static final String SQL_CREATE_ET = "CREATE TABLE eleitores(ele_titulo LONG NOT NULL PRIMARY KEY, ele_nome TEXT NOT NULL);";
    private static final String SQL_CREATE_VT = "CREATE TABLE votos(vot_ele_titulo LONG NOT NULL, vot_can_pid INTEGER NOT NULL, vot_branco bit(1) NOT NULL DEFAULT b0, PRIMARY KEY (vot_ele_titulo,vot_can_pid), FOREIGN KEY (vot_ele_titulo) REFERENCES eleitores(ele_titulo), FOREIGN KEY (vot_can_pid) REFERENCES candidatos (can_pid));";

    private static final String SQL_POPULATE_AP = "INSERT INTO administratorPrivileges (adm_key, adm_deadlock) VALUES ('123456',0);";
    private static final String SQL_POPULATE_CD = "INSERT INTO candidatos (can_pid, can_nome, can_partido) VALUES ('0', 'NULO', '');";

    private static final String SQL_DELETE_AP = " DROP TABLE IF EXISTS administratorPrivileges;";
    private static final String SQL_DELETE_CD = "DROP TABLE IF EXISTS candidatos;";
    private static final String SQL_DELETE_ET = "DROP TABLE IF EXISTS eleitores;";
    private static final String SQL_DELETE_VT = "DROP TABLE IF EXISTS votos";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        Log.e("SQLite", "Creating tables");
        db.execSQL(SQL_CREATE_AP);
        db.execSQL(SQL_CREATE_CD);
        db.execSQL(SQL_CREATE_ET);
        db.execSQL(SQL_CREATE_VT);

        db.execSQL(SQL_POPULATE_AP);
        db.execSQL(SQL_POPULATE_CD);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e("SQLite", "Updating tables");

        db.execSQL(SQL_DELETE_AP);
        db.execSQL(SQL_DELETE_CD);
        db.execSQL(SQL_DELETE_ET);
        db.execSQL(SQL_DELETE_VT);

        onCreate(db);
    }
}