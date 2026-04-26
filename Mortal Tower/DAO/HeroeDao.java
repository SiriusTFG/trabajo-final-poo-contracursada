package DAO;

import Modelo.Habilidad;
import Modelo.Heroe;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HeroeDao implements EntidadDao<Heroe> {
    
    private Connection conexion = GestorDeConexion.getInstancia().getConexion();
    private HabilidadDao habilidadDao = new HabilidadDao();

    @Override
    public Heroe obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM heroes WHERE id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapearHeroe(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Heroe> obtenerTodos() throws SQLException {
        String sql = "SELECT * FROM heroes";
        List<Heroe> lista = new ArrayList<>();
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapearHeroe(rs));
            }
        }
        return lista;
    }

    private Heroe mapearHeroe(ResultSet rs) throws SQLException {
        Heroe heroe = new Heroe(rs.getString("nombre"),
            rs.getInt("vida_max"),
            rs.getInt("mana_max"));
        heroe.setId(rs.getInt("id"));
        heroe.setAtaque(rs.getInt("ataque"));
        heroe.activarDefensa(rs.getDouble("defensa_base"));
        heroe.setRutaImagen(rs.getString("ruta_imagen"));

        List<Habilidad> habilidades = habilidadDao.obtenerPorEntidad(heroe.getId(), "Heroe");

        for (int i = 0; i < habilidades.size(); i++) {
            heroe.setHabilidad(i, habilidades.get(i));
        }              
        return heroe;
    }

}
