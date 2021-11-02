/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datnt.tbl_ArticleStatus;

import datnt.ultis.DBHelper;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author NTD
 */
public class Tbl_ArticleStatus_DAO implements Serializable {

    private List<Tbl_ArticleStatus_DTO> listArticleStus;

    public List<Tbl_ArticleStatus_DTO> getListArticleStus() {
        return listArticleStus;
    }

    public void getStatus() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "Select Id, Name "
                        + "From tbl_ArticleStatus ";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String name = rs.getString(2);
                    Tbl_ArticleStatus_DTO dto = new Tbl_ArticleStatus_DTO(id, name);
                    if (this.listArticleStus == null) {
                        this.listArticleStus = new ArrayList<>();

                    }
                    this.listArticleStus.add(dto);
                }
            }

        } finally {
            if (con != null) {
                con.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
    }

    public int getStatusID(String status) throws SQLException, NamingException {

        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "SELECT Id "
                        + "FROM tbl_ArticleStatus "
                        + "WHERE Name = ? ";
                stm = con.prepareStatement(sql);
                stm.setString(1, status);

                rs = stm.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return 0;
    }

}
