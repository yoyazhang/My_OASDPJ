package com.travel.db;

import com.travel.dao.DAO;
import com.travel.dao.FavorImageDAO;
import com.travel.entity.Travelimage;

import java.sql.SQLException;
import java.util.List;

public class FavorImageUtilImpl extends DAO<Travelimage> implements FavorImageDAO {
    @Override
    public List<Travelimage> getUserFavorImages(Integer uid,int page) throws SQLException {
        String sql = "select travelimage.ImageID,Title,Description,PATH from travelimage JOIN travelimagefavor ON travelimagefavor.UID = ? WHERE travelimage.ImageID = travelimagefavor.ImageID LIMIT ?,6";
        return getForList(sql,uid,(page-1)*6);
    }

    @Override
    public long allFavorPics(int uid) throws SQLException {
        String sql = "SELECT COUNT(*) FROM travelimagefavor WHERE UID = ?";
        return getForValue(sql,uid);
    }


}
