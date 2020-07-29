package com.travel.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Comments {
    private int commentId;
    private int uid;
    private int imageId;
    private String concreteContent;
    private long kudos;
    private long statusOwn;
    private String userName;
    private Timestamp commentTime;

    @Id
    @Column(name = "CommentID")
    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    @Basic
    @Column(name = "UID")
    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @Basic
    @Column(name = "ImageID")
    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    @Basic
    @Column(name = "concreteContent")
    public String getConcreteContent() {
        return concreteContent;
    }

    public void setConcreteContent(String concreteContent) {
        this.concreteContent = concreteContent;
    }

    public long getKudos(){return kudos;}
    public void setKudos(long kudos){
        this.kudos = kudos;
    }

    public long getStatusOwn(){return statusOwn;}
    public void setStatusOwn(long status){
        this.statusOwn = status;
    }

    public String getUserName(){return userName;}
    public void setUserName(String userName){
        this.userName = userName;
    }

    public Timestamp getCommentTime(){return commentTime;}
    public void setCommentTime(Timestamp date){
        this.commentTime = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comments comments = (Comments) o;
        return commentId == comments.commentId &&
                uid == comments.uid &&
                imageId == comments.imageId &&
                Objects.equals(concreteContent, comments.concreteContent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentId, uid, imageId, concreteContent);
    }
}
