package DAO;

import Modelo.Habilidad;
import Modelo.HabilidadAtaque;
import Modelo.HabilidadCuracion;
import Modelo.HabilidadDefensa;
import Modelo.HabilidadMana;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HabilidadDao implements ObjetoDao<Habilidad> {

    private Connection conexion = GestorDeConexion.getInstancia().getConexion();

    @Override
    public List<Habilidad> obtenerPorEntidad(int idEntidad, String tipoEntidad) throws SQLException {
        List<Habilidad> habilidades = new ArrayList<>();

        String tablaRelacion = tipoEntidad.equalsIgnoreCase("Heroe") ? "heroe_habilidades" : "enemigo_habilidades";
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
        String tipo = rs.getString("tipo");

        if (tipo.equalsIgnoreCase("Ataque")) {
            return new HabilidadAtaque(rs.getString("nombre"),
                rs.getString("descripcion"),
                tipo, 
                rs.getInt("costo_mana"), 
                rs.getInt("valor_base"), 
                rs.getDouble("prob_critico"), 
                rs.getInt("bonus_critico"), 
                rs.getInt("cooldown_max"));
        } else if (tipo.equalsIgnoreCase("Defensa")) {
            return new HabilidadDefensa(rs.getString("nombre"), 
            rs.getString("descripcion"), 
            tipo, 
            rs.getInt("costo_mana"), 
            rs.getInt("valor_base"), 
            rs.getInt("reduccion_danio"), 
            rs.getInt("cooldown_max"));
        } else if (tipo.equalsIgnoreCase("Curacion")) {
            return new HabilidadCuracion(rs.getString("nombre"), 
            rs.getString("descripcion"), 
            tipo, 
            rs.getInt("costo_mana"), 
            rs.getInt("valor_base"), 
            rs.getInt("cooldown_max"));
        } else if (tipo.equalsIgnoreCase("Mana")) {
            return new HabilidadMana(rs.getString("nombre"), 
            rs.getString("descripcion"), 
            tipo, 
            rs.getInt("costo_mana"), 
            rs.getInt("valor_base"), 
            rs.getInt("cooldown_max"));
        } else {
            return null;
        }
    }

    public List<Habilidad> obtenerPorPartida(int idPartida) throws SQLException {
        List<Habilidad> habilidades = new ArrayList<>();
        String sql = "SELECT h.* FROM habilidades h " + 
        "JOIN partida_habilidades ph ON h.id = ph.id_habilidad " + 
        "WHERE ph.id_partida = ? ORDER BY ph.slot ASC";

        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, idPartida);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    habilidades.add(mapearHabilidad(rs));
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
}
