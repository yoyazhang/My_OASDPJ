package com.travel.db;

import com.travel.dao.DAO;
import com.travel.dao.UserDAO;
import com.travel.entity.Traveluser;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class UserUtilsImpl extends DAO<Traveluser> implements UserDAO {
    @Override
    public List<Traveluser> getAll() {
        return null;
    }

    @Override
    public void addUser(Traveluser user) throws SQLException {
        String sql = "INSERT INTO traveluser (Email,UserName,Pass,State,DateJoined,DateLastModified,salt) VALUES (?,?,?,?,?,?,?)";
        update(sql,user.getEmail(),user.getUserName(),user.getPass(),user.getState(),user.getDateJoined(),user.getDateLastModified(),user.getSalt());
    }

    @Override
    public boolean nameRepeat(String name) throws SQLException {
        String sql = "SELECT COUNT(*) AS numbers FROM traveluser WHERE UserName = ?";
        return (long)(getForValue(sql, name)) > 0;
    }

    @Override
    public Traveluser getUser(String nameOrEmail, String password) throws SQLException {
        String sql;
        if(nameOrEmail.contains("@")){
            sql = "SELECT * FROM traveluser WHERE Email = ? AND Pass = ?";
        }else{
            sql = "SELECT * FROM traveluser WHERE UserName = ? AND Pass = ?";
        }
        return get(sql,nameOrEmail,password);
    }
    @Override
    public Traveluser getUser(int uid) throws SQLException {
        String sql = "SELECT * FROM traveluser WHERE UID = ?";
        return get(sql,uid);
    }

    @Override
    public String getSalt(String name) throws SQLException {
        String sql;
        if(name.contains("@")){
            sql = "SELECT salt FROM traveluser WHERE Email = ?";
        }else{
            sql = "SELECT salt FROM traveluser WHERE UserName = ?";
        }
        return getForValue(sql,name);
    }

    @Override
    public List<Traveluser> getFriends(int uid) throws SQLException {
        String sql = "SELECT traveluser.UID,UserName,Email,traveluser.State,DateJoined FROM traveluser JOIN friends ON AUID = ? WHERE traveluser.UID = friends.BUID";
        return getForList(sql,uid);
    }

    @Override
    public List<Traveluser> getReceivedList(int uid) throws SQLException {
        String sql = "SELECT traveluser.UserName,traveluser.UID FROM traveluser JOIN relationship ON relationship.ReceivedID = ? AND relationship.Status = 0 WHERE traveluser.UID = relationship.SendID ORDER BY relationship.UpdateTime DESC";
        return getForList(sql,uid);
    }

    @Override
    public List<Traveluser> getSendList(int uid) throws SQLException {
        String sql = "SELECT traveluser.UserName,traveluser.UID,relationship.Status FROM traveluser JOIN relationship ON relationship.SendID = ? WHERE traveluser.UID = relationship.ReceivedID ORDER BY relationship.UpdateTime DESC";
        return getForList(sql,uid);
    }

    @Override
    public boolean areFriends(int friendsUID,int meUID) throws SQLException {
        String sql = "SELECT COUNT(*) FROM friends WHERE AUID = ? AND BUID = ?";
        return (long) getForValue(sql, friendsUID, meUID) != 0;
    }

    @Override
    public List<Traveluser> searchUsers(String name,int uid) throws SQLException {
        //sql有问题
        String sql = "SELECT traveluser.UserName,traveluser.UID FROM traveluser WHERE traveluser.UserName LIKE '"+ name +"'";
        List<Traveluser> searchUsers = getForList(sql);
        for (Traveluser searchUser : searchUsers) {
            setStatus(searchUser, uid);
        }
        return searchUsers;
    }

    @Override
    public void setStatus(Traveluser traveluser,int uid) throws SQLException {
        String sql1 = "SELECT relationship.Status FROM relationship WHERE SendID = ? AND ReceivedID = ?";
        String sql2 = "SELECT relationship.Status FROM relationship WHERE ReceivedID = ? AND SendID = ?";
        if(traveluser.getUid() == uid){
            traveluser.setStatus("Yourself");
            return;
        }
        if(getForValue(sql1,traveluser.getUid(),uid) == null && getForValue(sql2,traveluser.getUid(),uid) == null){
            traveluser.setStatus("yes");
        }else if(getForValue(sql1,traveluser.getUid(),uid) != null){
            if((int)getForValue(sql1,traveluser.getUid(),uid) == 0){
                traveluser.setStatus("Request Received");
            }else if((int)getForValue(sql1,traveluser.getUid(),uid) == 1){
                traveluser.setStatus("Already Added");
            }else if((int)getForValue(sql1,traveluser.getUid(),uid) == 2){
                traveluser.setStatus("yes");
            }
        }else if(getForValue(sql2,traveluser.getUid(),uid) != null){
            if((int)getForValue(sql2,traveluser.getUid(),uid) == 0){
                traveluser.setStatus("Request Send");
            }else if((int)getForValue(sql2,traveluser.getUid(),uid) == 1){
                traveluser.setStatus("Already Added");
            }else if((int)getForValue(sql2,traveluser.getUid(),uid) == 2){
                traveluser.setStatus("yes");
            }
        }
    }



    @Override
    public void canSee(int uid) throws SQLException {
        String sql = "UPDATE traveluser SET State = 1 WHERE UID = ?";
        update(sql,uid);
    }

    @Override
    public void cantSee(int uid) throws SQLException {
        String sql = "UPDATE traveluser SET State = 0 WHERE UID = ?";
        update(sql,uid);
    }

    @Override
    public void sendRequest(int sendUID, int receiveUID) throws SQLException {
        String sql = "INSERT INTO relationship (SendID,ReceivedID,Status,UpdateTime) VALUES (?,?,?,?)";
        update(sql,sendUID,receiveUID,0,new Date(new java.util.Date().getTime()));
    }

    @Override
    public void acceptRequest(int sendUID, int decisionUID) throws SQLException {
        String sql1 = "UPDATE relationship SET Status = ?, UpdateTime = ? WHERE SendID = ? AND ReceivedID = ?";
        update(sql1,1,new Date(new java.util.Date().getTime()),sendUID,decisionUID);
        String sql2 = "INSERT INTO friends (AUID,BUID,UpdateTime) VALUES (?,?,?)";
        update(sql2,sendUID,decisionUID,new Date(new java.util.Date().getTime()));
        update(sql2,decisionUID,sendUID,new Date(new java.util.Date().getTime()));
    }

    @Override
    public void rejectRequest(int sendUID, int decisionUID) throws SQLException {
        String sql = "UPDATE relationship SET Status = ?, UpdateTime = ? WHERE SendID = ? AND ReceivedID = ?";
        update(sql,2,new Date(new java.util.Date().getTime()),sendUID,decisionUID);
    }
}