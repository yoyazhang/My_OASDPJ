package com.travel.dao;

import com.travel.entity.Travelimage;
import com.travel.entity.Traveluser;

import java.sql.SQLException;
import java.util.List;

public interface FavorImageDAO {
    public List<Travelimage> getUserFavorImages(Integer uid,int page) throws SQLException;
    public long allFavorPics(int uid) throws SQLException;
}
