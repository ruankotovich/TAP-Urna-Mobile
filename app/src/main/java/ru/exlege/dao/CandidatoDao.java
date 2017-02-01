/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.exlege.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import ru.exlege.bean.Candidato;
import ru.exlege.pojo.Validable;

/**
 *
 * @author dmitry
 */
public class CandidatoDao {

    private static PreparedStatement stmt;
    private static Connection con;
    private static ResultSet rs;

    public static boolean cadastrar(Validable<Candidato> candidatoVal) {
        String query = "INSERT INTO `candidatos` (`can_pid`, `can_nome`, `can_partido`, `can_foto`) VALUES (?,?,?,?)";
        return false;
    }

    public static Candidato consultar(int pid) {
        String query = "SELECT * FROM `candidatos` where `can_pid`=?;";
        return null;
    }

    public static boolean deletar(int pid) {
        String query = "DELETE FROM `candidatos` WHERE can_pid = ?";
        return false;
    }

    public static boolean alterar(int pid, Candidato candidato) {
        String query = "UPDATE `candidatos` set can_pid=?, can_nome=?, can_partido=?, can_foto=? where can_pid=?";
        return false;
    }

    public static ArrayList<Candidato> candidatos() {
        String query = "SELECT * FROM `candidatos` WHERE can_pid > 0 order by can_nome asc,can_partido asc";
        return null;
    }

    public static ArrayList<Candidato> candidatosQuery(String query) {

        String quer = ("SELECT * FROM `candidatos` WHERE can_pid > 0 AND (can_pid like ? OR can_nome like ? OR can_partido like ?) order by can_nome asc,can_partido asc");
        return null;
    }

    public static Candidato getWhite() {

        String query = ("select vot_can_pid, count(*) as c from votos group by vot_can_pid order by c desc;");
        return null;
    }
}