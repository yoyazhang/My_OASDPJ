package com.travel.db;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.sql.Connection;

import javax.sql.DataSource;
import java.sql.SQLException;

public class jdbcUtils {
    private static DataSource dataSource = null;
    static {
        //数据源只要创建一次
        dataSource = new ComboPooledDataSource("mvcapp");
    }

    public static Connection getConnection() throws SQLException {

        return dataSource.getConnection();
    }
    public static void releaseConnection(Connection connection) throws SQLException {
        try{
            if(connection != null){
                connection.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
