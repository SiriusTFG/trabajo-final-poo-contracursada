package DAO;

import java.sql.SQLException;
import java.util.List;

public interface ObjetoDao<t> {
    
    List<t> obtenerPorEntidad(int idEntidad, String tipoEntidad) throws SQLException;
    
}
