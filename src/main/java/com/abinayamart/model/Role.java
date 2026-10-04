package com.abinayamart.model;

/** User roles supported by AbinayaMart. */
public enum Role {
    BUYER, SELLER, ADMIN;

    public static Role fromString(String s) {
        if (s == null) {
            return null;
        }
        return Role.valueOf(s.trim().toUpperCase());
    }
}
