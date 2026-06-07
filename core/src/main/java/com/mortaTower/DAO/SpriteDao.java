package com.mortaTower.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.mortaTower.Modelo.DatosSprite;

public class SpriteDao implements ObjetoDao<DatosSprite> {
    
    private Connection conexion = GestorDeConexion.getInstancia().getConexion();

    @Override
    public List<DatosSprite> obtenerPorEntidad(int idEntidad, String tipoEntidad) throws SQLException {
        List<DatosSprite> sprites = new ArrayList<>();
        
        String columnaFiltro = tipoEntidad.equalsIgnoreCase("Heroe") ? "id_heroe" : "id_enemigo";
        String sql = "SELECT * FROM sprites WHERE " + columnaFiltro + " = ? ORDER BY estado, orden ASC";

        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, idEntidad);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    sprites.add(new DatosSprite(
                        rs.getInt("id"),
                        rs.getString("ruta_imagen"), 
                        rs.getString("estado"), 
                        rs.getInt("orden")));
                }
            }
        }
        return sprites;
    }
    public List <DatosSprite> obtenerSpritesPorHeroe(int idEntidad) throws SQLException {
        List<DatosSprite> sprites = new ArrayList<>();
        String sql = "SELECT id,estado, ruta_imagen, orden FROM sprites WHERE id_heroe = ?";

         try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, idEntidad);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    sprites.add(new DatosSprite(
                        rs.getInt("id"),
                        rs.getString("ruta_imagen"), 
                        rs.getString("estado"), 
                        rs.getInt("orden")));
                }
            }
        }
        return sprites;
    }

    public List <DatosSprite> obtenerSpritesPorEnemigo(int idEntidad) throws SQLException {
        List<DatosSprite> sprites = new ArrayList<>();
        String sql = "SELECT id,estado, ruta_imagen, orden FROM sprites WHERE id_enemigo = ?";

         try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, idEntidad);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    sprites.add(new DatosSprite(
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
