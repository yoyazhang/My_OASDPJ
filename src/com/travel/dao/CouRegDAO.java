package com.travel.dao;

import com.travel.entity.GeocountriesRegions;

import java.sql.SQLException;
import java.util.List;

public interface CouRegDAO {
    public List<GeocountriesRegions> getAllCous() throws SQLException;
}
