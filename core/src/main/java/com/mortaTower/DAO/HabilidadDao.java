package com.mortaTower.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mortaTower.Modelo.Habilidad;
import com.mortaTower.Modelo.HabilidadAtaque;
import com.mortaTower.Modelo.HabilidadCuracion;
import com.mortaTower.Modelo.HabilidadDefensa;
import com.mortaTower.Modelo.HabilidadMana;

public class HabilidadDao implements ObjetoDao<Habilidad> {

    private Connection conexion = GestorDeConexion.getInstancia().getConexion();

    @Override
    public List<Habilidad> obtenerPorEntidad(int idEntidad, String tipoEntidad) throws SQLException {
        List<Habilidad> habilidades = new ArrayList<>();

        String tablaRelacion = tipoEntidad.equalsIgnoreCase("Heroe") ? "heroe_habilidades" : "enemigos_habilidades";
        String columnaId = tipoEntidad.equalsIgnoreCase("Heroe") ? "id_heroe" : "id_enemigo";

        String sql = "SELECT h. * FROM habilidades h " + 
        "JOIN " + tablaRelacion + " rel ON h.id = rel.id_habilidad " + 
        "WHERE rel." + columnaId + "= ? ORDER BY rel.slot ASC";

        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, idEntidad);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    habilidades.add(mapearHabilidad(rs));
                    
                }
            }
        }
        return habilidades;
    }

    private Habilidad mapearHabilidad(ResultSet rs) throws SQLException {
        
        int id = rs.getInt("id");
        String tipo = rs.getString("tipo");
        
        if (tipo.equalsIgnoreCase("Ataque")) {
            return new HabilidadAtaque(id,
                rs.getString("nombre"), 
                rs.getString("descripcion"),
                tipo, 
                rs.getInt("costo_mana"), 
                rs.getInt("valor_base"), 
                rs.getDouble("prob_critico"), 
                rs.getInt("bonus_critico"), 
                rs.getInt("cooldown_max"));
        } else if (tipo.equalsIgnoreCase("Defensa")) {
            return new HabilidadDefensa(id,
            rs.getString("nombre"), 
            rs.getString("descripcion"), 
            tipo, 
            rs.getInt("costo_mana"), 
            rs.getInt("valor_base"), 
            rs.getDouble("reduccion_danio"), 
            rs.getInt("cooldown_max"));
        } else if (tipo.equalsIgnoreCase("Curacion")) {
            return new HabilidadCuracion(id,
            rs.getString("nombre"), 
            rs.getString("descripcion"), 
            tipo, 
            rs.getInt("costo_mana"), 
            rs.getInt("valor_base"), 
            rs.getInt("cooldown_max"));
        } else if (tipo.equalsIgnoreCase("Mana")) {
            return new HabilidadMana(id,
                rs.getString("nombre"), 
            rs.getString("descripcion"), 
            tipo, 
            rs.getInt("costo_mana"), 
            rs.getInt("valor_base"), 
            rs.getInt("cooldown_max"));
        } else {
            
            throw new IllegalArgumentException("Tipo de habilidad desconocido: " + tipo);
        }
    }

    public List<Habilidad> obtenerPorPartida(int idPartida) throws SQLException {

        List<Habilidad> habilidades = new ArrayList<>();

        String sql = """
            SELECT h.*, ph.slot
            FROM partida_habilidades ph
            JOIN habilidades h ON h.id = ph.id_habilidad
            WHERE ph.id_partida = ?
            ORDER BY ph.slot ASC
        """;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idPartida);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    habilidades.add(mapearHabilidad(rs));
                }
            }
        }

        return habilidades;
    }

    public List<Habilidad> obtenerTodas() throws SQLException {

        List<Habilidad> habilidades = new ArrayList<>();

        String sql = "SELECT * FROM habilidades ORDER BY id ASC";

        try (PreparedStatement pstmt = conexion.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {

                Habilidad h = mapearHabilidad(rs);

                if (h != null) {
                    habilidades.add(h);
                }
            }
        }

        return habilidades;
    }

    public void reemplazarHabilidadEnPartida(int idPartida, int idNuevaHabilidad, int slot) throws SQLException {
        String sql = "UPDATE partida_habilidades SET id_habilidad = ? " +
        "WHERE id_partida = ? AND slot =?";
        
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, idNuevaHabilidad);
            pstmt.setInt(2, idPartida);
            pstmt.setInt(3, slot);
            pstmt.executeUpdate();
        }
    }
    public List<Habilidad> obtenerRecompensasHeroe() throws SQLException {
        List<Habilidad> habilidades = new ArrayList<>();

        String sql = "SELECT * FROM habilidades WHERE origen = 'HEROE' ORDER BY id ASC";

        try (PreparedStatement pstmt = conexion.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {

                Habilidad h = mapearHabilidad(rs);

                if (h != null) {
                    habilidades.add(h);
                }
            }
        }

        return habilidades;
    }
}
