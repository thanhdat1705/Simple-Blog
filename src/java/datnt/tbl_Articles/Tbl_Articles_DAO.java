/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datnt.tbl_Articles;

import datnt.ultis.DBHelper;
import datnt.ultis.Tools;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author NTD
 */
public class Tbl_Articles_DAO implements Serializable {

    private List<Tbl_Articles_DTO> listArticles = new ArrayList<>();

    public List<Tbl_Articles_DTO> getListArticles() {
        return listArticles;
    }

    public void searchArticle(int pageIndex, int pageSize, String searchContent, int status, String searchTittle) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        int start = (pageIndex - 1) * pageSize + 1;
        int end = pageIndex * pageSize;

        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "SELECT Id, Tittle, EmailAccount, Author, ShortDescription, Content, CreateDate, StatusId, StatusName "
                        + "FROM "
                        + "(SELECT Ar.Id, Ar.Tittle, Ar.EmailAccount, Ar.Author, Ar.ShortDescription, Ar.Content, Ar.CreateDate, "
                        + "Ar.StatusId, ArS.Name as StatusName, ROW_NUMBER() OVER (ORDER BY Ar.CreateDate DESC) as RowNum "
                        + "FROM tbl_Articles as Ar INNER JOIN tbl_ArticleStatus as ArS ON Ar.StatusId = ArS.Id "
                        + "WHERE Ar.StatusId = ? AND Ar.Tittle LIKE ? AND Ar.Content LIKE ?) as List "
                        + "Where RowNum >= ? and RowNum <= ? ";
                stm = con.prepareStatement(sql);
                stm.setInt(1, status);
                stm.setString(2, "%" + searchTittle + "%");
                stm.setString(3, "%" + searchContent + "%");
                stm.setInt(4, start);
                stm.setInt(5, end);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String tittle = rs.getString(2);
                    String email = rs.getString(3);
                    String author = rs.getString(4);
                    String shortDes = rs.getString(5);
                    String content = rs.getString(6);
                    Timestamp createDate = rs.getTimestamp(7);
                    int statusId = rs.getInt(8);
                    String statusName = rs.getString(9);

                    Tbl_Articles_DTO dto = new Tbl_Articles_DTO(id, tittle, email, author, content, shortDes, Tools.TimeFormat(createDate), Tools.getTime(createDate), statusName, statusId, 0);
                    if (this.listArticles == null) {
                        this.listArticles = new ArrayList<>();
                    }
                    this.listArticles.add(dto);
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

    public int totalPage(int pageSize, String searchContent, int status, String searchTittle)
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        float totalArticles = 0;

        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "SELECT COUNT(RowNum) "
                        + "From "
                        + "(SELECT Ar.Id, ROW_NUMBER() OVER (ORDER BY Ar.CreateDate DESC) as RowNum "
                        + "FROM tbl_Articles as Ar "
                        + "WHERE Ar.StatusId = ? AND Ar.Tittle LIKE ? AND Ar.Content LIKE ?) as List ";
                stm = con.prepareStatement(sql);
                stm.setInt(1, status);
                stm.setString(2, "%" + searchTittle + "%");
                stm.setString(3, "%" + searchContent + "%");
                rs = stm.executeQuery();
                if (rs.next()) {
                    totalArticles = (float) rs.getInt(1);

                    if (totalArticles > 0) {
                        return (int) Math.ceil(totalArticles / pageSize);
                    }
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

    public int postArticle(String tittle, String email, String author, String content, String shortDes, Timestamp createDate, int statusId)
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = DBHelper.getConnect();

            if (con != null) {
                String sql = "INSERT INTO tbl_Articles(Tittle, EmailAccount, Author, Content, ShortDescription, CreateDate, StatusId) "
                        + "VALUES(?,?,?,?,?,?,?) ";
                stm = con.prepareStatement(sql);
                stm.setString(1, tittle);
                stm.setString(2, email);
                stm.setString(3, author);
                stm.setString(4, content);
                stm.setString(5, shortDes);
                stm.setTimestamp(6, createDate);
                stm.setInt(7, statusId);

                int row = stm.executeUpdate();
                if (row > 0) {
                    return row;
                }
            }

        } finally {
            if (con != null) {
                con.close();
            }
            if (stm != null) {
                stm.close();
            }

        }
        return 0;
    }

    public boolean checkExistedTittle(String tittle)
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.getConnect();

            if (con != null) {
                String sql = "SELECT Tittle "
                        + "FROM tbl_Articles "
                        + "WHERE Tittle = ? ";
                stm = con.prepareStatement(sql);
                stm.setString(1, tittle);

                rs = stm.executeQuery();
                if (rs.next()) {
                    return true;
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
        return false;
    }

    public int updateStatus(int articleId, int statusId) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "Update tbl_Articles "
                        + "SET StatusId = ? "
                        + "WHERE Id = ? ";
                stm = con.prepareStatement(sql);
                stm.setInt(1, statusId);
                stm.setInt(2, articleId);

                int row = stm.executeUpdate();
                if (row > 0) {
                    return row;
                }
            }

        } finally {
            if (con != null) {
                con.close();
            }
            if (stm != null) {
                stm.close();
            }
        }
        return 0;
    }

    public Tbl_Articles_DTO viewArticle(int articleId, int status) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "SELECT Ar.Id, Ar.Tittle, Ar.EmailAccount, Ar.Author, Ar.ShortDescription, Ar.Content, Ar.CreateDate, Ar.StatusId, ArS.Name as StatusName "
                        + "FROM dbo.tbl_Articles as Ar INNER JOIN dbo.tbl_ArticleStatus as ArS ON Ar.StatusId = ArS.Id "
                        + "WHERE Ar.Id = ? AND Ar.StatusId = ? ";
                stm = con.prepareStatement(sql);
                stm.setInt(1, articleId);
                stm.setInt(2, status);

                rs = stm.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String tittle = rs.getString(2);
                    String email = rs.getString(3);
                    String author = rs.getString(4);
                    String shortDes = rs.getString(5);
                    String content = rs.getString(6);
                    Timestamp createDate = rs.getTimestamp(7);
                    int statusId = rs.getInt(8);
                    String statusName = rs.getString(9);

                    Tbl_Articles_DTO dto = new Tbl_Articles_DTO(id, tittle, email, author, content, shortDes, Tools.TimeFormat(createDate), Tools.getTime(createDate), statusName, statusId, 0);
                    return dto;
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
        return null;
    }

    public String getArticleStatus(int articleId) throws SQLException, NamingException {

        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "SELECT A.Name "
                        + "FROM tbl_Articles as Ar INNER JOIN tbl_ArticleStatus as A ON Ar.StatusId = A.Id "
                        + "WHERE Ar.Id = ? ";
                stm = con.prepareStatement(sql);
                stm.setInt(1, articleId);

                rs = stm.executeQuery();
                if (rs.next()) {
                    return rs.getString(1);
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
        return null;
    }

}
