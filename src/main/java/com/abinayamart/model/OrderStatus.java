package com.abinayamart.model;

/** Order status lifecycle. */
public enum OrderStatus {
    PLACED, SHIPPED, DELIVERED, CANCELLED;

    public static OrderStatus fromString(String s) {
        if (s == null) {
            return PLACED;
        }
        return OrderStatus.valueOf(s.trim().toUpperCase());
    }
}
