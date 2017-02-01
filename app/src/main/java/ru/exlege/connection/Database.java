package ru.exlege.connection;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by dmitry on 1/31/17.
 */

public class Database extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 6;
    public static final String DATABASE_NAME = "SCPUrn.db";

    private static final String SQL_CREATE_TABLES = "CREATE TABLE administratorPrivileges (adm_key TEXT NOT NULL PRIMARY KEY, adm_deadlock bit(1) NOT NULL DEFAULT b0);" +
            "CREATE TABLE `candidatos` (`can_pid` INT NOT NULL AUTO_INCREMENT PRIMARY KEY, `can_nome` TEXT NOT NULL, `can_partido` TEXT NOT NULL);" +
            "CREATE TABLE `eleitores` (`ele_titulo` LONG NOT NULL AUTO_INCREMENT PRIMARY KEY, `ele_nome` TEXT NOT NULL, `ele_dataNasc` TEXT NOT NULL);" +
            "CREATE TABLE `votos` (`vot_ele_titulo` long NOT NULL, `vot_can_pid` int NOT NULL, `vot_branco` bit NOT NULL DEFAULT b0, PRIMARY KEY (`vot_ele_titulo`,`vot_can_pid`), KEY `vot_can_pid` (`vot_can_pid`), CONSTRAINT `votos_ibfk_1` FOREIGN KEY (`vot_ele_titulo`) REFERENCES `eleitores` (`ele_titulo`), CONSTRAINT `votos_ibfk_2` FOREIGN KEY (`vot_can_pid`) REFERENCES `candidatos` (`can_pid`));";

    private static final String SQL_POPULATE_TABLES = "INSERT INTO `administratorPrivileges` (`adm_key`, `adm_deadlock`) VALUES ('123456',0); INSERT INTO `candidatos` (`can_pid`, `can_nome`, `can_partido`) VALUES ('0', 'NULO', '');";

    private static final String SQL_DELETE_TABLES = " DROP TABLE IF EXISTS administratorPrivileges; DROP TABLE IF EXISTS candidatos; DROP TABLE IF EXISTS eleitores; DROP TABLE IF EXISTS votos";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLES);
        db.execSQL(SQL_POPULATE_TABLES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLES);
        onCreate(db);
    }
}