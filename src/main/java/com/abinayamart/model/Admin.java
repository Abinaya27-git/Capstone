package com.abinayamart.model;

/** Admin user type. */
public class Admin extends User {
    public Admin() {
        super();
        this.role = Role.ADMIN;
    }

    public Admin(int id, String name, String email, String password) {
        super(id, name, email, password, Role.ADMIN);
    }
}
