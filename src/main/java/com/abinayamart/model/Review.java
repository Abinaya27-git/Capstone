package com.abinayamart.model;

import java.sql.Timestamp;

/** Product review with 1-5 star rating. */
public class Review {
    private int id;
    private int productId;
    private int buyerId;
    private String buyerName;
    private int rating;
    private String comment;
    private Timestamp createdAt;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }
    public int getBuyerId() { return buyerId; }
    public void setBuyerId(int buyerId) { this.buyerId = buyerId; }
    public String getBuyerName() { return buyerName; }
    public void setBuyerName(String buyerName) { this.buyerName = buyerName; }
    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return String.format("%d/5 by %s: %s", rating,
                buyerName == null ? ("user#" + buyerId) : buyerName,
                comment == null ? "" : comment);
    }
}
