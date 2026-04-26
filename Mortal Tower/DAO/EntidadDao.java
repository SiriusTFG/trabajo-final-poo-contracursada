package DAO;

import java.sql.SQLException;
import java.util.List;

public interface EntidadDao<t> {

    t obtenerPorId(int id) throws SQLException;
    List<t> obtenerTodos() throws SQLException;
    
}
