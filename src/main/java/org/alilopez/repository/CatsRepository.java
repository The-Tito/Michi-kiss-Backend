package org.alilopez.repository;

import org.alilopez.config.DatabaseConfig;
import org.alilopez.model.Cat;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CatsRepository {

    public int save(Cat cat)throws SQLException {
        int id_gato = 0;
        String query = "INSERT INTO gatos (edad,nombre,descripcion,id_status_gato,id_sexo,id_estado_salud) VALUES (?,?,?,?,?,?)";
        try(
                Connection conn = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
                    stmt.setInt(1, cat.getEdad());
                    stmt.setString(2, cat.getNombre());
                    stmt.setString(3, cat.getDescripcion());
                    stmt.setInt(4, cat.getId_status_gato());
                    stmt.setInt(5, cat.getId_sexo());
                    stmt.setInt(6, cat.getId_estado_salud());
                    stmt.executeUpdate();
                    ResultSet rs = stmt.getGeneratedKeys();
                    if (rs.next()) {
                        id_gato = rs.getInt(1);
                    }

        }
        return id_gato;
    }

    public void saveImgsCat(int id_gato, String url_imagen, Boolean principal)throws SQLException {
        String query = "INSERT INTO imagenes_gatos (id_gato,url_imagen,es_principal) VALUES (?,?,?)";
        try(
                Connection conn = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1, id_gato);
            stmt.setString(2, url_imagen);
            if (principal == true) {
                stmt.setInt(3, 1);
            }else{
                stmt.setInt(3, 0);
            }
            stmt.executeUpdate();
        }
    }
}
