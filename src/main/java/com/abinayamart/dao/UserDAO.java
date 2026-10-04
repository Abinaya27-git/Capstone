package com.abinayamart.dao;

import com.abinayamart.config.DBConnection;
import com.abinayamart.exception.AbinayaMartException;
import com.abinayamart.model.Admin;
import com.abinayamart.model.Buyer;
import com.abinayamart.model.Role;
import com.abinayamart.model.Seller;
import com.abinayamart.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/** JDBC data-access for users. */
public class UserDAO {

    private User mapRow(ResultSet rs) throws SQLException {
        Role role = Role.fromString(rs.getString("role"));
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String email = rs.getString("email");
        String password = rs.getString("password");
        if (role == Role.SELLER) {
            return new Seller(id, name, email, password);
        } else if (role == Role.ADMIN) {
            return new Admin(id, name, email, password);
        }
        return new Buyer(id, name, email, password);
    }

    public User create(User user) throws AbinayaMartException {
        String sql = "INSERT INTO users(name, email, password, role) VALUES(?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRole().name());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    user.setId(keys.getInt(1));
                }
            }
            return user;
        } catch (SQLException e) {
            if (e.getMessage() != null && e.getMessage().toLowerCase().contains("duplicate")) {
                throw new AbinayaMartException("Email already registered: " + user.getEmail());
            }
            throw new AbinayaMartException("Could not register user: " + e.getMessage(), e);
        }
    }

    public User findByEmail(String email) throws AbinayaMartException {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new AbinayaMartException("User lookup failed: " + e.getMessage(), e);
        }
    }

    public List<User> findAll() throws AbinayaMartException {
        String sql = "SELECT * FROM users ORDER BY id";
        List<User> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new AbinayaMartException("Could not list users: " + e.getMessage(), e);
        }
    }
}
