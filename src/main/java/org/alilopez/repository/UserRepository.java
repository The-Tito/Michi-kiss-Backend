package org.alilopez.repository;

import org.alilopez.config.DatabaseConfig;
import org.alilopez.model.DTO.LoginDTO;
import org.alilopez.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    public List<User> findAll() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try (
                Connection conn = DatabaseConfig.getDataSource().getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                User u = new User();
                u.setId_usuario(rs.getInt("id"));
                u.setNombre(rs.getString("name"));
                u.setCorreo(rs.getString("email"));
                users.add(u);
            }
        }
        return users;
    }

    public User findByIdUser(int idUser) throws SQLException {
        User user = null;
        String query = "SELECT * FROM usuarios WHERE id_usuario = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idUser);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setId_usuario(rs.getInt("id_usuario"));
                    user.setNombre(rs.getString("nombre"));
                    user.setCorreo(rs.getString("correo"));
                    user.setNum_telefono(rs.getString("num_telefono"));
                    user.setEdad(rs.getInt("edad"));
                    user.setDireccion(rs.getString("direccion"));
                    user.setId_genero(rs.getInt("id_genero"));

                }
            }
        }

        return user;
    }

    public void save(User user) throws SQLException {
        String checkQuery = "SELECT * FROM usuarios WHERE correo = ?";

        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(checkQuery, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getCorreo());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    if (rs.getString("correo").equals(user.getCorreo())) {
                        String erroMSG = "Correo ya registrado";
                        throw new SQLException(erroMSG);
                    }
                }
            }
        }
        String query = "INSERT INTO usuarios (nombre,edad,id_genero,correo,num_telefono,direccion,contrasena,id_rol ) VALUES (?,?,?,?,?,?,?,?)";
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getNombre());
            stmt.setInt(2, user.getEdad());
            stmt.setInt(3, user.getId_genero());
            stmt.setString(4, user.getCorreo());
            stmt.setString(5, user.getNum_telefono());
            stmt.setString(6, user.getDireccion());
            String hashedPassword = BCrypt.hashpw(user.getContrasena(), BCrypt.gensalt());
            stmt.setString(7, hashedPassword);
            stmt.setInt(8, user.getId_rol());
            stmt.executeUpdate();
        }
    }

    public LoginDTO login(User user) throws SQLException {
        LoginDTO response = null;
        String query = "SELECT nombre, id_rol,correo,contrasena,id_usuario FROM usuarios WHERE correo = ?";
        try (Connection conn = DatabaseConfig.getDataSource().getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getCorreo());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String hashedPasswordFromDB = rs.getString("contrasena");
                    String password = user.getContrasena();
                    // Verificar contraseña usando BCrypt
                    if (BCrypt.checkpw(password, hashedPasswordFromDB)) {
                        response = new LoginDTO();
                        response.setNombre(rs.getString("nombre"));
                        response.setId_rol(rs.getInt("id_rol"));
                        response.setId_usuario(rs.getInt("id_usuario"));
                    } else {
                        // Contraseña incorrecta
                        throw new SQLException("Correo o contraseña incorrecto");
                    }
                }
            }
        }
        return response;

    }
}
