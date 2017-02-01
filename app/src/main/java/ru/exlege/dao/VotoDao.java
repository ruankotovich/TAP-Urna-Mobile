/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.exlege.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.exlege.model.bean.AveragePair;
import ru.exlege.model.pojo.Analytics;
import ru.exlege.model.pojo.ConnectionFactory;

/**
 *
 * @author dmitry
 */
public class VotoDao {

    private static PreparedStatement stmt;
    private static Connection con;
    private static ResultSet rs;

    public static boolean verifyAble(long titulo) {
        con = ConnectionFactory.openPersistentConnection(10000);
        try {
            stmt = con.prepareStatement("select * from votos where vot_ele_titulo = ?");
            stmt.setLong(1, titulo);
            rs = stmt.executeQuery();
            if (rs.next()) {
                con.close();
                ConnectionFactory.notifyThreadDeleted();
                return false;
            } else {
                con.close();
                ConnectionFactory.notifyThreadDeleted();
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(VotoDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static boolean commitVote(long titulo, int pid, boolean branco) {
        con = ConnectionFactory.openPersistentConnection(10000);
        try {
            stmt = con.prepareStatement("insert into votos (vot_ele_titulo, vot_can_pid,vot_branco) values (?,?,?)");
            stmt.setLong(1, titulo);
            stmt.setInt(2, pid);
            stmt.setBoolean(3, branco);
            stmt.execute();
            ConnectionFactory.notifyThreadDeleted();
            con.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(VotoDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static Analytics getAnalytcs() {
        Analytics analytics = null;
        ArrayList<AveragePair> arraylist = new ArrayList<>();
        AveragePair current = null;
        int count = 0;
        con = ConnectionFactory.openPersistentConnection(10000);
        try {
            stmt = con.prepareStatement("select can_nome, count(vot_can_pid) as c from candidatos left join votos on can_pid = vot_can_pid group by can_pid order by c desc, can_nome;");
            rs = stmt.executeQuery();

            while (rs.next()) {
                current = new AveragePair();
                current.setName(rs.getString("can_nome"));
                current.setVotes(rs.getInt("c"));
                arraylist.add(current);
                if (current.getVotes() != 0) {
                    count += current.getVotes();
                }
            }

            analytics = new Analytics(arraylist, count);
            ConnectionFactory.notifyThreadDeleted();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(VotoDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return analytics;
    }
}
