package com.jdbc.dao.position;

import com.jdbc.entity.Position;

import java.sql.SQLException;

public interface PositionDAO extends AutoCloseable {
    void insert(Position position) throws SQLException;

    void update(Position position) throws SQLException;

    void delete(int id) throws SQLException;

    Position get(int id) throws SQLException;

    @Override
    void close() throws SQLException;
}
