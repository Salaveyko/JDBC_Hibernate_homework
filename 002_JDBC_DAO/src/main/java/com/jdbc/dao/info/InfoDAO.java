package com.jdbc.dao.info;

import com.jdbc.entity.Info;

import java.sql.SQLException;

public interface InfoDAO extends AutoCloseable {
    void insert(Info info) throws SQLException;

    void update(Info info) throws SQLException;

    void delete(int id) throws SQLException;

    Info get(int id) throws SQLException;

    @Override
    void close() throws SQLException;
}
