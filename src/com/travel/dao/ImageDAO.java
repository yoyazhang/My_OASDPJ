package com.travel.dao;

import com.travel.entity.Travelimage;

import java.sql.SQLException;
import java.util.List;

public interface ImageDAO {
    public Travelimage getImageInfo(int ImageID) throws SQLException;
    public long getImageKudos(int ImageID) throws SQLException;
    public String likeOrNot(int ImageID,int uid) throws SQLException;
    public String getImageCity(int cityCode) throws SQLException;

    public void addToFavor(int uid,int ImageID) throws SQLException;
    public void removeFromFavor(int uid,int ImageID) throws SQLException;
    public void deletePic(int ImageID,int uid) throws SQLException;

    public long allMyPics(int uid) throws SQLException;
    public List<Travelimage> myPics(int uid,int page) throws SQLException;
    public List<Travelimage> hottestPics() throws SQLException;
    public List<Travelimage> newestPics() throws SQLException;
    public List<Travelimage> historyPics(int uid) throws SQLException;
    public void addToHistory(int ImageID, int uid) throws SQLException;
    public boolean historyExist(int ImageID,int uid) throws SQLException;
    public List<Travelimage> certainPicsByTitle(String title,String rankMethod,int page) throws SQLException;
    public List<Travelimage> certainPicsByContent(String content,String rankMethod,int page) throws SQLException;
    public long TitleImageNum(String title) throws SQLException;
    public long ContentImageNum(String content) throws SQLException;

    public void addPhoto(Travelimage travelimage) throws SQLException;
    public void updatePhoto(Travelimage travelimage) throws SQLException;
    public void updatePhotoWithPath(Travelimage travelimage) throws SQLException;

}
