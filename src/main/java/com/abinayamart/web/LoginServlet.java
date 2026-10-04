package com.abinayamart.web;

import com.abinayamart.model.Role;
import com.abinayamart.model.User;
import com.abinayamart.service.AuthService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final AuthService authService = new AuthService();

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            User user = authService.login(email, password);

            if (user == null) {
                response.sendRedirect("index.jsp?error=invalid");
                return;
            }

            HttpSession session = request.getSession(true);
            session.setAttribute("user", user);

            if (user.getRole() == Role.BUYER) {

                response.sendRedirect("products.jsp");

            } else if (user.getRole() == Role.SELLER) {

                response.sendRedirect("seller");

            } else if (user.getRole() == Role.ADMIN) {

                response.sendRedirect("admin.jsp");

            } else {

                response.sendRedirect("index.jsp");
            }

        } catch (Exception e) {

            throw new ServletException(
                    "Login failed: " + e.getMessage(),
                    e
            );
        }
    }
}