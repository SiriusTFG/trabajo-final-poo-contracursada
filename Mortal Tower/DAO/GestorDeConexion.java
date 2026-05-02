package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class GestorDeConexion {

    private static GestorDeConexion instancia;
    private Connection conexion;
    private static final String URL = "jdbc:sqlite:MortalTower.db";

    private GestorDeConexion() {
        try {
            this.conexion = DriverManager.getConnection(URL);
            iniciarBD();
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
        }
    }

    public static synchronized GestorDeConexion getInstancia() {
        if (instancia == null) {
            instancia = new GestorDeConexion();
        }
        return instancia;
    }

    public Connection getConexion() {
        return conexion;
    }

    private void iniciarBD() {

        String tablaHeroes = "CREATE TABLE IF NOT EXISTS heroes(" + 
        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
        "nombre TEXT NOT NULL, " + 
        "nivel INTEGER DEFAULT 1, " + 
        "experiencia INTEGER DEFAULT 0, " + 
        "vida_max INTEGER NOT NULL, " + 
        "mana_max INTEGER NOT NULL, " + 
        "ataque INTEGER NOT NULL, " +
        "defensa_base REAL DEFAULT 1.0, " + 
        "ruta_imagen TEXT NOT NULL);";

        String tablaEnemigos = "CREATE TABLE IF NOT EXISTS enemigos(" + 
        "id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
        "nombre TEXT NOT NULL, " +
        "vida_max INTEGER NOT NULL, " +
        "mana_max INTEGER NOT NULL, " +
        "ataque INTEGER NOT NULL, " + 
        "defensa_base REAL DEFAULT 1.0, " +
        "nivel_torre INTEGER NOT NULL, " + 
        "ruta_imagen TEXT NOT NULL);";

        String tablaHabilidades = "CREATE TABLE IF NOT EXISTS habilidades(" + 
        "id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
        "nombre TEXT NOT NULL, " +
        "descripcion TEXT NOT NULL, " +
        "tipo TEXT NOT NULL, " +
        "costo_mana INTEGER NOT NULL, " + 
        "valor_base INTEGER NOT NULL, " + 
        "prob_critico REAL, " + 
        "bonus_critico INTEGER, " + 
        "reduccion_danio REAL, " + 
        "cooldown_max INTEGER);";

        String tablaHeroeHabilidades = "CREATE TABLE IF NOT EXISTS heroe_habilidades(" + 
        "id_heroe  INTEGER, " + 
        "id_habilidad INTEGER, " + 
        "slot INTEGER CHECK(slot BETWEEN 0 AND 3), " + 
        "PRIMARY KEY (id_heroe, slot), " + 
        "FOREIGN KEY (id_heroe) REFERENCES heroes(id), " + 
        "FOREIGN KEY (id_habilidad) REFERENCES habilidades(id));";

        String tablaEnemigosHabilidades = "CREATE TABLE IF NOT EXISTS enemigos_habilidades(" +
        "id_enemigo INTEGER, " +
        "id_habilidad INTEGER, " +
        "slot INTEGER CHECK(slot BETWEEN 0 AND 3), " +
        "PRIMARY KEY (id_enemigo, slot), " +
        "FOREIGN KEY (id_enemigo) REFERENCES enemigos(id), " + 
        "FOREIGN KEY (id_habilidad) REFERENCES habilidades(id));";

        String tablaSprites = "CREATE TABLE IF NOT EXISTS sprites(" + 
        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
        "id_heroe INTEGER, " + 
        "id_enemigo INTEGER, " +
        "estado TEXT, " + 
        "orden INTEGER, " +
        "ruta_imagen TEXT NOT NULL, " + 
        "FOREIGN KEY (id_heroe) REFERENCES heroes(id), " + 
        "FOREIGN KEY (id_enemigo) REFERENCES enemigos(id));";

        String tablaPartidas = "CREATE TABLE IF NOT EXISTS partidas(" +
        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
        "nombre_partida TEXT NOT NULL, " +
        "id_heroe INTEGER NOT NULL, " +
        "nivel_actual INTEGER DEFAULT 1, " +
        "experiencia_actual INTEGER DEFAULT 0, " +
        "vida_actual INTEGER, " + 
        "mana_actual INTEGER, " +
        "ataque_actual INTEGER, " +
        "defensa_actual REAL, " +
        "piso_torre INTEGER DEFAULT 1, " +
        "fecha_guardado TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
        "FOREIGN KEY (id_heroe) REFERENCES heroes(id));"; 

        String tablaPartidasHabilidades = "CREATE TABLE IF NOT EXISTS partida_habilidades(" + 
        "id_partida INTEGER, " + 
        "id_habilidad INTEGER, " + 
        "slot INTEGER CHECK(slot BETWEEN 0 AND 3), " +
        "PRIMARY KEY (id_partida, slot), " +
        "FOREIGN KEY (id_partida) REFERENCES partidas(id) ON DELETE CASCADE, " + 
        "FOREIGN KEY (id_habilidad) REFERENCES habilidades(id));";

        try (Statement stmt = this.conexion.createStatement()) {
            stmt.execute(tablaHeroes);
            System.out.println("Tabla 'heroes' creada o ya existente");
            stmt.execute(tablaEnemigos);
            System.out.println("Tabla 'enemigos' creada o ya existente");
            stmt.execute(tablaHabilidades);
            System.out.println("Tabla 'habilidades' creada o ya existente");
            stmt.execute(tablaHeroeHabilidades);
            System.out.println("Tabla 'heroe_habilidades' creada o ya existente");
            stmt.execute(tablaEnemigosHabilidades);
            System.out.println("Tabla 'enemigos_habilidades' creada o ya existente");
            stmt.execute(tablaSprites);
            System.out.println("Tabla 'sprites' creada o ya existente");
            stmt.execute(tablaPartidas);
            System.out.println("Tabla 'partidas' creada o ya existente");
            stmt.execute(tablaPartidasHabilidades);
            System.out.println("Tabla 'partidas_habilidades' creada o ya existente");
        } catch (SQLException e) {
            System.err.println("Error en la base de datos: " + e.getMessage());
        }
    }
    
}
