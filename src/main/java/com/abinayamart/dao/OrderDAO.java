package com.abinayamart.dao;

import com.abinayamart.config.DBConnection;
import com.abinayamart.exception.AbinayaMartException;
import com.abinayamart.model.CartItem;
import com.abinayamart.model.Order;
import com.abinayamart.model.OrderItem;
import com.abinayamart.model.OrderStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/** JDBC data-access for orders + order items. */
public class OrderDAO {

    private Order mapOrder(ResultSet rs) throws SQLException {
        Order o = new Order();
        o.setId(rs.getInt("id"));
        o.setBuyerId(rs.getInt("buyer_id"));
        o.setTotalAmount(rs.getDouble("total_amount"));
        o.setStatus(OrderStatus.fromString(rs.getString("status")));
        o.setPaymentMode(rs.getString("payment_mode"));
        o.setCreatedAt(rs.getTimestamp("created_at"));
        try {
            o.setBuyerName(rs.getString("buyer_name"));
        } catch (SQLException ignored) {
        }
        return o;
    }

    /** Creates order + items + stock reduction in one transaction. */
    public Order createOrder(int buyerId, List<CartItem> items, String paymentMode) throws AbinayaMartException {
        if (items == null || items.isEmpty()) {
            throw new AbinayaMartException("Cart is empty. Add products before checkout.");
        }
        double total = items.stream().mapToDouble(CartItem::getSubtotal).sum();
        ProductDAO productDAO = new ProductDAO();
        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);
            try {
                int orderId;
                try (PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO orders(buyer_id, total_amount, status, payment_mode) VALUES(?,?,'PLACED',?)",
                        Statement.RETURN_GENERATED_KEYS)) {
                    ps.setInt(1, buyerId);
                    ps.setDouble(2, total);
                    ps.setString(3, paymentMode);
                    ps.executeUpdate();
                    try (ResultSet keys = ps.getGeneratedKeys()) {
                        keys.next();
                        orderId = keys.getInt(1);
                    }
                }
                try (PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO order_items(order_id, product_id, qty, price) VALUES(?,?,?,?)")) {
                    for (CartItem ci : items) {
                        ps.setInt(1, orderId);
                        ps.setInt(2, ci.getProduct().getId());
                        ps.setInt(3, ci.getQuantity());
                        ps.setDouble(4, ci.getProduct().getPrice());
                        ps.addBatch();
                    }
                    ps.executeBatch();
                }
                for (CartItem ci : items) {
                    productDAO.reduceStock(con, ci.getProduct().getId(), ci.getQuantity());
                }
                con.commit();
                Order o = new Order();
                o.setId(orderId);
                o.setBuyerId(buyerId);
                o.setTotalAmount(total);
                o.setStatus(OrderStatus.PLACED);
                o.setPaymentMode(paymentMode);
                return o;
            } catch (Exception e) {
                con.rollback();
                if (e instanceof AbinayaMartException) {
                    throw (AbinayaMartException) e;
                }
                throw new AbinayaMartException("Checkout failed: " + e.getMessage(), e);
            } finally {
                con.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new AbinayaMartException("Checkout failed: " + e.getMessage(), e);
        }
    }

    public List<Order> findByBuyer(int buyerId) throws AbinayaMartException {
        String sql = "SELECT o.*, u.name AS buyer_name FROM orders o JOIN users u ON u.id=o.buyer_id WHERE o.buyer_id=? ORDER BY o.id DESC";
        return queryOrders(sql, buyerId);
    }

    public List<Order> findAll() throws AbinayaMartException {
        String sql = "SELECT o.*, u.name AS buyer_name FROM orders o JOIN users u ON u.id=o.buyer_id ORDER BY o.id DESC";
        List<Order> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapOrder(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new AbinayaMartException("Could not list orders: " + e.getMessage(), e);
        }
    }

    /** Orders containing at least one product of the given seller. */
    public List<Order> findBySeller(int sellerId) throws AbinayaMartException {
        String sql = "SELECT DISTINCT o.*, u.name AS buyer_name FROM orders o "
                + "JOIN users u ON u.id=o.buyer_id "
                + "JOIN order_items oi ON oi.order_id=o.id "
                + "JOIN products p ON p.id=oi.product_id "
                + "WHERE p.seller_id=? ORDER BY o.id DESC";
        return queryOrders(sql, sellerId);
    }

    private List<Order> queryOrders(String sql, int param) throws AbinayaMartException {
        List<Order> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, param);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapOrder(rs));
                }
            }
            return list;
        } catch (SQLException e) {
            throw new AbinayaMartException("Could not list orders: " + e.getMessage(), e);
        }
    }

    public List<OrderItem> findItems(int orderId) throws AbinayaMartException {
        String sql = "SELECT oi.*, p.name AS pname FROM order_items oi JOIN products p ON p.id=oi.product_id WHERE oi.order_id=?";
        List<OrderItem> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrderItem it = new OrderItem();
                    it.setId(rs.getInt("id"));
                    it.setOrderId(rs.getInt("order_id"));
                    it.setProductId(rs.getInt("product_id"));
                    it.setProductName(rs.getString("pname"));
                    it.setQty(rs.getInt("qty"));
                    it.setPrice(rs.getDouble("price"));
                    list.add(it);
                }
            }
            return list;
        } catch (SQLException e) {
            throw new AbinayaMartException("Could not list order items: " + e.getMessage(), e);
        }
    }

    public void updateStatus(int orderId, OrderStatus status) throws AbinayaMartException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("UPDATE orders SET status=? WHERE id=?")) {
            ps.setString(1, status.name());
            ps.setInt(2, orderId);
            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new AbinayaMartException("Order not found: #" + orderId);
            }
        } catch (SQLException e) {
            throw new AbinayaMartException("Could not update order: " + e.getMessage(), e);
        }
    }
}
