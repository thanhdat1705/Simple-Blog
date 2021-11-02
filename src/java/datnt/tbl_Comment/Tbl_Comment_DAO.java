/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datnt.tbl_Comment;

import datnt.ultis.DBHelper;
import datnt.ultis.Tools;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
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
public class Tbl_Comment_DAO implements Serializable {

    private List<Tbl_Comment_DTO> listComments;

    public List<Tbl_Comment_DTO> getListComments() {
        return listComments;
    }

    public int getTotalComment(int articleId) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "SELECT COUNT(C.Id) "
                        + "FROM tbl_Comment as C INNER JOIN tbl_Articles as Ar ON C.ArticleId = Ar.Id "
                        + "WHERE Ar.Id = ? ";
                stm = con.prepareStatement(sql);
                stm.setInt(1, articleId);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int total = rs.getInt(1);
                    return total;
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
        return 0;
    }

    public void getListCommentByArticle(int articleId, String email) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "SELECT C.Id, C.ArticleId, C.Content, C.EmailAccount, A.Name "
                        + "FROM tbl_Comment as C INNER JOIN tbl_Articles as Ar ON C.ArticleId = Ar.Id INNER JOIN tbl_Account as A ON C.EmailAccount = A.Email "
                        + "WHERE Ar.Id = ? ";
                stm = con.prepareStatement(sql);
                stm.setInt(1, articleId);
                rs = stm.executeQuery();

                while (rs.next()) {
                    int id = rs.getInt(1);
                    int article = rs.getInt(2);
                    String content = rs.getString(3);
                    String emailUser = rs.getString(4);
                    String name = rs.getString(5);
                    String userComment = emailUser.equals(email) ? "You" : name;

                    Tbl_Comment_DTO dto = new Tbl_Comment_DTO(id, content, emailUser, article, userComment);
                    if (this.listComments == null) {
                        this.listComments = new ArrayList<>();
                    }
                    this.listComments.add(dto);
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

    public int insertComment(String comment, String email, int articleId)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = DBHelper.getConnect();

            if (con != null) {
                String sql = "INSERT INTO tbl_Comment(Content, EmailAccount, ArticleId) "
                        + "VALUES (?,?,?) ";
                stm = con.prepareStatement(sql);
                stm.setString(1, comment);
                stm.setString(2, email);
                stm.setInt(3, articleId);
                int row = stm.executeUpdate();

                if (row > 0) {
                    return row;
                }
            }
        } finally {
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
