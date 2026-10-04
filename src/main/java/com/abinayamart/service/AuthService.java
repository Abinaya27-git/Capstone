package com.abinayamart.service;

import com.abinayamart.dao.UserDAO;
import com.abinayamart.exception.AbinayaMartException;
import com.abinayamart.model.Buyer;
import com.abinayamart.model.Role;
import com.abinayamart.model.Seller;
import com.abinayamart.model.User;

/** Authentication / registration logic. */
public class AuthService {
    private final UserDAO userDAO = new UserDAO();

    public User register(String name, String email, String password, Role role) throws AbinayaMartException {
        if (name == null || name.isBlank()) {
            throw new AbinayaMartException("Name is required.");
        }
        if (email == null || !email.contains("@")) {
            throw new AbinayaMartException("Valid email is required.");
        }
        if (password == null || password.length() < 4) {
            throw new AbinayaMartException("Password must be at least 4 characters.");
        }
        if (role == null) {
            throw new AbinayaMartException("Role is required (BUYER/SELLER/ADMIN).");
        }
        User user = (role == Role.SELLER) ? new Seller(name.trim(), email.trim(), password)
                : (role == Role.BUYER) ? new Buyer(name.trim(), email.trim(), password)
                : new User(name.trim(), email.trim(), password, Role.ADMIN);
        return userDAO.create(user);
    }

    public User login(String email, String password) throws AbinayaMartException {
        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            throw new AbinayaMartException("Email and password are required.");
        }
        User user = userDAO.findByEmail(email.trim());
        if (user == null) {
            throw new AbinayaMartException("No account found for: " + email);
        }
        if (!user.getPassword().equals(password)) {
            throw new AbinayaMartException("Incorrect password.");
        }
        return user;
    }
}
