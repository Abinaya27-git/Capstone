package com.abinayamart.service;

import com.abinayamart.dao.OrderDAO;
import com.abinayamart.exception.AbinayaMartException;
import com.abinayamart.model.CartItem;
import com.abinayamart.model.Order;
import com.abinayamart.model.OrderItem;
import com.abinayamart.model.OrderStatus;

import java.util.List;

/** Checkout + order management logic. */
public class OrderService {
    private final OrderDAO orderDAO = new OrderDAO();

    public Order checkout(int buyerId, List<CartItem> items, String paymentMode) throws AbinayaMartException {
        if (items == null || items.isEmpty()) {
            throw new AbinayaMartException("Cart is empty.");
        }
        return orderDAO.createOrder(buyerId, items, paymentMode);
    }

    public List<Order> myOrders(int buyerId) throws AbinayaMartException {
        return orderDAO.findByBuyer(buyerId);
    }

    public List<Order> allOrders() throws AbinayaMartException {
        return orderDAO.findAll();
    }

    public List<Order> sellerOrders(int sellerId) throws AbinayaMartException {
        return orderDAO.findBySeller(sellerId);
    }

    public List<OrderItem> itemsOf(int orderId) throws AbinayaMartException {
        return orderDAO.findItems(orderId);
    }

    public void updateStatus(int orderId, String status) throws AbinayaMartException {
        try {
            orderDAO.updateStatus(orderId, OrderStatus.fromString(status));
        } catch (IllegalArgumentException e) {
            throw new AbinayaMartException("Invalid status. Use PLACED/SHIPPED/DELIVERED/CANCELLED.");
        }
    }
}
