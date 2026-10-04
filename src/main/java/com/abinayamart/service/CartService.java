package com.abinayamart.service;

import com.abinayamart.exception.AbinayaMartException;
import com.abinayamart.model.CartItem;
import com.abinayamart.model.Product;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** In-memory cart, one instance per buyer session. */
public class CartService {
    private final Map<Integer, CartItem> items = new LinkedHashMap<>();

    public void add(Product product, int qty) throws AbinayaMartException {
        if (product == null) {
            throw new AbinayaMartException("Product not found.");
        }
        if (qty <= 0) {
            throw new AbinayaMartException("Quantity must be at least 1.");
        }
        if (qty > product.getStock()) {
            throw new AbinayaMartException("Only " + product.getStock() + " in stock for " + product.getName());
        }
        CartItem existing = items.get(product.getId());
        if (existing == null) {
            items.put(product.getId(), new CartItem(product, qty));
        } else {
            int total = existing.getQuantity() + qty;
            if (total > product.getStock()) {
                throw new AbinayaMartException("Cart exceeds stock (" + product.getStock() + ").");
            }
            existing.setQuantity(total);
        }
    }

    public void remove(int productId) {
        items.remove(productId);
    }

    public void clear() {
        items.clear();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public List<CartItem> items() {
        return new ArrayList<>(items.values());
    }

    public double total() {
        return items.values().stream().mapToDouble(CartItem::getSubtotal).sum();
    }
}
