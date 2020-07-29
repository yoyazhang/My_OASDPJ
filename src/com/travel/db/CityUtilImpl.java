package com.travel.db;

import com.travel.dao.CityDAO;
import com.travel.dao.DAO;
import com.travel.entity.Geocities;

import java.sql.SQLException;
import java.util.List;

public class CityUtilImpl extends DAO<Geocities> implements CityDAO {
    @Override
    public List<Geocities> getCity(String iso) throws SQLException {
        String sql = "SELECT GeoNameID,AsciiName FROM geocities WHERE Country_RegionCodeISO = ? ORDER BY Population DESC ";
        return getForList(sql,iso);
    }
}
