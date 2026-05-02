package DAO;

import Modelo.Sprite;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SpriteDao implements ObjetoDao<Sprite> {
    
    private Connection conexion = GestorDeConexion.getInstancia().getConexion();

    @Override
    public List<Sprite> obtenerPorEntidad(int idEntidad, String tipoEntidad) throws SQLException {
        List<Sprite> sprites = new ArrayList<>();
        
        String columnaFiltro = tipoEntidad.equalsIgnoreCase("Heroe") ? "id_heroe" : "id_enemigo";
        String sql = "SELECT * FROM sprites WHERE " + columnaFiltro + " = ? ORDER BY estado, orden ASC";

        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, idEntidad);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    sprites.add(new Sprite(
                        rs.getInt("id"),
                        rs.getString("ruta_imagen"), 
                        rs.getString("estado"), 
                        rs.getInt("orden")));
                }
            }
        }
        return sprites;
    }
}
