package com.abinayamart.model;

/** Product entity sold on AbinayaMart. */
public class Product {

    private int id;
    private int sellerId;
    private String sellerName;
    private String name;
    private String description;
    private String category;
    private double price;
    private int stock;
    private double avgRating;
    private String imageUrl;

    public Product() {
    }

    public Product(int sellerId, String name, String description,
                   String category, double price, int stock) {
        this.sellerId = sellerId;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return String.format(
                "[%d] %s | %s | Rs.%.2f | Stock:%d | Rating:%.1f | Seller:%s",
                id,
                name,
                category,
                price,
                stock,
                avgRating,
                sellerName == null ? ("#" + sellerId) : sellerName
        );
    }
}