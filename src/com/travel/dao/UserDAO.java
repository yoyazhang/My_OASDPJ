package com.travel.dao;

import com.travel.entity.Traveluser;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO{
    public List<Traveluser> getAll();
    public void addUser(Traveluser user) throws SQLException;
    public boolean nameRepeat(String name) throws SQLException;
    public Traveluser getUser(String nameOrEmail, String password) throws SQLException;
    public Traveluser getUser(int uid) throws SQLException;
    public String getSalt(String name) throws SQLException;

    public List<Traveluser> getFriends(int uid) throws SQLException;
    public List<Traveluser> getReceivedList(int uid) throws SQLException;
    public List<Traveluser> getSendList(int uid) throws SQLException;

    public boolean areFriends(int friendsUID,int meUID) throws SQLException;
    public void canSee(int uid) throws SQLException;
    public void cantSee(int uid) throws SQLException;
    public List<Traveluser> searchUsers(String name,int uid) throws SQLException;
    public void setStatus(Traveluser traveluser,int uid) throws SQLException;
    public void sendRequest(int sendUID,int receiveUID) throws SQLException;
    public void acceptRequest(int sendUID,int decisionUID) throws SQLException;
    public void rejectRequest(int sendUID,int decisionUID) throws SQLException;
}
