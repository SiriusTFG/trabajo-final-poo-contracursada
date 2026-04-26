package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Modelo.Enemigo;
import Modelo.Habilidad;

import java.sql.Statement;
import java.util.List;

public class EnemigoDao implements EntidadDao<Enemigo> {

    private Connection conexion = GestorDeConexion.getInstancia().getConexion();
    private HabilidadDao habilidadDao = new HabilidadDao();

    public Enemigo obtenerEnemigoPorPiso(int piso) throws SQLException {
        String sql = "SELECT * FROM enemigos WHERE nivel_torre = ?";

        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, piso);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapearEnemigo(rs);
                }
            }
        }
        return null;
    }

    @Override
    public Enemigo obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM enemigos WHERE id = ?";

        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return mapearEnemigo(rs);
            }
        }
        return null;
    }

    @Override
    public List<Enemigo> obtenerTodos() throws SQLException {
        String sql = "SELECT * FROM enemigos";
        List<Enemigo> enemigos = new ArrayList<>();

        try (Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    enemigos.add(mapearEnemigo(rs));
                }
            }
        return enemigos;
    }

    private Enemigo mapearEnemigo(ResultSet rs) throws SQLException {
        Enemigo enemigo = new Enemigo(rs.getString("nombre"), 
            rs.getInt("vida_max"), 
            rs.getInt("mana_max"), 
            null);

        enemigo.setId(rs.getInt("id"));
        enemigo.setAtaque(rs.getInt("ataque"));
        enemigo.activarDefensa(rs.getDouble("defensa_base"));
        enemigo.setRutaImagen(rs.getString("ruta_imagen"));

        List<Habilidad> habilidades = habilidadDao.obtenerPorEntidad(enemigo.getId(), "Enemigo");
            
        for (int i = 0; i < habilidades.size(); i++) {
            enemigo.setHabilidad(i, habilidades.get(i));
        }
        return enemigo;
    }
    
}
