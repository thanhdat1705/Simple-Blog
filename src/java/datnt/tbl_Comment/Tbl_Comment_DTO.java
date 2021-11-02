/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datnt.tbl_Comment;

import java.io.Serializable;

/**
 *
 * @author NTD
 */
public class Tbl_Comment_DTO implements Serializable{
    private int id;
    private String content;
    private String email;
    private int articleId;
    private String userComment;

    public Tbl_Comment_DTO() {
    }

    public Tbl_Comment_DTO(int id, String content, String email, int articleId, String userComment) {
        this.id = id;
        this.content = content;
        this.email = email;
        this.articleId = articleId;
        this.userComment = userComment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }
    
    
}
