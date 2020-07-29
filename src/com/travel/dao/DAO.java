package com.travel.dao;

import java.sql.Connection;
import com.travel.db.jdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.List;

public class DAO <T> {
    private QueryRunner queryRunner = new QueryRunner();
    private Class<T> clazz;
    public DAO(){
        Type superclass = getClass().getGenericSuperclass();
        if(superclass instanceof ParameterizedType){
            ParameterizedType parameterizedType = (ParameterizedType)superclass;
            Type [] typeArgs = parameterizedType.getActualTypeArguments();
            if(typeArgs != null && typeArgs.length > 0){
                if(typeArgs[0] instanceof Class){
                    clazz = (Class<T>) typeArgs[0];
                }
            }
        }
    }

    //update方法 包括insert、update、delete
    public void update(String sql, Object ... args) throws SQLException {
        Connection connection = null;
        try{
            connection = jdbcUtils.getConnection();
            queryRunner.update(connection,sql,args);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            jdbcUtils.releaseConnection(connection);
        }
    }
    //get方法，拿到查询语句获得的第一条记录
    public T get(String sql, Object ... args) throws SQLException {
        Connection connection = null;
        try{
            connection = jdbcUtils.getConnection();
            return queryRunner.query(connection,sql,new BeanHandler<>(clazz),args);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            jdbcUtils.releaseConnection(connection);
        }
        return null;
    }
    //获取一组对象的方法
    public List<T> getForList(String sql, Object ... args) throws SQLException {
        Connection connection = null;
        try{
            connection = jdbcUtils.getConnection();
            return queryRunner.query(connection,sql,new BeanListHandler<>(clazz),args);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            jdbcUtils.releaseConnection(connection);
        }
        return null;
    }
    //获取单个信息的方法
    public <E> E getForValue(String sql, Object ... args) throws SQLException {
        Connection connection = null;
        try{
            connection = jdbcUtils.getConnection();
            return (E) queryRunner.query(connection,sql,new ScalarHandler<>(),args);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            jdbcUtils.releaseConnection(connection);
        }
        return null;
    }
}

