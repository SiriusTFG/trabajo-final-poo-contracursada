package DAO;

import Modelo.Habilidad;
import Modelo.Heroe;
import Modelo.Partida;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class PartidaDao {

    private Connection conexion = GestorDeConexion.getInstancia().getConexion();
    private HabilidadDao habilidadDao = new HabilidadDao();

    public int nuevaPartida(String nombrePartida, int idHeroe) throws SQLException {
        String sqlpartida = "INSERT INTO partidas (nombre_partida, id_heroe, nivel_actual, " +
                     "experiencia_actual, vida_actual, mana_actual, ataque_actual, piso_torre) " +
                     "SELECT ?, id, 1, 0, vida_max, mana_max, ataque, 1 FROM heroes WHERE id = ?";

        String sqlCopiaHabilidades = "INSERT INTO partida_habilidades (id_partida, id_habilidad, slot) " +
                                     "SELECT ?, id_habilidad, slot FROM heroe_habilidades WHERE id_heroe = ?";
        
        int idGenerado = -1;
        try {
            conexion.setAutoCommit(false); //para iniciar transaccion

            try (PreparedStatement pstmt = conexion.prepareStatement(sqlpartida, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, nombrePartida);
                pstmt.setInt(2, idHeroe);
                pstmt.executeUpdate();

                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    idGenerado = rs.getInt(1);
                }
            }

            if (idGenerado != -1) {
                try (PreparedStatement pstmtH = conexion.prepareStatement(sqlCopiaHabilidades)) {
                    pstmtH.setInt(1, idGenerado);
                    pstmtH.setInt(2, idHeroe);
                    pstmtH.executeUpdate();
                }
            }

            conexion.commit(); //guarda los cambios
        } catch (SQLException e) {
            conexion.rollback(); //si falla se deshace todo
            throw e;
        }  finally {
            conexion.setAutoCommit(true);
        }
        return idGenerado;
    }
    

    public Partida cargarPartida(int idPartida) throws SQLException {
        String sql = "SELECT p.*, h.nombre AS nombre_heroe, h.ruta_imagen " +
                     "FROM partidas p JOIN heroes h ON p.id_heroe = h.id " +
                     "WHERE p.id = ?";

        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, idPartida);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Heroe heroe = new Heroe(
                        rs.getInt("id_heroe"),
                        rs.getString("nombre_heroe"), 
                        rs.getInt("vida_actual"), 
                        rs.getInt("mana_actual"),
                        rs.getInt("nivel_actual"),
                        rs.getInt("experiencia_actual"),
                        rs.getInt("ataque_actual"),
                        rs.getDouble("defensa_actual"));
                    
                    heroe.setRutaImagen(rs.getString("ruta_imagen"));

                    List<Habilidad> habilidades = habilidadDao.obtenerPorPartida(idPartida);
                    for (int i = 0; i < habilidades.size(); i++) {
                        heroe.setHabilidad(i, habilidades.get(i));
                    }

                    LocalDateTime fecha = rs.getTimestamp("fecha_guardado").toLocalDateTime();
                    return new Partida(
                        rs.getInt("id"),
                        rs.getString("nombre_partida"),
                        heroe,
                        rs.getInt("piso_torre"),
                        fecha);
                }
            }
        }
        return null;
    }

    public void guardarProgreso(Partida partida) throws SQLException {
        String sql = "UPDATE partidas SET nivel_actual = ?, experiencia_actual = ?, " +
                     "vida_actual = ?, mana_actual = ?, ataque_actual = ?, piso_torre = ? " +
                     "WHERE id = ?";
        
        Heroe heroe = partida.getHeroe();
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, heroe.getNivel());
            pstmt.setInt(2, heroe.getExperiencia());
            pstmt.setInt(3, heroe.getVidaMax());
            pstmt.setInt(4, heroe.getManaMax());
            pstmt.setInt(5, heroe.getAtaque());
            pstmt.setInt(6, partida.getPisoActual());
            pstmt.setInt(7, partida.getId());
            pstmt.executeUpdate();
        }
    }

    public void borrarPartida(int idPartida) throws SQLException {
        String sql = "DELETE FROM partidas WHERE id = ?";

        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, idPartida);

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Partida con ID " + idPartida + " eliminada correctamente.");
            } else {
                System.out.println("No se encontró ninguna partida con el ID proporcionado.");
            }
        } catch (SQLException e) {
            System.err.println("Error al intentar borrar la partida: " + e.getMessage());
        }
    }

    public List<String> obtenerResumenPartidas() throws SQLException {
        List<String> resumenes = new ArrayList<>();
        String sql = "SELECT id, nombre_partida, nivel_actual FROM partidas";

        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    resumenes.add(rs.getInt("id") + " - " + rs.getString("nombre_partida") + " (Nivel " + rs.getInt("nivel_actual") + ") ");
                }
             }
             return resumenes;
    }

    public void ganarTorreYResetear(Partida partida) throws SQLException {
        String sql = "INSERT OR REPLACE INTO partida_habilidades (id_partida, id_habilidad, slot) " + 
                     "SELECT ?, id_habilidad, slot FROM heroe_habilidad WHERE id_heroe = ?";
        
        try {
            conexion.setAutoCommit(false);

            partida.setPisoActual(1);
            guardarProgreso(partida);

            try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
                pstmt.setInt(1, partida.getId());
                pstmt.setInt(2, partida.getHeroe().getId());
                pstmt.executeUpdate();
            }
            conexion.commit();
        } catch (SQLException e) {
            conexion.rollback();
            throw e;
        } finally {
            conexion.setAutoCommit(true);
        }
    }
}
