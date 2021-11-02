/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datnt.tbl_Account;

import java.io.Serializable;

/**
 *
 * @author NTD
 */
public class Tbl_Account_DTO implements Serializable{
    private String email;
    private String password;
    private String name;
    private int roleId;
    private int statusId;

    public Tbl_Account_DTO() {
    }

    public Tbl_Account_DTO(String email, String password, String name, int roleId, int statusId) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.roleId = roleId;
        this.statusId = statusId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }
    
    
}
