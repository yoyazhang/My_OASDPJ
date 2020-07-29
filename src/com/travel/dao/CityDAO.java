package com.travel.dao;

import com.travel.entity.Geocities;

import java.sql.SQLException;
import java.util.List;

public interface CityDAO {
    public List<Geocities> getCity(String iso) throws SQLException;
}
