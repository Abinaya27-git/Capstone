package com.abinayamart.model;

import java.sql.Timestamp;

/** Order placed by a buyer. */
public class Order {
    private int id;
    private int buyerId;
    private String buyerName;
    private double totalAmount;
    private OrderStatus status;
    private String paymentMode;
    private Timestamp createdAt;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getBuyerId() { return buyerId; }
    public void setBuyerId(int buyerId) { this.buyerId = buyerId; }
    public String getBuyerName() { return buyerName; }
    public void setBuyerName(String buyerName) { this.buyerName = buyerName; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
    public String getPaymentMode() { return paymentMode; }
    public void setPaymentMode(String paymentMode) { this.paymentMode = paymentMode; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return String.format("Order #%d | Buyer:%s | Total:Rs.%.2f | %s | Pay:%s | %s",
                id, buyerName == null ? ("#" + buyerId) : buyerName,
                totalAmount, status, paymentMode, createdAt);
    }
}
