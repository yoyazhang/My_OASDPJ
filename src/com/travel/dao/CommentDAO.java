package com.travel.dao;

import com.travel.entity.Comments;

import java.sql.SQLException;
import java.util.List;

public interface CommentDAO {
    public List<Comments> getImageComment(int ImageID,String method) throws SQLException;
    public void setKudos(Comments comment) throws SQLException;
    public void addComment(Comments comment) throws SQLException;
    public void deleteComment(int CommentID) throws SQLException;
    public void setCanLike(List<Comments> comments,int uid) throws SQLException;
    public void setCanLike(List<Comments> comments);
    public void addLike(int commentID,int uid) throws SQLException;
    public void removeLike(int commentID,int uid) throws SQLException;
}
