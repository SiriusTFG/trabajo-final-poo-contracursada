package com.mortaTower.DAO;

import com.mortaTower.Modelo.Entidad;
import com.mortaTower.Modelo.Habilidad;
import com.mortaTower.Modelo.Heroe;
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
        Heroe heroe = new Heroe(rs.getInt("id"),
            rs.getString("nombre"),
            rs.getInt("vida_max"),
            rs.getInt("mana_max"),
            rs.getInt("nivel"),
            rs.getInt("experiencia"),
            rs.getInt("ataque"),
            rs.getDouble("defensa_base"));
        heroe.setRutaImagen(rs.getString("ruta_imagen"));

        List<Habilidad> habilidades = habilidadDao.obtenerPorEntidad(heroe.getId(), "Heroe");
        cargarSprites(heroe);
        for (int i = 0; i < habilidades.size(); i++) {
            heroe.setHabilidad(i, habilidades.get(i));
        }              
        return heroe;
    }

    private void cargarSprites(Heroe heroe) throws SQLException {
    String sql = "SELECT estado, ruta_imagen FROM sprites WHERE id_heroe = ?";
    try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
        pstmt.setInt(1, heroe.getId());
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String estadoCrudo = rs.getString("estado");
                String estadoStr = estadoCrudo.toUpperCase().replace(" ", "_");
               String ruta = rs.getString("ruta_imagen");
                try {
                    Entidad.Estado estadoEnum = Entidad.Estado.valueOf(estadoStr);
                    heroe.agregarImagenEstado(estadoEnum, ruta);
                    //System.out.println("Cargando en BD -> Héroe: " + heroe.getNombre() + " | Estado: " + estadoEnum + " | Ruta: " + ruta);
                    } catch (IllegalArgumentException e) {
                    //System.err.println("Estado desconocido en la BD: " + estadoCrudo + " (Intentamos buscar: " + estadoStr + ")");
                    }
                }
            }
        }
    }
}
