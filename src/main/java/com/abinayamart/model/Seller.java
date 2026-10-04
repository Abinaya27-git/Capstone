package com.abinayamart.model;

/** Seller user type. */
public class Seller extends User {
    public Seller() {
        super();
        this.role = Role.SELLER;
    }

    public Seller(int id, String name, String email, String password) {
        super(id, name, email, password, Role.SELLER);
    }

    public Seller(String name, String email, String password) {
        super(name, email, password, Role.SELLER);
    }
}
