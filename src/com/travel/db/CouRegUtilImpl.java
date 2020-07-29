package com.travel.db;

import com.travel.dao.CouRegDAO;
import com.travel.dao.DAO;
import com.travel.entity.GeocountriesRegions;

import java.sql.SQLException;
import java.util.List;

public class CouRegUtilImpl extends DAO<GeocountriesRegions> implements CouRegDAO {
    @Override
    public List<GeocountriesRegions> getAllCous() throws SQLException {
        String sql = "SELECT ISO,Country_RegionName countryRegionName FROM geocountries_regions";
        return getForList(sql);
    }
}
