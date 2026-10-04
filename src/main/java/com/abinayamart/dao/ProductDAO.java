package com.abinayamart.dao;

import com.abinayamart.config.DBConnection;
import com.abinayamart.exception.AbinayaMartException;
import com.abinayamart.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    private Product mapRow(ResultSet rs) throws SQLException {

        Product p = new Product();

        p.setId(rs.getInt("id"));
        p.setSellerId(rs.getInt("seller_id"));
        p.setName(rs.getString("name"));
        p.setDescription(rs.getString("description"));
        p.setCategory(rs.getString("category"));
        p.setPrice(rs.getDouble("price"));
        p.setStock(rs.getInt("stock"));

        // Product image URL
        try {
            p.setImageUrl(rs.getString("image_url"));
        } catch (SQLException ignored) {
        }

        try {
            p.setSellerName(rs.getString("seller_name"));
        } catch (SQLException ignored) {
        }

        try {
            p.setAvgRating(rs.getDouble("avg_rating"));
        } catch (SQLException ignored) {
        }

        return p;
    }

    private static final String BASE_SELECT =
            "SELECT p.*, u.name AS seller_name, "
            + "IFNULL((SELECT AVG(rating) FROM reviews r "
            + "WHERE r.product_id = p.id), 0) AS avg_rating "
            + "FROM products p "
            + "JOIN users u ON u.id = p.seller_id ";

    // Add new product
    public Product create(Product p) throws AbinayaMartException {

        String sql = "INSERT INTO products "
                + "(seller_id, name, description, category, price, stock, image_url) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, p.getSellerId());
            ps.setString(2, p.getName());
            ps.setString(3, p.getDescription());
            ps.setString(4, p.getCategory());
            ps.setDouble(5, p.getPrice());
            ps.setInt(6, p.getStock());
            ps.setString(7, p.getImageUrl());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    p.setId(keys.getInt(1));
                }
            }

            return p;

        } catch (SQLException e) {
            throw new AbinayaMartException(
                    "Could not add product: " + e.getMessage(), e);
        }
    }

    // Update product
    public void update(Product p) throws AbinayaMartException {

        String sql = "UPDATE products SET "
                + "name=?, description=?, category=?, price=?, stock=?, image_url=? "
                + "WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getName());
            ps.setString(2, p.getDescription());
            ps.setString(3, p.getCategory());
            ps.setDouble(4, p.getPrice());
            ps.setInt(5, p.getStock());
            ps.setString(6, p.getImageUrl());
            ps.setInt(7, p.getId());

            int rows = ps.executeUpdate();

            if (rows == 0) {
                throw new AbinayaMartException(
                        "Product not found: #" + p.getId());
            }

        } catch (SQLException e) {
            throw new AbinayaMartException(
                    "Could not update product: " + e.getMessage(), e);
        }
    }

    // Delete product
    public void delete(int productId) throws AbinayaMartException {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "DELETE FROM products WHERE id=?")) {

            ps.setInt(1, productId);

            int rows = ps.executeUpdate();

            if (rows == 0) {
                throw new AbinayaMartException(
                        "Product not found: #" + productId);
            }

        } catch (SQLException e) {
            throw new AbinayaMartException(
                    "Could not delete product: " + e.getMessage(), e);
        }
    }

    // Find product by ID
    public Product findById(int id) throws AbinayaMartException {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     BASE_SELECT + "WHERE p.id = ?")) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapRow(rs);
                }

                return null;
            }

        } catch (SQLException e) {
            throw new AbinayaMartException(
                    "Product lookup failed: " + e.getMessage(), e);
        }
    }

    // Find all products
    public List<Product> findAll() throws AbinayaMartException {
        return search(null, null);
    }

    // Find products by seller
    public List<Product> findBySeller(int sellerId)
            throws AbinayaMartException {

        List<Product> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     BASE_SELECT
                     + "WHERE p.seller_id = ? "
                     + "ORDER BY p.id")) {

            ps.setInt(1, sellerId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }

            return list;

        } catch (SQLException e) {
            throw new AbinayaMartException(
                    "Could not list seller products: "
                    + e.getMessage(), e);
        }
    }

    // Search products
    public List<Product> search(String keyword, String category)
            throws AbinayaMartException {

        StringBuilder sb =
                new StringBuilder(BASE_SELECT + "WHERE 1=1 ");

        if (keyword != null && !keyword.isBlank()) {

            sb.append(
                    "AND (p.name LIKE ? OR p.description LIKE ?) ");
        }

        if (category != null && !category.isBlank()) {

            sb.append("AND p.category = ? ");
        }

        sb.append("ORDER BY p.id");

        List<Product> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sb.toString())) {

            int i = 1;

            if (keyword != null && !keyword.isBlank()) {

                ps.setString(
                        i++,
                        "%" + keyword.trim() + "%");

                ps.setString(
                        i++,
                        "%" + keyword.trim() + "%");
            }

            if (category != null && !category.isBlank()) {

                ps.setString(
                        i++,
                        category.trim());
            }

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }

            return list;

        } catch (SQLException e) {
            throw new AbinayaMartException(
                    "Product search failed: "
                    + e.getMessage(), e);
        }
    }

    // Find categories
    public List<String> findCategories()
            throws AbinayaMartException {

        List<String> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT DISTINCT category "
                     + "FROM products ORDER BY category");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(rs.getString(1));
            }

            return list;

        } catch (SQLException e) {
            throw new AbinayaMartException(
                    "Could not list categories: "
                    + e.getMessage(), e);
        }
    }

    // Reduce stock after purchase
    public void reduceStock(
            Connection con,
            int productId,
            int qty)
            throws SQLException, AbinayaMartException {

        try (PreparedStatement ps = con.prepareStatement(
                "UPDATE products "
                + "SET stock = stock - ? "
                + "WHERE id = ? AND stock >= ?")) {

            ps.setInt(1, qty);
            ps.setInt(2, productId);
            ps.setInt(3, qty);

            int rows = ps.executeUpdate();

            if (rows == 0) {
                throw new AbinayaMartException(
                        "Insufficient stock for product #"
                        + productId);
            }
        }
    }
}