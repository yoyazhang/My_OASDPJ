package com.travel.db;

import com.travel.dao.CommentDAO;
import com.travel.dao.DAO;
import com.travel.entity.Comments;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class CommentUtilImpl extends DAO<Comments> implements CommentDAO {
    @Override
    public List<Comments> getImageComment(int ImageID,String method) throws SQLException {
        String sql;
        if(method.equals("RankByHot")){
            sql = "SELECT comments.CommentID,traveluser.UserName,traveluser.UID,concreteContent," +
                    "COUNT(commentsfavor.UID) AS kudos FROM comments LEFT JOIN commentsfavor ON " +
                    "commentsfavor.CommentID = comments.CommentID JOIN traveluser ON traveluser.UID = comments.UID " +
                    "WHERE comments.ImageID = ? GROUP BY comments.CommentID ORDER BY kudos DESC";
        }else{
            sql = "SELECT comments.CommentID,traveluser.UserName,traveluser.UID,concreteContent," +
                    "COUNT(commentsfavor.UID) AS kudos FROM comments LEFT JOIN commentsfavor ON " +
                    "commentsfavor.CommentID = comments.CommentID JOIN traveluser ON traveluser.UID = comments.UID " +
                    "WHERE comments.ImageID = ? GROUP BY comments.CommentID ORDER BY comments.CommentTime DESC";
        }
        return getForList(sql,ImageID);
    }

    @Override
    public void setKudos(Comments comment) throws SQLException {
        String sql = "SELECT COUNT(*) AS kudos FROM commentsfavor WHERE commentsfavor.CommentID = ?";
        long kudos = getForValue(sql,comment.getCommentId());
        comment.setKudos(kudos);
    }

    @Override
    public void addComment(Comments comment) throws SQLException {
        String sql = "INSERT INTO comments (UID,ImageID,concreteContent,CommentTime) VALUES (?,?,?,?)";
        update(sql,comment.getUid(),comment.getImageId(),comment.getConcreteContent(),new Timestamp(new java.util.Date().getTime()));
    }

    @Override
    public void deleteComment(int CommentID) throws SQLException {
        String sql = "DELETE FROM comments WHERE CommentID = ?";
        update(sql,CommentID);
    }

    @Override
    public void setCanLike(List<Comments> comments,int uid) throws SQLException {
        for(Comments comment: comments){
            String sql = "SELECT COUNT(*) AS likeYet FROM commentsfavor WHERE commentsfavor.UID = ? AND CommentID = ?";
           comment.setStatusOwn(getForValue(sql,uid,comment.getCommentId()));
        }
    }

    @Override
    public void setCanLike(List<Comments> comments) {
        for(Comments comment: comments){
            comment.setStatusOwn(-1);
        }
    }
    @Override
    public void addLike(int commentID, int uid) throws SQLException {
        String sql = "INSERT INTO commentsfavor (CommentID,UID) VALUES (?,?)";
        update(sql,commentID,uid);
    }

    @Override
    public void removeLike(int commentID, int uid) throws SQLException {
        String sql = "DELETE FROM commentsfavor WHERE CommentID = ? AND UID = ?";
        update(sql,commentID,uid);
    }
}
