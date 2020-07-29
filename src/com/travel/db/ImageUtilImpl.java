package com.travel.db;

import com.travel.dao.DAO;
import com.travel.dao.ImageDAO;
import com.travel.entity.Travelimage;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class ImageUtilImpl extends DAO<Travelimage> implements ImageDAO {
    @Override
    public Travelimage getImageInfo(int ImageID) throws SQLException {
        String sql="SELECT travelimage.ImageID, Title,travelimage.UID,Description,Content,PATH,UpdateTime updateTime,traveluser.UserName author," +
                "travelimage.Country_RegionCodeISO countryRegionCodeIso," +
                "geocountries_regions.Country_RegionName country,CityCode from travelimage NATURAL JOIN traveluser" +
                " JOIN geocountries_regions ON geocountries_regions.ISO= travelimage.Country_RegionCodeISO WHERE travelimage.ImageId =?";
        return get(sql,ImageID);
    }

    public long getImageKudos(int ImageID) throws SQLException {
        String sql = "SELECT COUNT(*) AS kudos FROM travelimagefavor WHERE travelimagefavor.ImageID = ?";
        return getForValue(sql,ImageID);
    }

    @Override
    public String likeOrNot(int ImageID, int uid) throws SQLException {
        String sql = "SELECT COUNT(*) AS LIKES FROM travelimagefavor WHERE travelimagefavor.UID = ? AND travelimagefavor.ImageID = ?";
        if((long)(getForValue(sql,uid,ImageID)) > 0) return "yes";
        else return "no";
    }

    @Override
    public String getImageCity(int cityCode) throws SQLException {
        String sql = "SELECT AsciiName FROM geocities WHERE GeoNameID = ?";
        return getForValue(sql,cityCode);
    }

    @Override
    public List<Travelimage> hottestPics() throws SQLException {
        String sql = "SELECT Title,PATH,travelimage.ImageID, COUNT(travelimagefavor.ImageID) AS instnum FROM travelimage LEFT JOIN travelimagefavor ON travelimage.ImageID =travelimagefavor.ImageID GROUP BY travelimage.ImageID ORDER BY instnum DESC LIMIT 5";
        return getForList(sql);
    }

    @Override
    public List<Travelimage> newestPics() throws SQLException {
        String sql = "SELECT Title,PATH,travelimage.ImageID,traveluser.UserName author," +
                "UpdateTime FROM travelimage NATURAL JOIN traveluser ORDER BY UpdateTime DESC LIMIT 6";
        return getForList(sql);
    }

    @Override
    public List<Travelimage> historyPics(int uid) throws SQLException {
        String sql = "SELECT Title,travelimage.ImageID FROM travelimage JOIN history ON travelimage.ImageID = history.ImageID" +
                " AND history.UID = ? ORDER BY HistoryID DESC LIMIT 10";
        return getForList(sql,uid);
    }

    @Override
    public void addToHistory(int ImageID, int uid) throws SQLException {
        if(historyExist(ImageID,uid)){
            String deleteSql = "DELETE FROM history WHERE UID = ? AND ImageID = ?";
            update(deleteSql,uid,ImageID);
        }
        String sql = "INSERT INTO history (UID,ImageID) VALUES (?,?)";
        update(sql,uid,ImageID);
    }

    @Override
    public boolean historyExist(int ImageID, int uid) throws SQLException {
        String sql = "SELECT COUNT(*) AS num FROM history WHERE UID = ? AND ImageID = ?";
        return (long) (getForValue(sql, uid, ImageID)) > 0;
    }

    @Override
    public List<Travelimage> certainPicsByTitle(String title,String rankMethod,int page) throws SQLException {
        String sql;
        if(rankMethod.equals("RankByHot")){
            sql ="SELECT travelimage.ImageID,Title,Description,Path,COUNT(travelimagefavor.ImageID) AS instnum from travelimage LEFT JOIN travelimagefavor ON travelimage.ImageID =travelimagefavor.ImageID WHERE Title LIKE '"+ title +"' GROUP BY travelimage.ImageID ORDER BY instnum DESC LIMIT ?,6";
        }else{
            sql = "SELECT travelimage.ImageID,Title,Description,Path from travelimage WHERE Title LIKE '"+ title +"' ORDER BY UpdateTime DESC LIMIT ?,6";
        }
        return getForList(sql,(page-1)*6);
    }

    @Override
    public List<Travelimage> certainPicsByContent(String content,String rankMethod,int page) throws SQLException {
        String sql;
        if(rankMethod.equals("RankByHot")){
            sql = "SELECT travelimage.ImageID,Title,Description,Path,COUNT(travelimagefavor.ImageID) AS instnum from travelimage LEFT JOIN travelimagefavor ON travelimage.ImageID =travelimagefavor.ImageID WHERE content LIKE '"+ content +"' GROUP BY travelimage.ImageID ORDER BY instnum DESC LIMIT ?,6";
        }else{
            sql = "SELECT travelimage.ImageID,Title,Description,Path from travelimage WHERE content LIKE '"+ content +"' ORDER BY UpdateTime DESC LIMIT ?,6";
        }
        return getForList(sql,(page-1)*6);
    }

    @Override
    public long TitleImageNum(String title) throws SQLException {
        String sql = "SELECT COUNT(*) AS num FROM travelimage WHERE Title LIKE '"+ title +"'";
        return getForValue(sql);
    }

    @Override
    public long ContentImageNum(String content) throws SQLException {
        String sql = "SELECT COUNT(*) AS num FROM travelimage WHERE content LIKE '"+ content +"'";
        return getForValue(sql);
    }

    @Override
    public void addPhoto(Travelimage travelimage) throws SQLException {
        String sql = "INSERT INTO travelimage (Title, Description, CityCode,Country_RegionCodeISO,UID,PATH,Content,UpdateTime) VALUES (?,?,?,?,?,?,?,?)";
        update(sql,travelimage.getTitle(),travelimage.getDescription(),travelimage.getCityCode(),travelimage.getCountryRegionCodeIso(),travelimage.getUid(),travelimage.getPath(),travelimage.getContent(),travelimage.getUpdateTime());
    }

    @Override
    public void updatePhoto(Travelimage travelimage) throws SQLException {
        String sql = "UPDATE travelimage SET Title = ?, Description = ?,CityCode = ?,Country_RegionCodeISO = ?,Content = ?,UpdateTime = ? WHERE ImageID = ?";
        update(sql,travelimage.getTitle(),travelimage.getDescription(),travelimage.getCityCode(),travelimage.getCountryRegionCodeIso(),travelimage.getContent(),new Timestamp(new java.util.Date().getTime()),travelimage.getImageId());
    }

    @Override
    public void updatePhotoWithPath(Travelimage travelimage) throws SQLException {
        String sql = "UPDATE travelimage SET Title = ?, Description = ?,CityCode = ?,Country_RegionCodeISO = ?,Content = ?,PATH = ? ,UpdateTime = ? WHERE ImageID = ?";
        update(sql,travelimage.getTitle(),travelimage.getDescription(),travelimage.getCityCode(),travelimage.getCountryRegionCodeIso(),travelimage.getContent(),travelimage.getPath(),new Timestamp(new java.util.Date().getTime()),travelimage.getImageId());
    }

    @Override
    public void addToFavor(int uid, int ImageID) throws SQLException {
        if(likeOrNot(ImageID, uid).equals("true")){
            return;
        }
        String sql = "INSERT INTO travelimagefavor (UID,ImageID) VALUES (?,?)";
        update(sql,uid,ImageID);
    }
    @Override
    public void removeFromFavor(int uid,int ImageID) throws SQLException {
        String sql = "DELETE FROM travelimagefavor WHERE ImageID = ? and UID = ?";
        update(sql,ImageID,uid);
    }

    @Override
    public void deletePic(int ImageID,int uid) throws SQLException {
        //删除所有与该图相关的喜欢
        String sql = "DELETE FROM travelimagefavor WHERE ImageID = ?";
        update(sql,ImageID);

        //删除该图
        sql = "DELETE FROM travelimage WHERE ImageID = ? and UID = ?";
        update(sql,ImageID,uid);
    }

    @Override
    public long allMyPics(int uid) throws SQLException {
        String sql = "select COUNT(*) from travelimage WHERE travelimage.UID = ?";
        return getForValue(sql,uid);
    }

    @Override
    public List<Travelimage> myPics(int uid,int page) throws SQLException {
        String sql ="select ImageID,Title, Description,PATH from travelimage WHERE travelimage.UID = ? LIMIT ?,6";
        return getForList(sql,uid,(page-1)*6);
    }
}
