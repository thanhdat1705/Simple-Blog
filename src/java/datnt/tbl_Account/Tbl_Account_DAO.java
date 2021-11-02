/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datnt.tbl_Account;

import datnt.ultis.DBHelper;
import datnt.ultis.Tools;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

/**
 *
 * @author NTD
 */
public class Tbl_Account_DAO implements Serializable {

    private Tbl_Account_DTO dto;

    public Tbl_Account_DTO getAccount() {
        return dto;
    }

    public boolean checkLogin(String email, String password)
            throws SQLException, NamingException, NoSuchAlgorithmException, UnsupportedEncodingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelper.getConnect();

            if (con != null) {
                String sql = "SELECT Email, Password, Name, RoleId, StatusId "
                        + "FROM tbl_Account "
                        + "WHERE Email = ? And Password = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, email);
                stm.setString(2, Tools.encryptPass(password));
                rs = stm.executeQuery();
                if (rs.next()) {
                    String name = rs.getString("Name");
                    int role = rs.getInt("RoleId");
                    int status = rs.getInt("StatusId");
                    this.dto = new Tbl_Account_DTO(email, password, name, role, status);

                    return true;
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
        return false;
    }

    public boolean checkExisted(String email) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.getConnect();
            if (con != null) {
                String sql = "SELECT Email "
                        + "FROM tbl_Account "
                        + "where Email = ? ";
                stm = con.prepareStatement(sql);
                stm.setString(1, email);
                rs = stm.executeQuery();
                if (rs.next()) {
                    return true;
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
        return false;
    }

    public int createAccount(String email, String password, String name, int roleId, int statusId)
            throws SQLException, NamingException, NoSuchAlgorithmException, UnsupportedEncodingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelper.getConnect();

            if (con != null) {
                String sql = "INSERT INTO tbl_Account(Email, Password, Name, RoleId, StatusId) "
                        + "VALUES (?,?,?,?,?) ";
                stm = con.prepareStatement(sql);
                stm.setString(1, email);
                stm.setString(2, Tools.encryptPass(password));
                stm.setString(3, name);
                stm.setInt(4, roleId);
                stm.setInt(5, statusId);
                int row = stm.executeUpdate();

                if (row > 0) {
                    return row;
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

    public boolean isActive(String email)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelper.getConnect();

            if (con != null) {
                String sql = "SELECT Asts.Name "
                        + "FROM tbl_Account as A INNER JOIN tbl_AccountStatus as Asts ON A.StatusId = Asts.Id "
                        + "WHERE A.Email = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, email);
                rs = stm.executeQuery();
                if (rs.next()) {
                    String status = rs.getString(1).trim();
                    if (status.equals("Active")) {
                        return true;
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
        return false;
    }

    public boolean activeAccount(String email, int statusID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.getConnect();

            if (con != null) {
                String sql = "UPDATE tbl_Account SET StatusId = ? "
                        + "WHERE Email = ? ";

                stm = con.prepareStatement(sql);
                stm.setInt(1, statusID);
                stm.setString(2, email);

                int row = stm.executeUpdate();
                if (row > 0) {
                    return true;
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
        return false;
    }

    public Tbl_Account_DTO getUserAccount(String email, String password)
            throws SQLException, NamingException, NoSuchAlgorithmException, UnsupportedEncodingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelper.getConnect();

            if (con != null) {
                String sql = "SELECT Email, Password, Name, RoleId, StatusId "
                        + "FROM tbl_Account "
                        + "WHERE Email = ? And Password = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, email);
                stm.setString(2, Tools.encryptPass(password));
                rs = stm.executeQuery();
                if (rs.next()) {
                    String name = rs.getString("Name");
                    int role = rs.getInt("RoleId");
                    int status = rs.getInt("StatusId");
                    Tbl_Account_DTO dto = new Tbl_Account_DTO(email, password, name, role, status);

                    return dto;
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
