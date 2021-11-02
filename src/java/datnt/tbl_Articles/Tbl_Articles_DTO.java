/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datnt.tbl_Articles;

import datnt.tbl_Comment.Tbl_Comment_DTO;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author NTD
 */
public class Tbl_Articles_DTO implements Serializable {

    private int id;
    private String tittle;
    private String email;
    private String author;
    private String content;
    private String shortDescription;
    private String createDate;
    private String time;
    private String status;
    private int statusId;
    private int totalComment;

    public Tbl_Articles_DTO() {
    }

    public Tbl_Articles_DTO(int id, String tittle, String email, String author, String content, String shortDescription, String createDate, String time, String status, int statusId, int totalComment) {
        this.id = id;
        this.tittle = tittle;
        this.email = email;
        this.author = author;
        this.content = content;
        this.shortDescription = shortDescription;
        this.createDate = createDate;
        this.time = time;
        this.status = status;
        this.statusId = statusId;
        this.totalComment = totalComment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getTotalComment() {
        return totalComment;
    }

    public void setTotalComment(int totalComment) {
        this.totalComment = totalComment;
    }

}
