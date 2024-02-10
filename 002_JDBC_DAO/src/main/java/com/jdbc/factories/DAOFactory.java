package com.jdbc.factories;

import com.jdbc.dao.position.PositionDAO;
import com.jdbc.dao.employee.EmployeeDAO;
import com.jdbc.dao.info.InfoDAO;

public interface DAOFactory {
    EmployeeDAO getEmployeeDAO();
    InfoDAO getInfoDAO();
    PositionDAO getPositionDAO();
}
