package com.abinayamart.dao;

import com.abinayamart.config.DBConnection;
import com.abinayamart.exception.AbinayaMartException;
import com.abinayamart.model.Review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** JDBC data-access for reviews. */
public class ReviewDAO {

    public void addOrUpdate(Review r) throws AbinayaMartException {
        if (r.getRating() < 1 || r.getRating() > 5) {
            throw new AbinayaMartException("Rating must be between 1 and 5.");
        }
        String sql = "INSERT INTO reviews(product_id, buyer_id, rating, comment) VALUES(?,?,?,?) "
                + "ON DUPLICATE KEY UPDATE rating=VALUES(rating), comment=VALUES(comment)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, r.getProductId());
            ps.setInt(2, r.getBuyerId());
            ps.setInt(3, r.getRating());
            ps.setString(4, r.getComment());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new AbinayaMartException("Could not save review: " + e.getMessage(), e);
        }
    }

    public List<Review> findByProduct(int productId) throws AbinayaMartException {
        String sql = "SELECT r.*, u.name AS buyer_name FROM reviews r JOIN users u ON u.id=r.buyer_id WHERE r.product_id=? ORDER BY r.id DESC";
        List<Review> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Review r = new Review();
                    r.setId(rs.getInt("id"));
                    r.setProductId(rs.getInt("product_id"));
                    r.setBuyerId(rs.getInt("buyer_id"));
                    r.setBuyerName(rs.getString("buyer_name"));
                    r.setRating(rs.getInt("rating"));
                    r.setComment(rs.getString("comment"));
                    r.setCreatedAt(rs.getTimestamp("created_at"));
                    list.add(r);
                }
            }
            return list;
        } catch (SQLException e) {
            throw new AbinayaMartException("Could not list reviews: " + e.getMessage(), e);
        }
    }

    public double avgRating(int productId) throws AbinayaMartException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT IFNULL(AVG(rating),0) FROM reviews WHERE product_id=?")) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            throw new AbinayaMartException("Could not compute rating: " + e.getMessage(), e);
        }
    }
}
