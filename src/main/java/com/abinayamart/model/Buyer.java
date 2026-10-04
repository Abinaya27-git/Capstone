package com.abinayamart.model;

/** Buyer user type. */
public class Buyer extends User {
    public Buyer() {
        super();
        this.role = Role.BUYER;
    }

    public Buyer(int id, String name, String email, String password) {
        super(id, name, email, password, Role.BUYER);
    }

    public Buyer(String name, String email, String password) {
        super(name, email, password, Role.BUYER);
    }
}
